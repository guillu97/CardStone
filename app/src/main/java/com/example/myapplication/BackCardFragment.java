package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackCardFragment extends Fragment implements Filterable {

    private Context context;

    private RecyclerView mList;

    private GridLayoutManager gridLayoutManager;
    private final int NUMBER_OF_COLUMN = 1;
    private DividerItemDecoration dividerItemDecoration;
    private List<BackCard> backCardList;

    private RecyclerView.Adapter adapter;
    ImageView imageView;
    RequestQueue requestQueue;
    private ProgressDialog progressDialog;

    private SearchView searchView;

    private String url = "https://omgvamp-hearthstone-v1.p.rapidapi.com/cardbacks";

    private List<BackCard> backCardListFiltered;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View currentView = inflater.inflate(R.layout.fragment_back_cards, container,false );
        context = getContext();

        mList = currentView.findViewById(R.id.back_card_list_in_fragment);

        progressDialog = new ProgressDialog(context);


        progressDialog.setMessage("Loading...");
        progressDialog.show();

        backCardList = new ArrayList<>();
        getData();
        backCardListFiltered = backCardList;

        progressDialog.dismiss();

        adapter = new BackCardAdapter(context,backCardListFiltered);




        gridLayoutManager = new GridLayoutManager(context, NUMBER_OF_COLUMN);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), gridLayoutManager.getOrientation());

        mList.setHasFixedSize(false);
        mList.setLayoutManager(gridLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);



        requestQueue = Volley.newRequestQueue(context);

        searchView = currentView.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SearchFragment","onQueryTextSubmit");
                getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Log.d("SearchFragment","onQueryTextChange");
                getFilter().filter(query);
                return false;
            }
        });


        return currentView;
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        final BackCard backCard = new BackCard();
                        backCard.setId(jsonObject.optString("cardBackId","no id"));
                        backCard.setName(jsonObject.optString("name","no name"));
                        backCard.setDescription(jsonObject.optString("description","no description"));
                        backCard.setImage_url(jsonObject.optString("img","no image"));
                        backCard.setGif_url(jsonObject.optString("imgAnimated","no image animated"));

                        backCardList.add(backCard);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();

                //setAllImagesUrl();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        }){
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("X-RapidAPI-Host", "omgvamp-hearthstone-v1.p.rapidapi.com");
                headers.put("X-RapidAPI-Key", "940c7dabdemsh8f6ba49836208d5p177889jsndf691d05c393");
                return headers;
            }
        };
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    ((BackCardAdapter) adapter).list = backCardList;
                } else {
                    Log.d("SearchFragment","charSequence in getfilter:" + charSequence);
                    List<BackCard> filteredList = new ArrayList<>();
                    for (BackCard card: backCardList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (card.getName().toLowerCase().contains(charString.toLowerCase()) /*|| row.getPhone().contains(charSequence) */) {
                            Log.d("SearchFragment","card added in filteredList:" + card.getName());
                            filteredList.add(card);
                        }
                    }

                    backCardListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = backCardListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                Log.d("SearchFragment","publishResults");
                ((BackCardAdapter) adapter).list = (ArrayList<BackCard>) filterResults.values;
                for(BackCard card : ((BackCardAdapter) adapter).list){
                    Log.d("SearchFragment","cardListFiltered:" + card.getName());
                }
                adapter.notifyDataSetChanged();

            }
        };
    }
}
