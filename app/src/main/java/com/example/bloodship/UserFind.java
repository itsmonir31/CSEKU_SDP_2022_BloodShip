package com.example.bloodship;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UserFind {
    public static String typeI, mk;
    int typeX;
    Context context;

    public UserFind() {
    }

    public UserFind(Context context) {
//        super(contentLayoutId);
        this.context = context;
    }

    /*public static String getTypeI() {
        return typeI;
    }*/

    public int USER_find() {
        final String[] type_ = new String[1];
        SharedPreferences sp = context.getSharedPreferences("credentials", MODE_PRIVATE);
        if (sp.contains("email")) {
            String email = sp.getString("email", "");
            String url = Urls.USER_TYPE_FIND + email;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {

                    if (response.equals("admin")) {
                        type_[0] = response;
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();


                    } else {
                        type_[0] = "general";
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();

                        Toast.makeText(context, "under response: " + type_[0], Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Server Problem!!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();

//                    map.put("email", email.getText().toString());
//                    map.put("password", encrypt(password));

                    return map;
                }

            };

            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(stringRequest);
        } else {
            String url = Urls.USER_TYPE_FIND + "sdh";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {

                    typeX = 2;
//                    Toast.makeText(context, "under response: " + typeX, Toast.LENGTH_SHORT).show();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Server Problem!!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();

//                    map.put("email", email.getText().toString());
//                    map.put("password", encrypt(password));

                    return map;
                }

            };
//
            RequestQueue queue = Volley.newRequestQueue(context);

            queue.add(stringRequest);

        }

        /* return typeI; */
        return typeX;
    }
}
