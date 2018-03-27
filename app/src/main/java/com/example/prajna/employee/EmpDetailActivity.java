package com.example.prajna.employee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by prajna on 24/8/16.
 */
public class EmpDetailActivity extends AppCompatActivity {
    private TextView first_name, last_name, email, mobile, dob, department, designation, address, employee_heading;
    private String key_first_name, key_last_name, key_email, key_mobile, key_dob, key_department, key_designation, key_address;
    ArrayList<Employee> EmployeeList = new ArrayList<Employee>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_detail);
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("Employee Details"); // set the top title
        String title = actionBar.getTitle().toString(); // get the title
        Intent intent = getIntent();
        if (intent != null) {
            key_first_name = getIntent().getStringExtra("first_name");
            key_last_name = getIntent().getStringExtra("last_name");
            key_email = getIntent().getStringExtra("email");
            key_mobile = getIntent().getStringExtra("mobile");
            key_dob = getIntent().getStringExtra("dob");
            key_department = getIntent().getStringExtra("department");
            key_designation = getIntent().getStringExtra("designation");
            key_address = getIntent().getStringExtra("address");

        }

//        EmployeeList = DBHandler.getDBHandlerInstance(EmpDetailActivity.this).getContact(key_first_name);
        employee_heading = (TextView) findViewById(R.id.employee_heading);
        employee_heading.setText(key_first_name + " " + key_last_name + " Details");
        first_name = (TextView) findViewById(R.id.first_name_value);
        first_name.setText(key_first_name);
        last_name = (TextView) findViewById(R.id.last_name_value);
        last_name.setText(key_last_name);
        email = (TextView) findViewById(R.id.email_value);
        email.setText(key_email);
        mobile = (TextView) findViewById(R.id.mobile_no_value);
        mobile.setText(key_mobile);
        dob = (TextView) findViewById(R.id.dob_value);
        dob.setText(key_dob);
        department = (TextView) findViewById(R.id.department_value);
        department.setText(key_department);
        designation = (TextView) findViewById(R.id.designation_value);
        designation.setText(key_designation);
        address = (TextView) findViewById(R.id.address_value);
        address.setText(key_address);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backButton:

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}