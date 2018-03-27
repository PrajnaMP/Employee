package com.example.prajna.employee;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by prajna on 24/8/16.
 */
public class EmpAdapter extends BaseAdapter {

    private ArrayList<Employee> mEmployeeList;
    private Context context;
    LayoutInflater inflater;
    Button button_delete, btn_update;

    public EmpAdapter(Context context, ArrayList empList) {
        this.mEmployeeList = empList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mEmployeeList.size();
    }

    @Override
    public Employee getItem(int position) {
        return mEmployeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_layout, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        final Employee employee = getItem(position);

        mViewHolder.first_name.setText(employee.getFirst_name());
        mViewHolder.last_name.setText(employee.getLast_name());
        mViewHolder.department.setText(employee.getDepartment());
        mViewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
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
        button_delete = (Button) convertView.findViewById(R.id.button_delete);
        button_delete.setTag(employee);
        button_delete.setOnClickListener(new View.OnClickListener() {
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

        btn_update = (Button) convertView.findViewById(R.id.button_update);
        btn_update.setTag(employee);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(final View view) {

                Employee selectedEmployee = (Employee) view.getTag();
                Intent intent = new Intent(context, UpdateRecordActivity.class);
                intent.putExtra("Selected_Employee_To_Update", selectedEmployee);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private static class MyViewHolder {
        private TextView first_name;
        private TextView last_name;
        private TextView department;
        private Button update, delete;
        private LinearLayout mMainLayout;

        public MyViewHolder(View item) {
            first_name = (TextView) item.findViewById(R.id.first_name_text_view);
            last_name = (TextView) item.findViewById(R.id.last_name_text_view);
            department = (TextView) item.findViewById(R.id.department_text_view);
            mMainLayout = (LinearLayout) item.findViewById(R.id.main_layout);
            update = (Button) item.findViewById(R.id.button_update);
            delete = (Button) item.findViewById(R.id.button_delete);
        }
    }
}