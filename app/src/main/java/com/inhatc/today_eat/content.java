package com.inhatc.today_eat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class content extends AppCompatActivity {
    public String id=null; // id 저장 변수
    SQLiteDatabase db;
    EditText edt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Intent intent=getIntent();
        id=intent.getStringExtra("loginId"); // 아이디 받아와서 저장

        edt1=(EditText)findViewById(R.id.edt1);
    }

    public void GogoClick(View view){

        db=this.openOrCreateDatabase("food", MODE_PRIVATE, null);
        String cont=edt1.getText().toString();
        if(!cont.isEmpty()){ // 내용이 비어있으면 등록을 할 수 없음
            db.execSQL("insert into talk (id, content) "+"values ('"+id+"','"+cont+"');");
            if(db != null) db.close();

            Intent talk=new Intent(this, FreeTalk.class);
            talk.putExtra("loginId", id); // 다시 넘어갈 창으로 인텐트로 값 전송
            startActivity(talk);
        }else{
            Toast.makeText(getApplicationContext(), "빈 칸을 채워주세용", Toast.LENGTH_SHORT).show();
        }
    }
}
