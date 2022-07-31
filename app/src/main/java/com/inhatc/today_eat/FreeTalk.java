package com.inhatc.today_eat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FreeTalk extends AppCompatActivity {
    ListView lst;
    String txt=null; // 리스트뷰에서 아이템을 가져와서 담을 문자열
    SQLiteDatabase db;
    ArrayList<String> arrlist;
    ArrayAdapter<String> adapter;

    public static String id=null; // 사용자가 로그인 한 아이디가 이 변수에 저장됨

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_talk);

        Intent intent=getIntent();
        id=intent.getStringExtra("loginId"); // 아이디 받아와서 저장

        db=this.openOrCreateDatabase("food", MODE_PRIVATE, null);
        db.execSQL("create table if not exists talk ("+
                " id text not null, content text not null);");
        Cursor cur=db.query("talk", null, null, null, null, null, null, null);

        arrlist = new ArrayList<String>();

        if(cur!=null){
            if(cur.moveToFirst()){
                do{
                    arrlist.add(cur.getString(0)+"\t\t"+cur.getString(1));
                }while(cur.moveToNext());
            }
        }

        lst=(ListView)findViewById(R.id.listView1);

        adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, arrlist);
        lst.setAdapter(adapter);
        lst.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        if(db != null) db.close();
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                txt = (String)adapterView.getItemAtPosition(position);

            }
        });
    }

    public void btnDeleteClick(View v){
        db=this.openOrCreateDatabase("food", MODE_PRIVATE, null);
        String[] arr=txt.split("\t\t");

        int select=lst.getCheckedItemPosition(); // 리스트뷰에서 체크된 아이템의 자리를 정수로 갖고옴

        if(select != lst.INVALID_POSITION && id.equals(arr[0])){ // 내가 로그인한 아이디랑 똑같을 경우만 삭제가능 !
            db.execSQL("delete from talk where id='"+id+"' and content='"+arr[1]+"';");
            arrlist.remove(select);
            lst.clearChoices();
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
        }else if(!id.equals(arr[0])){ // 아이디랑 같지 않을 경우 삭제할 수 없다고 뜸
            Toast.makeText(getApplicationContext(), "삭제할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
        if(db != null) db.close();
    }

    public void btnGoClick(View view){ // 등록 버튼을 누를 경우 창 이동
        Intent content123=new Intent(this, content.class);
        content123.putExtra("loginId",id);

        startActivity(content123);
    }

}
