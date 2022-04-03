package com.example.bloodship.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodship.Models.NestedChildModel;
import com.example.bloodship.Models.NestedParentModel;
import com.example.bloodship.R;

import java.util.List;

public class adapterNestedChild extends RecyclerView.Adapter<adapterNestedChild.viewHolder>{

    Context context;
    List<NestedChildModel> child_dataList;

    public adapterNestedChild(Activity context, List<NestedChildModel> childData_list) {
        this.context = context;
        this.child_dataList = childData_list;
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

        Toast.makeText(context, model.getStudentID(), Toast.LENGTH_SHORT).show();
        holder.nrv_nameTV.setText(model.getName());
        holder.nrv_bgTV.setText("Blood Group: " + model.getBloodGroup());
        holder.nrv_disciplineTV.setText("Discipline: " + model.getDiscipline());
        holder.nrv_studentID_TV.setText("Student ID: " + model.getStudentID());
    }

    @Override
    public int getItemCount() {
        if (child_dataList != null){
            return child_dataList.size();
        }else{
            return 0;
        }
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private TextView nrv_nameTV, nrv_bgTV, nrv_disciplineTV, nrv_studentID_TV;
        private Button callNowChildbtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            nrv_nameTV = (TextView) itemView.findViewById(R.id.nrv_nameTV);
            nrv_bgTV = (TextView) itemView.findViewById(R.id.nrv_bgTV);
            nrv_disciplineTV = (TextView) itemView.findViewById(R.id.nrv_disciplineTV);
            nrv_studentID_TV = (TextView) itemView.findViewById(R.id.nrv_studentID_TV);

            callNowChildbtn = (Button) itemView.findViewById(R.id.callNowChildbtn);
        }
    }
}
