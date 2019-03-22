package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ImageView;

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

public class HomeFragment extends Fragment {

    private Context context;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View currentView = inflater.inflate(R.layout.fragment_home, container,false );
        context = getContext();

        mList = currentView.findViewById(R.id.main_list_in_fragment);

        cardList = new ArrayList<>();
        adapter = new CardAdapter(context,cardList);

        progressDialog = new ProgressDialog(context);

        gridLayoutManager = new GridLayoutManager(context, NUMBER_OF_COLUMN);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), gridLayoutManager.getOrientation());

        mList.setHasFixedSize(false);
        mList.setLayoutManager(gridLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(context);

        getData();
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

                        final String imageUrl_256x = "https://art.hearthstonejson.com/v1/render/latest/frFR/256x/" + card.getId() + ".png";
                        final String imageUrl_512x = "https://art.hearthstonejson.com/v1/render/latest/frFR/512x/" + card.getId() + ".png";
                        card.setImage_url_256x(imageUrl_256x);
                        card.setImage_url_512x(imageUrl_512x);

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
}
