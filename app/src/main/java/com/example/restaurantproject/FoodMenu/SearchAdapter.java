package com.example.restaurantproject.FoodMenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.restaurantproject.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{

    private Context context;
    private List<Food> foods;

    public SearchAdapter(Context context, List<Food> foods) {
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item_food,parent,false);

        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        searchViewHolder.name.setText(foods.get(i).getName());
        searchViewHolder.price.setText(foods.get(i).getPrice()+" baht");
        searchViewHolder.des.setText(foods.get(i).getDes());
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }
}

class SearchViewHolder extends RecyclerView.ViewHolder {

    public TextView name,price,des;

    public SearchViewHolder(View itemView){
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.fname);
        price = (TextView)itemView.findViewById(R.id.price);
        des = (TextView)itemView.findViewById(R.id.des);
    }

}


