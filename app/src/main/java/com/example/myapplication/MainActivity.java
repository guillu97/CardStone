package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private RecyclerView mList;

    private GridLayoutManager gridLayoutManager;
    private final int NUMBER_OF_COLUMN = 1;
    private DividerItemDecoration dividerItemDecoration;
    private List<Card> cardList;
    private RecyclerView.Adapter adapter;
    ImageView imageView;
    RequestQueue requestQueue;
    private ProgressDialog progressDialog;

    private String url = "https://api.hearthstonejson.com/v1/25770/frFR/cards.collectible.json";


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*mList = findViewById(R.id.main_list);

        cardList = new ArrayList<>();
        adapter = new CardAdapter(getApplicationContext(),cardList);

        progressDialog = new ProgressDialog(this);

        gridLayoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMN);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), gridLayoutManager.getOrientation());

        mList.setHasFixedSize(false);
        mList.setLayoutManager(gridLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        getData();*/

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView); // Barre de menu
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    // react to click on item
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_saved:
                            selectedFragment = new SavedFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_create:
                            selectedFragment = new CreateFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Card card = new Card();
                        card.setId(jsonObject.getString("id"));
                        card.setName(jsonObject.getString("name"));
                        card.setCardClass(jsonObject.getString("cardClass"));
                        card.setCost(jsonObject.optInt("cost",0));

                        cardList.add(card);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();

                downloadImages();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    // download all the images
    public void downloadImages(){
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        /*
        // to get all the images of the cards
        int index = 0;
        for (Card card: cardList) {
            downloadImage(card.getId(), index);
            index++;
        }*/
        // to get the 100 firsts images of the cards
        for (int i = 0; i<cardList.size(); i++ ){
            downloadImage(cardList.get(i).getId(), i);
        }

        progressDialog.dismiss();
    }

    // do a volley request to download an Image
    public void downloadImage(final String cardId, final int cardIndex) {



        //if (NetworkConnection.getConnection(this)) {

            final String imageUrl = "https://art.hearthstonejson.com/v1/render/latest/frFR/256x/" + cardId + ".png";
            cardList.get(cardIndex).setImage_url(imageUrl);
            adapter.notifyDataSetChanged();


        /*}else {
            Toast.makeText(this, "No internet connectivity...", Toast.LENGTH_SHORT).show();
        }*/

    }

    public void click(View view) {
        Log.d("Main", "testclick");
    }
/*
    public void clickDetailsAboutCard(View view) {
        Intent intent = new Intent(this, CardDetails.class);
        /*
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent);
    }
    */
}
