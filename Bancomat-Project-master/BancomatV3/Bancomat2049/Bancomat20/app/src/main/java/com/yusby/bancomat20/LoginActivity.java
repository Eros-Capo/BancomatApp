package com.yusby.bancomat20;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    private EditText mIban;
    private EditText mPin;
    private TextView errorMessage,passdimenticata;
    private Button mLogin;
    private Button Register;

    private String saldoDisponibile=null;
    private int id = 0;
    private String nomeInte=null;
    private String numeroConto=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDb = new DatabaseHelper(this);

        mIban=resText((EditText) findViewById(R.id.IbanLogin));
        mPin= rusText((EditText) findViewById(R.id.pinLogin));
        mLogin= findViewById(R.id.loginButton);
        Register = findViewById(R.id.Registrati);
        errorMessage = findViewById(R.id.errorMessage);
        passdimenticata = findViewById(R.id.passworddimenticata);

        errorMessage.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        saldoDisponibile= intent.getStringExtra("name");


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verificAccess(mIban,mPin)){

                    StartOpzioni();
                    errorMessage.setVisibility(View.INVISIBLE);
                }else {
                    errorMessage.setVisibility(View.VISIBLE);
                    passdimenticata.setVisibility(View.VISIBLE);
                }

            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartSignUp();
            }
        });

        passdimenticata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperaPassword();
            }
        });

    }

    public void StartOpzioni(){
        Intent intent = new Intent(this,OpzioniActivity.class);
        intent.putExtra("name",id);
        intent.putExtra("key",nomeInte);
        intent.putExtra("id",numeroConto);
        startActivity(intent);
    }

    public void recuperaPassword(){
        Intent intent = new Intent(this,Change_Password_Activity.class);
        startActivity(intent);
    }

    private boolean verificAccess(EditText mIban, EditText mPin){
        String iban= mIban.getText().toString();
        String pin= mPin.getText().toString();
        boolean ok = false;

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            StartSignUp();
            return false;
        }else{
            String bufferNome = null;
            String bufferCognome = null;
            while (res.moveToNext()) {
                bufferNome = res.getString(1);
                bufferCognome = res.getString(0);
                System.out.println(bufferCognome);
                if (res.getString(1).equalsIgnoreCase(mIban.getText().toString()) & res.getString(2).equalsIgnoreCase(mPin.getText().toString())) {
                    /*Toast.makeText(LoginActivity.this, "Data ci sono", Toast.LENGTH_LONG).show();*/
                    id = res.getPosition();
                    nomeInte = bufferNome;
                    numeroConto= bufferCognome;
                    System.out.println(id);
                    ok = true;
                    break;
                }else {
                    ok= false;
                }
            }
        }
        return ok;
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

    public void StartSignUp(){
        Intent intent = new Intent(this,Register_Activity.class);
        startActivity(intent);

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
}
