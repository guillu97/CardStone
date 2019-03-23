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
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import java.util.List;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;
    private List<Card> list;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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
        Picasso.with(context).load(card.getImage_url_256x()).into(holder.imageView);

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
                PhotoView photoView = view.findViewById(R.id.photo_view);
                //photoView.

                // download the image from the image_url for the image in the dialog
                Picasso.with(context).load(list.get(position).getImage_url_512x()).into(photoView);

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

        holder.imageSaved.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO ajouter dans la table
                if(list.get(position).isSaved()) {
                    list.get(position).setSaved(false);
                    holder.imageSaved.setImageAlpha(100);
                }
                else{
                    list.get(position).setSaved(true);
                    holder.imageSaved.setImageAlpha(500);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}