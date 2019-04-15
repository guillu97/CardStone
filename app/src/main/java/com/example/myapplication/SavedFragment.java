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

import static com.example.myapplication.savedCards.savedCardsList;

public class SavedFragment extends Fragment implements Filterable {

    private Context context;

    private RecyclerView mList;

    private GridLayoutManager gridLayoutManager;
    private final int NUMBER_OF_COLUMN = 1;
    private DividerItemDecoration dividerItemDecoration;
    private List<Card> cardList;
    private List<Card> cardListFiltered;
    private RecyclerView.Adapter adapter;
    ImageView imageView;
    RequestQueue requestQueue;
    private ProgressDialog progressDialog;

    private SearchView searchView;

    private String url = "https://api.hearthstonejson.com/v1/latest/enUS/cards.collectible.json";

    private String url_image_256x = "https://art.hearthstonejson.com/v1/render/latest/enUS/256x/";

    private String url_gif = "http://media.services.zam.com/v1/media/byName/hs/cards/enus/animated/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View currentView = inflater.inflate(R.layout.fragment_saved, container,false );
        context = getContext(); // get fragment and activity

        mList = currentView.findViewById(R.id.main_list_in_fragment);

        progressDialog = new ProgressDialog(context);


        progressDialog.setMessage("Loading...");
        progressDialog.show();

        cardList = new ArrayList<>();
        getData();
        cardListFiltered = cardList;

        progressDialog.dismiss();

        adapter = new SavedCardAdapter(context,cardListFiltered);

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

        for(int i = 0; i < savedCardsList.size(); i++){
            cardList.add(savedCardsList.get(i));
        }

        progressDialog.dismiss();

    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    ((SavedCardAdapter) adapter).list = cardList;
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
                ((SavedCardAdapter) adapter).list = (ArrayList<Card>) filterResults.values;
                for(Card card : ((SavedCardAdapter) adapter).list){
                    Log.d("SearchFragment","cardListFiltered:" + card.getName());
                }
                adapter.notifyDataSetChanged();
            }
        };
    }
}