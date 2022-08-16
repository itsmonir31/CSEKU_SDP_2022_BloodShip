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
import com.example.bloodship.Adapters.AdapterReport;
import com.example.bloodship.Adapters.adapterRequestBlood;
import com.example.bloodship.Models.ModelReport;
import com.example.bloodship.Models.modelBloodReq;
import com.example.bloodship.others.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Report extends AppCompatActivity {

    RecyclerView reportRView;
    AdapterReport adapterReport;
    private List<ModelReport> reportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        reportRView = findViewById(R.id.reportRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        reportRView.setLayoutManager(linearLayoutManager);

        reportList = new ArrayList<>();

        loadReport();
    }

    private void loadReport() {
        Boolean adm = Boolean.valueOf(SharedPref.readSP(getApplicationContext(), "isA", "false"));
        String sid = SharedPref.readSP(getApplicationContext(), "s_id", "0");
        String URL_;

        if (adm) {
            URL_ = Urls.ROOT_URL + "fetch_reports.php" + Urls.KEY + "&xdm=1";
        } else {
            URL_ = Urls.ROOT_URL + "fetch_reports.php" + Urls.KEY + "&sid=" + sid;
        }

        Log.d("adminspecific", URL_);
//        requestList_info.clear(); //clear the previous data


        JsonArrayRequest request = new JsonArrayRequest(URL_, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                JSONObject object;
                Log.d("json", array.toString());
                for (int i = 0; i <= array.length(); i++) {
                    try {
                        object = array.getJSONObject(i);

                        String bg = object.getString("bg_cbdsb");
                        String quantity = object.getString("quantity");
                        String managed = object.getString("managed_app");
                        String req_date = object.getString("req_date");
                        String name = object.getString("name");
                        String archive = object.getString("archive");
                        String reqID = object.getString("reqID");


                        ModelReport data = new ModelReport();

                        data.setBg(bg);
                        data.setTotal(quantity);
                        data.setReqDate(req_date);
                        data.setName(name);
                        data.setThroughApp(managed);
                        data.setArchive(Integer.valueOf(archive));
                        data.setReqID(Integer.valueOf(reqID));


                        reportList.add(data);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapterReport = new AdapterReport(Report.this, reportList);
                reportRView.setAdapter(adapterReport);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Report.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Report.this);
        requestQueue.add(request);
    }

}