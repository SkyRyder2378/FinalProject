package com.example.finalproject.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalproject.Data.NasaPhotoDatabase;
import com.example.finalproject.Data.NasaPhotoInfo;
import com.example.finalproject.Data.NasaPhotoInfoDAO;
import com.example.finalproject.Data.NasaViewModel;
import com.example.finalproject.R;
import com.example.finalproject.databinding.ActivityNasaSampleInputBinding;
import com.example.finalproject.databinding.ActivityNasaSavedPhotosBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NasaSampleInput extends AppCompatActivity {

    /** This hold the binding for the nasa saved photos activity */
    private ActivityNasaSampleInputBinding binding;

    /** This holds the array of nasa photo objects */
    ArrayList<NasaPhotoInfo> nasaPhotos;

    /** This holds the nasa view model */
    NasaViewModel nasaModel;

    /** This holds the RecyclerView Adapter */
    RecyclerView.Adapter nasaAdapter;

    /** This hold the access to the nasa photos database */
    NasaPhotoInfoDAO nasaDAO;

    /** This holds the shared preferences */
    SharedPreferences prefs;

    private Executor thread;


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

        binding = ActivityNasaSampleInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = getSharedPreferences("NasaData", Context.MODE_PRIVATE);

        String defaultS = "";
        String cameraInput = prefs.getString("cameraInput", "");
        binding.cameraNameInput.setText(cameraInput);

        String roverInput = prefs.getString("roverInput", "");
        binding.roverNameInput.setText(roverInput);

        String urlInput = prefs.getString("urlInput", "");
        binding.imageURLInput.setText(urlInput);

        nasaModel = new ViewModelProvider(this).get(NasaViewModel.class);
        nasaPhotos = nasaModel.nasaPhotos.getValue();
        if(nasaPhotos == null){
            nasaModel.nasaPhotos.setValue(nasaPhotos = new ArrayList<>());
        }

        Button saveButton = binding.inputSaveButton;

        saveButton.setOnClickListener(click -> {
            String roverNameInput = binding.roverNameInput.getText().toString();
            String cameraNameInput = binding.cameraNameInput.getText().toString();
            String imageURLInput = binding.imageURLInput.getText().toString();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("roverInput", roverNameInput);
            editor.putString("cameraInput", cameraNameInput);
            editor.putString("urlInput", imageURLInput);
            editor.apply();

            String roverName = String.valueOf(binding.roverNameInput.getText());
            String cameraName = String.valueOf(binding.cameraNameInput.getText());
            String imageURL = String.valueOf(binding.imageURLInput.getText());

            NasaPhotoInfo nasaPhoto = new NasaPhotoInfo(roverName, cameraName, imageURL);

            nasaPhotos.add(nasaPhoto);

            thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                nasaDAO.insertImage(nasaPhoto);
            });

            Context context = getApplicationContext();
            CharSequence message = "Saved photo to database";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, message, duration);
            toast.show();
        });



    }

    @Override
    protected void onPause() {
        super.onPause();

        String roverNameInput = binding.roverNameInput.getText().toString();
        String cameraNameInput = binding.cameraNameInput.getText().toString();
        String imageURLInput = binding.imageURLInput.getText().toString();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("roverInput", roverNameInput);
        editor.putString("cameraInput", cameraNameInput);
        editor.putString("urlInput", imageURLInput);
        editor.apply();
    }
}