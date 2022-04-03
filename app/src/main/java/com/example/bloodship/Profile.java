package com.example.bloodship;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodship.others.Age;
import com.example.bloodship.others.AgeCalc;
import com.example.bloodship.others.SharedPref;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    TextView name, sID, profAge, mobile, discipline, bg, lastDonate, status;
    ProgressBar progressBar;
    ImageView profileImg;
    String s__ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //hooks
        name = findViewById(R.id.p_nameTV);
        sID = findViewById(R.id.p_sidTV);
        profAge = findViewById(R.id.prof_age);
        mobile = findViewById(R.id.p_mobileTV);
        discipline = findViewById(R.id.p_disciplineTV);
        bg = findViewById(R.id.p_bgTV);
        lastDonate = findViewById(R.id.p_lastDate);
        status = findViewById(R.id.p_statustv);

        progressBar = findViewById(R.id.progressBarDate);

        profileImg = findViewById(R.id.profImgV);


        loadData();


    }

    public void myRequestDir(View view) {
        startActivity(new Intent(getApplicationContext(), MyRequest.class));
    }

    public void tryLogout(View view) {
        SharedPref.saveSP(getApplicationContext(), "session", "false");
//        Toast.makeText(getApplicationContext(), "Successfully Logout", Toast.LENGTH_SHORT).show();
        Snackbar.make(view, "Successfully Logout", Snackbar.LENGTH_LONG).show();

        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void loadData() {
        s__ID = SharedPref.readSP(getApplicationContext(), "s_id", "0");

        String URL_ = Urls.ROOT_URL + "fetch_profile.php" + Urls.KEY + "&s_id=" + s__ID;
        Log.d("ursl", URL_);
        JsonArrayRequest request = new JsonArrayRequest(URL_, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                JSONObject object;
                Log.d("json", array.toString());
                for (int i = 0; i <= array.length(); i++) {
                    try {
                        object = array.getJSONObject(i);

                        bg.setText(object.getString("bg_cbdsb"));
                        discipline.setText(object.getString("discipline"));
                        name.setText(object.getString("name"));
                        mobile.setText(object.getString("phn"));
                        sID.setText(object.getString("s_id"));
                        lastDonate.setText(object.getString("last_date"));

                        String dob = object.getString("dob");
                        String lastDonate = object.getString("last_date");

                        String imgURL = object.getString("img_url");

                        //Age Calculation
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date birthDate = null;
                        Date lastDD = null;
                        try {
                            birthDate = sdf.parse(dob);
                            lastDD = sdf.parse(lastDonate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Age age = AgeCalc.calculateAge(birthDate);
                        Age state = AgeCalc.calculateAge(lastDD);
                        //Age Calculation end

//        Log.d("age", age.toString());
                        profAge.setText(age.toString());

//                        Log.d("test", state.getMonths()+"");
                        if (state.getMonths() >= 3) {
                            status.setText("Available");
                            status.setTextColor(Color.GREEN);
                        } else {
                            status.setText("Not Available");
                            status.setTextColor(Color.RED);
                        }

                        Picasso.get()
                                .load(imgURL)
                                .resize(100, 100)
                                .centerCrop()
                                .into(profileImg);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Profile.this);
        requestQueue.add(request);
    }

    public void LastDateUpdate(View view) {
        progressBar.setVisibility(View.VISIBLE);
        //Date Picker
        Calendar myCalendar = Calendar.getInstance();

//        SimpleDateFormat sdate;// = new SimpleDateFormat("yyyy-MM-dd");
//        Date birthDate = null;

        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Profile.this, R.style.datePicker1, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String Month_ = (month+1) + "";
                String Days_ = dayOfMonth + "";

                if (month < 10) {

                    Month_ = "0" + (month + 1);
                }
                if (dayOfMonth < 10) {

                    Days_ = "0" + dayOfMonth;
                }

                String datee = year + "-" + Month_ + "-" + Days_;

//                Toast.makeText(getApplicationContext(), datee, Toast.LENGTH_SHORT).show();

                //insert Database
                updateDatabase(datee);

//                loadData();
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void updateDatabase(String datee) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.ROOT_URL + "updateDonateDate.php" + Urls.KEY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")) {
                    progressBar.setVisibility(View.INVISIBLE);
//                    Toast.makeText(getApplicationContext(), "Date Updated Successfully!", Toast.LENGTH_LONG).show();
                    Snackbar.make(lastDonate, "Date Updated Successfully!", Snackbar.LENGTH_LONG).show();

                    loadData();
                } else if (response.equals("fail")) {
                    progressBar.setVisibility(View.INVISIBLE);
//                    Toast.makeText(getApplicationContext(), "Date Updated Failed!!! :(", Toast.LENGTH_SHORT).show();
                    Snackbar.make(lastDonate, "Date Updated Failed!!! :(", Snackbar.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Invalid Credentials.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put("date", datee);
                map.put("s_id", s__ID);

                return map;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}