package com.example.final_project_cs361;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class LoginMerchant extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "LoginMerchant";
    public Merchant merchant_;
    EditText mUserName, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_merchant);
        mUserName = (EditText)findViewById(R.id.log_merchant_user);
        mPassword = (EditText)findViewById(R.id.log_merchant_Password);

        mUserName.setText("test04");
        mPassword.setText("1234");
        Bundle extras = getIntent().getExtras();
        Log.d(TAG, "extras");
        if (extras != null) {
            merchant_ = extras.getParcelable("merchant");
            extras.clear();
        }
    }

    public void SigninMerchant(View view) {
        Intent intent = new Intent(this, RegisterMerchant.class);
//        intent.putExtra("merchant", merchant_);
        startActivity(intent);
    }

    public void login(View view) {

        String userName = mUserName.getText().toString(), password = mPassword.getText().toString();
        Log.d(TAG, "start log-in " + userName);
        db.collection("merchants").whereEqualTo("user_name", userName)
            .get()
            .addOnCompleteListener(task -> {
                Log.d(TAG, "onComplete ");

                if (task.isSuccessful()) {
                    Log.d(TAG, "isSuccessful ");
                    boolean haveResult = false;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        haveResult = true;
                        String pwd = (String) document.get("password");
                        if (pwd.equals(password)) {
//                            Toast.makeText(this, "login success!!!", Toast.LENGTH_SHORT).show();
                            Merchant merchant = new Merchant();
                            merchant.setName_((String) document.get("name"));
                            merchant.setEmail_((String) document.get("email"));
                            merchant.setPhone_((String) document.get("phone"));
                            MerchantCurrent.set(merchant);

                            UserMode.setMode_(UserMode.Mode.MERCHANT);
                            Intent intent = new Intent(this, MerchantMainActivity.class);
//                            intent.putExtra("merchant", merchant_);
                            startActivity(intent);
                        } else {
                            mPassword.setError("Invalid password");
                        }
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                    if (!haveResult) {
                        mUserName.setError("Invalid user name");
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                    mUserName.setError("Invalid user name");
                }
            });
    }
}