package com.example.prajna.employee;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    Intent intent;
    private ArrayList<Employee> mEmployeeList;
private SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        mEmployeeList = DBHandler.getDBHandlerInstance(MainActivity.this).getAllStoredEmployeeRecord();

        if (mEmployeeList != null) {
         /*EmpAdapter customList = new EmpAdapter(this, mEmployeeList);

          ListView emplvw = (ListView) findViewById(R.id.emp_list);
          emplvw.setAdapter(customList);
*/
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            // 2. set layoutManger
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            // 3. create an adapter
            EmployeeAdapter mAdapter = new EmployeeAdapter(this,mEmployeeList);
            // 4. set adapter
            recyclerView.setAdapter(mAdapter);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color
                .parseColor("#3F51B5")));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), EmployeeEntryDetailsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void refreshItems() {
        // Load items
        mEmployeeList = DBHandler.getDBHandlerInstance(MainActivity.this).getAllStoredEmployeeRecord();

        if (mEmployeeList != null) {

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            EmployeeAdapter mAdapter = new EmployeeAdapter(this, mEmployeeList);
            recyclerView.setAdapter(mAdapter);
        }

        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        refreshLayout.setRefreshing(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}