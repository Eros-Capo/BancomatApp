package com.yusby.bancomat20;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Conto_corrente extends Fragment {

    public String strText=null;
    DatabaseHelper myDb;


    public Conto_corrente() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        myDb = new DatabaseHelper(this.getContext());

        View view = inflater.inflate(R.layout.fragment_conto_corrente, container, false);
        TextView contocorrente= view.findViewById(R.id.saldoDisponibile);
        TextView nomeInteText = view.findViewById(R.id.textView6);
        TextView numeroContoText = view.findViewById(R.id.textView4);

        OpzioniActivity opzioniActivity = (OpzioniActivity) getActivity();
        int positionAccount= opzioniActivity.positionAccount();
        System.out.println(positionAccount+" Bellissima");
        String nomeInte = opzioniActivity.getNomeInte();
        String numeroConto= opzioniActivity.getNumeroConto();

        float saldoDsiponibile = daiMoney(positionAccount);

        contocorrente.setText(saldoDsiponibile+"");
        nomeInteText.setText(nomeInte);
        numeroContoText.setText("000000"+numeroConto);

        return view;
    }


    /*public void controllaPresenzaAccount(){
        final String Nome = "Alessandro";
        final String Cognome = "Nerva";
        btnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    //show message

                    return;
                }else{
                    String bufferNome = null;
                    String bufferCognome = null;
                    res.moveToPosition(0);
                    bufferNome = "Name :" + res.getString(1);
                    bufferCognome = "Surname :" + res.getString(2);
                    if (res.getString(1).equalsIgnoreCase(Nome) & res.getString(2).equalsIgnoreCase(Cognome)){
                        Toast.makeText(Conto_corrente.this.getContext(),"Data ci sono",Toast.LENGTH_LONG).show();
                        System.out.println(res.getString(0).equals("1"));
                    }else{
                        System.out.println(res.getString(0).equalsIgnoreCase(Nome));
                        Toast.makeText(Conto_corrente.this.getContext(),"Data not ci sono",Toast.LENGTH_LONG).show();
                    }
                }

                    /*while(res.moveToNext()){
                        if ()
                    }*/

            /*}*/
        /*});
    }*/

    public float daiMoney(int positionAccount){
        Cursor res = myDb.getAllData();
        int bufferNome = 0;
        res.moveToPosition(positionAccount);
        bufferNome = res.getInt(3);

        return bufferNome;

    }




}
