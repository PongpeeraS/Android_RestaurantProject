package com.example.restaurantproject.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.restaurantproject.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderAdapter(String[] myDataset) {
        //TODO: Change dataset (including init)
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //TODO: Edit layout_item_order for this menu
        View itemView = inflater.inflate(R.layout.layout_item_order, parent,false);
        return new OrderViewHolder((TextView) itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        /*TODO: Set details on each item, Example from SearchAdapter:
        OrderViewHolder.name.setText(foods.get(i).getName());
        OrderViewHolder.price.setText(foods.get(i).getPrice()+" baht");
        OrderViewHolder.des.setText(foods.get(i).getDes());*/
        holder.textView.setText(mDataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}

class OrderViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView textView;
    public OrderViewHolder(TextView v) {
        super(v);
        /*TODO: Set details on each item, Example from SearchAdapter:
        name = (TextView)itemView.findViewById(R.id.fname);
        price = (TextView)itemView.findViewById(R.id.price);
        des = (TextView)itemView.findViewById(R.id.des); */
        textView = v;
    }
}

