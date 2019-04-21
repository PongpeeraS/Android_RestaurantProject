package com.example.restaurantproject.Order;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.restaurantproject.OrderViewActivity;
import com.example.restaurantproject.R;

import java.util.List;

/*
* This method was created for setting the format of each orders
* to display in OrderActivity layout.
* */

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private Context mContext;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.orderList = orderList;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item_order, parent,false);
        return new OrderViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, final int position) {
        // - get element from the dataset at this position
        // - replace the contents of the view with that element
        holder.name.setText(orderList.get(position).getFood());
        holder.price.setText(orderList.get(position).getPrice()+" "+
                mContext.getResources().getString(R.string.text_baht));
        holder.amount.setText(mContext.getResources().getString(R.string.label_amount)+" "+
                orderList.get(position).getAmount());

        /*
        * When click on the order it will turn to OrderViewActivity
        * to see the information of the order and press receive button to confirm
        * that the user received the food.
        * */
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create intent to contain the data from this method to use in OrderViewActivity
                Intent intent = new Intent(mContext, OrderViewActivity.class);
                intent.putExtra("oname", orderList.get(position).getFood());
                intent.putExtra("oprice", orderList.get(position).getPrice());
                intent.putExtra("oamount", orderList.get(position).getAmount());
                intent.putExtra("oaddress", orderList.get(position).getAddress());
                intent.putExtra("uID", orderList.get(position).getUID());
                intent.putExtra("status", orderList.get(position).isReceived());
                //intent.putExtra("image_name", mImageNames.get(position));
                mContext.startActivity(intent);
                ((Activity)mContext).finish();
            }
        });
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderList.size();
    }
}

//Define the name of space that will display the information of the order
class OrderViewHolder extends RecyclerView.ViewHolder {
    public TextView name,price,amount;
    LinearLayout parentLayout;
    public OrderViewHolder(View view) {
        super(view);
        parentLayout = itemView.findViewById(R.id.layout_orderitem);
        name = itemView.findViewById(R.id.oname);
        price = itemView.findViewById(R.id.oprice);
        amount = itemView.findViewById(R.id.oamount);
    }
}

