/*package com.yusby.bancomat20;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class bonifico_activity extends AppCompatActivity {

    @BindView(R.id.beneficiarioTesto)
    TextView beneficiarioTesto;
    @BindView(R.id.beneficiario)
    EditText beneficiario;
    @BindView(R.id.ibanTesto)
    TextView ibanTesto;
    @BindView(R.id.iban)
    EditText iban;
    @BindView(R.id.importoTesto)
    TextView importoTesto;
    @BindView(R.id.importo)
    EditText importo;
    @BindView(R.id.dataBonificoTesto)
    TextView dataBonificoTesto;
    @BindView(R.id.giorno)
    EditText giorno;
    @BindView(R.id.mese)
    EditText mese;
    @BindView(R.id.anno)
    EditText anno;
    @BindView(R.id.effettuaBonificoButton)
    Button effettuaBonificoButton;
    @BindView(R.id.erroreTesto)
    TextView erroreTesto;

    private float saldoDisponibile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonifico_activity);

    }

    @OnClick(R.id.effettuaBonificoButton)
    public void OnClick(){
        boolean firstControl= true;
        boolean secondControl = true;
        boolean thirdControl = true;
        boolean fourthControl = true;
        firstControl = controlBeneficiario();
        secondControl = controlIban();
        thirdControl = controlImporto();
        fourthControl = controlDataBonifico();
        if (firstControl && secondControl && thirdControl && fourthControl){
            saldoDisponibile-=Float.parseFloat(importo.getText().toString());*/
            /*StartResult();*/
       /* }

    }

    private boolean controlDataBonifico() {
        boolean firstControl= true;
        boolean secondControl = true;
        boolean thirdControl = true;
        firstControl = controlGiorno();
        secondControl = controlMese();
        thirdControl = controlAnno();
        if (firstControl && secondControl && thirdControl){
            return true;
        }else{
            return false;
        }
    }



    private boolean controlImporto() {
        boolean importoValido = importoValido();
        if (importoValido){
            if (importoOltreLimite()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }

    private boolean controlIban() {
        if (iban.getText().length()<27){
            setErroreTesto();
            ibanTesto.setTextColor(Color.RED);
            return false;
        }else {
            ibanTesto.setTextColor(Color.BLACK);
            return true;
        }

    }

    private boolean controlBeneficiario() {
        if (beneficiario.getText().length()<1){
            setErroreTesto();
            beneficiarioTesto.setTextColor(Color.RED);
            return false;
        }else{
            beneficiarioTesto.setTextColor(Color.BLACK);
            return true;
        }
    }




    private void setErroreTesto(){
        erroreTesto.setVisibility(View.VISIBLE);
    }

    private boolean importoOltreLimite(){
        double importoValido=0.0;
        if (importo.getText().length()>=1) {
            importoValido = Double.parseDouble(importo.getText().toString());
            if (importoValido>saldoDisponibile){
                setErroreTesto();
                importoTesto.setTextColor(Color.RED);
                return false;
            }else{
                importoTesto.setTextColor(Color.BLACK);
                return true;
            }
        }else{
            setErroreTesto();
            importoTesto.setTextColor(Color.RED);
            return false;
        }

    }

    private boolean importoValido(){
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
            setErroreTesto();
            importoTesto.setTextColor(Color.RED);
            return false;
        }else{
            importoTesto.setTextColor(Color.BLACK);
            return true;
        }

    }

    private boolean controlGiorno() {
        if (giorno.getText().length()>=1){
            if (Integer.parseInt(giorno.getText().toString())>31){
                setErroreTesto();
                dataBonificoTesto.setTextColor(Color.RED);
                giorno.setHintTextColor(Color.RED);
                giorno.setText("");
                return false;
            }else{
                dataBonificoTesto.setTextColor(Color.BLACK);
                giorno.setHintTextColor(Color.BLACK);
                return true;
            }
        }else{
            setErroreTesto();
            dataBonificoTesto.setTextColor(Color.RED);
            giorno.setHintTextColor(Color.RED);
            return false;
        }
    }

    private boolean controlMese(){
        if (mese.getText().length()>=1){
            if (Integer.parseInt(mese.getText().toString())>12){
                setErroreTesto();
                dataBonificoTesto.setTextColor(Color.RED);
                mese.setHintTextColor(Color.RED);
                mese.setText("");
                return false;
            }else{
                mese.setHintTextColor(Color.BLACK);
                dataBonificoTesto.setTextColor(Color.BLACK);
                return true;
            }
        }else{
            setErroreTesto();
            dataBonificoTesto.setTextColor(Color.RED);
            mese.setHintTextColor(Color.RED);
            return false;
        }
    }

    private boolean controlAnno(){
        if (anno.getText().length()>=1) {
            if (Integer.parseInt(anno.getText().toString()) < 2018) {
                setErroreTesto();
                anno.setHintTextColor(Color.RED);
                dataBonificoTesto.setTextColor(Color.RED);
                anno.setText("");
                return false;
            } else {
                anno.setHintTextColor(Color.BLACK);
                dataBonificoTesto.setTextColor(Color.BLACK);
                return true;
            }
        }else{
            setErroreTesto();
            anno.setHintTextColor(Color.RED);
            dataBonificoTesto.setTextColor(Color.RED);
            return false;
        }
    }



    /*public void StartResult(){
        Intent intent = new Intent(this,finalResult.class);
        intent.putExtra("name",saldoDisponibile+"");
        startActivity(intent);
    }*/

/*}*/
