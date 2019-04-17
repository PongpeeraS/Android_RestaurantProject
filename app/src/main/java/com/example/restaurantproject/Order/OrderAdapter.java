package com.example.restaurantproject.Order;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.restaurantproject.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private String[] mDataset;
    private Context mContext;

    public OrderAdapter(Context context, String[] myDataset) {
        //TODO: Change dataset (including init)
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //TODO: Edit layout_item_order for this menu
        View itemView = inflater.inflate(R.layout.layout_item_order, parent,false);
        return new OrderViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {
        // - get element from the dataset at this position
        // - replace the contents of the view with that element
        /*TODO: Set details on each item, Example from SearchAdapter:
        OrderViewHolder.name.setText(foods.get(i).getName());
        OrderViewHolder.price.setText(foods.get(i).getPrice()+" baht");
        OrderViewHolder.des.setText(foods.get(i).getDes());*/
        holder.textView.setText(mDataset[position]);


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                intent.putExtra("oname", mDataset[position]);
                //intent.putExtra("image_name", mImageNames.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}

class OrderViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    LinearLayout parentLayout;
    public OrderViewHolder(View view) {
        super(view);
        /*TODO: Set details on each item, Example from SearchAdapter:
        name = (TextView)itemView.findViewById(R.id.fname);
        price = (TextView)itemView.findViewById(R.id.price);
        des = (TextView)itemView.findViewById(R.id.des); */
        parentLayout = itemView.findViewById(R.id.layout_orderitem);
        textView = view.findViewById(R.id.oname);
        //TODO: OnClick to more details/activity (same for both current & history?)
    }
}

