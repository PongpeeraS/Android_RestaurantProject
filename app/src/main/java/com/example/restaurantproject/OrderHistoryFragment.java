package com.example.restaurantproject;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*Second fragment of the OrderActivity, displays the previous orders*/
public class OrderHistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_history);

        // Creating & setting a linear layout manager for the RecyclerView
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Specifying the Orders adapter
        String[] str = new String[]{"Gamma", "Zeta"};
        mAdapter = new OrderAdapter(str); //TODO: CHANGE NULL INTO HISTORY ORDERS DATA!
        recyclerView.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        return view;
    }
}