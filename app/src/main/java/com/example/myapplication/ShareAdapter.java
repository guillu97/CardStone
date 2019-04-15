package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ViewHolder> {

    private Context context;
    public List<Card> list;


    public ShareAdapter(Context context, List<Card> list) {
        this.context = context;
        this.list = list;
    }

    /*
    *
    *   VIEW HOLDER CLASS
    *
    * */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private static final String EXTRA_MESSAGE = "card";

        public ConstraintLayout layout;
        public TextView textID, textName, textClass, textCost, textFlavor, textDescription;
        public ImageView imageView, imageShare;

        public ViewHolder(View itemView) {
            super(itemView);

            /* LAYOUT */
            layout = itemView.findViewById(R.id.ConstrainLayout);

            /* TEXT */
            textID = itemView.findViewById(R.id.main_id);
            textName = itemView.findViewById(R.id.main_name);
            textClass = itemView.findViewById(R.id.main_class);
            textCost = itemView.findViewById(R.id.main_cost);
            textFlavor = itemView.findViewById(R.id.main_flavor);
            textDescription = itemView.findViewById(R.id.main_description);

            /* IMAGES */
            imageShare = itemView.findViewById(R.id.main_share);
            imageView = itemView.findViewById(R.id.main_image);
        }
    }

    /*
    *
    *   CREATE A VIEW HOLDER
    *
    * */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_card_share, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Card card = list.get(position);

        holder.textID.setText(String.valueOf(card.getId()));
        holder.textName.setText(card.getName());
        holder.textClass.setText(card.getCardClass());
        holder.textCost.setText(String.valueOf(card.getCost()));
        holder.textFlavor.setText(card.getFlavor());
        holder.textDescription.setText(card.getCardText());

        holder.imageShare.setImageResource(R.drawable.share);

        // the circular bar to wait until the image is downloaded
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        // download the image from the image_url for the images in the list
        Glide.with(context).load(list.get(position).getImage_url()).placeholder(circularProgressDrawable).into(holder.imageView);

        // on click of the linear layout of a card item in the list
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mainActivity", "onClick position:" + position + "cardId:" + list.get(position).getId());

                /*
                *   Creation of an alert Dialog to print the card in full size
                * */
                AlertDialog.Builder alertadd = new AlertDialog.Builder(context, R.style.DialogCustomTheme);
                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.card_inflate, null);

                alertadd.setNegativeButton("X", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                alertadd.setView(view);

                //ImageView imgView = view.findViewById(R.id.image_view_details);
                //ImageView img = new ImageView(imageView);
                //PhotoView photoView = view.findViewById(R.id.photo_view);
                //photoView.

                // the image view on the xml file
                ImageView imageView = view.findViewById(R.id.image_view);


                // the circular bar to wait until the image is downloaded
                CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
                circularProgressDrawable.setColorSchemeColors(Color.WHITE);
                circularProgressDrawable.setStrokeWidth(30f);
                circularProgressDrawable.setCenterRadius(50f);
                circularProgressDrawable.start();

                // downloading the gif
                Glide.with(context).asGif().load(list.get(position).getGif_url()).placeholder(circularProgressDrawable).into(imageView);

            alertadd.show();
            }
        });

        holder.imageShare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String shareBody = "your body here";
            String sharesub = " test ";
            myIntent.putExtra(Intent.EXTRA_SUBJECT, sharesub);
            myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            v.getContext().startActivity(Intent.createChooser(myIntent, "share test"));

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

/*
    // code to create a new intent from the recycler view and send the card through extra message

            Intent intent = new Intent(context, CardDetails.class);

            /*
            *   getLayoutPosition()
            *   or
            *   getAdapterPosition()
            *   ?


            Card cardSelected = list.get(getLayoutPosition());

            intent.putExtra(EXTRA_MESSAGE, cardSelected );

            /*String message = textID.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);


            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
 */