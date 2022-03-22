package com.example.bloodship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    //myAdapter myAdapter;
    private mainAdapter main_Adapter;
    private List<modelRV> dataList_info;

    String bg_spinner = "All", disc_spinner = "All", dis, lett , sign;
    int userType;
    Spinner dropdown, diciplineSpinner;

    //FastScroller fastScroller;
//    DragScrollBar scrollBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dropdown = findViewById(R.id.fgf);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bg, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bg_spinner = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), bg_spinner, Toast.LENGTH_SHORT).show();

                LoadAllUserInfo(bg_spinner, disc_spinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        diciplineSpinner = findViewById(R.id.discipline_spinner);
        ArrayAdapter<CharSequence> adapter_ = ArrayAdapter.createFromResource(this, R.array.disc, android.R.layout.simple_spinner_item);
        adapter_.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diciplineSpinner.setAdapter(adapter_);
        diciplineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                disc_spinner = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), disc_spinner, Toast.LENGTH_SHORT).show();

                LoadAllUserInfo(bg_spinner, disc_spinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerView = findViewById(R.id.recView);

//        scrollBar = findViewById(R.id.touchScrollBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //sql
        dataList_info = new ArrayList<>();

        LoadAllUserInfo(bg_spinner, disc_spinner);
        //sql-end


    }

    public static char getCharFromString(String str, int index) {
        return str.charAt(index);
    }

    private void bloodGroupText(String dropItem) {
        lett = ""; sign="";
        int i = 0;
        while (true) {
            if (Character.compare(getCharFromString(dropItem, i), '+') == 0) {
                sign = "pos";
                break;
            } else if (Character.compare(getCharFromString(dropItem, i), '-') == 0) {
                sign = "neg";
                break;
            } else {
                lett = lett + String.valueOf(getCharFromString(dropItem, i));
                i++;
            }
        }
    }

    private void LoadAllUserInfo(String dropItem, String disc) {
        String URL_ = "";
        dataList_info.clear(); //clear the previous data



        if (dropItem.equals("All") && disc.equals("All")) {
            URL_ = Urls.FETCH_USER_DATA;//+ "&disc=" + disc
        } else if (dropItem.equals("All") && disc.compareTo("All") != 0) {
            URL_ = Urls.FETCH_USER_DATA + "&disc=" + disc;
        } else if (dropItem.compareTo("All") != 0 && disc.equals("All")) {
            bloodGroupText(dropItem);
            URL_ = Urls.FETCH_USER_DATA + "&bg_=" + lett + "&sign=" + sign;
        } else if (dropItem.compareTo("All") != 0 && disc.compareTo("All") != 0) {

            bloodGroupText(dropItem);
            URL_ = Urls.FETCH_USER_DATA + "&bg_=" + lett + "&sign=" + sign + "&disc=" + disc;
        }

        Log.d("url", URL_);
        Log.d("lett", lett + sign+ disc);

        JsonArrayRequest request = new JsonArrayRequest(URL_, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                JSONObject object;
                Log.d("json", array.toString());
                for (int i = 0; i <= array.length(); i++) {
                    try {
                        object = array.getJSONObject(i);

                        String bg = object.getString("bg_cbdsb");
                        String discipline = object.getString("discipline");
                        //String lastDonate = object.getString("bg_cbdsb");
                        //String addr = object.getString("bg_cbdsb");
                        String name = object.getString("name");
                        String mob = object.getString("phn");
                        //String update = object.getString("bg_cbdsb");

                        dis = discipline;

                        //Toast.makeText(getApplicationContext(), name+bg+discipline+mob, Toast.LENGTH_SHORT).show();

                        modelRV data = new modelRV();
                        data.setName(name);
                        data.setBloodGroup(bg);
                        data.setDiscipline(discipline);
                        data.setPhone(mob);
                        data.setHomeDistrict("addr");
                        data.setReqDate("update");
                        data.setLastDonate("lastDonate");

                        dataList_info.add(data);


//                        while(object.length()>0)
//                            object.remove(object.keys().next());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                UserFind uf = new UserFind(getApplicationContext());
                userType = uf.USER_find();
                Toast.makeText(getApplicationContext(), "main activity: " + userType, Toast.LENGTH_SHORT).show();

                main_Adapter = new mainAdapter(MainActivity.this, dataList_info, "dfh@sdfgsd.bpm");
                recyclerView.setAdapter(main_Adapter);


                //dataList_info.clear();
//                while(object.length()>0)
//                    object.remove(object.keys().next());

//                scrollBar.setIndicator(new CustomIndicator(MainActivity.this), true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        myAdapter.startListening();
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        myAdapter.stopListening();
//    }


}