package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.example.finalproject.databinding.ActivityNewyorktimesBinding;
import com.google.android.material.snackbar.Snackbar;

public class NewYorkTimes extends AppCompatActivity {
    ActivityNewyorktimesBinding binding;
    ArrayList<NewsData> news = new ArrayList<>();
    RecyclerView.Adapter myAdapter;
    NewYorkTimesViewModel newsModel;
    private RecyclerView mRecyclerView;
    private RequestQueue queue;

    NewsDataDAO nDAO;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }


    // A method as handler for menu item on menu bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch( item.getItemId() )
        {
            case R.id.item_1:

                if (newsModel.selectedArticle.getValue() == null) {
                    break;
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(NewYorkTimes.this);
                    builder.setMessage("Do you want to add this event to the favourite list: ")
                            .setTitle("Question:")
                            .setNegativeButton("No", (dialog, cl) -> {
                            })
                            .setPositiveButton("Yes", (dialog, cl) -> {

                                NewsData Article = newsModel.selectedArticle.getValue();

                                Executor thread = Executors.newSingleThreadExecutor();
                                thread.execute(() -> {
                                    Article.id = (int) nDAO.insertList(Article);
                                });

                            }).create().show();
                }
                break;

            case R.id.item_2:

                if (newsModel.selectedArticle.getValue() == null) {
                    break;
                } else {
                    int position = newsModel.titles.getValue().indexOf(newsModel.selectedArticle.getValue());

                    AlertDialog.Builder builder2 = new AlertDialog.Builder(NewYorkTimes.this);
                    builder2.setMessage("Do you want to delete this event from the favourite list: ")
                            .setTitle("Question:")
                            .setNegativeButton("No", (dialog, cl) -> {
                            })
                            .setPositiveButton("Yes", (dialog, cl) -> {

                                NewsData thisNews = news.get(position);
                                Executor thread = Executors.newSingleThreadExecutor();
                                thread.execute(() -> {
                                    nDAO.deleteList(thisNews);
                                });

                                news.remove(position);
                                myAdapter.notifyItemRemoved(position);

                                LinearLayout linearLayout = binding.linear;
                                Snackbar.make(linearLayout, "You deleted event #" + position, Snackbar.LENGTH_LONG)
                                        .setAction("UNDO", clk1 -> {

                                            thread.execute(() -> {
                                                thisNews.id = (int) nDAO.insertList(thisNews);
                                            });
                                            news.add(position, thisNews);
                                            myAdapter.notifyItemInserted(position);
                                        }).show();
                            }).create().show();
                }
                break;

            case R.id.item_3:
                news.clear(); // clear list
                myAdapter.notifyDataSetChanged();

                binding.caption.setVisibility(View.VISIBLE);

                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() ->
                {
                    news.addAll( nDAO.getAllList() ); //Once you get the data from database

                    runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
                });
                break;

            case R.id.item_4:
                AlertDialog.Builder builder = new AlertDialog.Builder(NewYorkTimes.this);
                builder.setMessage("Helpful Information: It is an app designed to search for article in NewYorkTimes. " +
                                "Find the article after clicking on the SEARCH button with your interest. " +
                                "Click on the event name to view the details. " +
                                "You can add or delete the article on the toolbar. ")
                        .create().show();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewyorktimesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        Button button = findViewById(R.id.searchButton);
        EditText editText = findViewById(R.id.article);

        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        news = new ArrayList<>();

        queue = Volley.newRequestQueue(this);

        newsModel = new ViewModelProvider(this).get(NewYorkTimesViewModel.class);
        news = newsModel.titles.getValue();
        NewsDatabase db = Room.databaseBuilder(getApplicationContext(), NewsDatabase.class, "database-name").build();
        nDAO = db.ndDAO();

        if(news == null)
        {
            newsModel.titles.postValue( news = new ArrayList<NewsData>());
        }


        // Observer to create and load a fragment
        newsModel.selectedArticle.observe(this, (newValue) -> {
            NewsDetailsFragment newsFragment = new NewsDetailsFragment( newValue );
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            tx.replace(R.id.fragmentLocation, newsFragment);
            tx.commit();
            tx.addToBackStack("");
        });

        setSupportActionBar(binding.myToolbar);

        // SharePre Object to store data
        SharedPreferences prefs = getSharedPreferences("SearchData", Context.MODE_PRIVATE);
        String title = prefs.getString("SearchData", "");
        editText.setText(title);
        SharedPreferences.Editor editor = prefs.edit();


        // OnClickListener to perform action after clicking on button
        button.setOnClickListener(click ->{
            news.clear();
            myAdapter.notifyDataSetChanged();

            String value = editText.getText().toString();
            editor.putString("SearchData", value);
            editor.apply();


            parseJSON(); //invoke the method

            binding.caption.setVisibility(View.VISIBLE);

            // Toast Message part
            String name = editText.getText().toString();
            Context context = getApplicationContext();
            CharSequence text = "The search result of "+ name +" is displayed." ;
            int duration = Toast.LENGTH_LONG;

            Toast.makeText(context, text, duration).show();
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View root;
                root = getLayoutInflater().inflate(R.layout.title_list, parent, false);
                return new MyRowHolder(root);
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                NewsData currentItem = news.get(position);
                String obj = currentItem.getHeadline();
                holder.title.setText(obj);
            }

            @Override
            public int getItemCount() {
                return news.size();
            }
        });
    }

    // A method to interact with external API by JSON for data
    private void parseJSON() {
        EditText editText = findViewById(R.id.article);
        String TitleOfArticle = editText.getText().toString();

        String StringURL="";
            try {
                StringURL= "https://api.nytimes.com/svc/search/v2/articlesearch.json?q="
                        + URLEncoder.encode(TitleOfArticle, "UTF-8")
                        + "&api-key=tHgU8qWjq0ufkR6t8NbDFd8b6NuocsyE";

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, StringURL, null,
                    (response) -> {
                        try{
                            JSONObject respon = response.getJSONObject("response");
                            JSONArray docs = respon.getJSONArray("docs");
                            for(int i = 0; i<docs.length(); i++) {
                                // For all
                                JSONObject position0 = docs.getJSONObject(i);
                                // For URL, date
                                String webUrl = position0.getString("web_url");
                                String pubDate = position0.getString("pub_date");
                                // For main
                                JSONObject position1 = position0.getJSONObject("headline");
                                String headline = position1.getString("main");

                                NewsData nd = new NewsData();
                                nd.setHeadline(headline);
                                nd.setWebUrl(webUrl);
                                nd.setPubDate(pubDate);
                                news.add(nd);
                            }
                            runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter)); //You can then load the RecyclerView

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, (error) -> {
                error.printStackTrace();
            });
            queue.add(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

        class MyRowHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(click -> {
                int position = getAbsoluteAdapterPosition();
                NewsData selected = news.get(position);

                newsModel.selectedArticle.postValue(selected);
            });
            title = itemView.findViewById(R.id.titleList);
        }
    }

}
