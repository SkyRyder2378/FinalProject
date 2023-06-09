package com.example.finalproject.UI;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.finalproject.Data.WeatherDatabase;
import com.example.finalproject.Data.WeatherItem;
import com.example.finalproject.Data.WeatherItemDAO;
import com.example.finalproject.Data.WeatherViewModel;
import com.example.finalproject.R;
import com.example.finalproject.databinding.ActivitySavedWeatherPageBinding;
import com.example.finalproject.databinding.WeatherRowBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This class contains the saved weather captured
 * @author Abdullah Sabbagh
 * @version 01
 */
public class SavedWeatherPage extends AppCompatActivity {
    private RecyclerView.Adapter myAdapter;
    private ArrayList<WeatherItem> weatherItems;
    private WeatherItemDAO mDAO;
    private Executor thread;
    private WeatherViewModel weatherModel;
    private ActivitySavedWeatherPageBinding binding;
    private  boolean IsSelected;
    int position;
    private WeatherDetailsFragment weatherFragment;
    public TextView tv_city;

    /**
     * Constructor
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.saved_weather_menu, menu);
        return true;
    }

    /**
     * @param item
     * @return if the item has been selected
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch( item.getItemId() ) {
            case R.id.Item_1:
                if (weatherItems.size() != 0 && IsSelected) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SavedWeatherPage.this);
                    builder.setMessage(getString(R.string.do_you_want_to_delete_this_city) + tv_city.getText().toString()).
                            setTitle(R.string.question).
                            setNegativeButton(R.string.no, (dialog, cl) -> {
                            })
                            .setPositiveButton(R.string.yes
                                    , (dialog, cl) -> {
                                        WeatherItem removedItem = weatherItems.get(position);
                                        thread.execute(() ->
                                        {
                                            mDAO.deleteWeatherItem(removedItem);
                                        });
                                        runOnUiThread(() -> {
                                            weatherItems.remove(position);
                                            myAdapter.notifyItemRemoved(position);
                                        });
                                        Snackbar.make(binding.getRoot(), getString(R.string.you_deleted_city) + tv_city.getText(), Snackbar.LENGTH_SHORT)
                                                .setAction(R.string.undo, c -> {
                                                    weatherItems.add(position, removedItem);
                                                    myAdapter.notifyItemInserted(position);
                                                }).show();
                                        getSupportFragmentManager().popBackStack();
                                        IsSelected = false;
                                    }).create().show();
                }
                break;
            case R.id.Item_2:
                AlertDialog.Builder builder = new AlertDialog.Builder(SavedWeatherPage.this);
                builder.setMessage(R.string.about_message ).
                        setTitle(R.string.about_title).
                        setNegativeButton(R.string.ok, (dialog, cl) -> {
                        }).create().show();
                break;
        }
        return true;
    }
    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedWeatherPageBinding.inflate(getLayoutInflater());
        setTitle(R.string.saved_weather);
        setSupportActionBar(binding.toolbar);
        weatherModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherItems = weatherModel.weatherItems.getValue();
        if (weatherItems == null) {
            weatherModel.weatherItems.setValue(weatherItems = new ArrayList<WeatherItem>());
            thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                WeatherDatabase db = Room.databaseBuilder(getApplicationContext(), WeatherDatabase.class, "database-name").build();
                mDAO = db.cmDAO();
                weatherItems.addAll(mDAO.getAllWeatherItems()); //Once you get the data from database
                runOnUiThread(() -> {
                    binding.recycleView.setAdapter(myAdapter);
                    setContentView(binding.getRoot());
                    if (weatherItems.size() - 1 > 0) {
                        binding.recycleView.smoothScrollToPosition(weatherItems.size() - 1);
                    }
                }); //You can then load the RecyclerView
            });
        }
        weatherModel.selectedWeatherItem.observe(this, (newWeatherItemValue) -> {
            Log.i("tag", "onCreate: " + newWeatherItemValue.getName());
            weatherFragment = new WeatherDetailsFragment(newWeatherItemValue);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation, weatherFragment).addToBackStack("").commit();
        });
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                WeatherRowBinding weatherRowBinding = WeatherRowBinding.inflate(getLayoutInflater(), parent, false);
                View root = weatherRowBinding.getRoot();
                return new MyRowHolder(root);
            }

            /**
             * @param holder   The ViewHolder which should be updated to represent the contents of the
             *                 item at the given position in the data set.
             * @param position The position of the item within the adapter's data set.
             */
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.timeLocate.setText(weatherItems.get(position).getLocateTime());
                holder.temp.setText(weatherItems.get(position).getTemperature() + " c");
                holder.Image.setImageBitmap(BitmapFactory.decodeFile(weatherItems.get(position).getPathName()));
                holder.city.setText(weatherItems.get(position).getName());
            }

            /**
             * @return true
             */
            @Override
            public int getItemCount() {
                return weatherItems.size();
            }

            //function to check what kind of ChatMessage object is at row position
            // If the isSend is true, then return 0
            // so that the onCreateViewHolder checks the viewType and inflates a send_message layout.
            // If isSend is false, then getItemViewType returns 1 and onCreateViewHolder checks
            // if the viewType is 1 and inflates a receive_message layout.

            /**
             * @param position position to query
             * @return
             */
            @Override
            public int getItemViewType(int position) {
                return 0;
            }
        });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }
    class MyRowHolder extends RecyclerView.ViewHolder {
        public TextView temp;
        public TextView city;
        public TextView timeLocate;
        public ImageView Image;

        /**
         * @param itemView
         */
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(clk -> {
                position = getAbsoluteAdapterPosition();
                WeatherItem selected = weatherItems.get(position);
                weatherModel.selectedWeatherItem.postValue(selected);
                tv_city=city;
                IsSelected=true;
            });
            itemView.setOnLongClickListener(click -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(SavedWeatherPage.this);
                builder.setMessage(String.valueOf(R.string.do_you_want_to_delete_this_city)+ city.getText()).
                        setTitle(R.string.question).
                        setNegativeButton(R.string.no, (dialog, cl) -> {
                        })
                        .setPositiveButton(R.string.yes, (dialog, cl) -> {
                            int Position = getAbsoluteAdapterPosition();
                            WeatherItem removedItem = weatherItems.get(Position);
                            thread.execute(() ->
                            {
                                mDAO.deleteWeatherItem(removedItem);
                            });
                            runOnUiThread(() -> {
                                weatherItems.remove(Position);
                                myAdapter.notifyItemRemoved(Position);
                            });
                            Snackbar.make(itemView, String.valueOf(R.string.you_deleted_city )+ city.getText(), Snackbar.LENGTH_SHORT)
                                    .setAction(R.string.undo, c -> {
                                        weatherItems.add(Position, removedItem);
                                        myAdapter.notifyItemInserted(Position);
                                    }).show();
                        }).create().show();
                return true;
            });
            Image = itemView.findViewById(R.id.rowImage);
            temp = itemView.findViewById(R.id.rowTemp);
            timeLocate = itemView.findViewById(R.id.locateTime);
            city = itemView.findViewById(R.id.rowCity);
        }
    }
}