package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.DoctorReportAdapter;
import com.example.myapplication.Apis.Api;
import com.example.myapplication.Apis.ApiServices;
import com.example.myapplication.Model.DoctorReportModel;
import com.example.myapplication.Model.Result;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Mr_doctor_visit_list_report extends AppCompatActivity {

    ArrayList<DoctorReportModel> doctorReportModels = new ArrayList<>();
    DoctorReportAdapter doctorsAdapter;
    RecyclerView recyclerView;
    String select_month_data[] = {"January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    String selecyear_data[] = {"2021", "2020", "2019"};
    Spinner select_month, selecyear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mr_doctor_visit_list_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.toolbar_color, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.toolbar_color));
        }
        select_month = findViewById(R.id.select_month);
        ArrayAdapter<String> adapter_month = new ArrayAdapter<String>(Mr_doctor_visit_list_report.this, R.layout.spinner_item, select_month_data);
        select_month.setAdapter(adapter_month);

        selecyear = findViewById(R.id.selectyear);
        ArrayAdapter<String> adapter_selecyear = new ArrayAdapter<String>(Mr_doctor_visit_list_report.this, R.layout.spinner_item, selecyear_data);
        selecyear.setAdapter(adapter_selecyear);

        String mr_id = getIntent().getStringExtra("mr_id");
        String dr_id = getIntent().getStringExtra("dr_id");

        recyclerView = findViewById(R.id.recylerview);
        doctorsAdapter = new DoctorReportAdapter(doctorReportModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(Mr_doctor_visit_list_report.this));
        recyclerView.setAdapter(doctorsAdapter);
        get_records(mr_id, dr_id);

    }

    void get_records(String mr_id, String dr_id) {

        final ProgressDialog progressDialog = new ProgressDialog(Mr_doctor_visit_list_report.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.get_mr_report_for_doctor(mr_id, dr_id, "1");

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        doctorReportModels.clear();
                        doctorReportModels.addAll(response.body().getDoctor_reports());
                        doctorsAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}