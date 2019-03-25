package in.example.rahul.vegcartpro;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import in.example.rahul.vegcartpro.Model.Item;

/**
 * Created by Rahul on 19-03-2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Item> itemList;
    private ClickListener clickListener=null;
    public MyAdapter(List<Item> itemList){
        this.itemList= itemList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, subtitle;
        public ImageView icon;
        LinearLayout main;

        public MyViewHolder(final View parent){
            super(parent);

            title=parent.findViewById(R.id.title);
            subtitle=parent.findViewById(R.id.subtitle);
            icon=parent.findViewById(R.id.icon);
            main=parent.findViewById(R.id.main);

            main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                    Log.e("Position adapter:", String.valueOf(getPosition()));
                if (clickListener != null){
                    clickListener.itemClicked(v, getAdapterPosition());
                }
                }
            });

        }
    }
    public void setClickListener(ClickListener clickListener){
      this.clickListener= clickListener;
    }
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Item row= itemList.get(position);
        holder.title.setText(row.getTitle());
        holder.subtitle.setText(row.getSubtitle());
        holder.icon.setImageResource(row.getImageId());

    }
    @Override
    public int getItemCount(){
        return itemList.size();
    }

    public interface ClickListener{
        public void itemClicked(View view, int position);
    }
}
