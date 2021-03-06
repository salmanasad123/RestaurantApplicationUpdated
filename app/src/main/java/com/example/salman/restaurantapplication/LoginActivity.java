package com.example.salman.restaurantapplication;


import android.content.Intent;
import android.content.SharedPreferences;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MTAG";

    Toolbar toolbar;
    EditText inputEmail;
    EditText inputPassword;
    Button btnSignUp;
    Button Login;

    String getEmail;
    String getPassword;

    List<Customer> customerList;
    Integer CustomerID;

    SharedPreferences sharedPreferences;
    String customerEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.loginActivityToolbar);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);



        inputEmail = findViewById(R.id.etInputEmail);
        inputPassword = findViewById(R.id.etInputPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        Login = findViewById(R.id.btnLogin);

        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmail = inputEmail.getText().toString().trim();
                getPassword = inputPassword.getText().toString().trim();


                Retrofit retrofit = RetrofitClient.getClient();

                ApiInterface apiInterface = retrofit.create(ApiInterface.class);

                Call<List<Customer>> listCall = apiInterface.postLoginData(getEmail, getPassword);

                listCall.enqueue(new Callback<List<Customer>>() {
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {


                        Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");


                        customerList = response.body();

                        if (!response.body().isEmpty()) {
                            for (int i = 0; i < customerList.size(); i++) {

                                CustomerID = customerList.get(i).getCustomerID();
                                EventBus.getDefault().postSticky(new AccountIDEvent(CustomerID));
                                customerEmail = customerList.get(i).getCustomerEmail();
                            }
                             SharedPreferences.Editor editor = sharedPreferences.edit();
                             editor.putString("username", customerEmail);
                             editor.putInt("customerID", CustomerID);
                             editor.apply();

                        }



                        //   AccountInfoEvent accountInfoEvent = new AccountInfoEvent(customerList);
                        //   EventBus.getDefault().postSticky(accountInfoEvent);


                        if (!response.body().isEmpty()) {

                            Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                            startActivity(intent);

                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "Credentials Do Not Match", Snackbar.LENGTH_LONG)
                                    .show();
                        }


                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {
                        Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");


                    }

                });

            }

        });

    }
}

