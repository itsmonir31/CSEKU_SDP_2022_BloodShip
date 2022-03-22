package com.example.bloodship.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodship.Models.modelBloodReq;
import com.example.bloodship.R;
import com.example.bloodship.Urls;
import com.example.bloodship.mainAdapter;
import com.example.bloodship.modelRV;

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

        holder.ProblemTV.setText("Problem: "+model.getProblem());
        holder.bgTV.setText("Blood Group: "+model.getBg());
        holder.quantityTV.setText("Quantity: "+model.getQuantity());
        holder.timeTV.setText("Time: "+model.getTime());
        holder.dateTV.setText("Date: "+model.getDate());
        holder.addressTV.setText("Address: "+model.getAddress());
        holder.a_contactTV.setText(model.getA_contact());
        holder.r_contactTV.setText(model.getR_contact());
        holder.req_dateTV.setText(model.getReq_date());


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


//these for user specific
//        String url = Urls.USER_TYPE_FIND + email;
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

/*
            @Override
            public void onResponse(String response) {

//                if (response.equals("admin")) {
//                    holder.name.setText(model.getName());
//                    holder.discipline.setText(model.getDiscipline());
//                    holder.bg.setText(model.getBloodGroup());
//                    holder.mob.setText(model.getPhone());
//                    holder.addr.setText("*****");
//                    holder.lastDonate.setText(" ");
//                    holder.lastDonate.setText("");
//                } else {
//                    holder.name.setText(model.getName());
//                    holder.discipline.setText(model.getDiscipline());
//                    holder.bg.setText(model.getBloodGroup());
//                    holder.mob.setText("*********");
//                    holder.addr.setText("*****");
//                    holder.lastDonate.setText(" ");
//                    holder.lastDonate.setText("");
//                }

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

        discipline = model.getDiscipline();


 */
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class mainHolder extends RecyclerView.ViewHolder {
        private ImageView copyBTN;
        private TextView ProblemTV, bgTV, quantityTV, timeTV, dateTV, addressTV, a_contactTV, r_contactTV, req_dateTV;

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
            req_dateTV = (TextView) itemView.findViewById(R.id.reqDateTV);

            copyBTN = itemView.findViewById(R.id.copy_btn);

            //update = (TextView) itemView.findViewById(R.id.updatebtn);
        }
    }
}
