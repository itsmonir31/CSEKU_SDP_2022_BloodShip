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
import com.example.bloodship.Adapters.adapterNestedChild;
import com.example.bloodship.Adapters.adapterNestedParent;
import com.example.bloodship.Adapters.adapterRequestBlood;
import com.example.bloodship.Models.NestedChildModel;
import com.example.bloodship.Models.NestedParentModel;
import com.example.bloodship.Models.modelBloodReq;
import com.example.bloodship.others.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MyRequest extends AppCompatActivity {

    RecyclerView recyclerView, CRV;
    adapterNestedParent adapterNestedParent;
    private List<NestedParentModel> parentData_list;
    List<NestedChildModel> childData_list;
    private String requestID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request);

        CRV = findViewById(R.id.childRecyclerView);
        recyclerView = findViewById(R.id.nestedRVparent);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        //reverse the list (Latest Features)
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);


        parentData_list = new ArrayList<>();
        childData_list = new ArrayList<>();

        LoadAllRequests();
    }

    private void LoadAllRequests() {
        String s_ID = SharedPref.readSP(getApplicationContext(), "s_id", "0");

        String URL_ = Urls.ROOT_URL + "fetch_my_requests.php" + Urls.KEY + "&s_id=" + s_ID;
//        requestList_info.clear(); //clear the previous data


        JsonArrayRequest request = new JsonArrayRequest(URL_, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                JSONObject object;
                Log.d("json", array.toString());
                for (int i = 0; i <= array.length(); i++) {
                    try {
                        childData_list.clear();

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

                        requestID = object.getString("request_id");

                        //Toast.makeText(getApplicationContext(), name+bg+discipline+mob, Toast.LENGTH_SHORT).show();

                        NestedParentModel data = new NestedParentModel();
                        data.setProblem(Problem);
                        data.setBg(bg);
                        data.setQuantity(quantity);
                        data.setTime(time);
                        data.setDate(date);
                        data.setAddress(address);
                        data.setA_contact(a_contact);
                        data.setR_contact(r_contact);
                        data.setReq_date(req_date);
                        data.setReqID(requestID);

                        parentData_list.add(data);

//                        loadChildDate(requestID);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

//                Log.d("child___xc", parentData_list.get(0).getAddress());
                adapterNestedParent = new adapterNestedParent(MyRequest.this, parentData_list);
                recyclerView.setAdapter(adapterNestedParent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyRequest.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MyRequest.this);
        requestQueue.add(request);
    }

    private void loadChildDate(String parentID) {
        childData_list.clear();

        String URL_ = Urls.ROOT_URL + "interestedDonor.php" + Urls.KEY + "&reqID=" + parentID;
        Log.d("childurl", URL_);

        JsonArrayRequest request = new JsonArrayRequest(URL_, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {

                if (array.isNull(0)) {
                    childData_list.clear();
//                    adapterNestedParent = new adapterNestedParent(MyRequest.this, parentData_list, childData_list);
                    recyclerView.setAdapter(adapterNestedParent);
                } else {


                    JSONObject object1;


                    Log.d("jsonChild", parentID + "----" + array.toString() + "---" +childData_list.toString());
                    for (int i = 0; i <= array.length(); i++) {
                        try {

                            object1 = array.getJSONObject(i);

                            String name = object1.getString("name");
                            String bg = object1.getString("bg");
                            String discipline = object1.getString("discipline");
                            String studentID = object1.getString("s_id");
                            String phone = object1.getString("phone");


                            NestedChildModel data = new NestedChildModel();

                            data.setName(name);
                            data.setStudentID(studentID);
                            data.setDiscipline(discipline);
                            data.setBloodGroup(bg);
                            data.setPhone(phone);

                            childData_list.add(data);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

                Log.d("zzzz", parentData_list.size()+"-----"+childData_list.size());
//                adapterNestedParent = new adapterNestedParent(MyRequest.this, parentData_list, childData_list);
                recyclerView.setAdapter(adapterNestedParent);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyRequest.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MyRequest.this);
        requestQueue.add(request);

        childData_list.clear();
        Log.d("zzz", parentData_list.size()+"-----"+childData_list.size());
//        Log.d("child__gj", childData_list.get(0).getDiscipline());
    }
}