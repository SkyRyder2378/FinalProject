package com.example.finalproject;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class PlaceKittenRecycler extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private static PlaceKittenAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        mRecyclerView = findViewById(R.id.recycler);
        mAdapter = new PlaceKittenAdapter(getItemsFromDatabase());
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<PlaceKittenInfo> getItemsFromDatabase() {
        PlaceKittenDatabase db = Room.databaseBuilder(getApplicationContext(), PlaceKittenDatabase.class, "kitten").build();
        PlaceKittenInfoDao dao = db.cmDAO();
        new LoadPlaceKittenItemsTask().execute();

    }

    private class LoadPlaceKittenItemsTask extends AsyncTask<Void, Void, List<PlaceKittenInfo>> {

        @Override
        protected List<PlaceKittenInfo> doInBackground(Void... voids) {
            PlaceKittenDatabase db = Room.databaseBuilder(getApplicationContext(), PlaceKittenDatabase.class, "kitten").build();
            PlaceKittenInfoDao dao = db.cmDAO();
            return dao.getAllPlaceKittenItems();
        }

        @Override
        protected void onPostExecute(List<PlaceKittenInfo> placeKittenInfos) {
            mAdapter.setItems(placeKittenInfos);
        }
    }


}