package com.example.finalproject.UI;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.finalproject.Data.WeatherItem;
import com.example.finalproject.databinding.WeatherDetailsBinding;

/**
 * @see this is the Weather Details Fragment.
 * @author Abdullah Sabbagh
 */
public class WeatherDetailsFragment extends Fragment {
    WeatherItem selected;

    /**
     * @param w
     */
    public WeatherDetailsFragment(WeatherItem w) {
        selected = w;
    }

    /**
     * @param item The menu item that was selected.
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        return true;
    }
    /**
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        WeatherDetailsBinding binding = WeatherDetailsBinding.inflate(inflater);
        binding.getRoot().setBackgroundColor(Color.WHITE);
        binding.weatherDetailsId.setText("Id = " + selected.id);
        binding.weatherDetailsName.setText("Name : " + selected.getName());
        binding.weatherDetailsLocateTime.setText("LocateTime : " + selected.getLocateTime());
        binding.weatherDetailsTemperature.setText("Temperature : " + selected.getTemperature());
        binding.weatherDetailsIcon.setImageBitmap(BitmapFactory.decodeFile(selected.getPathName()));
        binding.weatherDetailsDescription.setText("Description = " + selected.getDescription());
        binding.weatherDetailsHumidity.setText("humidity = " + selected.getHumidity());
        return binding.getRoot();
    }
}