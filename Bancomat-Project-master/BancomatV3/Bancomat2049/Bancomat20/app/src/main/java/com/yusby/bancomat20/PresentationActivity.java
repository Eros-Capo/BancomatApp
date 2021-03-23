package com.yusby.bancomat20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;

public class PresentationActivity extends AppCompatActivity {

    @BindView(R.id.TextPresenation)
    TextView presentazione;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        StartLogin();
    }

    public void StartLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
