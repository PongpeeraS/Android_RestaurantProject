package com.example.restaurantproject.Order;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantproject.R;
import com.google.firebase.auth.FirebaseAuth;

/*Second fragment of the OrderActivity, displays the previous orders*/
public class OrderHistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private OrderDatabase orderDatabase;
    private FirebaseAuth auth;
    private String uID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        recyclerView = view.findViewById(R.id.recycler_history);

        // Initializing database & Firebase uID
        orderDatabase = new OrderDatabase(this.getContext());
        auth = FirebaseAuth.getInstance();
        uID = auth.getCurrentUser().getUid();

        // Creating & setting a linear layout manager for the RecyclerView
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Specifying the Orders adapter
        mAdapter = new OrderAdapter(this.getContext(),  orderDatabase.getHistoryOrder(uID));
        recyclerView.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        return view;
    }
}