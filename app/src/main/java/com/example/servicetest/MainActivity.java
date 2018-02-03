package com.example.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public MyService.MyBinder myBinder;
    public static final String TAG = "MainActivity";
    public MyAidlService myAidlService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        Button bind = findViewById(R.id.bindService);
        Button unbind = findViewById(R.id.unbind);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
        Log.d(TAG, Thread.currentThread().getId() + "");

    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /*myAidlService = MyAidlService.Stub.asInterface(service);
            try {
                myAidlService.plus(3, 6);
            } catch (RemoteException e) {
                e.printStackTrace();
            }*/
            myBinder = (MyService.MyBinder)service;
            myBinder.start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("disconnected", "onServiceDisconnected: ");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                Intent intent = new Intent(this, MyService.class);
                startService(intent);
                break;
            case R.id.stop:
                Intent intent1 = new Intent(this, MyService.class);
                stopService(intent1);
                break;
            case R.id.bindService:
                Intent intent2 = new Intent(this, MyService.class);
                bindService(intent2, conn, BIND_AUTO_CREATE);
                break;
            case R.id.unbind:
                unbindService(conn);
                break;
        }
    }
}
