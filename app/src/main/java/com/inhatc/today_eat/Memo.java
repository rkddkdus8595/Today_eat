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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Memo extends AppCompatActivity {

    ListView lst;
    String txt=null; // 리스트뷰에서 아이템을 가져와서 담을 문자열
    SQLiteDatabase db;
    ArrayList<String> arrlist;
    ArrayAdapter<String> adapter;

    public String id=null; // 사용자가 로그인 한 아이디가 이 변수에 저장됨

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);


        Intent intent=getIntent();
        id=intent.getStringExtra("loginId"); // 아이디 받아와서 저장

        db=this.openOrCreateDatabase("food", MODE_PRIVATE, null);
        db.execSQL("create table if not exists notepad ("+
                " id text not null, memo text not null, date text not null);");
        Cursor cur=db.query("notepad", null, null, null, null, null, null, null);

        arrlist = new ArrayList<String>();

        if(cur!=null){
            if(cur.moveToFirst()){
                do{
                    if(id.equals(cur.getString(0))){
                        arrlist.add(cur.getString(1)+"\t\t"+cur.getString(2)); // 메모는 내용 + 날짜로 이루어 질 것임
                    }
                }while(cur.moveToNext());
            }
        }

        lst=(ListView)findViewById(R.id.listView2);

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

    public void btnDeleteClick(View v){ // 애초에 내 메모장이라 내 것만 뜨기 때문에 삭제에 제약을 걸 필요가 없음
        db=this.openOrCreateDatabase("food", MODE_PRIVATE, null);
        String[] arr=txt.split("\t\t");

        int select=lst.getCheckedItemPosition(); // 리스트뷰에서 체크된 아이템의 자리를 정수로 갖고옴

        if(select != lst.INVALID_POSITION){
            db.execSQL("delete from notepad where id='"+id+"' and memo='"+arr[0]+"';");
            arrlist.remove(select);
            lst.clearChoices();
            adapter.notifyDataSetChanged();
        }
        if(db != null) db.close();
    }

    public void btnGoClick(View view){ // 등록 버튼을 누를 경우 창 이동
        Intent content1=new Intent(this, MemoContent.class);
        content1.putExtra("loginId",id);
        content1.putExtra("kind","insert");
        startActivity(content1);
    }

    public void btnUpdateClick(View view){
        Intent content1=new Intent(this, MemoContent.class);
        content1.putExtra("loginId",id);

        String[] arr=txt.split("\t\t");
        String memotxt=arr[0];
        content1.putExtra("memotxt",memotxt);
        content1.putExtra("kind","update"); // 내가 뭘 눌렀는지 구분해주기 위해서

        Toast.makeText(getApplicationContext(), memotxt, Toast.LENGTH_SHORT).show();
        startActivity(content1);
    }

}
