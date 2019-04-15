package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;

import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

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

    private View mView;
    private LinearLayout clickableLayoutAllCards;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // create the inflater view
        mView = inflater.inflate(R.layout.fragment_home, container, false);


        // get the linearLayout wich is our all cards button
        LinearLayout clickableLayoutAllCards = mView.findViewById(R.id.clickable_layout_all_cards);
        // get the linearLayout wich is our back cards button
        LinearLayout clickableLayoutBackCards = mView.findViewById(R.id.clickable_layout_back_cards);

        // set the click listener of the linear layout this (which implement the onClickListener interface)
        // then in the onClick we will do the clicks event for each layout
        clickableLayoutAllCards.setOnClickListener(this);
        clickableLayoutBackCards.setOnClickListener(this);

        return mView;
    }

    @Override
    public void onClick(View view) {
        // what did the user clicked ?
        switch (view.getId()) {
            // the case where he has clicked on the linear layout wich is our button to get to all the cards
            case R.id.clickable_layout_all_cards:
                BottomNavigationView bottomNavigationView;
                if (getActivity() != null) {
                    bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setSelectedItemId(R.id.nav_search);
                }
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
                }
                break;
            case R.id.clickable_layout_back_cards:
                if (getFragmentManager() != null)
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BackCardFragment()).commit();
                break;
            default:
                break;
        }
    }
}
