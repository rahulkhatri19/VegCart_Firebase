package in.example.rahul.vegcartpro.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.example.rahul.vegcartpro.Interface.ItemClickListener;
import in.example.rahul.vegcartpro.R;

/**
 * Created by Rahul on 01-05-2018.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ItemClickListener itemClickListener;
    public TextView tv1,tv2,tv3,tv4;
    public FoodViewHolder(View itemView) {
        super(itemView);
        tv1=(TextView)itemView.findViewById(R.id.tv_name);
        tv2=(TextView)itemView.findViewById(R.id.tv_price);
        tv3=(TextView)itemView.findViewById(R.id.tv_quantity);
        tv4=(TextView)itemView.findViewById(R.id.tv_address);

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
