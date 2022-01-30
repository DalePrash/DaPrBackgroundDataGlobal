package com.example.daprbackgrounddataglobal.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daprbackgrounddataglobal.R;
import com.example.daprbackgrounddataglobal.remotebind.RemoteBindingProviderService;

public class RemoteBindingProvideActivity extends AppCompatActivity implements View.OnClickListener{

private static final String TAG= RemoteBindingProvideActivity.class.getSimpleName();

private Button buttonStartService, buttonStopService;
private Context mContext;

private Intent serviceIntent;

@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_message_provide_service);
        mContext=getApplicationContext();
        buttonStartService=(Button)findViewById(R.id.buttonStartService);
        buttonStopService=(Button)findViewById(R.id.buttonStopService);

        buttonStopService.setOnClickListener(this);
        buttonStartService.setOnClickListener(this);
        serviceIntent=new Intent(getApplicationContext(), RemoteBindingProviderService.class);
        }

@Override
public void onClick(View view) {
        switch (view.getId()){
        case R.id.buttonStartService: startService(serviceIntent);
        Toast.makeText(mContext,"Service Started", Toast.LENGTH_SHORT).show();
        break;
        case R.id.buttonStopService: stopService(serviceIntent);
        break;
default:break;
        }

        }
        }