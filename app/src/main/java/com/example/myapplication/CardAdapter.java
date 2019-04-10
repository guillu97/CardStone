package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
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


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;
    public List<Card> list;



    public CardAdapter(Context context, List<Card> list) {
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
        public ImageView imageView, imageSaved;

        public ViewHolder(View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.ConstrainLayout);
            textID = itemView.findViewById(R.id.main_id);
            textName = itemView.findViewById(R.id.main_name);
            textClass = itemView.findViewById(R.id.main_class);
            textCost = itemView.findViewById(R.id.main_cost);
            textFlavor = itemView.findViewById(R.id.main_flavor);
            textDescription = itemView.findViewById(R.id.main_description);

            imageSaved = itemView.findViewById(R.id.main_saved);
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
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Card card = list.get(position);

        holder.textID.setText(String.valueOf(card.getId()));
        holder.textName.setText(card.getName());
        holder.textClass.setText(card.getCardClass());
        holder.textCost.setText(String.valueOf(card.getCost()));
        holder.textFlavor.setText(card.getFlavor());
        holder.textDescription.setText(card.getCardText());


        holder.imageSaved.setImageResource(R.drawable.hearts);

        if (!card.isSaved()){
            holder.imageSaved.setImageAlpha(100);
        }

        //holder.imageView.setImageBitmap(card.getBitmap());

        // download the image from the image_url for the images in the list
        //Picasso.with(context).load(card.getImage_url_256x()).into(holder.imageView);
        Glide.with(context).asGif().load(list.get(position).getImage_url_512x()).into(holder.imageView);

        // on click of the linear layout of a card item in the list
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mainActivity", "onClick position:" + position + "cardId:" + list.get(position).getId());

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

                //photoView.
                //PhotoView photoView = view.findViewById(R.id.photo_view);

                ImageView imageView = view.findViewById(R.id.image_view);
                Glide.with(context).asGif().load(list.get(position).getImage_url_512x()).into(imageView);

                // download the image from the image_url for the image in the dialog
                //Picasso.with(context).load(list.get(position).getImage_url_512x()).into(photoView);


                /*
                //to add a button on the dialog
                alertadd.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {

                    }
                });
                */

            alertadd.show();
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