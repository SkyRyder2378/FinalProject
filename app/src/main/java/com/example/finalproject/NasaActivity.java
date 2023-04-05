package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.databinding.ActivityNasaBinding;
import com.example.finalproject.databinding.NasaPhotoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NasaActivity extends AppCompatActivity {

    private ActivityNasaBinding binding;

    SharedPreferences prefs;

    RequestQueue queue = null;

    RecyclerView.Adapter nasaAdapter;

    ArrayList<NasaPhotoInfo> nasaPhotos;

    NasaViewModel nasaModel;

    NasaPhotoInfoDAO nasaDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NasaPhotoDatabase db = Room.databaseBuilder(getApplicationContext(), NasaPhotoDatabase.class, "nasa-photos").build();
        nasaDAO = db.nPDAO();

        queue = Volley.newRequestQueue(this);

        prefs = getSharedPreferences("NasaData", Context.MODE_PRIVATE);

        String searchBar = prefs.getString("searchBar", "");
        binding.nasaSearchBar.setText(searchBar);

        binding = ActivityNasaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nasaRecycler.setLayoutManager(new LinearLayoutManager(this));

        nasaModel = new ViewModelProvider(this).get(NasaViewModel.class);
        nasaPhotos = nasaModel.nasaPhotos.getValue();
        if(nasaPhotos == null){
            nasaModel.nasaPhotos.setValue(nasaPhotos = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                nasaPhotos.addAll(nasaDAO.getAllImages());
                binding.nasaRecycler.setAdapter(nasaAdapter);
            });
        }

        binding.nasaSearchButton.setOnClickListener(click -> {
            String search = binding.nasaSearchBar.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("searchBar", search);
            editor.apply();
            int s = Integer.parseInt(search);
            if(s >=0 && s<=1000){
                String stringURL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol="+s+"&api_key=ZTccaVancF9fVehaEVHOQPuoAvuWyzcAKJggN4DW";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        try{
                            JSONObject jsonPhotos = response.getJSONObject("photos");
                            JSONArray jsonPhotoArray = new JSONArray();
                            jsonPhotoArray.put(jsonPhotos);

                            int length = jsonPhotos.length();
                            JSONObject tempItem;
                            NasaPhotoInfo tempPhoto;
                            for(int i=0; i<=length; i++){
                                tempItem = jsonPhotoArray.getJSONObject(i);
                                JSONObject cameraArray = tempItem.getJSONObject("camera");
                                String imageURL = tempItem.getString("img_scr");
                                JSONObject roverArray = tempItem.getJSONObject("rover");

                                String cameraName = cameraArray.getString("full_name");
                                String roverName = roverArray.getString("name");
                                Bitmap tempImg = null;

                                try {
                                    tempImg = BitmapFactory.decodeStream((InputStream) new URL(imageURL).getContent());
                                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                    tempImg.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                                    byte[] tempByteArray = outputStream.toByteArray();
                                }catch(MalformedURLException malURLE){
                                    malURLE.printStackTrace();
                                }catch(IOException ioE){
                                    ioE.printStackTrace();
                                }
                                tempPhoto = new NasaPhotoInfo(tempImg, roverName, cameraName, imageURL);
                                nasaPhotos.add(tempPhoto);
                                nasaAdapter.notifyItemInserted(nasaPhotos.size()-1);

                            }
                        } catch(JSONException jE){ jE.printStackTrace(); }
                    }
                    }
                    , (error) -> {  });
                queue.add(request);

            }
        });

        nasaModel.selectedPhoto.observe(this, (newPhoto) -> {
            NasaPhotoDetailFragment photoFragment = new NasaPhotoDetailFragment(newPhoto);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.nasaFrameLayout, photoFragment);
            transaction.commit();
            transaction.addToBackStack("");
        });



        binding.nasaRecycler.setAdapter(nasaAdapter = new RecyclerView.Adapter<NasaRowHolder>() {
            @NonNull
            @Override
            public NasaRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view;
                NasaRowHolder rowHolder;

                NasaPhotoBinding newBinding = NasaPhotoBinding.inflate(getLayoutInflater());
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nasa_photo, parent, false);
                rowHolder = new NasaRowHolder(newBinding.getRoot());
                return rowHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull NasaRowHolder holder, int position) {
                Bitmap i = nasaPhotos.get(position).getRoverImage();
                String r = nasaPhotos.get(position).getCameraName();//correction
                holder.marsImage.setImageBitmap(i);
                holder.roverName.setText(r);
            }

            @Override
            public int getItemCount() {
                return nasaPhotos.size();
            }
        });
    }

    class NasaRowHolder extends RecyclerView.ViewHolder{

        ImageView marsImage;
        TextView roverName;

        public NasaRowHolder(@NonNull View itemView){
            super(itemView);

            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                NasaPhotoInfo selected = nasaPhotos.get(position);

                nasaModel.selectedPhoto.postValue(selected);
            });

            marsImage = itemView.findViewById(R.id.marsImage);
            roverName = itemView.findViewById(R.id.roverName);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        String search = binding.nasaSearchBar.getText().toString();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("searchBar", search);
        editor.apply();
    }
}

