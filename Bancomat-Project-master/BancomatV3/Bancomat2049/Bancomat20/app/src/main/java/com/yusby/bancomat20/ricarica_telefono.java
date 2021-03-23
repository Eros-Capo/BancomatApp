package com.yusby.bancomat20;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ricarica_telefono extends Fragment {


    DatabaseHelper myDb;

    private float saldoDisponibile;

    public ricarica_telefono() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ricarica_telefono, container, false);

        myDb = new DatabaseHelper(this.getContext());

        final Button ricaricaTelefonoButton= view.findViewById(R.id.RicaricaTelefonoAvvia);
        final TextView numeroTelefonoTesto = view.findViewById(R.id.numeroTelefonoTesto);
        final EditText numeroTelefono = view.findViewById(R.id.numeroTelefono);
        final TextView importoRicaricaTesto = view.findViewById(R.id.importoRicaricaTesto);
        final EditText importoRicarica = view.findViewById(R.id.importoRicarica);
        final CheckBox Vodafone= view.findViewById(R.id.Vodafone);
        final CheckBox Tim = view.findViewById(R.id.Tim);
        final CheckBox Wind = view.findViewById(R.id.Wind);
        final CheckBox Tre = view.findViewById(R.id.Tre);
        final TextView operatoreTelefonicoTesto = view.findViewById(R.id.operatoreTelefonicoTesto);
        final TextView erroreTesto = view.findViewById(R.id.erroreTesto);
        erroreTesto.setVisibility(View.INVISIBLE);

        final TextView OpConSucc= view.findViewById(R.id.opConSucc);
        OpConSucc.setVisibility(View.INVISIBLE);

        OpzioniActivity opzioniActivity = (OpzioniActivity) getActivity();
        final int positionAccount= opzioniActivity.positionAccount();
        System.out.println(positionAccount+" Bellissima");
        saldoDisponibile = daiMoney(positionAccount);

        ricaricaTelefonoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean firstControl= true;
                boolean secondControl = true;
                boolean thirdControl = true;
                firstControl = controlPhoneNumber(numeroTelefono,numeroTelefonoTesto,erroreTesto);
                secondControl = controlImportoRicarica(importoRicarica,importoRicaricaTesto,erroreTesto);
                thirdControl =controlSceltaOperatore(Wind,Vodafone,Tre,Tim,erroreTesto,operatoreTelefonicoTesto);
                if (firstControl && secondControl && thirdControl){
                    saldoDisponibile-= Float.parseFloat(importoRicarica.getText().toString());
                    UpdateData(positionAccount);
                    OpConSucc.setVisibility(View.VISIBLE);
                    ricaricaTelefonoButton.setVisibility(View.INVISIBLE);
                    numeroTelefono.setFocusable(false);
                    importoRicarica.setFocusable(false);
                    Vodafone.setClickable(false);
                    Tim.setClickable(false);
                    Wind.setClickable(false);
                    Tre.setClickable(false);
                }else {
                    erroreTesto.setVisibility(View.VISIBLE);
                }
            }
        });

        numeroTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ricaricaTelefonoButton.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        importoRicarica.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ricaricaTelefonoButton.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Vodafone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ricaricaTelefonoButton.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }
        });
        Tim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ricaricaTelefonoButton.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }
        });
        Tre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ricaricaTelefonoButton.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }
        });
        Wind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ricaricaTelefonoButton.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }
        });

        Vodafone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vodafone.setChecked(true);
                Tim.setChecked(false);
                Tre.setChecked(false);
                Wind.setChecked(false);
            }
        });

        Tim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tim.setChecked(true);
                Wind.setChecked(false);
                Tre.setChecked(false);
                Vodafone.setChecked(false);
            }
        });

        Tre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tre.setChecked(true);
                Wind.setChecked(false);
                Tim.setChecked(false);
                Vodafone.setChecked(false);
            }
        });

        Wind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wind.setChecked(true);
                Tre.setChecked(false);
                Tim.setChecked(false);
                Vodafone.setChecked(false);
            }
        });

        return view;
    }


    private boolean controlSceltaOperatore(CheckBox Wind,CheckBox Vodafone, CheckBox Tre, CheckBox Tim,TextView erroreTesto,TextView operatoreTelefonicoTesto) {
        if (!Wind.isChecked() && !Tre.isChecked() && !Tim.isChecked() && !Vodafone.isChecked()){
            operatoreTelefonicoTesto.setTextColor(Color.RED);
            erroreTesto.setVisibility(View.VISIBLE);
            return false;
        }else{
            erroreTesto.setVisibility(View.INVISIBLE);
            operatoreTelefonicoTesto.setTextColor(Color.WHITE);
            return true;
        }
    }

    private boolean controlImportoRicarica(EditText importoRicarica, TextView importoRicaricaTesto,TextView erroreTesto) {
        if (importoRicarica.getText().toString().equals("") || (Float.parseFloat(importoRicarica.getText().toString())>saldoDisponibile)  ) {
            importoRicaricaTesto.setTextColor(Color.RED);
            erroreTesto.setVisibility(View.VISIBLE);
            return false;
        }else {
            erroreTesto.setVisibility(View.INVISIBLE);
            importoRicaricaTesto.setTextColor(Color.WHITE);
            return true;
        }
    }

    private boolean controlPhoneNumber(EditText numeroTelefono,TextView numeroTelefonoTesto,TextView erroreTesto) {
        if (numeroTelefono.getText().length()<10){
            numeroTelefonoTesto.setTextColor(Color.RED);
            erroreTesto.setVisibility(View.VISIBLE);
            return false;
        }else{
            erroreTesto.setVisibility(View.INVISIBLE);
            numeroTelefonoTesto.setTextColor(Color.WHITE);
            return true;
        }
    }



    public void UpdateData(int positionAccount){

        boolean isUpdate = myDb.updateData((positionAccount+1)+"",saldoDisponibile+"");


    }

    public float daiMoney(int positionAccount){
        Cursor res = myDb.getAllData();
        int bufferNome = 0;
        res.moveToPosition(positionAccount);
        bufferNome = res.getInt(3);

        return bufferNome;

    }

}
