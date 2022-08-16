package com.example.bloodship.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodship.Models.NestedChildModel;
import com.example.bloodship.Models.NestedParentModel;
import com.example.bloodship.MyRequest;
import com.example.bloodship.R;
import com.example.bloodship.Urls;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class adapterNestedChild extends RecyclerView.Adapter<adapterNestedChild.viewHolder> {

    Context context;
    List<NestedChildModel> child_dataList;
    String reqID, reqID_;

    public adapterNestedChild(Activity context, List<NestedChildModel> childData_list, String reqID) {
        this.context = context;
        this.child_dataList = childData_list;
        this.reqID = reqID;
    }

//    public void adapterNestedChild(List<NestedChildModel> child_dataList) {
//        this.context = context;
//        this.child_dataList = child_dataList;
//    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View dataLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.sr_my_req_child, parent, false);
        return new adapterNestedChild.viewHolder(dataLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        NestedChildModel model = child_dataList.get(position);

        int posi = position;

        Toast.makeText(context, model.getStudentID(), Toast.LENGTH_SHORT).show();
        holder.nrv_nameTV.setText(model.getName());
        holder.nrv_bgTV.setText("Blood Group: " + model.getBloodGroup());
        holder.nrv_disciplineTV.setText("Discipline: " + model.getDiscipline());

        String SID = model.getStudentID();
        holder.nrv_studentID_TV.setText("Student ID: " + SID);

        reqID_ = model.getReqID();
        Log.d("req-----", reqID);

//        holder.receivedChildbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context.getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });

        if (Integer.parseInt(model.getCheckDonate()) == 1) {
            holder.switchMaterial.setChecked(true);
        } else {
            holder.switchMaterial.setChecked(false);
        }

        holder.switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Log.d("poiu", reqID);
                    bgDonated(reqID, posi, 1, holder.nrv_bgTV, SID);
                    bgManaged(reqID, posi, 0);  //quantity - 1
//                    MyRequest.class.notify();
                } else {
                    Toast.makeText(context.getApplicationContext(), "Not Checked", Toast.LENGTH_SHORT).show();
                    bgDonated(reqID, posi, 0, holder.nrv_bgTV, SID);
                    bgManaged(reqID, posi, 1);
                }
                context.startActivity(new Intent(context, MyRequest.class));
                ((Activity) context).finish();
            }
        });
    }


    @Override
    public int getItemCount() {
        if (child_dataList != null) {
            return child_dataList.size();
        } else {
            return 0;
        }
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView nrv_nameTV, nrv_bgTV, nrv_disciplineTV, nrv_studentID_TV;
        private Button callNowChildbtn, receivedChildbtn;
        private SwitchMaterial switchMaterial;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            nrv_nameTV = (TextView) itemView.findViewById(R.id.nrv_nameTV);
            nrv_bgTV = (TextView) itemView.findViewById(R.id.nrv_bgTV);
            nrv_disciplineTV = (TextView) itemView.findViewById(R.id.nrv_disciplineTV);
            nrv_studentID_TV = (TextView) itemView.findViewById(R.id.nrv_studentID_TV);

            callNowChildbtn = (Button) itemView.findViewById(R.id.callNowChildbtn);
//            receivedChildbtn = (Button) itemView.findViewById(R.id.receivedChildbtn);
            switchMaterial = itemView.findViewById(R.id.switchDonate);
        }
    }


    public void bgDonated(String reqID, int position, int valPlusMinus, View view, String sid_) {


        String URL_ = Urls.ROOT_URL + "bgDonatedConfirm.php" + Urls.KEY + "&reqID=" + reqID + "&sid=" + sid_ + "&val=" + valPlusMinus;
        Log.d("terimal", URL_);

        JsonArrayRequest request = new JsonArrayRequest(URL_, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                JSONObject object1;

                for (int i = 0; i <= array.length(); i++) {
                    try {

                        object1 = array.getJSONObject(i);

                        String msg = object1.getString("response");
                        if (msg.equals("success")) {
//                            Toast.makeText(context, "Data Updated", Toast.LENGTH_SHORT).show();
                            Snackbar.make(view, "Data Updated", Snackbar.LENGTH_SHORT).show();
                        } else if (msg.equals("fail")) {
                            Snackbar.make(view, "Server Problem!!!", Snackbar.LENGTH_SHORT).show();
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

    public void bgManaged(String reqID, int position, int valPlusMinus) {


        String URL_ = Urls.ROOT_URL + "bgManaged.php" + Urls.KEY + "&reqID=" + reqID + "&val=" + valPlusMinus +"&app=y";
        Log.d("ter", URL_);

        JsonArrayRequest request = new JsonArrayRequest(URL_, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                JSONObject object1;

                for (int i = 0; i <= array.length(); i++) {
                    try {

                        object1 = array.getJSONObject(i);

                        String msg = object1.getString("response");
                        if (msg.equals("0")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                                    .setTitle("Delete Request")
                                    .setMessage("Are you sure want to delete!?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
//                                            remove(reqID, position);
                                            notifyDataSetChanged();
                                            notifyDataSetChanged();
                                            adapterNestedChild.this.notify();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            bgManaged(reqID, position, 1);
//                                            qntty = qntty+1;
//                                            context.getApplicationContext().
                                            notifyDataSetChanged();
                                        }
                                    });

                            builder.show();
                        } else if (Integer.parseInt(msg) > 0) {
                            Toast.makeText(context, "Blood Quantity Updated", Toast.LENGTH_SHORT).show();
                        } else if (msg.equals("fail")) {
                            Toast.makeText(context, "Server problem!!!", Toast.LENGTH_SHORT).show();
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
