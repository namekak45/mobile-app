package com.example.final_project_cs361;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class Merchant implements Parcelable {
    final private String merchant_fs = "merchant.txt";
    private String name_;
    private String userName_;
    private String password_;
    private String email_;
    private String phone_;
    private String token_;


    public Merchant() {
        name_ = "";
        email_ = "";
        phone_ = "";
        userName_ = "";
        password_ = "";
    }

    protected Merchant(Parcel in) {
        name_ = in.readString();
        email_ = in.readString();
        phone_ = in.readString();
        userName_ = in.readString();
        password_ = in.readString();
        token_ = in.readString();
    }

    public static final Parcelable.Creator<Merchant> CREATOR = new Parcelable.Creator<Merchant>() {
        @Override
        public Merchant createFromParcel(Parcel in) {
            return new Merchant(in);
        }

        @Override
        public Merchant[] newArray(int size) {
            return new Merchant[size];
        }
    };

    public String getName_() {
        return name_;
    }

    public String getToken_() {
        return token_;
    }

    public String getEmail_() {
        return email_;
    }

    public String getPhone_() {
        return phone_;
    }

    public String getUserName_(String regisUserName) {
        return userName_;
    }

    public String getPassword_(String regisPassword) {
        return password_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    public void setEmail_(String email_) {
        this.email_ = email_;
    }

    public void setPhone_(String phone_) {
        this.phone_ = phone_;
    }

    public void setPassword_(String password_) {
        this.password_ = password_;
    }

    public void setUserName_(String userName_) {
        this.userName_ = userName_;
    }

    public void setToken_(String token_) {
        this.token_ = token_;
    }


    public void loadMerchant(Context ctx) {
        FileInputStream fis = null;

        try {
            fis = ctx.openFileInput(merchant_fs);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            int i = 1;
            while ((text = br.readLine()) != null) {
                switch (i) {
                    case 1:
                        name_ = text;
                        break;
                    case 2:
                        email_ = text;
                        break;
                    case 3:
                        phone_ = text;
                    case 4:
                        userName_ = text;
                    case 5:
                        password_ = text;

                }
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(Context ctx, String name, String email, String phone, String userName, String password, String token) {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("\n");
        sb.append(userName);
        sb.append("\n");
        sb.append(password);
        sb.append("\n");
        sb.append(email);
        sb.append("\n");
        sb.append(phone);
        sb.append("\n");
        sb.append(token);
        sb.append("\n");

        FileOutputStream fOut;

        try {
            fOut = ctx.openFileOutput(merchant_fs, Context.MODE_APPEND);
            fOut.write(sb.toString().getBytes());
            fOut.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name_);
        parcel.writeString(email_);
        parcel.writeString(phone_);
        parcel.writeString(userName_);
        parcel.writeString(password_);
        parcel.writeString(token_);
    }
}
