package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShareFragment extends Fragment  implements Filterable {

    private Context context;

    private RecyclerView mList;

    private GridLayoutManager gridLayoutManager;
    private final int NUMBER_OF_COLUMN = 1;
    private DividerItemDecoration dividerItemDecoration;
    private List<Card> cardList;
    private List<Card> cardListFiltered;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter savedAdapter;
    ImageView imageView;
    RequestQueue requestQueue;
    private ProgressDialog progressDialog;

    private SearchView searchView;

    private String url = "https://api.hearthstonejson.com/v1/latest/enUS/cards.collectible.json";

    private String url_image_256x = "https://art.hearthstonejson.com/v1/render/latest/enUS/256x/";

    private String url_gif = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View currentView = inflater.inflate(R.layout.fragment_share, container, false);
        context = getContext();

        mList = currentView.findViewById(R.id.main_list_in_fragment);

        progressDialog = new ProgressDialog(context);


        progressDialog.setMessage("Loading...");
        progressDialog.show();

        cardList = new ArrayList<>();
        getData();
        cardListFiltered = cardList;

        progressDialog.dismiss();

        adapter = new ShareAdapter(context,cardListFiltered);
        savedAdapter = new ShareAdapter(context,cardList);

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

                        Card card = new Card();
                        card.setId(jsonObject.optString("id","no id"));
                        card.setName(jsonObject.optString("name","no name"));
                        card.setCardClass(jsonObject.optString("cardClass",""));

                        card.setCost(jsonObject.optInt("cost",0));
                        card.setAttack(jsonObject.optInt("attack",0));
                        card.setHealth(jsonObject.optInt("health",0));

                        card.setFlavor(jsonObject.optString("flavor",""));
                        String cardText = jsonObject.optString("text","");
                        cardText = cardText.replaceAll("\\<.*?\\>", "");
                        card.setText(cardText);

                        final String imageUrl_256x = url_image_256x  + card.getId() + ".png";
                        final String gif_url = url_gif + card.getId() + "_premium.gif";
                        card.setImage_url(imageUrl_256x);
                        card.setGif_url(gif_url);

                        card.setSaved(false);

                        cardList.add(card);
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
        });
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
                    ((ShareAdapter) adapter).list = cardList;
                } else {
                    Log.d("SearchFragment","charSequence in getfilter:" + charSequence);
                    List<Card> filteredList = new ArrayList<>();
                    for (Card card: cardList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (card.getName().toLowerCase().contains(charString.toLowerCase()) /*|| row.getPhone().contains(charSequence) */) {
                            Log.d("SearchFragment","card added in filteredList:" + card.getName());
                            filteredList.add(card);
                        }
                    }

                    cardListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = cardListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                Log.d("SearchFragment","publishResults");
                ((ShareAdapter) adapter).list = (ArrayList<Card>) filterResults.values;
                for(Card card : ((ShareAdapter) adapter).list){
                    Log.d("SearchFragment","cardListFiltered:" + card.getName());
                }
                adapter.notifyDataSetChanged();
            }
        };
    }

    /*
        bt = currentView.findViewById(R.id.main_share);
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "your body here";
                String sharesub = " test ";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, sharesub);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "share test"));
            }
        });*/
}
