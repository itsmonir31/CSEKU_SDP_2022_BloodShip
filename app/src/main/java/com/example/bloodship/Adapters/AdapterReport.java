package com.example.bloodship.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodship.Models.ModelReport;
import com.example.bloodship.R;
import com.example.bloodship.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdapterReport extends RecyclerView.Adapter<AdapterReport.mainHolder> {
    Activity context;
    List<ModelReport> dataList;

    public AdapterReport(Activity report, List<ModelReport> reportList) {
        this.context = report;
        this.dataList = reportList;
    }

    @NonNull
    @Override
    public AdapterReport.mainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View dataLAYOUT = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_report, parent, false);
        return new AdapterReport.mainHolder(dataLAYOUT);
    }

    public void onBindViewHolder(@NonNull AdapterReport.mainHolder holder, int position) {
        ModelReport model = dataList.get(position);

        holder.reqDateR.setText(model.getReqDate());
        holder.bgR.setText(model.getBg());
        holder.nameR.setText(model.getName());
        holder.totalR.setText(model.getTotal());
        holder.managedR.setText(model.getThroughApp());
//        holder.reqDateR.setText(model.getReqDate());
//        holder.reqDateR.setText(model.getReqDate());

        holder.reppostR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repost(v, model.getReqID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class mainHolder extends RecyclerView.ViewHolder {
        TextView reqDateR, nameR, bgR, totalR, managedR, viewR, reppostR;

        public mainHolder(View itemView) {
            super(itemView);

            reqDateR = itemView.findViewById(R.id.dateR);
            nameR = itemView.findViewById(R.id.nameR);
            bgR = itemView.findViewById(R.id.bgR);
            totalR = itemView.findViewById(R.id.totalR);
            managedR = itemView.findViewById(R.id.managedR);
//            viewR = itemView.findViewById(R.id.viewR);
            reppostR = itemView.findViewById(R.id.repostR);
        }
    }

    public void Repost(View view, int reqID) {
        String URL_ = Urls.ROOT_URL + "repost.php" + Urls.KEY + "&reqID=" + reqID;
        Log.d("point", URL_);
        JsonArrayRequest request = new JsonArrayRequest(URL_, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                JSONObject object1;

                for (int i = 0; i <= array.length(); i++) {
                    try {

                        object1 = array.getJSONObject(i);

                        String msg = object1.getString("response");
                        if (msg.equals("success")) {
                            context.recreate();
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
                Toast.makeText(view.getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(request);
    }
}
