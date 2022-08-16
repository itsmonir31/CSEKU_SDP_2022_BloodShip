package com.example.bloodship.Adapters;

import static com.example.bloodship.R.string.bloodship;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodship.Models.modelBloodReq;
import com.example.bloodship.R;
import com.example.bloodship.RequestedBloodList;
import com.example.bloodship.Urls;
import com.example.bloodship.mainAdapter;
import com.example.bloodship.modelRV;
import com.example.bloodship.others.SharedPref;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adapterRequestBlood extends RecyclerView.Adapter<adapterRequestBlood.mainHolder> {
    Context context;
    List<modelBloodReq> dataList;
    String discipline;
    ImageView copyBTN;

    String email;

    public adapterRequestBlood(Context context, List<modelBloodReq> dataList) {
        this.context = context;
        this.dataList = dataList;
//        this.email = x;
    }

    @NonNull
    @Override
    public adapterRequestBlood.mainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View dataLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.requests_single_card, parent, false);
        return new adapterRequestBlood.mainHolder(dataLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterRequestBlood.mainHolder holder, int position) {
        modelBloodReq model = dataList.get(position);
        int number = position;

        holder.ProblemTV.setText("Problem: " + model.getProblem());
        holder.bgTV.setText("Blood Group: " + model.getBg());
        holder.timeTV.setText("Time: " + model.getTime());
        holder.dateTV.setText("Date: " + model.getDate());
        holder.addressTV.setText("Address: " + model.getAddress());
        holder.a_contactTV.setText(model.getA_contact());
        holder.r_contactTV.setText(model.getR_contact());
        holder.req_dateTV.setText( "Requested at: "+model.getReq_date());

        holder.quantityTV.setText("Total Requested: " + model.getQuantity());
        holder.need.setText("Need "+model.getNeed() +" more bag(s)");
        holder.managhed_app.setText("Managed by APP: "+model.getManaged() + " bag(s)");


        holder.RemoveRequestA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete Request")
                        .setMessage("Are you sure want to delete!?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                remove(model.getReqID(), number);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                builder.show();
            }
        });

        holder.copyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notice;
                String nLine = System.getProperty("line.separator");
                notice = "রোগীর সমস্যাঃ " + model.getProblem() + nLine +
                        "রক্তের গ্রুপঃ " + model.getBg() + nLine +
                        "রক্তের পরিমাণঃ " + model.getQuantity() + nLine +
                        "সময়ঃ " + model.getTime() + nLine +
                        "তারিখঃ " + model.getDate() + nLine +
                        "স্থানঃ " + model.getAddress() + nLine +
                        "Contact Number" + nLine + "`````````````````````````" + nLine +
                        "আবেদনকারীঃ " + model.getA_contact() + nLine +
                        "আত্মীয়-স্বজনঃ " + model.getR_contact();


                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("EditText", notice);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(context, "COPIED!", Toast.LENGTH_LONG).show();
            }
        });

        holder.imInterested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reqID = model.getReqID();

                String s_ID = SharedPref.readSP(context, "s_id", "0");

                //server works
                String URL_ = Urls.ROOT_URL + "i_am_interested.php" + Urls.KEY + "&&s_id=" + s_ID + "&reqID=" + reqID;

                JsonArrayRequest request = new JsonArrayRequest(URL_, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray array) {
                        JSONObject object;
//                        Log.d("json", array.toString());
                        for (int i = 0; i <= array.length(); i++) {
                            try {
                                object = array.getJSONObject(i);

                                String message = object.getString("response");

//                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                Snackbar.make(holder.imInterested, message, Snackbar.LENGTH_LONG).show();
//                                Snackbar.make(context, message, Snackbar.LENGTH_LONG)
//                                        .show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(request);
                //server works end

//                Toast.makeText(context, reqID + "--" + s_ID + "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class mainHolder extends RecyclerView.ViewHolder {
        private ImageView copyBTN;
        Button imInterested;
        private TextView ProblemTV, bgTV, quantityTV, timeTV, dateTV, addressTV, a_contactTV, r_contactTV, req_dateTV, RemoveRequestA, need, managhed_app;

        public mainHolder(@NonNull View itemView) {
            super(itemView);

            ProblemTV = (TextView) itemView.findViewById(R.id.problemTV);
            bgTV = (TextView) itemView.findViewById(R.id.bgTV);
            quantityTV = (TextView) itemView.findViewById(R.id.quantityTV);
            timeTV = (TextView) itemView.findViewById(R.id.timeTV);
            dateTV = (TextView) itemView.findViewById(R.id.dateTV);
            addressTV = (TextView) itemView.findViewById(R.id.addressTV);
            a_contactTV = (TextView) itemView.findViewById(R.id.a_contactTV);
            r_contactTV = (TextView) itemView.findViewById(R.id.r_contactTV);
            req_dateTV = (TextView) itemView.findViewById(R.id.reqDateTVA);
            managhed_app = (TextView) itemView.findViewById(R.id.managedTV);
            need = (TextView) itemView.findViewById(R.id.needMoreTV);

            copyBTN = itemView.findViewById(R.id.copy_btn);
            imInterested = itemView.findViewById(R.id.imInterested);

            RemoveRequestA = itemView.findViewById(R.id.RemoveRequestA);

            //update = (TextView) itemView.findViewById(R.id.updatebtn);
        }
    }

    public void remove(String reqID, int position) {


        String URL_ = Urls.ROOT_URL + "removeRequest.php" + Urls.KEY + "&reqID=" + reqID;

        JsonArrayRequest request = new JsonArrayRequest(URL_, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                JSONObject object1;

                for (int i = 0; i <= array.length(); i++) {
                    try {

                        object1 = array.getJSONObject(i);

                        String msg = object1.getString("response");
                        if (msg.equals("success")) {
                            dataList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(0, getItemCount());
                            Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
                        } else if (msg.equals("fail")) {

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
