package in.example.rahul.vegcartpro.ViewHolder;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.example.rahul.vegcartpro.Interface.ItemClickListener;
import in.example.rahul.vegcartpro.R;

/**
 * Created by Rahul on 23-01-2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtname;
    public TextView txtnameHindi;
    public ImageView imageView;
    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);
        txtname=(TextView)itemView.findViewById(R.id.name);
        txtnameHindi=(TextView)itemView.findViewById(R.id.nameHindi);
        imageView=(ImageView)itemView.findViewById(R.id.image);

        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener= itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

  }
