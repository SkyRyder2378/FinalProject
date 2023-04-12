/**

 The SearchKittenImage activity allows users to search for kitten images by specifying the width and height of the image they want.
 Users can save the image to the database if they want.
 */
package com.example.finalproject.UI;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.Data.Kitten;
import com.example.finalproject.Data.KittenDAO;
import com.example.finalproject.Data.KittenDatabase;
import com.example.finalproject.R;
import com.example.finalproject.databinding.ActivitySearchKittenImageBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class SearchKittenImage extends AppCompatActivity {
    // View binding
    ActivitySearchKittenImageBinding binding;

    // SharedPreferences
    SharedPreferences prefs;

    // Thread executor
    private Executor thread;

    // Database
    private KittenDAO mDAO;

    // Image path and bitmap
    String imagePath;
    Bitmap image;

    // Request queue for Volley
    protected RequestQueue queue = null;

    /**
     * Creates the options menu for the activity.
     * @param menu The menu to be inflated.
     * @return Returns true after inflating the menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.get_kitten_image, menu);
        return true;
    }

    /**
     * Handles the selected option from the options menu.
     * @param item The selected item from the options menu.
     * @return Returns true after handling the selected item.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.Item_1:
                // Show about message
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchKittenImage.this);
                builder.setMessage(R.string.kitten_about_message)
                        .setTitle(R.string.kitten_about_title)
                        .setNegativeButton(R.string.ok, (dialog, cl) -> {
                        }).create().show();
                break;
            case R.id.Item_2:
                // Save image to database
                thread.execute(() -> {
                    if (image!=null){
                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
                        String currentDatEndTime = sdf.format(new Date());

                        mDAO.insertKittenItem(new Kitten(binding.width.getText().toString(),binding.height.getText().toString(),currentDatEndTime,imagePath));
                        Snackbar.make(binding.getRoot(),getString(R.string.image_saved) , Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        return true;
    }

    /**
     * Initializes the activity.
     * @param savedInstanceState The saved instance state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.search_kitten_image));

        binding = ActivitySearchKittenImageBinding.inflate(getLayoutInflater());

        setSupportActionBar(binding.toolbar);

        queue = Volley.newRequestQueue(this);

        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        binding.width.setText(prefs.getString("width", ""));
        binding.height.setText(prefs.getString("height", ""));

        thread = Executors.newSingleThreadExecutor();


        thread.execute(() ->
        {

            KittenDatabase db = Room.databaseBuilder(getApplicationContext(), KittenDatabase.class, "database-placeKitten").build();
            mDAO = db.cmDAO();


        });


        runOnUiThread(() -> {
            setContentView(binding.getRoot());
        });



        binding.btnFavouriteKittens.setOnClickListener(c->{

            Intent intent= new Intent(this, FavouriteKittenImages.class);

            startActivity(intent);

        });



        binding.btnGetImage.setOnClickListener(c -> {




            String   width = binding.width.getText().toString();
            String  height = binding.height.getText().toString();


            String imageName = width + height + ".png";

            String imgUrl = "https://placekitten.com/" + width + "/" + height;

            imagePath = getFilesDir() + "/" + imageName;
            File file = new File(imagePath);
            if (file.exists()) {
                image = BitmapFactory.decodeFile(imagePath);
            } else {
                ImageRequest imgReq = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {



                    @Override
                    public void onResponse(Bitmap bitmap) {


                        Toast.makeText(SearchKittenImage.this, "please wait image", Toast.LENGTH_SHORT).show();

                        image = bitmap;

                        binding.kittenImage.setImageBitmap(image);

                        FileOutputStream fOut = null;
                        try {
                            fOut = openFileOutput(imageName, Context.MODE_PRIVATE);
                            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                            fOut.flush();
                            fOut.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {
                });


                queue.add(imgReq);


            }

runOnUiThread(()->{

    binding.kittenImage.setImageBitmap(image);


});



        });
    }
}