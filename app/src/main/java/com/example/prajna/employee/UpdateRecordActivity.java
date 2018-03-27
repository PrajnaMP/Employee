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
public class UpdateRecordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private TextView first_name, mDob, company;
    private EditText last_name, email, mobile, address;
    private Button dob_button, update_button;
    private Spinner mDepartmentSpinner, mDesignationSpinner;
    Intent intent;
    Employee employee = new Employee();
    private Calendar cal;
    private int day;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("Update Employee Details"); // set the top title
        String title = actionBar.getTitle().toString(); // get the title
        Intent intent = getIntent();
        if (intent != null) {

            Employee employee = (Employee) intent.getSerializableExtra("Selected_Employee_To_Update");
            first_name = (TextView) findViewById(R.id.first_name_value);
            first_name.setText(employee.getFirst_name());
            last_name = (EditText) findViewById(R.id.last_name_value);
            last_name.setText(employee.getLast_name());
            email = (EditText) findViewById(R.id.email_value);
            email.setText(employee.getEmail());
            mobile = (EditText) findViewById(R.id.mobile_no_value);
            mobile.setKeyListener(null);
            mobile.setEnabled(false);
            mobile.setText(employee.getMobile());
            mDob = (TextView) findViewById(R.id.dob_updated_value);
            mDob.setText(employee.getDOB());
            mDob.setOnClickListener(UpdateRecordActivity.this);
            company = (TextView) findViewById(R.id.organisation_value);
            mDepartmentSpinner = (Spinner) findViewById(R.id.department_spinner);
            mDepartmentSpinner.setOnItemSelectedListener(this);
            setDepartmentSpinners();
            mDesignationSpinner = (Spinner) findViewById(R.id.designation_spinner);
            mDesignationSpinner.setOnItemSelectedListener(this);
            setDesignationItem();
            address = (EditText) findViewById(R.id.address_value);
            address.setText(employee.getAddress());
            update_button = (Button) findViewById(R.id.update);
            update_button.setOnClickListener(this);

            cal = Calendar.getInstance();
            day = cal.get(Calendar.DAY_OF_MONTH);
            month = cal.get(Calendar.MONTH);
            year = cal.get(Calendar.YEAR);

            Toast.makeText(UpdateRecordActivity.this, employee.getFirst_name() + "  is Clicked.", Toast.LENGTH_LONG).show();
        }
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
        // On selecting a spingetMobilener itemadapterView
        String item = parent.getItemAtPosition(i).toString();
//        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean validateData(Employee employee) {
        //Implement logic to check all the fields having value otherwise show specific toast message and return false
        employee.setFirst_name(first_name.getText().toString().trim());
        employee.setLast_name(last_name.getText().toString().trim());
        employee.setEmail(email.getText().toString().trim());
        employee.setMobile(mobile.getText().toString().trim());
        employee.setDOB(mDob.getText().toString().trim());
        employee.setDepartment(mDepartmentSpinner.getSelectedItem().toString().trim());
        employee.setDesignation(mDesignationSpinner.getSelectedItem().toString().trim());
        employee.setAddress(address.getText().toString().trim());

        if (employee.getFirst_name() == null || employee.getFirst_name().isEmpty() || employee.getFirst_name().length() < 5) {
            Toast.makeText(this, "Invalid First Name", Toast.LENGTH_LONG).show();
            return false;
        } else if (employee.getLast_name() == null || employee.getLast_name().isEmpty() || employee.getLast_name().length() < 1) {
            Toast.makeText(this, "Invalid Last Name", Toast.LENGTH_LONG).show();
            return false;
        } else if (!validateEmailAddress(employee.getEmail())) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show();
            return false;
        } else if (!validateDateOfBirth(employee.getDOB())) {
            Toast.makeText(this, "Invalid DOB", Toast.LENGTH_LONG).show();
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

    public static boolean validateDateOfBirth(String dob) {
        String regex = "[^\\d/]";
        String PhoneDigits = dob.replaceAll(regex, "");
        return (PhoneDigits.length() <= 10);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.update:
                if (validateData(employee)) {
                    //If all the fields are filled by user then retrieve all user input data and store into Employee model object and send to DB insert method.
                    //Send Employee data to DB class

                    DBHandler.getDBHandlerInstance(UpdateRecordActivity.this).updateContact(employee);
                    Toast.makeText(this, "Successfully updated ", Toast.LENGTH_LONG).show();
                    Intent mIntent = new Intent(UpdateRecordActivity.this, MainActivity.class);
                    startActivity(mIntent);

                }

                break;

            case R.id.dob_updated_value:
                DateDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            mDob.setText(data.getStringExtra("date_after_set"));
        } else {
            Toast.makeText(UpdateRecordActivity.this, "Sorry, something went wrong.Please try again.", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void DateDialog() {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
                mDob.setText(dayOfMonth + "/" + monthOfYear + "/" + year);

            }
        };

        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, year, month, day);
        dpDialog.show();

    }
}