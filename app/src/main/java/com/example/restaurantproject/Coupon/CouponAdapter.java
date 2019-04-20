package com.example.restaurantproject.Coupon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.restaurantproject.R;

import java.util.List;

/*Adapter for CouponActivity's RecyclerView*/
public class CouponAdapter extends RecyclerView.Adapter<CouponViewHolder> {
    private List<Coupon> mDataset;
    private Context mContext;
    private couponDatabase couponDatabase;

    public CouponAdapter(Context mContext, List<Coupon> mDataset, couponDatabase couponDatabase) {
        this.mDataset = mDataset;
        this.mContext = mContext;
        this.couponDatabase = couponDatabase;
    }

    // Create a new view (invoked by the layout manager)
    @Override
    public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item_coupon, parent,false);
        return new CouponViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CouponViewHolder holder, final int i) {
        // - get element from the dataset at this position
        // - replace the contents of the view with that element
        final Coupon c = mDataset.get(i);
        holder.cname.setText(c.getName());
        holder.cdate.setText(mContext.getString(R.string.text_cdate)+": "+c.getEnddate());

        // For each list item, the user can click to view more details & use the ticket
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Dialog is used here for users to view info & use the coupon*/
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                String s1 = mContext.getString(R.string.text_cname)+":\n"+c.getName();
                String s2 = mContext.getString(R.string.text_cdesc)+":\n"+c.getDesc();
                String s3 = mContext.getString(R.string.text_cdate)+":\n"+c.getEnddate();
                String s4 = mContext.getString(R.string.text_ccode)+":\n"+c.getCode();
                String s5 = mContext.getString(R.string.text_cnumuse)+":\n"+c.getNumOfUses();
                builder.setMessage(s1+"\n\n"+s2+"\n\n"+s3+"\n\n"+s4+"\n\n"+s5);
                // Positive button = use coupon (i.e. remaining uses -1 or delete coupon)
                builder.setPositiveButton(mContext.getString(R.string.text_use), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(c.getNumOfUses() == 1) {
                            couponDatabase.deleteCoupon(c); //1 use left = delete coupon from db
                            mDataset.remove(i);
                            notifyDataSetChanged();
                        }
                        else {
                            couponDatabase.updateCoupon(c); //remove 1 use from coupon in db
                            mDataset.get(i).setNumOfUses(c.getNumOfUses()-1);
                            notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton(mContext.getString(R.string.text_back), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

class CouponViewHolder extends RecyclerView.ViewHolder {
    public TextView cname, cdate;
    LinearLayout parentLayout;

    public CouponViewHolder(View view) {
        super(view);
        parentLayout = itemView.findViewById(R.id.layout_coupon);
        cname = view.findViewById(R.id.cname);
        cdate = view.findViewById(R.id.cdate);
    }
}

