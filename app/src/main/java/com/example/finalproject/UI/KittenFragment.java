/**
 *
 A fragment that displays details of a selected Kitten object.
 */
package com.example.finalproject.UI;
import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.Data.Kitten;
import com.example.finalproject.R;
import com.example.finalproject.databinding.KittenDetailsBinding;

/**
 * @author IQRAM
 * @version 1.0
 */
public class KittenFragment extends Fragment {

    Kitten selected;

    /**
     * Constructor that accepts a Kitten object as a parameter and sets it as the selected object.
     *
     * @param k The Kitten object to be displayed in this fragment.
     */
    public KittenFragment(Kitten k) {
        selected = k;
    }

    /**
     * Called when a menu item is selected.
     *
     * @param item The selected MenuItem object.
     * @return true.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        return true;
    }

    /**
     * Called when the fragment should create its view hierarchy.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState This fragment's previously saved state, if any.
     * @return The fragment's view hierarchy.
     */
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment using data binding.
        KittenDetailsBinding binding = KittenDetailsBinding.inflate(inflater);

        // Set the background color of the root view to white.
        binding.getRoot().setBackgroundColor(Color.WHITE);

        // Populate the views with data from the selected Kitten object.
        binding.kittenDetailsId.setText("Id = " + selected.id);
        binding.kittenDetailsWidth.setText(getString(R.string.width) + " : " + selected.getWidth());
        binding.kittenDetailsHeight.setText(getString(R.string.height) + " : " + selected.getHeight());
        binding.kittenDetailsDate.setText(getString(R.string.date) + " : " + selected.getDate());
        binding.weatherDetailsIcon.setImageBitmap(BitmapFactory.decodeFile(selected.getImagePath()));

        // Return the root view of the binding.
        return binding.getRoot();
    }
}