package com.example.final_project_cs361;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueManager {
    private static final String TAG = "QueueManager";
    private static Map<String, Queue<MessageTransaction>> mapQueue_ = new HashMap<>();
    private final static String a = "A";
    private final static String b = "B";
    private static int aq = 1;
    private static int bq = 1;
    public static ArrayList<MessageTransaction> customerList = new ArrayList<>();

    public static MessageTransaction add(MessageTransaction messageTransaction) {
        Log.d(TAG, "add");
        MessageTransaction messageTransaction1 = new MessageTransaction();
        messageTransaction1.setTime_(messageTransaction.getTime_());
        messageTransaction1.setEmail_(messageTransaction.getEmail_());
        messageTransaction1.setName_(messageTransaction.getName_());
        messageTransaction1.setDate_(messageTransaction.getDate_());
        messageTransaction1.setPhone_(messageTransaction.getPhone_());
        messageTransaction1.setSeats_(messageTransaction.getSeats_());
        messageTransaction1.setToken_(messageTransaction.getToken_());
        messageTransaction1.setMerchantName_(messageTransaction.getMerchantName_());
        Log.d(TAG, "seats " + messageTransaction.getSeats_() + " " + messageTransaction.getName_());
        Queue<MessageTransaction> q = null;
        if (messageTransaction1.getSeats_() >= 1 && messageTransaction1.getSeats_() <= 4) {
            Log.d(TAG, "seats >= 1 && <= 4");
            if (!mapQueue_.containsKey(a)) {
                Log.d(TAG, "not containkey A");
                q = new PriorityQueue<>();
                mapQueue_.put(a, q);
            } else {
                Log.d(TAG, "containkey A");
                q = mapQueue_.get(a);
            }

            messageTransaction1.setLane_(a);
            messageTransaction1.setQnum_(aq);
            aq++;
        } else {
            Log.d(TAG, "seats > 4");
            if (!mapQueue_.containsKey(b)) {
                Log.d(TAG, "not containkey B");
                q = new PriorityQueue<>();
                mapQueue_.put(b, q);
            } else {
                q = mapQueue_.get(b);
            }
            messageTransaction1.setLane_(b);
            messageTransaction1.setQnum_(bq);
            aq++;
        }

        try {
            q.add(messageTransaction1);

            Log.d(TAG, "end add q size is " + q.size());

            Log.d(TAG, "q num is " + messageTransaction1.getQnum_());
        } catch (Exception ex) {
            Log.d(TAG, "Error : " + ex);
        }

        return messageTransaction1;
    }

    public static MessageTransaction pop(String lane) {
        Queue<MessageTransaction> q = mapQueue_.get(lane);
        if (q != null) {
            return q.poll();
        }
        return null;
    }

    public static MessageTransaction front(String lane) {
        Queue<MessageTransaction> q = mapQueue_.get(lane);
        if (q == null) {
            return null;
        }
        return q.peek();
    }

    public static int size(String lane) {
        Queue<MessageTransaction> q = mapQueue_.get(lane);
        if (q == null) {
            return 0;
        }
        return q.size();
    }

    public static void reset() {
        aq = 1;
        bq = 1;
    }
}
