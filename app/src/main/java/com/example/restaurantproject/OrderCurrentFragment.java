package com.example.restaurantproject;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*First fragment of the OrderActivity, displays the current orders*/
public class OrderCurrentFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_order_current, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_current);

        // Creating & setting a linear layout manager for the RecyclerView
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Specifying the Orders adapter
        String[] str = new String[]{"Alpha", "Beta"};
        mAdapter = new OrderAdapter(this.getContext(), str); //TODO: CHANGE NULL INTO CURRENT ORDERS DATA!
        recyclerView.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        return view;
    }
}