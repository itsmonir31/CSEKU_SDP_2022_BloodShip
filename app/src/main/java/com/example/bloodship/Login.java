package com.example.bloodship;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodship.others.SharedPref;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    boolean session;
    EditText email, password;
    boolean valid;
    LottieAnimationView rprogress;
    CheckBox admin;

//    private static final String url = "https://mk31.xyz/API_rcku/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.signin_email);
        password = findViewById(R.id.signin_pass);

        rprogress = findViewById(R.id.lprogress);

        admin = (CheckBox) findViewById(R.id.checkBox);

    } //onCreate end


    public void session() {
        session = Boolean.valueOf(SharedPref.readSP(getApplicationContext(), "session", "false"));
        //Toast.makeText(getApplicationContext(), String.valueOf(session), Toast.LENGTH_LONG).show();
        if (session) {
            startActivity(new Intent(getApplicationContext(), HomeScreen.class));
            finish();
        }
    }


    //    check someone is already logged in or not
    @Override
    protected void onStart() {
        session();
        super.onStart();
    }

    public void forgetPassword(View view) {
        startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
    }

    public boolean checkField(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError("Fill this");
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    public String encrypt(EditText x){
//        char [] chars = x.getText().toString().toCharArray();
//        String ret="";
//
//        for (char c : chars){
//            c += 4;
//            ret += c;
//        }
//
//        return ret;
//    }

    public void tryLogin(View view) {
        if (checkField(email)) {
            checkField(password);
        }

        if (admin.isChecked() && valid) {
            rprogress.setVisibility(View.VISIBLE);

            loginFuncSQl("adminFetch.php");
        }else if (valid) {
            rprogress.setVisibility(View.VISIBLE);

            loginFuncSQl("login.php");
        }
    }


    public void loginFuncSQl_() {

    }

    public void loginFuncSQl(String base) {
        Log.d("check", Urls.ROOT_URL + base + Urls.KEY);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.ROOT_URL + base + Urls.KEY, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                email.getText().clear();
                password.getText().clear();

                if (response != null && response.length() >= 5) {
                    Toast.makeText(getApplicationContext(), "Login Successfull! " + response, Toast.LENGTH_LONG).show();

                    //shared-preference
//                    SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putString("email", email.getText().toString());
//                    editor.putString("pass", password.getText().toString());
//                    editor.commit();
                    SharedPref.saveSP(getApplicationContext(), "session", "true");
                    SharedPref.saveSP(getApplicationContext(), "s_id", response);
                    //SP end

                    rprogress.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                    finish();
                }else  if (response != null && response.length() >= 1 && !response.equals("fail")) {
                    Snackbar.make(email, "Success " + response, Snackbar.LENGTH_LONG).show();
                    SharedPref.saveSP(getApplicationContext(), "session", "true");
                    SharedPref.saveSP(getApplicationContext(), "s_id", response);
                    SharedPref.saveSP(getApplicationContext(), "isA", "true");

                    rprogress.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                    finish();
                }else if (response.equals("fail")) {
                    rprogress.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                email.getText().clear();
                password.getText().clear();
                rprogress.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Invalid Credentials.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put("email", email.getText().toString());
                map.put("password", password.getText().toString());

                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}