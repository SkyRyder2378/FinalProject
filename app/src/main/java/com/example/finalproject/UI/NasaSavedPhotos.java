package com.example.finalproject.UI;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.finalproject.Data.NasaPhotoDatabase;
import com.example.finalproject.Data.NasaPhotoInfo;
import com.example.finalproject.Data.NasaPhotoInfoDAO;
import com.example.finalproject.Data.NasaViewModel;
import com.example.finalproject.R;
import com.example.finalproject.databinding.ActivityNasaSavedPhotosBinding;
import com.example.finalproject.databinding.NasaPhotoBinding;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/** This class controls the saved photos activity
 * @author Laura Mayer 040 882 118
 * @version 1.0
 */
public class NasaSavedPhotos extends AppCompatActivity {

    /** This hold the binding for the nasa saved photos activity */
    private ActivityNasaSavedPhotosBinding binding;

    /** This holds the RecyclerView Adapter */
    RecyclerView.Adapter nasaAdapter;

    /** This holds the array of nasa photo objects */
    ArrayList<NasaPhotoInfo> nasaPhotos;

    /** This holds the nasa view model */
    NasaViewModel nasaModel;

    /** This hold the access to the nasa photos database */
    NasaPhotoInfoDAO nasaDAO;

    public int position;

    private Executor thread;

    /** This method creates and controls the nasa saved photos activity
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->
        {
            NasaPhotoDatabase db = Room.databaseBuilder(getApplicationContext(), NasaPhotoDatabase.class, "nasa-photos")
                    .fallbackToDestructiveMigration()
                    .build();
            nasaDAO = db.nPDAO();
        });

        binding = ActivityNasaSavedPhotosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nasaRecycler.setLayoutManager(new LinearLayoutManager(this));

        nasaModel = new ViewModelProvider(this).get(NasaViewModel.class);
        nasaPhotos = nasaModel.nasaPhotos.getValue();
        if(nasaPhotos == null){
            nasaModel.nasaPhotos.setValue(nasaPhotos = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                nasaPhotos.addAll(nasaDAO.getAllImages());
                runOnUiThread(()-> binding.nasaRecycler.setAdapter(nasaAdapter));
            });
        }

        binding.nasaRecycler.setAdapter(nasaAdapter = new RecyclerView.Adapter<SavesRowHolder>() {
            @NonNull
            @Override
            public SavesRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view;
                SavesRowHolder rowHolder;

                NasaPhotoBinding newBinding = NasaPhotoBinding.inflate(getLayoutInflater());
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nasa_photo, parent, false);
                rowHolder = new SavesRowHolder(newBinding.getRoot());
                return rowHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull SavesRowHolder holder, int position) {
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

        nasaModel.selectedPhoto.observe(this, (newPhoto) -> {
            SavedNasaPhotoDetailFragment photoFragment = new SavedNasaPhotoDetailFragment(newPhoto);
            photoFragment.setItemPosition(position);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.nasaFrameLayout, photoFragment);
            transaction.commit();
            transaction.addToBackStack("");
        });

    }

    /** This is an inner class that creates a row holder for the saved nasa photo objects
     *
     */
    class SavesRowHolder extends RecyclerView.ViewHolder{

        /** This holds an image view that contains the rover image */
        ImageView marsImage;
        /** This holds a text view that contains the rover name */
        TextView roverName;

        int position;


        /** This method creates an item view of the rover photo object
         *
         * @param itemView
         */
        public SavesRowHolder(@NonNull View itemView){
            super(itemView);

            itemView.setOnClickListener(clk -> {
                position = getAbsoluteAdapterPosition();
                nasaModel.position = position;
                NasaPhotoInfo selected = nasaPhotos.get(position);

                nasaModel.selectedPhoto.postValue(selected);
            });

            marsImage = itemView.findViewById(R.id.marsImage);
            roverName = itemView.findViewById(R.id.roverName);
        }

    }

}