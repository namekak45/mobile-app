package com.example.final_project_cs361;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;

public class CardCustomerViewHolder extends RecyclerView.Adapter<CardCustomerViewHolder.CustomerViewHolder> {
    private static final String TAG = "CardCustomerViewHolder";
    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
//    ArrayList<MessageTransaction> customers;
    Context context;
    MerchantMainFragment merchantMainFragment;

    public CardCustomerViewHolder(Context context, /*ArrayList<MessageTransaction> customers,*/ MerchantMainFragment merchantMainFragment) {
        this.context = context;
//        this.customers = customers;
        this.merchantMainFragment = merchantMainFragment;

        Log.d(TAG, "CardCustomerViewHolder customers size = " + QueueManager.customerList.size());
    }


    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder ");
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_customer_booking, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // pass the view to View Holder
        return new CardCustomerViewHolder.CustomerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder ");
        MessageTransaction customer = QueueManager.customerList.get(position);
        int pos = position;
//        MerchantViewHolder holder = (MerchantViewHolder)holderParent;
        holder.tvName.setText(customer.getName_());
        holder.tvToken.setText(customer.getToken_());
        holder.tvPhone.setText(customer.getPhone_());
        holder.tvDate.setText(customer.getDate_());
        holder.tvTime.setText(customer.getTime_());
        holder.tvSeat.setText(String.valueOf(customer.getSeats_()));

        holder.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btCancel.setEnabled(false);
                holder.btSubmit.setEnabled(false);
                MessageTransaction messageTransaction = customer;// CustomerBookingNotifyManagement.poll();
                messageTransaction.setBookingStatus_(MessageTransaction.BookingStatus.WAIT);
                QueueManager.customerList.remove(pos);
//                Log.d(TAG, "customerBookingQueue " + ((customerBookingQueue != null)? customerBookingQueue.getName_(): "null"));
                MessageTransaction result = QueueManager.add(messageTransaction);
                CustomerBookingQueueManager.set(result);
                notifyDataSetChanged();
                sendMessage(result);
                merchantMainFragment.onRefresh();
            }
        });

        holder.btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QueueManager.customerList.remove(pos);
                notifyDataSetChanged();
                merchantMainFragment.onRefresh();
            }
        });
    }

    @Override
    public int getItemCount() {
        return QueueManager.customerList.size();
    }

    private void sendMessage(MessageTransaction messageTransaction) {
        Log.d(TAG, "sendMessage");
        String title = "Your Q is " + messageTransaction.getLane_() + messageTransaction.getQnum_();
        Log.d(TAG, title);
        String body = "Please don't go too far!!!";
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
//            msgObj.put("data", dataObj);
//
////            Log.d(TAG, "tokenMerchant_ " + tokenMerchant_);
////            String result = api.doInBackground(FCM_MESSAGE_URL, "POST", msgObj.toString());
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

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvToken;
        TextView tvPhone;
        TextView tvDate;
        TextView tvTime;
        TextView tvSeat;
        Button btSubmit;
        Button btCancel;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.card_cus_name);
            tvToken = itemView.findViewById(R.id.card_cus_token_id);
            tvPhone = itemView.findViewById(R.id.card_cus_phone);
            tvDate = itemView.findViewById(R.id.card_cus_date);
            tvTime = itemView.findViewById(R.id.card_cus_time);
            tvSeat = itemView.findViewById(R.id.card_cus_num_seat);
            btSubmit = itemView.findViewById(R.id.card_cus_button_submit);
            btCancel = itemView.findViewById(R.id.card_cus_button_cancel);
        }
    }
}
