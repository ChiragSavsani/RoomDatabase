package com.chiragmvvm.roomdbexercise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chiragmvvm.roomdbexercise.MainActivity;
import com.chiragmvvm.roomdbexercise.R;
import com.chiragmvvm.roomdbexercise.db.entity.Employee;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<Employee> employeeList;
    private MainActivity mainActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView email;
        public TextView mobile;
        public TextView country;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            email = view.findViewById(R.id.email);
            mobile = view.findViewById(R.id.mobile);
            country = view.findViewById(R.id.country);

        }
    }


    public EmployeeAdapter(Context context, ArrayList<Employee> employeeList, MainActivity mainActivity) {
        this.context = context;
        this.employeeList = employeeList;
        this.mainActivity = mainActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list_item, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        final Employee emp = employeeList.get(position);

        holder.name.setText("Name: " + emp.getName());
        holder.email.setText("Email: " + emp.getEmail());
        holder.mobile.setText("Phone No: " + emp.getPhoneNo());
        holder.country.setText("Country: " + emp.getCountry());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mainActivity.addAndEditEmployee(true, emp, position);
            }
        });

    }

    @Override
    public int getItemCount() {

        return employeeList.size();
    }
}
