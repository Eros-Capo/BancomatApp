package com.yusby.bancomat20;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_bonifico extends Fragment {

    DatabaseHelper myDb;


    public float saldoDisponibile;
    private String saldoDisponibile2;


    public fragment_bonifico() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myDb = new DatabaseHelper(this.getContext());

        View view = inflater.inflate(R.layout.fragment_bonifico,container,false);
        final TextView beneficiarioTesto= view.findViewById(R.id.beneficiarioTesto);
        final EditText beneficiario= resText((EditText) view.findViewById(R.id.beneficiario));
        final TextView ibanTesto= view.findViewById(R.id.ibanTesto);
        final EditText iban=resText((EditText) view.findViewById(R.id.iban));
        final TextView importoTesto = view.findViewById(R.id.importoTesto);
        final EditText importo= view.findViewById(R.id.importo);
        final TextView erroreTesto= view.findViewById(R.id.erroreTesto);
        final TextView dataBonificoTesto= view.findViewById(R.id.dataBonificoTesto);
        final EditText giorno = view.findViewById(R.id.giorno);
        final EditText mese = view.findViewById(R.id.mese);
        final EditText anno= view.findViewById(R.id.anno);
        final TextView OpConSucc = view.findViewById(R.id.opConSucc);
        final EditText causale= view.findViewById(R.id.causale);

        erroreTesto.setVisibility(View.INVISIBLE);
        OpConSucc.setVisibility(View.INVISIBLE);


        OpzioniActivity opzioniActivity = (OpzioniActivity) getActivity();
        final int positionAccount= opzioniActivity.positionAccount();
        System.out.println(positionAccount+" Bellissima");
        saldoDisponibile = daiMoney(positionAccount);
        System.out.println(saldoDisponibile2);



        final Button effettuaBonifico= view.findViewById(R.id.effettuaBonificoButton);
        effettuaBonifico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean firstControl= true;
                boolean secondControl = true;
                boolean thirdControl = true;
                boolean fourthControl = true;
                firstControl = controlBeneficiario(beneficiario,beneficiarioTesto,erroreTesto);
                secondControl = controlIban(iban,ibanTesto,erroreTesto);
                thirdControl = controlImporto(importo,importoTesto,erroreTesto);
                fourthControl = controlDataBonifico(giorno,mese,anno,dataBonificoTesto,erroreTesto);
                if (firstControl && secondControl && thirdControl && fourthControl){
                    saldoDisponibile-=Float.parseFloat(importo.getText().toString());
                    UpdateData(positionAccount);
                    effettuaBonifico.setVisibility(View.INVISIBLE);
                    OpConSucc.setVisibility(View.VISIBLE);
                    beneficiario.setFocusable(false);
                    iban.setFocusable(false);
                    importo.setFocusable(false);
                    giorno.setFocusable(false);
                    mese.setFocusable(false);
                    anno.setFocusable(false);
                    causale.setFocusable(false);
                }
            }
        });

        mese.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                effettuaBonifico.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        beneficiario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                effettuaBonifico.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        iban.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                effettuaBonifico.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        importo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                effettuaBonifico.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        giorno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                effettuaBonifico.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mese.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                effettuaBonifico.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        anno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                effettuaBonifico.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }



    private boolean controlDataBonifico(EditText giorno,EditText mese,EditText anno, TextView dataBonificoTesto,TextView erroreTesto) {
        boolean firstControl= true;
        boolean secondControl = true;
        boolean thirdControl = true;
        firstControl = controlGiorno(giorno,dataBonificoTesto,erroreTesto);
        secondControl = controlMese(mese,dataBonificoTesto,erroreTesto);
        thirdControl = controlAnno(anno,dataBonificoTesto,erroreTesto);
        if (firstControl && secondControl && thirdControl){
            return true;
        }else{
            return false;
        }
    }



    private boolean controlImporto(EditText importo, TextView importoTesto, TextView erroreTesto) {
        boolean importoValido = importoValido(importo,importoTesto,erroreTesto);
        if (importoValido){
            if (importoOltreLimite(importo,importoTesto,erroreTesto)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }

    private boolean controlIban(EditText iban,TextView ibanTesto,TextView erroreTesto) {
        if (iban.getText().length()!=16){
            setErroreTesto(erroreTesto);
            ibanTesto.setTextColor(Color.RED);
            return false;
        }else {
            notErroreTesto(erroreTesto);
            ibanTesto.setTextColor(Color.WHITE);
            return true;
        }

    }

    private boolean controlBeneficiario(EditText beneficiario, TextView beneficiarioTesto,TextView erroreTesto) {
        if (beneficiario.getText().length()<1){
            setErroreTesto(erroreTesto);
            beneficiarioTesto.setTextColor(Color.RED);
            return false;
        }else{
            notErroreTesto(erroreTesto);
            beneficiarioTesto.setTextColor(Color.WHITE);
            return true;
        }
    }




    private void setErroreTesto(TextView erroreTesto){
        erroreTesto.setVisibility(View.VISIBLE);
    }
    private void notErroreTesto(TextView erroreTesto){ erroreTesto.setVisibility(View.INVISIBLE);}

    private boolean importoOltreLimite(EditText importo, TextView importoTesto,TextView erroreTesto){
        double importoValido=0.0;
        if (importo.getText().length()>=1) {
            importoValido = Double.parseDouble(importo.getText().toString());
            if (importoValido>saldoDisponibile){
                setErroreTesto(erroreTesto);
                importoTesto.setTextColor(Color.RED);
                return false;
            }else{
                notErroreTesto(erroreTesto);
                importoTesto.setTextColor(Color.WHITE);
                return true;
            }
        }else{
            setErroreTesto(erroreTesto);
            importoTesto.setTextColor(Color.RED);
            return false;
        }

    }

    private boolean importoValido(EditText importo,TextView importoTesto,TextView erroreTesto){
        String array[] = new String[15];
        boolean errori= false;
        String importoString = importo.getText().toString();
        array = importoString.split("");
        for (int i=0;i<importo.getText().length();i++){
            if (array[i].equals(".") && !array[i+3].isEmpty()){
                errori=  true;
                break;
            }
        }

        if (errori){
            setErroreTesto(erroreTesto);
            importoTesto.setTextColor(Color.RED);
            return false;
        }else{
            notErroreTesto(erroreTesto);
            importoTesto.setTextColor(Color.WHITE);
            return true;
        }

    }

    private boolean controlGiorno(EditText giorno,TextView dataBonificoTesto,TextView erroreTesto) {
        if (giorno.getText().length()>=1){
            if (Integer.parseInt(giorno.getText().toString())>31){
                setErroreTesto(erroreTesto);
                dataBonificoTesto.setTextColor(Color.RED);
                giorno.setHintTextColor(Color.RED);
                giorno.setText("");
                return false;
            }else{
                notErroreTesto(erroreTesto);
                dataBonificoTesto.setTextColor(Color.WHITE);
                giorno.setHintTextColor(Color.WHITE);
                return true;
            }
        }else{
            setErroreTesto(erroreTesto);
            dataBonificoTesto.setTextColor(Color.RED);
            giorno.setHintTextColor(Color.RED);
            return false;
        }
    }

    private boolean controlMese(EditText mese,TextView dataBonificoTesto,TextView erroreTesto){
        if (mese.getText().length()>=1){
            if (Integer.parseInt(mese.getText().toString())>12){
                setErroreTesto(erroreTesto);
                dataBonificoTesto.setTextColor(Color.RED);
                mese.setHintTextColor(Color.RED);
                mese.setText("");
                return false;
            }else{
                notErroreTesto(erroreTesto);
                mese.setHintTextColor(Color.WHITE);
                dataBonificoTesto.setTextColor(Color.WHITE);
                return true;
            }
        }else{
            setErroreTesto(erroreTesto);
            dataBonificoTesto.setTextColor(Color.RED);
            mese.setHintTextColor(Color.RED);
            return false;
        }
    }

    private boolean controlAnno(EditText anno,TextView dataBonificoTesto,TextView erroreTesto){
        if (anno.getText().length()>=1) {
            if (Integer.parseInt(anno.getText().toString()) < 2018) {
                setErroreTesto(erroreTesto);
                anno.setHintTextColor(Color.RED);
                dataBonificoTesto.setTextColor(Color.RED);
                anno.setText("");
                return false;
            } else {
                notErroreTesto(erroreTesto);
                anno.setHintTextColor(Color.WHITE);
                dataBonificoTesto.setTextColor(Color.WHITE);
                return true;
            }
        }else{
            setErroreTesto(erroreTesto);
            anno.setHintTextColor(Color.RED);
            dataBonificoTesto.setTextColor(Color.RED);
            return false;
        }
    }

    /*public void onBackPressed() {
        FragmentManager fm= getFragmentManager();
        for (Fragment frag:fm.getFragments()){
            if(frag.isVisible()){
                FragmentManager childFm=frag.getChildFragmentManager();
                if(childFm.getBackStackEntryCount()>0){
                    childFm.popBackStack();
                    return;
                }
            }
        }
        Intent intent = new Intent(fragment_bonifico.this,OpzioniActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }*/

    /*public void StartMenu(){
        Intent intent = new Intent(this,SceltaOpzioni.class);
        intent.putExtra("name",saldoDisponibile+"");
        startActivity(intent);
    }*/



    public float daiMoney(int positionAccount){
        Cursor res = myDb.getAllData();
        int bufferNome = 0;
        res.moveToPosition(positionAccount);
        bufferNome = res.getInt(3);

        return bufferNome;

    }

    public void UpdateData(int positionAccount){

                boolean isUpdate = myDb.updateData((positionAccount+1)+"",saldoDisponibile+"");

    }

    public EditText resText(EditText nomeAccount){
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    int type=Character.getType(source.charAt(i));
                    if (Character.isWhitespace(source.charAt(i)) || type== Character.SURROGATE || type== Character.OTHER_SYMBOL) {
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


}



