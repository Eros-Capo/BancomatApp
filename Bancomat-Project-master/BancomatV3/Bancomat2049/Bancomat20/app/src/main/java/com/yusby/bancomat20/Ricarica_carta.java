package com.yusby.bancomat20;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class Ricarica_carta extends Fragment {

    DatabaseHelper myDb;

    private float saldoDisponibile;


    public Ricarica_carta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ricarica_carta, container, false);

        myDb = new DatabaseHelper(this.getContext());

        final EditText nomeIntestatario= resText((EditText) view.findViewById(R.id.nomeIntestatario));
        final EditText nomeCarta= view.findViewById(R.id.numeroCarta);
        final TextView caricaCarta= view.findViewById(R.id.caricaCartaAvvia);
        final TextView nomeInteTesto= view.findViewById(R.id.nomeInteTesto);
        final TextView nCartaTesto= view.findViewById(R.id.ncartaTesto);
        final EditText meseScadenza= view.findViewById(R.id.meseScadenza);
        final EditText annoScadenza= view.findViewById(R.id.annoScadenza);
        final TextView dataScadenzaGen= view.findViewById(R.id.dataScadenzaGen);
        final TextView CvcTesto= view.findViewById(R.id.cvcTesto);
        final EditText Cvc= view.findViewById(R.id.cvc);
        final TextView importoTesto= view.findViewById(R.id.importoTesto);
        final EditText importo = view.findViewById(R.id.importo);
        final TextView erroreTesto= view.findViewById(R.id.textView17);
        erroreTesto.setVisibility(View.INVISIBLE);

        final TextView OpConSucc= view.findViewById(R.id.opConSucc);

        OpzioniActivity opzioniActivity = (OpzioniActivity) getActivity();
        final int positionAccount= opzioniActivity.positionAccount();
        System.out.println(positionAccount+" Bellissima");
        saldoDisponibile = daiMoney(positionAccount);

        caricaCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean firstControl= true;
                boolean secondControl = true;
                boolean thirdControl = true;
                boolean fourthControl = true;
                boolean fifhtControl= true;
                boolean sixthControl= true;
                firstControl = controlCart(nomeCarta,nCartaTesto,erroreTesto);
                secondControl = controlMonth(meseScadenza,dataScadenzaGen,erroreTesto);
                thirdControl = controlYear(annoScadenza,dataScadenzaGen,erroreTesto);
                fourthControl = controlCvc(Cvc,CvcTesto,erroreTesto);
                fifhtControl = controlImport(importo,importoTesto,erroreTesto);
                sixthControl = controlBeneficiario(nomeIntestatario,nomeInteTesto,erroreTesto);
                if (firstControl && secondControl && thirdControl && fourthControl && fifhtControl && sixthControl){
                    saldoDisponibile-=Float.parseFloat(importo.getText().toString());
                    UpdateData(positionAccount);
                    caricaCarta.setVisibility(View.INVISIBLE);
                    OpConSucc.setVisibility(View.VISIBLE);
                    nomeIntestatario.setFocusable(false);
                    nomeCarta.setFocusable(false);
                    meseScadenza.setFocusable(false);
                    annoScadenza.setFocusable(false);
                    Cvc.setFocusable(false);
                    importo.setFocusable(false);
                }else {
                    compareErrore(erroreTesto);
                }
            }
        });


        nomeIntestatario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                caricaCarta.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        nomeCarta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                caricaCarta.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        meseScadenza.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                caricaCarta.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        annoScadenza.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                caricaCarta.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Cvc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                caricaCarta.setVisibility(View.VISIBLE);
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
                caricaCarta.setVisibility(View.VISIBLE);
                OpConSucc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }



    private boolean controlBeneficiario(EditText nomeIntestatario,TextView nomeInteTesto,TextView erroretesto) {
        if (nomeIntestatario.getText().length()<1){
            compareErrore(erroretesto);
            nomeInteTesto.setTextColor(Color.RED);
            return false;
        }else{
            nonCompareErrore(erroretesto);
            nomeInteTesto.setTextColor(Color.WHITE);
            return true;
        }
    }

    private boolean controlImport(EditText importo, TextView importoTesto, TextView erroreTesto) {
        if (importo.getText().toString().equals("") || (Float.parseFloat(importo.getText().toString())>saldoDisponibile)  ) {
            compareErrore(erroreTesto);
            importoTesto.setTextColor(Color.RED);
            return false;
        }else {
            erroreTesto.setVisibility(View.INVISIBLE);
            importoTesto.setTextColor(Color.WHITE);
            return true;
        }
    }

    private boolean controlCvc(EditText Cvc, TextView CvcTesto, TextView erroreTesto) {
        if (Cvc.getText().length()<3){
            compareErrore(erroreTesto);
            CvcTesto.setTextColor(Color.RED);
            return false;
        }else{
            erroreTesto.setVisibility(View.INVISIBLE);
            CvcTesto.setTextColor(Color.WHITE);
            return true;
        }
    }

    private boolean controlYear(EditText annoScadenza, TextView dataScadenzaGen,TextView erroreTesto) {
        if (annoScadenza.length()>=1) {
            if (Integer.parseInt(annoScadenza.getText().toString()) < 2018) {
                compareErrore(erroreTesto);
                annoScadenza.setText("");
                annoScadenza.setHintTextColor(Color.RED);
                dataScadenzaGen.setTextColor(Color.RED);
                return false;
            } else {
                erroreTesto.setVisibility(View.INVISIBLE);
                annoScadenza.setHintTextColor(Color.WHITE);
                dataScadenzaGen.setTextColor(Color.WHITE);
                return true;
            }
        }else{
            compareErrore(erroreTesto);
            annoScadenza.setText("");
            annoScadenza.setHintTextColor(Color.RED);
            dataScadenzaGen.setTextColor(Color.RED);
            return false;
        }
    }

    private boolean controlMonth(EditText meseScadenza, TextView dataScadenzaGen,TextView erroreTesto) {
        if (meseScadenza.length()==2) {
            if (Integer.parseInt(meseScadenza.getText().toString()) > 12 && meseScadenza.getText().length() < 2) {
                compareErrore(erroreTesto);
                meseScadenza.setText("");
                meseScadenza.setHintTextColor(Color.RED);
                dataScadenzaGen.setTextColor(Color.RED);
                return false;
            } else {
                erroreTesto.setVisibility(View.INVISIBLE);
                meseScadenza.setHintTextColor(Color.WHITE);
                dataScadenzaGen.setTextColor(Color.WHITE);
                return true;
            }
        }else{
            compareErrore(erroreTesto);
            meseScadenza.setText("");
            meseScadenza.setHintTextColor(Color.RED);
            dataScadenzaGen.setTextColor(Color.RED);
            return false;
        }
    }

    private boolean controlCart(EditText nomeCarta, TextView ncartaTesto,TextView erroreTesto) {

        if (nomeCarta.getText().length() == 16) {
            erroreTesto.setVisibility(View.INVISIBLE);
            ncartaTesto.setTextColor(Color.WHITE);
            return true;
        }else{
            erroreTesto.setVisibility(View.VISIBLE);
            ncartaTesto.setTextColor(Color.RED);
            return false;
        }
    }

    public void compareErrore(TextView erroreTesto){
        erroreTesto.setVisibility(View.VISIBLE);
    }
    public void nonCompareErrore(TextView erroreTesto){erroreTesto.setVisibility(View.INVISIBLE);}


    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        StartMenu();
    }*/

    /*public void StartMenu(){
        Intent intent = new Intent(this,SceltaOpzioni.class);
        intent.putExtra("name",saldoDisponibile+"");
        startActivity(intent);
    }*/

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
    public EditText resText(EditText nomeIntestatario){
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
                for (int j=start;j<end;j++){
                    int type=Character.getType(source.charAt(j));
                    if (type== Character.SURROGATE || type== Character.OTHER_SYMBOL) {
                        return "";
                    }
                }
                return null;
            }

        };
        nomeIntestatario.setFilters(new InputFilter[] { filter });


        return nomeIntestatario;
    }

}
