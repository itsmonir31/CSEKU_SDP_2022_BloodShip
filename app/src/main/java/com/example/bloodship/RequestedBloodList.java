package com.example.bloodship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodship.Adapters.adapterRequestBlood;
import com.example.bloodship.Models.modelBloodReq;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RequestedBloodList extends AppCompatActivity {

    RecyclerView recyclerView;
    adapterRequestBlood adapterReqBlood;
    private List<modelBloodReq> requestList_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_blood_list);

        recyclerView = findViewById(R.id.recViewRequestedBlood);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        //reverse the list (Latest Features)
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        requestList_info = new ArrayList<>();

        LoadAllRequests();

//        adapterReqBlood = new adapterRequestBlood(options_);
//        recyclerView.setAdapter(adapterReqBlood);
    }

    private void LoadAllRequests( ) {
        String URL_ = Urls.ROOT_URL+"fetch_requests.php"+Urls.KEY;
//        requestList_info.clear(); //clear the previous data


        JsonArrayRequest request = new JsonArrayRequest(URL_, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                JSONObject object;
                Log.d("json", array.toString());
                for (int i = 0; i <= array.length(); i++) {
                    try {
                        object = array.getJSONObject(i);

                        String Problem = object.getString("problem");
                        String bg = object.getString("bg_cbdsb");
                        String quantity = object.getString("quantity");
                        String time = object.getString("time");
                        String date = object.getString("date");
                        String address = object.getString("address");
                        String a_contact = object.getString("a_contact");
                        String r_contact = object.getString("r_contact");
                        String req_date = object.getString("req_date");
                        String reqID = object.getString("reqID");

                        //Toast.makeText(getApplicationContext(), name+bg+discipline+mob, Toast.LENGTH_SHORT).show();

                        modelBloodReq data = new modelBloodReq();
                        data.setProblem(Problem);
                        data.setBg(bg);
                        data.setQuantity(quantity);
                        data.setTime(time);
                        data.setDate(date);
                        data.setAddress(address);
                        data.setA_contact(a_contact);
                        data.setR_contact(r_contact);
                        data.setReq_date(req_date);
                        data.setReqID(reqID);

                        requestList_info.add(data);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapterReqBlood = new adapterRequestBlood(RequestedBloodList.this, requestList_info);
                recyclerView.setAdapter(adapterReqBlood);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RequestedBloodList.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(RequestedBloodList.this);
        requestQueue.add(request);
    }

}