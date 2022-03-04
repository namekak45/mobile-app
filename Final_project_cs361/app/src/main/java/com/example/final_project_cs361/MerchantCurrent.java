package com.example.final_project_cs361;

public class MerchantCurrent {
    public static Merchant merchant_;

    public static Merchant get() { return  merchant_; }

    public static void set(Merchant merchant) {
        merchant_ = merchant;
    }
}
