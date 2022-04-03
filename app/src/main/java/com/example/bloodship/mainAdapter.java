package com.example.bloodship;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mainAdapter extends RecyclerView.Adapter<mainAdapter.mainHolder> {
    //implements ICustomAdapter
    Context context;
    List<modelRV> dataList;
    String discipline;

    String email;

    public mainAdapter(Context context, List<modelRV> dataList, String x) {
        this.context = context;
        this.dataList = dataList;
        this.email = x;
    }

    @NonNull
    @Override
    public mainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View dataLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new mainHolder(dataLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull mainHolder holder, int position) {
        modelRV model = dataList.get(position);

        holder.name.setText(model.getName());
        holder.discipline.setText(model.getDiscipline());
        holder.bg.setText(model.getBloodGroup());
        holder.mob.setText(model.getPhone());
        holder.addr.setText(model.getAddress());
        holder.lastDonate.setText(model.getLastDonate());

        //for user specific
//        String url = Urls.USER_TYPE_FIND + email;
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//
//
//            @Override
//            public void onResponse(String response) {
//                if (response.equals("admin")){
//                    holder.name.setText(model.getName());
//                    holder.discipline.setText(model.getDiscipline());
//                    holder.bg.setText(model.getBloodGroup());
//                    holder.mob.setText(model.getPhone());
//                    holder.addr.setText("*****");
//                    holder.lastDonate.setText(" ");
//                    holder.lastDonate.setText("");
//                }else{
//                    holder.name.setText(model.getName());
//                    holder.discipline.setText(model.getDiscipline());
//                    holder.bg.setText(model.getBloodGroup());
//                    holder.mob.setText("*********");
//                    holder.addr.setText("*****");
//                    holder.lastDonate.setText(" ");
//                    holder.lastDonate.setText("");
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, "Server Problem!!", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<String, String>();
//
////                    map.put("email", email.getText().toString());
////                    map.put("password", encrypt(password));
//
//                return map;
//            }
//
//        };
////
//        RequestQueue queue = Volley.newRequestQueue(context);
//
//        queue.add(stringRequest);

        //for user specific end


        discipline = model.getDiscipline();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class mainHolder extends RecyclerView.ViewHolder {

        TextView bg, discipline, lastDonate, addr, name, mob, update;

        public mainHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.nametv_single_row);
            discipline = (TextView) itemView.findViewById(R.id.bsdiscipline);
            bg = (TextView) itemView.findViewById(R.id.bsBloodGroup);
            mob = (TextView) itemView.findViewById(R.id.bscallnow);
            addr = (TextView) itemView.findViewById(R.id.bsdistrict);
            lastDonate = (TextView) itemView.findViewById(R.id.bslastDonate);

//            update = (TextView) itemView.findViewById(R.id.updatebtn);
        }
    }
}
