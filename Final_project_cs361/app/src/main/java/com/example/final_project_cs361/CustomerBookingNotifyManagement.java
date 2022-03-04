package com.example.final_project_cs361;

import android.util.Log;

import java.util.PriorityQueue;
import java.util.Queue;

public class CustomerBookingNotifyManagement {
    private static Queue<MessageTransaction>  queue = new PriorityQueue<MessageTransaction>();
    public static Queue<MessageTransaction> getQueue()
    {
        Log.d("Notify", "Add");
        if (queue == null) {
            Log.d("Notify", "Q is null");
        } else {
            Log.d("Notify", "Q is not null");
        }
        return queue;
    }
    public static void setQueue(Queue<MessageTransaction> q) { queue = q; }
    public static void add(MessageTransaction customer) {
        Log.d("Notify", "Add");
        if (queue == null) {
            Log.d("Notify", "Q is null");
        } else {
            Log.d("Notify", "Q is not null");
            queue.add(customer);
        }
    }
    public static MessageTransaction poll() {
        return queue.poll();
    }

    public static int length() {
        return queue.size();
    }

    public static boolean isEmpty() {
        return queue.isEmpty();
    }
}
