package com.example.prajna.employee;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by prajna on 25/8/16.
 */
public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.CustomViewHolder> {
    private ArrayList<Employee> mEmployeeList;
    private Context context;
    LayoutInflater inflater;
//    Button btn_delete, btn_update;


    public EmployeeAdapter(Context context, ArrayList<Employee> empList) {
        this.mEmployeeList = empList;
        this.context = context;
    }

    @Override
    public EmployeeAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_layout, null);

        CustomViewHolder viewHolder = new CustomViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EmployeeAdapter.CustomViewHolder holder, int position) {

//        Employee employee=new Employee();
        final Employee employee = mEmployeeList.get(position);

        holder.first_name.setText(employee.getFirst_name());
        holder.last_name.setText(employee.getLast_name());
        holder.department.setText(employee.getDepartment());

        holder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, EmpDetailActivity.class);
                intent.putExtra("first_name", employee.getFirst_name());
                intent.putExtra("last_name", employee.getLast_name());
                intent.putExtra("email", employee.getEmail());
                intent.putExtra("mobile", employee.getMobile());
                intent.putExtra("dob", employee.getDOB());
                intent.putExtra("company", employee.getCompany());
                intent.putExtra("department", employee.getDepartment());
                intent.putExtra("designation", employee.getDesignation());
                intent.putExtra("address", employee.getAddress());
                context.startActivity(intent);
            }

        });

        holder.delete.setTag(employee);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure,You wanted to delete " + employee.getFirst_name() + " details..");

                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        DBHandler.getDBHandlerInstance(context).deleteContact((Employee) view.getTag());
                        Toast.makeText(context, employee.getFirst_name() + " deleted successfully", Toast.LENGTH_LONG).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


        holder.update.setTag(employee);
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(final View view) {

                Employee selectedEmployee = (Employee) view.getTag();
                Intent intent = new Intent(context, UpdateRecordActivity.class);
                intent.putExtra("Selected_Employee_To_Update", selectedEmployee);
                context.startActivity(intent);
            }
        });
    }
        @Override
        public int getItemCount () {
            return mEmployeeList.size();
        }


        // inner class to hold a reference to each item of RecyclerView
        public static class CustomViewHolder extends RecyclerView.ViewHolder {
            private TextView first_name;
            private TextView last_name;
            private TextView department;
            private Button update, delete;
            private LinearLayout mMainLayout;

            public CustomViewHolder(final View itemLayoutView) {
                super(itemLayoutView);
                first_name = (TextView) itemLayoutView.findViewById(R.id.first_name_text_view);
                last_name = (TextView) itemLayoutView.findViewById(R.id.last_name_text_view);
                department = (TextView) itemLayoutView.findViewById(R.id.department_text_view);
                mMainLayout = (LinearLayout) itemLayoutView.findViewById(R.id.main_layout);
                update = (Button) itemLayoutView.findViewById(R.id.button_update);
                delete = (Button) itemLayoutView.findViewById(R.id.button_delete);
            }
        }
    }

