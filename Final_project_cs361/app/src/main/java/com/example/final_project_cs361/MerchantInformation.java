package com.example.final_project_cs361;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MerchantInformation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MerchantInformation extends Fragment {

    public MerchantInformation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment MerchantInformation.
     */
    // TODO: Rename and change types and number of parameters
    public static MerchantInformation newInstance() {
        return new MerchantInformation();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant_information, container, false);
        TextView tvName = view.findViewById(R.id.restaurant_name);
        tvName.setText(MerchantCurrent.get().getName_());
        TextView tvEmail = view.findViewById(R.id.restaurant_email);
        tvEmail.setText(MerchantCurrent.get().getEmail_());
        TextView tvPhone = view.findViewById(R.id.restaurant_tel);
        tvPhone.setText(MerchantCurrent.get().getPhone_());
        Button button = view.findViewById(R.id.restaurant_reset);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QueueManager.reset();
                alertView("Your queue was reset");
            }
        });

        return view;
    }

    private void alertView( String message ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle( "Q" )
                .setIcon(R.drawable.logo)
                .setMessage(message)
//     .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface dialoginterface, int i) {
//          dialoginterface.cancel();
//          }})
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                }).show();
    }
}