package com.yusby.bancomat20;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register_Activity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText nomeAccount,password,email,chiaveDiSicurezza;
    TextView erroreTesto,erroreAccount;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        myDb = new DatabaseHelper(this);

        nomeAccount =findViewById(R.id.Username);
        password = findViewById(R.id.password);
        signUp = findViewById(R.id.sign_Up);
        erroreTesto = findViewById(R.id.testoErrore);
        email= findViewById(R.id.email);
        chiaveDiSicurezza = findViewById(R.id.chiavedisicurezza);


        erroreTesto.setVisibility(View.INVISIBLE);
        nomeAccount=resText(nomeAccount);
        password=rusText(password);
        email=rusText(email);
        setData();

    }

    public EditText resText(EditText nomeAccount){
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                    if (Character.isLetter(source.charAt(i))){
                    }else {
                        return "";
                    }
                }
                return null;
            }

        };
        nomeAccount.setFilters(new InputFilter[] { filter });
        return nomeAccount;
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

    public void setData(){
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!nomeAccount.getText().toString().equals("") && !password.getText().toString().equals("") && !email.getText().toString().equals("") && !chiaveDiSicurezza.getText().toString().equals("")) {
                    boolean ok = true;
                    Cursor res = myDb.getAllData();
                    String bufferNome = null;
                    String bufferCognome = null;
                    while (res.moveToNext()) {
                        if (res.getString(1).equals(nomeAccount.getText().toString()) || res.getString(4).equals(email.getText().toString())) {
                            ok = false;
                        } else {
                            ok = true;
                        }
                    }

                    if (ok) {
                        boolean inInseted=false;
                        if(controlMail()) {
                            System.out.println(nomeAccount.getText().toString() + "Co dio");
                            inInseted = myDb.insertData(nomeAccount.getText().toString(), password.getText().toString(), "15000", email.getText().toString(), chiaveDiSicurezza.getText().toString());
                            System.out.println(nomeAccount.getText().toString() + "Co dio");
                        }else{
                            ok=false;
                            erroreTesto.setVisibility(View.VISIBLE);
                        }
                        if (inInseted) {
                            Toast.makeText(Register_Activity.this, "Account Creato", Toast.LENGTH_LONG).show();
                            erroreTesto.setVisibility(View.INVISIBLE);
                            startLogin();
                        } else {
                    /*Toast.makeText(Register_Activity.this,"Data not Inserted",Toast.LENGTH_LONG).show();*/
                        }
                    } else {
                        erroreTesto.setVisibility(View.INVISIBLE);
                        erroreAccount.setVisibility(View.VISIBLE);
                        /*nomeAccount.setTextColor(Color.RED);*/
                        controlInserimento();
                    }
                }else {
                    erroreTesto.setVisibility(View.VISIBLE);
                    controlInserimento();
                }

            }
        });
    }

    public void startLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void controlInserimento(){
        if (nomeAccount.getText().toString().equals("")){
            nomeAccount.setHintTextColor(Color.RED);
        }

        if (password.getText().toString().equals("")){
            password.setHintTextColor(Color.RED);
        }else{
            password.setText("");
            password.setHintTextColor(Color.RED);
        }

        if (email.getText().toString().equals("")){
            email.setHintTextColor(Color.RED);
        }else{
            email.setText("");
            email.setHintTextColor(Color.RED);
        }

        if (chiaveDiSicurezza.getText().toString().equals("")){
            chiaveDiSicurezza.setHintTextColor(Color.RED);
        }else{
            chiaveDiSicurezza.setText("");
            chiaveDiSicurezza.setHintTextColor(Color.RED);
        }
    }

    public boolean controlMail(){
        boolean test;
        if(!email.getText().toString().contains("@") || !email.getText().toString().contains(".")){
            email.setHintTextColor(Color.RED);
            email.setTextColor(Color.RED);
            test=false;
        }else{
            email.setTextColor(Color.WHITE);
            test=true;
        }

        if(test){
            return true;
        }else{
            return false;
        }
    }

}
