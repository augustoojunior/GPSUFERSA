package com.ufersa.gps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Marker meuMarcadorAtual;
    private String provider;


    public double lat_Origem;
    public double lng_Origem;
    public double lat;
    public double lng;

    public double ultimaLat, ultimaLng;

    private final int FINE_LOCATION_PERMISSION = 9999;

    public static double latitude;
    public static double longitude;
    public static int controleBt;

    private Polyline polyline;

    public List<LatLng> rotas;
    public List<LatLng> rotasEntrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Criando o mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Chamando serviço de localização
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Configurando Criterios do provedor
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);

        //Definindo o provedor

        provider = locationManager.getBestProvider(new Criteria(), true);

        //Analiza as permissões e caso não passe, requisita as mesmas
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION);
            return;
        }


        //Guardando a última localização conhecida
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            Log.i("LOG INFO", "Location Arquivado");
            Toast.makeText(this, "Localizado", Toast.LENGTH_LONG).show();
        } else {
            Log.i("LOG INFO", "No Location");
            Toast.makeText(this, "Sem Localização", Toast.LENGTH_LONG).show();
        }

        //Requisição de atualização
        locationManager.requestLocationUpdates(provider, 100000, 5, this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        mMap.setMapType(2);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng ufersa = new LatLng(-5.773997, -37.569635);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ufersa, 17));
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        return;
        }
        mMap.setMyLocationEnabled(true);
    }


    //Pegando a URL de direção
    private String getDirectionsUrl(Location location) {
        lat_Origem = location.getLatitude();
        lng_Origem = location.getLongitude();
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin=" + lat_Origem + "," + lng_Origem);
        googleDirectionsUrl.append("&destination=" + latitude + "," + longitude);
        googleDirectionsUrl.append("&key=" + "AIzaSyCjvSOHnXA_KnPtxCnQQaANPpUEQi7aGU8");

        return googleDirectionsUrl.toString();
    }


    //Ao mudar a localização
    @Override
    public void onLocationChanged(Location location) {

        lat = location.getLatitude();
        lng = location.getLongitude();

        LatLng minhaPosition = new LatLng(lat, lng);

        //CRIAR ROTA
        Object dataTransfer[];
        dataTransfer = new Object[3];
        String url = getDirectionsUrl(location);
        GetDirectionsData getDirectionsData = new GetDirectionsData();
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
        dataTransfer[2] = new LatLng(latitude, longitude);

       switch (controleBt){
           case 1:
               ultimaLat = -5.773849;
               ultimaLng = -37.569986;
               rotas = new ArrayList <LatLng>();
               rotas.add(new LatLng(ultimaLat, ultimaLng));
               rotas.add(new LatLng(-5.773730, -37.569899));
               rotas.add(new LatLng(-5.773627, -37.569915));
               rotas.add(new LatLng(-5.773523, -37.569916));
               //Log.i("FUNCIONANDO", "Funciona Legal");
           break;

           case 2:
               ultimaLat = -5.773834;
               ultimaLng = -37.570361;
               rotas = new ArrayList <LatLng>();
               rotas.add(new LatLng(ultimaLat, ultimaLng));
               rotas.add(new LatLng(-5.773215, -37.570335));
               rotas.add(new LatLng(-5.772784, -37.570303));

               break;


           case 3:
               ultimaLat = -5.773903;
               ultimaLng = -37.567984;
               rotas = new ArrayList <LatLng>();
               rotas.add(new LatLng(ultimaLat, ultimaLng));
               rotas.add(new LatLng(-5.773547, -37.567995));
           break;
       }

        double r = calculateDistance(lat, lng, ultimaLat, ultimaLng);
        //Log.i("VERIFICAÇÃO DA FUNÇÃO", Double.toString(r));

        if(r>0.07) {

            getDirectionsData.execute(dataTransfer);
            tracarRotas1();

        }else {

            switch (controleBt){
                case 1:
                    rotasEntrada = new ArrayList <LatLng>();
                    rotasEntrada.add(new LatLng(lat, lng));
                    rotasEntrada.add(new LatLng(-5.773730, -37.569899));
                    rotasEntrada.add(new LatLng(-5.773627, -37.569915));
                    rotasEntrada.add(new LatLng(-5.773523, -37.569916));
                    break;

                case 2:
                    rotasEntrada = new ArrayList <LatLng>();
                    rotasEntrada.add(new LatLng(lat, lng));
                    rotasEntrada.add(new LatLng(-5.773215, -37.570335));
                    rotasEntrada.add(new LatLng(-5.772784, -37.570303));
                    break;


                case 3:
                    rotasEntrada = new ArrayList <LatLng>();
                    rotasEntrada.add(new LatLng(lat, lng));
                    rotasEntrada.add(new LatLng(-5.773547, -37.567995));
                    break;
                    }

            tracarRotas2();
        }

        if (meuMarcadorAtual != null) {
            meuMarcadorAtual.remove();
            mMap.clear();
            //Log.i("VALORBT",Integer.toString(controleBt));
        }
        meuMarcadorAtual = mMap.addMarker(new MarkerOptions().position(minhaPosition).title("MINHA POSIÇÃO").alpha(0));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minhaPosition, 17));
        //Log.i("LOG INFO", "Lat: " + lat + " Lng: " + lng);

    }

    double calculateDistance( double nLat1, double nLon1, double nLat2, double nLon2 )
    {
        double nRadius = 6371;
        // Earth's radius in Kilometers
        // Get the difference between our two points
        // then convert the difference into radians

        double nDLat = toRadians(nLat2 - nLat1);
        double nDLon = toRadians(nLon2 - nLon1);

        // Here is the new line
        nLat1 =  toRadians(nLat1);
        nLat2 =  toRadians(nLat2);

        double nA = pow ( sin(nDLat/2), 2 ) + cos(nLat1) * cos(nLat2) * pow ( sin(nDLon/2), 2 );

        double nC = 2 * atan2( sqrt(nA), sqrt( 1 - nA ));
        double nD = nRadius * nC;

        return nD; // Return our calculated distance
    }


    public void tracarRotas1() {

        PolylineOptions optionsDoPolyline;
        if (polyline == null) {
            optionsDoPolyline = new PolylineOptions();

            for (int i = 0, tam = rotas.size(); i < tam; i++) {
                optionsDoPolyline.add(rotas.get(i));
            }

            optionsDoPolyline.color(Color.BLUE);

            polyline = mMap.addPolyline(optionsDoPolyline);
        } else {
            polyline.setPoints(rotas);
        }
    }


    public void tracarRotas2() {

        PolylineOptions optionsDoPolyline;
        if (polyline == null) {
            optionsDoPolyline = new PolylineOptions();

            for (int i = 0, tam = rotasEntrada.size(); i < tam; i++) {
                optionsDoPolyline.add(rotasEntrada.get(i));
            }

            optionsDoPolyline.color(Color.BLUE);

            polyline = mMap.addPolyline(optionsDoPolyline);
        } else {
            polyline.setPoints(rotasEntrada);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {


    }

    @Override
    public void onProviderDisabled(String provider) {

        Intent intentProviderDisabled = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intentProviderDisabled);

    }
}
