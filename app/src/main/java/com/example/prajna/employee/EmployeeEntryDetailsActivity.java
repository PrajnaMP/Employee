package com.example.prajna.employee;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by prajna on 24/8/16.
 */
public class EmployeeEntryDetailsActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private Spinner mDepartmentSpinner, mDesignationSpinner;
    private EditText mMobile_noView;
    private EditText mFirstNameView, mLastNameView, mEmailView, mAddressView;
    private Button submit_button;
    private TextView mCompanyView, mDobView;
    private Calendar cal;
    private int day;
    private int month;
    private int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("Enter Employee Details"); // set the top title
        String title = actionBar.getTitle().toString(); // get the title
      /*  getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.back_button);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);*/
        init();
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

    private void init() {
        String text = "+ 91 ";
        //If all the fields are filled by user then retrieve all user input data and store into Employee model object and send to DB insert method.

        mMobile_noView = (EditText) findViewById(R.id.mobile_no_value);
        mMobile_noView.setText(text);
        mFirstNameView = (EditText) findViewById(R.id.first_name_value);
        mLastNameView = (EditText) findViewById(R.id.last_name_value);
        mEmailView = (EditText) findViewById(R.id.email_value);
        mCompanyView = (TextView) findViewById(R.id.organisation_value);
        mAddressView = (EditText) findViewById(R.id.address_value);
        mDobView = (TextView) findViewById(R.id.dob_value);
        mDobView.setText("Click on this to set DOB");
        mDobView.setOnClickListener(EmployeeEntryDetailsActivity.this);
        mDepartmentSpinner = (Spinner) findViewById(R.id.department_spinner);
        mDepartmentSpinner.setOnItemSelectedListener(this);
        setDepartmentSpinners();
        mDesignationSpinner = (Spinner) findViewById(R.id.designation_spinner);
        mDesignationSpinner.setOnItemSelectedListener(this);
        setDesignationItem();
        submit_button = (Button) findViewById(R.id.submit);
        submit_button.setOnClickListener(EmployeeEntryDetailsActivity.this);

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

//        Employee employee = new Employee();
//        employee.setFirst_name(mFirstNameView.getText().toString().trim());
//        employee.setLast_name(mLastNameView.getText().toString().trim());
//        employee.setEmail(mEmailView.getText().toString().trim());
//        employee.setMobile(mMobile_noView.getText().toString().trim());
//        employee.setDOB(mDobView.getText().toString().trim());
//        employee.setCompany(mCompanyView.getText().toString().trim());
//        employee.setDepartment(mDepartmentSpinner.getSelectedItem().toString().trim());
//        employee.setDesignation(mDesignationSpinner.getSelectedItem().toString().trim());
//        employee.setAddress(mAddressView.getText().toString().trim());
    }

    private boolean validateData(Employee employee) {
        //Implement logic to check all the fields having value otherwise show specific toast message and return false

        if (employee.getFirst_name() == null || employee.getFirst_name().isEmpty() || employee.getFirst_name().length() < 5) {
            Toast.makeText(this, "Invalid First Name", Toast.LENGTH_LONG).show();
            return false;
        } else if (employee.getLast_name() == null || employee.getLast_name().isEmpty() || employee.getLast_name().length() < 1) {
            Toast.makeText(this, "Invalid Last Name", Toast.LENGTH_LONG).show();
            return false;
        } else if (!validateEmailAddress(employee.getEmail())) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show();
            return false;
        } else if (!validateMobileNumber(employee.getMobile())) {
            Toast.makeText(this, "Invalid Mobile number", Toast.LENGTH_LONG).show();
            return false;
        } else if (!validateDateOfBirth(employee.getDOB())) {
            Toast.makeText(this, "Invalid DOB", Toast.LENGTH_LONG).show();
            return false;
        } else if (employee.getCompany() == null || employee.getCompany().isEmpty()) {
            Toast.makeText(this, "Invalid Company", Toast.LENGTH_LONG).show();
            return false;
        } else if (employee.getDepartment() == null || employee.getDepartment().isEmpty()) {
            Toast.makeText(this, "Invalid Department", Toast.LENGTH_LONG).show();
            return false;
        } else if (employee.getDesignation() == null || employee.getDesignation().isEmpty()) {
            Toast.makeText(this, "Invalid Designation", Toast.LENGTH_LONG).show();
            return false;
        } else if (employee.getAddress() == null || employee.getAddress().isEmpty() || employee.getAddress().length() < 10) {
            Toast.makeText(this, "Invalid Address", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    public static boolean validateEmailAddress(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean validateMobileNumber(String mobile) {
        String regex = "[^\\d+]";
        String PhoneDigits = mobile.replaceAll(regex, "");
        return (PhoneDigits.length() == 13);
    }

    public static boolean validateDateOfBirth(String dob) {
        if (dob == "Click on this to set DOB")
            return false;
        else {
            String regex = "[^\\d/]";
            String PhoneDigits = dob.replaceAll(regex, "");
            return (PhoneDigits.length() <= 10);
        }
    }

    private void setDepartmentSpinners() {
        List<String> Department = new ArrayList<String>();
        Department.add("Android");
        Department.add("iOS");
        Department.add("Web");
        Department.add("php");
        Department.add("dotnet");
        Department.add("JAVA");
        Department.add("HR");
        Department.add("Accounts");
        Department.add("Admin");
        Department.add("Sales");
        Department.add("Testing");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Department);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mDepartmentSpinner.setAdapter(dataAdapter);
    }

    private void setDesignationItem() {
        List<String> Designation = new ArrayList<String>();
        Designation.add("Trainee s/w eng");
        Designation.add("S/w engineer");
        Designation.add("Senior s/w eng");
        Designation.add("Lead");
        Designation.add("Manager");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Designation);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mDesignationSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        // On selecting a spinner itemadapterView
        String item = parent.getItemAtPosition(i).toString();
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.submit:

                //If all the fields are filled by user then retrieve all user input data and store into Employee model object and send to DB insert method.
                Employee employee = new Employee();
                employee.setFirst_name(mFirstNameView.getText().toString().trim());
                employee.setLast_name(mLastNameView.getText().toString().trim());
                employee.setEmail(mEmailView.getText().toString().trim());
                employee.setMobile(mMobile_noView.getText().toString().trim());
                employee.setDOB(mDobView.getText().toString().trim());
                employee.setCompany(mCompanyView.getText().toString().trim());
                employee.setDepartment(mDepartmentSpinner.getSelectedItem().toString().trim());
                employee.setDesignation(mDesignationSpinner.getSelectedItem().toString().trim());
                employee.setAddress(mAddressView.getText().toString().trim());

                if (validateData(employee)) {

                    //Send Employee data to DB class

                    DBHandler.getDBHandlerInstance(EmployeeEntryDetailsActivity.this).insertAllDataIntoEmployeeList(employee);
                    Toast.makeText(this, "Successfully added ", Toast.LENGTH_LONG).show();
                    Intent mintent = new Intent(EmployeeEntryDetailsActivity.this, MainActivity.class);
                    startActivity(mintent);

                }

                break;

            case R.id.dob_value:

                DateDialog();

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            mDobView.setText(data.getStringExtra("date"));
        } else {
            Toast.makeText(EmployeeEntryDetailsActivity.this, "Sorry, something went wrong.Please try again.", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void DateDialog() {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;

                mDobView.setText(dayOfMonth + "/" + monthOfYear + "/" + year);

            }
        };

        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, year, month, day);
        dpDialog.show();

    }
}