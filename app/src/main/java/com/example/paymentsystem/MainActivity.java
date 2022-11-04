package com.example.paymentsystem;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    Button paybtn;
    TextView paystatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Checkout.preload(getApplicationContext());

        paybtn = findViewById(R.id.pay_btn);
        paystatus = findViewById(R.id.paystatus);

        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentNow("1000");
            }
        });
    }

    private void PaymentNow(String amount) {

        final Activity activity = this;


        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_KTw816Gbln0tQd");
        checkout.setImage(R.drawable.ic_launcher_foreground);

        double finalAmount = Float.parseFloat(amount)*100;

        try {
            JSONObject options = new JSONObject();
            options.put("name","KUSHAL PAREEK");
            options.put("description","Reference No. #123456");
            options.put("image","https://s3.amazonaws.com/rzp-mobile/image/rzp.png");
            options.put("theme.color","3399cc");
            options.put("currency","INR");
            options.put("final",finalAmount+"");
            options.put("prefill.email","kushalpareek82@gmail.com");
            options.put("prefill.contact","7340549069");

            checkout.open(activity,options);


        }catch (Exception e){
            Log.e("TAG","Error in starting Razorpay checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

        Toast.makeText(getApplicationContext(),"Payment success",Toast.LENGTH_SHORT).show();
        paystatus.setText(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        paystatus.setText("failed");


    }
}