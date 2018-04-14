package com.ufersa.gps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

private DatabaseReference fireBaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gps-ufersa.firebaseio.com/Aula 1/");

private String bdLatString;
private String bdLngString;
private double bdLat;
private double bdLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        MapsActivity mapsActivity = new MapsActivity();


        if (id == R.id.button1) {
            Intent abrirMap = new Intent(MainActivity.this, MapsActivity.class);
            abrirMap.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(abrirMap);
            MapsActivity.controleBt = 1;
            fireBaseReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                   bdLatString = dataSnapshot.child("Circuitos2").child("Coordenada").child("Latitude").getValue().toString();
                   // bdLatString = bdLatString.replace("Latitude=","");
                    bdLat = Double.parseDouble(bdLatString);

                    bdLngString = dataSnapshot.child("Circuitos2").child("Coordenada").child("Longitude").getValue().toString();
                    //bdLngString = bdLngString.replace("Longitude=","");
                    bdLng = Double.parseDouble(bdLngString);

                    Log.i("ZER", bdLngString);
                    Toast.makeText(MainActivity.this, bdLngString, Toast.LENGTH_LONG).show();
                    setLatLng(bdLat, bdLng);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        } else if (id == R.id.button2) {
            Intent abrirMap = new Intent(MainActivity.this, MapsActivity.class);
            abrirMap.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(abrirMap);
            MapsActivity.controleBt = 2;

            fireBaseReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    bdLatString = dataSnapshot.child("Eletrônicos1").child("Coordenada").child("Latitude").getValue().toString();
                    // bdLatString = bdLatString.replace("Latitude=","");
                    bdLat = Double.parseDouble(bdLatString);

                    bdLngString = dataSnapshot.child("Eletrônicos1").child("Coordenada").child("Longitude").getValue().toString();
                    //bdLngString = bdLngString.replace("Longitude=","");
                    bdLng = Double.parseDouble(bdLngString);

                    Log.i("ZER", bdLngString);
                    Toast.makeText(MainActivity.this, bdLngString, Toast.LENGTH_LONG).show();
                    setLatLng(bdLat, bdLng);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else if (id == R.id.button3) {
            Intent abrirMap = new Intent(MainActivity.this, MapsActivity.class);
            abrirMap.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(abrirMap);
            MapsActivity.controleBt = 3;

            fireBaseReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    bdLatString = dataSnapshot.child("uPuC").child("Coordenada").child("Latitude").getValue().toString();
                    // bdLatString = bdLatString.replace("Latitude=","");
                    bdLat = Double.parseDouble(bdLatString);

                    bdLngString = dataSnapshot.child("uPuC").child("Coordenada").child("Longitude").getValue().toString();
                    //bdLngString = bdLngString.replace("Longitude=","");
                    bdLng = Double.parseDouble(bdLngString);

                    Log.i("ZER", bdLngString);
                    Toast.makeText(MainActivity.this, bdLngString, Toast.LENGTH_LONG).show();
                    setLatLng(bdLat, bdLng);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void setLatLng(double latitude_Lat, double longitude_Lng){

        MapsActivity.latitude =  latitude_Lat;
        MapsActivity.longitude = longitude_Lng;
        Intent it = new Intent(MainActivity.this, MapsActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(it);
    }
}
