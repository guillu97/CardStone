package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HeroesCardAdapter extends RecyclerView.Adapter<HeroesCardAdapter.ViewHolder> {



    private Context context;
    public List<Card> list;



    public HeroesCardAdapter(Context context, List<Card> list) {
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


        public LinearLayout layout;
        public TextView textID;
        public TextView textName;
        public TextView textDescription;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textID = itemView.findViewById(R.id.back_card_id);
            textName = itemView.findViewById(R.id.back_card_name);
            textDescription = itemView.findViewById(R.id.back_card_description);
            layout = itemView.findViewById(R.id.back_card_linear_layout);
            imageView = itemView.findViewById(R.id.back_card_image);
        }
    }

    /*
     *
     *   CREATE A VIEW HOLDER
     *
     * */
    @Override
    public HeroesCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_back_card, parent, false);
        return new HeroesCardAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HeroesCardAdapter.ViewHolder holder, final int position) {
        Card card = list.get(position);

        holder.textName.setText(card.getName());
        holder.textDescription.setText(card.getCardClass());


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

                AlertDialog.Builder alertadd = new AlertDialog.Builder(context, R.style.DialogCustomTheme);
                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.card_inflate, null);

                alertadd.setNegativeButton("X", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                alertadd.setView(view);


                // the image view on the xml file
                ImageView imageView = view.findViewById(R.id.image_view);


                // the circular bar to wait until the image is downloaded
                CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
                circularProgressDrawable.setColorSchemeColors(Color.WHITE);
                circularProgressDrawable.setStrokeWidth(30f);
                circularProgressDrawable.setCenterRadius(50f);
                circularProgressDrawable.start();

                // if there is an error display this image
                //
                Drawable errorImage = view.getResources().getDrawable(R.drawable.victory, null);

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(circularProgressDrawable)
                        .error(errorImage);

                // downloading the gif
                Glide.with(context)
                        .load(list.get(position).getImage_url_512())
                        .apply(options)
                        .into(imageView);

                alertadd.show();
            }
        });


    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
