package com.yusby.bancomat20;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Change_Password_Activity extends AppCompatActivity {

    DatabaseHelper myDb;
    TextView erroreMessage;
    EditText email,parolachiave,vecchiapassword,nuovapassaword;
    Button cambiaPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__password_);
        myDb = new DatabaseHelper(this);

        erroreMessage = findViewById(R.id.erroreMessage);
        erroreMessage.setVisibility(View.INVISIBLE);
        TextInputLayout emailBanner = findViewById(R.id.bannerEmail);
        email = rusText((EditText) findViewById(R.id.IbanEmail));
        parolachiave = findViewById(R.id.IbanParolaChiave);
        nuovapassaword = findViewById(R.id.Nuovapassaword);
        cambiaPassword = findViewById(R.id.cmabiaPassword);

        cambiaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (verificaPresenzaEmaileParolachiave() && controlInserimento()){
                    startLogin();
                }else if(!controlInserimento()){
                    erroreMessage.setVisibility(View.VISIBLE);
                }else{
                    erroreMessage.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    public boolean verificaPresenzaEmaileParolachiave(){
        boolean ok = false;
        int id = 0;

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            StartSignUp();
            return false;
        }else{
            while (res.moveToNext()){
                if (res.getString(4).equals(email.getText().toString()) && res.getString(5).equals(parolachiave.getText().toString())){
                    ok = true;
                    id = res.getPosition();
                }else {
                    ok = false;
                }
            }

            if (ok){
                myDb.updatePassword((id+1)+"",nuovapassaword.getText().toString());
                Toast.makeText(Change_Password_Activity.this, "Password Cambiata", Toast.LENGTH_LONG).show();
                return true;
            }else {
                return false;
            }

        }
    }

    public void StartSignUp(){
        Intent intent = new Intent(this,Register_Activity.class);
        startActivity(intent);

    }

    public void startLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public boolean controlInserimento(){
        boolean oki=true;
        int c=0;
            if (email.getText().toString().equals("") || !email.getText().toString().contains("@") || email.getText().toString().length() < 8 || !email.getText().toString().contains(".") || rusText(email).equals("")) {
                erroreMessage.setVisibility(View.VISIBLE);
                email.setHintTextColor(Color.RED);
                email.setTextColor(Color.RED);
                oki = false;
                c=1;
            } else if (parolachiave.getText().toString().equals("") || parolachiave.getText().toString().length() != 4) {
                parolachiave.setHintTextColor(Color.RED);
                parolachiave.setTextColor(Color.RED);
                oki = false;
                c=2;
            } else if (nuovapassaword.getText().toString().equals("")) {
                nuovapassaword.setHintTextColor(Color.RED);
                nuovapassaword.setTextColor(Color.RED);
                oki = false;
                c=3;
            }

            if(c!=1){
                email.setTextColor(Color.WHITE);
            }else if(c!=2){
                parolachiave.setTextColor(Color.WHITE);
            }else if(c!=3){
                nuovapassaword.setTextColor(Color.WHITE);
            }else if(c!=1 && c!=2 && c!=3){
                oki=true;
            }


            if(oki){
                return true;
            }else{
                return false;
            }
        /*
        else{
            parolachiave.setText("");
            parolachiave.setHintTextColor(Color.RED);
        }
         */

    }

    public EditText rusText(EditText password){
        InputFilter filte = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    int type=Character.getType(source.charAt(i));
                    if (type== Character.SURROGATE || type== Character.OTHER_SYMBOL) {
                        return "";
                    }
                }
                return null;
            }

        };
        password.setFilters(new InputFilter[] { filte });
        return password;
    }

}
