package in.example.rahul.vegcartpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements MyAdapter.ClickListener {

    private List<Item> itemList= new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private LinearLayout main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        prepareItem();

        adapter= new MyAdapter(itemList);
        adapter.setClickListener(this);

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void prepareItem(){
        Item item= new Item(R.drawable.asparagus2,"Product 1","Price is 20");
        itemList.add(item);

        item= new Item(R.drawable.apple,"Product 2","Price is 50");
        itemList.add(item);

        item= new Item(R.drawable.cauliflower,"Product 3","Price is 70");
        itemList.add(item);
        item= new Item(R.drawable.lettuce2,"Product 4","Price is 30");
        itemList.add(item);

        item= new Item(R.drawable.potato,"Product 1","Price is 20");
        itemList.add(item);

        item= new Item(R.drawable.ic_fruit,"Product 2","Price is 40");
        itemList.add(item);

        item= new Item(R.drawable.ic_salid,"Product 3","Price is 25");
        itemList.add(item);
    }

    @Override
    public void itemClicked(View view, int position){
        if (position==6){
            Intent intent= new Intent(CategoryActivity.this, SaladActivity.class);
            intent.putExtra("ItemPosition ", position);
            startActivity(intent);
        }
        else if (position == 2){
            Intent intent= new Intent(CategoryActivity.this, FlowerVegActivity.class);
            intent.putExtra("ItemPosition ", position);
            startActivity(intent);
        }
        else if (position == 5 || position == 1){
            Intent intent= new Intent(CategoryActivity.this, FruitActivity.class);
            intent.putExtra("ItemPosition ", position);
            startActivity(intent);
        }
        else {
            System.out.println("position..."+position);
        }
    }
}
