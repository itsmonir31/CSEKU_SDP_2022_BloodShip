package com.example.bloodship;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodship.others.SharedPref;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RequestBlood extends AppCompatActivity {

    EditText problem, bg, quantity, time, date, addrs, aContact, rContact;
    boolean valid = true;
    String sdate, stime;
    int checkCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);

        //hoooks
        problem = findViewById(R.id.ETproblem);
        bg = findViewById(R.id.ETbg);
        quantity = findViewById(R.id.ETquantity);
        time = findViewById(R.id.ETtime);
        date = findViewById(R.id.ETdate);
        addrs = findViewById(R.id.ETaddress);
        aContact = findViewById(R.id.ETaContact);
        rContact = findViewById(R.id.ETrContact);

        time.setInputType(InputType.TYPE_NULL);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar myCalendar = Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String am_pm = " AM", HH = "00", MM = "00";
                        int hours = hourOfDay;

                        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCalendar.set(Calendar.MINUTE, minute);

                        if (hourOfDay > 12) {
                            hours = hours - 12;
                            am_pm = " PM";
                        }

                        if (hours < 10) {
                            HH = "0" + String.valueOf(hours);
                        } else {
                            HH = String.valueOf(hours);
                        }

                        if (minute < 10) {
                            MM = "0" + String.valueOf(minute);
                        } else {
                            MM = String.valueOf(minute);
                        }


                        stime = HH + ":" + MM + am_pm;
                        time.setText(stime);
                    }
                };

                new TimePickerDialog(RequestBlood.this, R.style.datePicker1, timeListener, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false).show();
            }
        });

        date.setInputType(InputType.TYPE_NULL); //for disabling edit Text taking input
        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                //Date Picker
                Calendar myCalendar = Calendar.getInstance();

                int year = myCalendar.get(Calendar.YEAR);
                int month = myCalendar.get(Calendar.MONTH);
                int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RequestBlood.this, R.style.datePicker1, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        sdate = SimpleDateFormat.getDateInstance().format(myCalendar.getTime()).toString();
                        date.setText(sdate);
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        findViewById(R.id.requestSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCount = 0;

                checkField(problem);
                checkField(bg);
                checkField(quantity);
                checkField(time);
                checkField(date);
                checkField(addrs);
                checkField(aContact);
                checkField(rContact);

                if (valid && checkCount == 8) {

                    String url = Urls.ROOT_URL + "insertBR.php" + Urls.KEY;
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            clearEditText();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            //Map
                            Map<String, String> reqData = new HashMap<String, String>();
                            reqData.put("problem", problem.getText().toString());
                            reqData.put("bg", bg.getText().toString());
                            reqData.put("quantity", quantity.getText().toString());
                            reqData.put("time", time.getText().toString());
                            reqData.put("date", date.getText().toString());
                            reqData.put("addrs", addrs.getText().toString());
                            reqData.put("aContact", "+88" + aContact.getText().toString());
                            reqData.put("rContact", "+88" + rContact.getText().toString());

                            String s_ID = SharedPref.readSP(getApplicationContext(), "s_id", "0");
                            reqData.put("s_id", s_ID);

                            return reqData;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);
                } // end of "if (valid && checkCount == 8)"
            }
        });

        findViewById(R.id.clearRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearEditText();
            }
        });
    }   // onCreate end


    private void clearEditText() {
        problem.getText().clear();
        bg.getText().clear();
        quantity.getText().clear();
        time.getText().clear();
        date.getText().clear();
        addrs.getText().clear();
        aContact.getText().clear();
        rContact.getText().clear();
    } // clearEditText end


    public boolean checkField(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError("Fill this");
            valid = false;
        } else {
            valid = true;
            checkCount++;
        }
        return valid;
    } // checkField end

}