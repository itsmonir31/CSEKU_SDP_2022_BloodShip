package com.example.bloodship.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.bloodship.Models.NestedChildModel;
import com.example.bloodship.Models.NestedParentModel;
import com.example.bloodship.Models.modelBloodReq;
import com.example.bloodship.MyRequest;
import com.example.bloodship.R;
import com.example.bloodship.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class adapterNestedParent extends RecyclerView.Adapter<adapterNestedParent.viewHolder> {
    Activity context;
    List<NestedParentModel> parent_dataList;
    List<NestedChildModel> childData_list;

    adapterNestedChild adapterNestedChild;

    adapterNestedChild childAdapter;

    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();//, List<NestedChildModel> child_dataList

    public adapterNestedParent(Activity context, List<NestedParentModel> parent_dataList) {
        this.context = context;
        this.parent_dataList = parent_dataList;
//        this.child_dataList = child_dataList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View dataLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.sr_my_req_parent, parent, false);
        return new viewHolder(dataLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        int number = position;
        NestedParentModel model = parent_dataList.get(position);

        holder.nrv_reqAt.setText("Requested at: " + model.getReq_date());
        holder.nrv_ProblemTV.setText("Problem: " + model.getProblem());
        holder.nrv_bgTV.setText("Blood Group: " + model.getBg());
        holder.nrv_quantityTV.setText("Quantity: " + model.getQuantity());
        holder.nrv_timeTV.setText("Time: " + model.getTime());
        holder.nrv_dateTV.setText("Date: " + model.getDate());
        holder.nrv_addressTV.setText("Address: " + model.getAddress());
        holder.nrv_a_contactTV.setText(model.getA_contact());
        holder.nrv_r_contactTV.setText(model.getR_contact());

        holder.RemoveRequest.setOnClickListener(new View.OnClickListener() {
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


//        holder.childRV.setNestedScrollingEnabled(false);
//        ViewCompat.setNestedScrollingEnabled(holder.childRV, false);
        holder.childRV.setHasFixedSize(true);


//        childData_list = new ArrayList<NestedChildModel>();
//        if (number == 0) {
//            childData_list.add(new NestedChildModel("190231", "B+", "CSE", "Monir", "019292428"));
//            childData_list.add(new NestedChildModel("190233", "P+", "CSE", "Monir", "019292428"));
//        } else if (number == 1) {
//            childData_list.add(new NestedChildModel("190231", "B+", "CSE", "rupa", "019292428"));
//            childData_list.add(new NestedChildModel("190233", "P+", "CSE", "rupa", "019292428"));
//        }

        //test
        childData_list = new ArrayList<>();

        String URL_ = Urls.ROOT_URL + "interestedDonor.php" + Urls.KEY + "&reqID=" + model.getReqID();
        Log.d("childurl", URL_);

        JsonArrayRequest request = new JsonArrayRequest(URL_, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                JSONObject object1;
                childData_list.clear();

//                Log.d("jsonChild", model.getReqID() + "----" + array.toString() + "---" + childData_list.toString());
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
//                Log.d("zzzz",parentData_list.size()+"-----"+childData_list.size());
//            adapterNestedChild adapterNestedChild__ = new adapterNestedChild(context, childData_list);
                childAdapter = new adapterNestedChild(context, childData_list);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                holder.childRV.setLayoutManager(linearLayoutManager);
                holder.childRV.setAdapter(childAdapter);

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

        Log.d("balsa", childData_list.size()+"") ;
        //test end


//        holder.childRV.setRecycledViewPool(viewPool);


//        loadChildDate(model.getReqID(),holder);


//        Log.d("child_", child_dataList.toString());
//        if (holder.childRV.isShown()) {
//            Toast.makeText(context, "Shown", Toast.LENGTH_SHORT).show();
//        }
    }

//    public void clear() {
//        int size = child_dataList.size();
//        child_dataList.clear();
////        notifyItemRangeRemoved(0, size);
//    };

    @Override
    public int getItemCount() {
        if (parent_dataList != null) {
            return parent_dataList.size();
        } else {
            return 0;
        }
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView RemoveRequest, nrv_reqAt, nrv_ProblemTV, nrv_bgTV, nrv_quantityTV, nrv_timeTV, nrv_dateTV, nrv_addressTV, nrv_a_contactTV, nrv_r_contactTV, nrv_req_dateTV;
        RecyclerView childRV;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            nrv_reqAt = (TextView) itemView.findViewById(R.id.nrv_reqAtt);
            nrv_ProblemTV = (TextView) itemView.findViewById(R.id.nrv_problemTV);
            nrv_bgTV = (TextView) itemView.findViewById(R.id.nrv_bgTV);
            nrv_quantityTV = (TextView) itemView.findViewById(R.id.nrv_quantityTV);
            nrv_timeTV = (TextView) itemView.findViewById(R.id.nrv_timeTV);
            nrv_dateTV = (TextView) itemView.findViewById(R.id.nrv_dateTV);
            nrv_addressTV = (TextView) itemView.findViewById(R.id.nrv_addressTV);
            nrv_a_contactTV = (TextView) itemView.findViewById(R.id.nrv_a_contactTV);
            nrv_r_contactTV = (TextView) itemView.findViewById(R.id.nrv_r_contactTV);

            RemoveRequest = (TextView) itemView.findViewById(R.id.RemoveRequest);

            childRV = (RecyclerView) itemView.findViewById(R.id.childRecyclerView);

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
                            parent_dataList.remove(position);
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

    private adapterNestedChild loadChildDate(String parentID) {
        childData_list = new ArrayList<NestedChildModel>();
//        childData_list.clear();

        String URL_ = Urls.ROOT_URL + "interestedDonor.php" + Urls.KEY + "&reqID=" + parentID;
        Log.d("childurl", URL_);

        JsonArrayRequest request = new JsonArrayRequest(URL_, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {

                JSONObject object1;


                Log.d("jsonChild", parentID + "----" + array.toString() + "---" + childData_list.toString());
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

            //                Log.d("zzzz",parentData_list.size()+"-----"+childData_list.size());
            adapterNestedChild adapterNestedChild = new adapterNestedChild(context, childData_list);


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

        return adapterNestedChild;
    }
}
