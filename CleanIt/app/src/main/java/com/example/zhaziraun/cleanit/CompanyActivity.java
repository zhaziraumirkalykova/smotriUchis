package com.example.zhaziraun.cleanit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CompanyActivity extends AppCompatActivity implements TypeListener{

    CompanyDataHelper companyDataHelper;
    private final String TAG = "Clean";

    TextView name, description;
    RecyclerView mRecyclerView;
    TypeAdapter adapter;
    ImageView im, back;
    Button totalCost;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        mRecyclerView = findViewById(R.id.typeList);
        totalCost = findViewById(R.id.totalCost);
        im = findViewById(R.id.comment);
        back = findViewById(R.id.back);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyActivity.this, CommentActivity.class);
                intent.putExtra("id", companyDataHelper.companyId);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        companyDataHelper = intent.getParcelableExtra("company");

        name.setText(companyDataHelper.companyName);
        description.setText(companyDataHelper.description);

        adapter = new TypeAdapter(companyDataHelper.cleanTypes, this, this);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TypeAdapter(companyDataHelper.cleanTypes, this, this);
        mRecyclerView.setAdapter(mAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        totalCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(CompanyActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(CompanyActivity.this);
                }
                builder.setTitle("Спасибо!")
                        .setMessage("Заказ был успешно оформлен!")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                 onBackPressed();
                            }
                        })
                        .setIcon(R.drawable.ok)
                        .show();
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void onClick(Double cost) {
        totalCost.setText("Заказать " + cost);
    }
}
