package com.yusby.bancomat20;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class OpzioniActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String saldoDisponibile;
    int position =0;
    String nomeInte= null;
    String numeroConto= null;
    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opzioni);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDb = new DatabaseHelper(this);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Intent intent = getIntent();
        position = intent.getIntExtra("name",0);
        nomeInte = intent.getStringExtra("key");
        numeroConto = intent.getStringExtra("id");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        TextView drawerUsername =  headerView.findViewById(R.id.Username);
        TextView drawerAccount =  headerView.findViewById(R.id.NumeroConto);
        drawerUsername.setText(nomeInte);
        drawerAccount.setText("N° 000000"+numeroConto);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navigationView.setCheckedItem(R.id.contoCorrente);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //ALGORITMO SALVA VITA
        /*
        do {
            if(MotionEvent.BUTTON_BACK==8) {
                navigationView.setCheckedItem(R.id.contoCorrente);
                drawer.addDrawerListener(toggle);
                toggle.syncState();
            }
        }while(drawer.isClickable());
        */
       System.out.println(position);

        showFragment(Conto_corrente.class);
    }

    @Override
    public void onBackPressed() {
        showFragment(Conto_corrente.class);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(MotionEvent.BUTTON_BACK==8) {
            navigationView.setCheckedItem(R.id.contoCorrente);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        }
        /*
        DrawerLayout drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
            ActionBarDrawerToggle drawerToggle = null;
        drawerToggle.syncState();
        }
        ActionBarDrawerToggle drawerToggle = null;
        drawerToggle.syncState();
        }
        Intent intent = new Intent(this,Conto_corrente.class);
        intent.putExtra("name",saldoDisponibile);
        startActivity(intent);
        ActionBarDrawerToggle drawerToggle = null;
        drawerToggle.syncState();
         */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opzioni, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            returnLogout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Class fragment=null;

        if (id == R.id.contoCorrente) {
            // Handle the camera action
            fragment = Conto_corrente.class;
            showFragment(fragment);
        } else if (id == R.id.bonifico) {
            fragment = fragment_bonifico.class;
            showFragment(fragment);
        } else if (id == R.id.ricaricaCarta) {
            fragment = Ricarica_carta.class;
            showFragment(fragment);
        } else if (id == R.id.ricaricaTelefono) {
            fragment = ricarica_telefono.class;
            showFragment(fragment);
        } else if (id == R.id.logout) {
            returnLogout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void returnLogout(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    /*public void bonificoActivity(){
        Intent intent = new Intent(this,bonifico_activity.class);
        startActivity(intent);
    }*/

    private void showFragment(Class fragmentClass) {

        android.support.v4.app.Fragment fragment = null;

        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent,fragment)
                .commit();
    }

    public int positionAccount(){
        return position;
    }

    public String getNomeInte(){
        return nomeInte;
    }

    public String getNumeroConto(){
        return numeroConto;
    }

    public String daiUsername(int positionAccount){
        Cursor res = myDb.getAllData();
        String bufferNome = null;
        res.moveToPosition(positionAccount);
        bufferNome = res.getString(1);

        return bufferNome;

    }

    public String daiNumero(int positionAccount){
        Cursor res = myDb.getAllData();
        String bufferNome = null;
        res.moveToPosition(positionAccount);
        bufferNome = String.valueOf(res.getInt(0));

        return bufferNome;

    }

}
