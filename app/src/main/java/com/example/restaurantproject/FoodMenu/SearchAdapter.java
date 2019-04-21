package com.example.restaurantproject.FoodMenu;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restaurantproject.OrderConfirmActivity;
import com.example.restaurantproject.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{

    private Context context;
    private List<Food> foods;
    private String uID;
    /*
     * The data structure that contain integer that indicate to resource images.
     * We sort the address of the resource image by ID of the food.*/
    private Integer[] mThumbIds = {
            R.drawable.images, R.drawable.foodimg_friedrice, R.drawable.foodimg_tomyumkung,
            R.drawable.foodimg_friedchicken, R.drawable.foodimg_stickyrice,
            R.drawable.foodimg_somtum, R.drawable.foodimg_beefsteak,
            R.drawable.foodimg_porksteak, R.drawable.foodimg_padthai,
            R.drawable.foodimg_tomkhakai};
    public SearchAdapter(Context context, List<Food> foods, String uID) {
        this.context = context;
        this.foods = foods;
        this.uID = uID;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate(R.layout.layout_item_food,parent,false);

        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder,final int i) {
        searchViewHolder.name.setText(foods.get(i).getName());
        searchViewHolder.price.setText(foods.get(i).getPrice()+" baht");
        searchViewHolder.des.setText(foods.get(i).getDes());
        /*
         * Set the image of each food by using ID of each food to indicate the rID of the resource image
         * from mThumbIds matrix that contain Integer Number that represent address of the resource image.
         * */
        searchViewHolder.img.setImageResource(mThumbIds[foods.get(i).getID()]);
        //Set Buy Button for each Food that link to OrderConfirmActivity.
        searchViewHolder.itemView.findViewById(R.id.buy_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Put the intent for using in OrderConfirmActivity
                Intent intent = new Intent(context,OrderConfirmActivity.class);
                intent.putExtra("food",foods.get(i).getName());
                intent.putExtra("price",foods.get(i).getPrice());
                intent.putExtra("uID",uID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }
}

class SearchViewHolder extends RecyclerView.ViewHolder {

    public TextView name,price,des;
    public ImageView img;

    public SearchViewHolder(View itemView){
        super(itemView);
        // Defining space to display the information of the food.
        name = itemView.findViewById(R.id.fname);
        price = itemView.findViewById(R.id.price);
        des = itemView.findViewById(R.id.des);
        img = itemView.findViewById(R.id.food_img);
    }

}


