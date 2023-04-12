package com.example.finalproject.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.finalproject.Data.NasaPhotoDatabase;
import com.example.finalproject.Data.NasaPhotoInfo;
import com.example.finalproject.Data.NasaPhotoInfoDAO;
import com.example.finalproject.Data.NasaViewModel;
import com.example.finalproject.R;
import com.example.finalproject.databinding.ActivityNasaSavedPhotosBinding;
import com.example.finalproject.databinding.NasaPhotoInformationBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/** This class creates the details page of the saved fragment information
 * @author Laura Mayer 040 882 118
 * @version 1.0
 */
public class SavedNasaPhotoDetailFragment extends Fragment {

    /** This holds the selected nasa photo object */
    NasaPhotoInfo selected;

    ArrayList<NasaPhotoInfo> nasaPhotos;

    RecyclerView.Adapter nasaAdapter;

    NasaViewModel nasaModel;

    Executor thread;

    int position;

    /** This is a constructor for simple access
     *
     */
    public SavedNasaPhotoDetailFragment(){}

    /** This is a constructor for initializing with a selected nasa photo abject
     *
     * @param photo This is the selected nasa photo object
     */
    public SavedNasaPhotoDetailFragment(NasaPhotoInfo photo){selected = photo;}

    public void setItemPosition(int pos){position = pos;}

    /** This method returns the selected nasa photo object
     *
     * @param photo This is the selected nasa photo object
     */
    public void displayPhoto(NasaPhotoInfo photo){selected = photo;}

    /** This method creates a fragment view of the selected nasa photo object
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return A fragment view of the selected nasa photo object
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);

        NasaPhotoDatabase db = Room.databaseBuilder(getContext().getApplicationContext(), NasaPhotoDatabase.class, "nasa-photos").build();
        NasaPhotoInfoDAO nasaDAO = db.nPDAO();

        NasaPhotoInformationBinding binding = NasaPhotoInformationBinding.inflate(getLayoutInflater());
        ActivityNasaSavedPhotosBinding sourceBinding = ActivityNasaSavedPhotosBinding.inflate(getLayoutInflater());

        nasaModel = new ViewModelProvider(this).get(NasaViewModel.class);
        nasaPhotos = nasaModel.nasaPhotos.getValue();
        if(nasaPhotos == null){
            nasaModel.nasaPhotos.setValue(nasaPhotos = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                nasaPhotos.addAll(nasaDAO.getAllImages());
                sourceBinding.nasaRecycler.setAdapter(nasaAdapter);
            });
        }
        Button deleteButton = binding.actionButton;

        binding.roverImageFull.setImageBitmap(selected.getRoverImage());
        binding.roverNameD.setText(selected.getCameraName());
        binding.imageURL.setText(selected.getImageURL());
        deleteButton.setText(R.string.delete_button);
        deleteButton.setBackgroundColor(Color.RED);

        deleteButton.setOnClickListener(clk -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext().getApplicationContext());
            builder.setMessage("Do you want to delete this photo?");
            builder.setTitle("Question:");
            builder.setNegativeButton("no", (dialog, cl) -> {  });
            builder.setPositiveButton("yes", (dialog, cl) -> {
                thread = Executors.newSingleThreadExecutor();
                thread.execute(() -> {
                    nasaDAO.deleteImage(selected);
                });

                nasaPhotos.remove(selected);
                nasaAdapter.notifyItemRemoved(position);
                Snackbar.make(binding.roverImageFull, "You deleted the current photo from the database", Snackbar.LENGTH_LONG).show();

            });
            builder.create().show();
        });

        return binding.getRoot();
    }
}
