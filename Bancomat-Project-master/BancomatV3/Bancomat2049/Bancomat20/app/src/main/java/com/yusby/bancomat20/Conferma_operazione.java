package com.yusby.bancomat20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Conferma_operazione extends AppCompatActivity {

    public String saldoDisponibile=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferma_operazione);

        Button conferma_operazione = findViewById(R.id.Come_to_menu);

        Intent intent = getIntent();
        saldoDisponibile = intent.getStringExtra("name");
        System.out.println(saldoDisponibile);

        conferma_operazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMenu();
            }
        });

    }

    public void startMenu(){
        Intent intent = new Intent(this,OpzioniActivity.class);
        intent.putExtra("name",saldoDisponibile);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,OpzioniActivity.class);
        intent.putExtra("name",saldoDisponibile);
        startActivity(intent);
        super.onBackPressed();

    }
}
