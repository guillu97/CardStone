package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class CardDetails extends AppCompatActivity {
    private static final String EXTRA_MESSAGE = "card";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail);

        Card cardItem = (Card)getIntent().getSerializableExtra(EXTRA_MESSAGE);

        textView = findViewById(R.id.textView);

        textView.setText(cardItem.getId());
    }
}
