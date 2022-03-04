package com.example.final_project_cs361;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Queue;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MerchantMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MerchantMainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "MerchantMainFragment";
    SwipeRefreshLayout mSwipeRefreshLayout;
    CardCustomerViewHolder cardCustomerViewHolder;

    RecyclerView recyclerView;

    public MerchantMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MerchantMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MerchantMainFragment newInstance() {
        return new MerchantMainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant_main, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.merchantRecyclerView);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshMerchant);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
//                mSwipeRefreshLayout.setRefreshing(true);
                // Fetching data from server
                loadRecyclerViewData();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onRefresh() {
        loadRecyclerViewData();
    }

//    public void setMessage(CustomerBookingQueue customerBookingQueue) {
//    }

    private void loadRecyclerViewData() {
        Log.d(TAG, "START loadRecyclerViewData");
        mSwipeRefreshLayout.setRefreshing(true);
        Log.d(TAG, "q size : " + CustomerBookingNotifyManagement.length());
//        customerList.clear();
        Queue<MessageTransaction> queue = CustomerBookingNotifyManagement.getQueue();
        for (MessageTransaction customerBookQueue : CustomerBookingNotifyManagement.getQueue()) {
            Log.d(TAG, customerBookQueue.getName_());
            QueueManager.customerList.add(customerBookQueue);
            CustomerBookingNotifyManagement.getQueue().remove(customerBookQueue);
        }
        Log.d(TAG, "q size : " + CustomerBookingNotifyManagement.length());
        Log.d(TAG, "Customer List size : " + QueueManager.customerList.size());
        cardCustomerViewHolder = new CardCustomerViewHolder(getActivity(), /*QueueManager.customerList,*/ this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardCustomerViewHolder);
        mSwipeRefreshLayout.setRefreshing(false);
        Log.d(TAG, "END loadRecyclerViewData");
    }
}