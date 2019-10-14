package com.chiragmvvm.roomdbexercise;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.chiragmvvm.roomdbexercise.adapter.EmployeeAdapter;
import com.chiragmvvm.roomdbexercise.db.EmployeeAppDatabase;
import com.chiragmvvm.roomdbexercise.db.entity.Employee;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EmployeeAdapter employeeAdapter;
    private ArrayList<Employee> employeeArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    EmployeeAppDatabase employeeAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view_employee);
        employeeAppDatabase = Room.databaseBuilder(getApplicationContext(), EmployeeAppDatabase.class, "EmployeeDB")
                .addCallback(roomDatabaseCallback)
                .build();

        employeeAdapter = new EmployeeAdapter(this, employeeArrayList, MainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(employeeAdapter);

        new GetAllEmployeeAsyncTask().execute();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAndEditEmployee(false, null, -1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //For Add dummy record at the time of initialize.
            Log.d("MainActivity", "CallBack onCreate Invoked");
            createEmployee("Chirag", "chirag@gmail.com", "0000000001", "India");
            createEmployee("Raj", "raj@gmail.com", "0000000002", "Canada");
            createEmployee("Dhaval", "dhaval@gmail.com", "0000000003", "US");
            createEmployee("Jainik", "jainik@gmail.com", "0000000004", "UK");
            createEmployee("Vishal", "vishal@gmail.com", "0000000005", "Russia");
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d("MainActivity", "CallBack onOpen Invoked");
        }
    };

    private void createEmployee(String name, String email, String mobile, String country) {

        new CreateEmployeesAsyncTask().execute(new Employee(0, name, email, mobile, country));
    }

    private void deleteContact(Employee employee, int position) {

        employeeArrayList.remove(position);
        new DeleteEmployeeAsyncTask().execute(employee);

    }

    private void updateContact(String name, String email, String mobileNo, String country, int position) {

        Employee emp = employeeArrayList.get(position);

        emp.setName(name);
        emp.setEmail(email);
        emp.setPhoneNo(mobileNo);
        emp.setCountry(country);

        new UpdateEmployeeAsyncTask().execute(emp);

        employeeArrayList.set(position, emp);

    }

    public void addAndEditEmployee(final boolean isUpdate, final Employee employee, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.layout_add_employee, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        TextView employeeTitle = view.findViewById(R.id.new_employee_title);
        final EditText edtTxtName = view.findViewById(R.id.edtTxtName);
        final EditText edtTxtEmail = view.findViewById(R.id.edtTxtEmail);
        final EditText edtTxtMobile = view.findViewById(R.id.edtTxtMobile);
        final EditText edtTxtCountry = view.findViewById(R.id.edtTxtCountry);

        employeeTitle.setText(!isUpdate ? "Add New Employee" : "Edit Employee");

        if (isUpdate && employee != null) {
            edtTxtName.setText(employee.getName());
            edtTxtEmail.setText(employee.getEmail());
            edtTxtMobile.setText(employee.getPhoneNo());
            edtTxtCountry.setText(employee.getCountry());
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(isUpdate ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                if (isUpdate) {
                                    deleteContact(employee, position);
                                } else {
                                    dialogBox.cancel();
                                }
                            }
                        });


        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtTxtName.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter employee name!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }


                if (isUpdate && employee != null) {

                    updateContact(edtTxtName.getText().toString(), edtTxtEmail.getText().toString(),
                            edtTxtMobile.getText().toString(), edtTxtCountry.getText().toString(), position);
                } else {

                    createEmployee(edtTxtName.getText().toString(), edtTxtEmail.getText().toString(),
                            edtTxtMobile.getText().toString(), edtTxtCountry.getText().toString());
                }
            }
        });
    }

    private class GetAllEmployeeAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            employeeArrayList.addAll(employeeAppDatabase.getEmployeeDAO().getAllEmployee());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            employeeAdapter.notifyDataSetChanged();
        }
    }

    private class CreateEmployeesAsyncTask extends AsyncTask<Employee, Void, Void> {

        @Override
        protected Void doInBackground(Employee... contacts) {
            long id = employeeAppDatabase.getEmployeeDAO().addEmployee(contacts[0]);

            Employee emp = employeeAppDatabase.getEmployeeDAO().getEmployee(id);

            if (emp != null) {
                employeeArrayList.add(0, emp);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            employeeAdapter.notifyDataSetChanged();
        }
    }

    private class UpdateEmployeeAsyncTask extends AsyncTask<Employee, Void, Void> {

        @Override
        protected Void doInBackground(Employee... contacts) {

            employeeAppDatabase.getEmployeeDAO().updateEmployee(contacts[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            employeeAdapter.notifyDataSetChanged();
        }
    }

    private class DeleteEmployeeAsyncTask extends AsyncTask<Employee, Void, Void> {

        @Override
        protected Void doInBackground(Employee... contacts) {

            employeeAppDatabase.getEmployeeDAO().deleteEmployee(contacts[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            employeeAdapter.notifyDataSetChanged();
        }
    }

}
