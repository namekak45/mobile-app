package com.example.final_project_cs361;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubmitQueueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubmitQueueFragment extends Fragment  {

    private static final String TAG = "SubmitQueueFragment";
    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    Button laneA, laneB;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SubmitQueueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubmitQueueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubmitQueueFragment newInstance(String param1, String param2) {
        SubmitQueueFragment fragment = new SubmitQueueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submit_queue, container, false);

        laneA = view.findViewById(R.id.button_lan_A);
        laneB = view.findViewById(R.id.button_lan_B);

        laneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageTransaction messageTransaction = QueueManager.pop("A");
                if (messageTransaction != null) {
                    messageTransaction.setBookingStatus_(MessageTransaction.BookingStatus.SUCCESS);
                    sendMessage(messageTransaction);
                }
            }
        });

        laneB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageTransaction messageTransaction = QueueManager.pop("B");
                if (messageTransaction != null) {
                    messageTransaction.setBookingStatus_(MessageTransaction.BookingStatus.SUCCESS);
                    sendMessage(messageTransaction);
                }
            }
        });

        return view;
    }


    private void sendMessage(MessageTransaction messageTransaction) {
        Log.d(TAG, "sendMessage");
        String title = "Your Q is " + messageTransaction.getLane_() + messageTransaction.getQnum_();
        String body = "Your queue arrive now.";
        String to = messageTransaction.getToken_();
        messageTransaction.setMode(1);
        JSONObject msgObj = JSONUtil.CreateJSONOBJ(messageTransaction, title, body, to);
        new CallAPI().execute(FCM_MESSAGE_URL, "POST", msgObj.toString());
//        try {
//            JSONObject msgObj = new JSONObject();
//            msgObj.put("to", customerBookingQueue.getToken_());
//            JSONObject notifyObj = new JSONObject();
//            notifyObj.put("body", "Please don't go too far!!!");
//            notifyObj.put("title", "Your Q is " + customerBookingQueue.getLane_() + customerBookingQueue.getQnum_());
//
//            msgObj.put("notification", notifyObj);
//            JSONObject dataObj = new JSONObject();
//            dataObj.put("name", customerBookingQueue.getName_());
//            dataObj.put("phone", customerBookingQueue.getPhone_());
//            dataObj.put("email", customerBookingQueue.getEmail_());
//            dataObj.put("date", customerBookingQueue.getDate_());
//            dataObj.put("time", customerBookingQueue.getTime_());
//            dataObj.put("numberOfSeats", customerBookingQueue.getSeats_());
//            dataObj.put("token", customerBookingQueue.getToken_());
//            dataObj.put("lane", customerBookingQueue.getLane_());
//            dataObj.put("qnum", customerBookingQueue.getQnum_());
//            dataObj.put("merchantName", customerBookingQueue.getMerchantName_());
//            dataObj.put("bookingStatus", customerBookingQueue.getBookingStatus_());
//
//            msgObj.put("data", dataObj);
//
//            new CallAPI().execute(FCM_MESSAGE_URL, "POST", msgObj.toString());
//
//        } catch (JSONException ex) {
//            Log.d(TAG, "JSONException " + ex);
//
//        } catch (Exception ex) {
//            Log.d(TAG, "Exception " + ex);
//        }
    }

}