package com.example.finalproject.UI;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.finalproject.Data.NasaPhotoDatabase;
import com.example.finalproject.Data.NasaPhotoInfo;
import com.example.finalproject.Data.NasaPhotoInfoDAO;
import com.example.finalproject.R;
import com.example.finalproject.databinding.NasaPhotoInformationBinding;

/** This class creates a details page of the selected fragment
 * @author Laura Mayer 040 882 118
 * @version 1.0
 */
public class NasaPhotoDetailFragment extends Fragment {

    /** This holds the selected nasa photo object */
    NasaPhotoInfo selected;

    /** This is a constructor for simple access
     *
     */
    public NasaPhotoDetailFragment(){}

    /** This is a constructor for initializing with a selected nasa photo abject
     *
     * @param photo This is the selected nasa photo object
     */
    public NasaPhotoDetailFragment(NasaPhotoInfo photo){selected = photo;}

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

        Button saveButton = binding.actionButton;

        binding.roverImageFull.setImageBitmap(selected.getRoverImage());
        binding.roverNameD.setText(selected.getCameraName());
        binding.imageURL.setText(selected.getImageURL());
        saveButton.setText(R.string.save_button);

        saveButton.setOnClickListener(clk -> {
            nasaDAO.insertImage(selected);

            Context context = getContext().getApplicationContext();
            CharSequence message = "Saved photo to database";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, message, duration);
            toast.show();
        });

        return binding.getRoot();
    }



}
