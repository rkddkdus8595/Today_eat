package com.inhatc.today_eat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MemoContent extends AppCompatActivity {

    public String id=null; // id 저장 변수
    SQLiteDatabase db;
    EditText edt1;
    String memotxt=null;
    String kind=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_content);

        Intent intent=getIntent();
        id=intent.getStringExtra("loginId"); // 아이디 받아와서 저장
        memotxt=intent.getStringExtra("memotxt"); // 수정할 때 쓰기 위해 내용을 받아왔음
        kind=intent.getStringExtra("kind"); // 내가 어떤 버튼을 눌러서 이 액티비티로 넘어왔는지 확인하려고


        edt1=(EditText)findViewById(R.id.edt1);
        edt1.setText(memotxt);
    }

    public void goClick(View view){
        db=this.openOrCreateDatabase("food", MODE_PRIVATE, null);

        Calendar calendar = Calendar.getInstance(); // 캘린더 변수 선언
        int month = calendar.get(Calendar.MONTH)+1; // 월
        int day = calendar.get(Calendar.DAY_OF_MONTH); // 일
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 시
        int min = calendar.get(Calendar.MINUTE); // 분
        String total=month+"월 "+day+"일 "+hour+":"+min;

        String cont=edt1.getText().toString();
        if(!cont.isEmpty()){ // 내용이 비어있으면 등록을 할 수 없음
            if(kind.equals("insert")){
                db.execSQL("insert into notepad values ('"+id+"','"+cont+"','"+total+"');");
                if(db != null) db.close();
            }else if(kind.equals("update")){
                db.execSQL("update notepad set memo='"+cont+"', date ='"+total+"' "+"where id='"+id+"' and memo='"+memotxt+"';");
                if(db != null) db.close();
            }

            Intent memo=new Intent(this, Memo.class);
            memo.putExtra("loginId", id); // 다시 넘어갈 창으로 인텐트로 값 전송
            startActivity(memo);
        }else{
            Toast.makeText(getApplicationContext(), "빈 칸을 채워주세용", Toast.LENGTH_SHORT).show();
        }
    }
}
