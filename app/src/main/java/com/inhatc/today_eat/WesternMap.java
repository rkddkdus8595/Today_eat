package com.inhatc.today_eat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class WesternMap extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    final String dbName="food";
    SQLiteDatabase db;
    Cursor record; //
    String name=null; // 가게이름

    double latitude; //위도
    double longtitude; //경도

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_western_map);

        db=this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
        db.execSQL("create table if not exists western ("+
                " name text, latitude real, longtitude real, kind text);");
        db.execSQL("delete from western"); // 데이터 다 지우고 시작
        db.execSQL("Insert into western values('까사올리브',37.452234,126.654188,'양식');");
        db.execSQL("Insert into western values('챕터원스테이크',37.451932,126.656194,'양식');");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng startP = new LatLng(37.4507243,126.6557173); // 시작위치
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startP,16));

        record=db.query("western", null, null, null,null,null,null,null );


        if(record!=null){
            if(record.moveToFirst()){
                do{
                    MarkerOptions markerOptions=new MarkerOptions(); // 마커옵션

                    name=record.getString(0); // 가게 이름
                    latitude=record.getDouble(1); // 위도
                    longtitude=record.getDouble(2); // 경도

                    markerOptions.position(new LatLng(latitude, longtitude))
                            .title(name);

                    mMap.addMarker(markerOptions);
                }while(record.moveToNext());
            }
        }
        mMap.setOnMarkerClickListener(this);
        if(db!=null) db.close(); // 지도까지 다 불러주고 나서 디비 닫아주기
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getApplicationContext(),
                marker.getTitle()+"을 클릭하였습니다.",
                Toast.LENGTH_SHORT).show();

        return false;
    }
}
