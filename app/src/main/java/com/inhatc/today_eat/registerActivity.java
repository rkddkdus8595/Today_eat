package com.inhatc.today_eat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registerActivity extends AppCompatActivity {
    int version = 1;

    SQLiteDatabase database;

    Button registerButton;
    EditText idText, passwordText, nameText, ageText;

    String sql;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        idText = (EditText)findViewById(R.id.idText);
        passwordText = (EditText)findViewById(R.id.passwordText);
        nameText = (EditText)findViewById(R.id.nameText);
        ageText = (EditText)findViewById(R.id.ageText);
        registerButton = (Button)findViewById(R.id.registerButton);

        database=this.openOrCreateDatabase("food", MODE_PRIVATE, null);
        database.execSQL("create table if not exists member ("+
                " id text not null primary key, password text not null, name text not null, age text not null);"); // 테이블이 없을 시에 만들어준다.

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String id = idText.getText().toString();
                String pw = passwordText.getText().toString();
                String name=nameText.getText().toString();
                String age=ageText.getText().toString();

                if(id.length() == 0 || pw.length() == 0 || name.length()==0 || age.length()==0 ) {
                    Toast toast = Toast.makeText(registerActivity.this, "아이디/비밀번호/이름/나이 필수 입력사항입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }


                sql = "SELECT id FROM member WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);

                if(cursor.getCount() != 0){
                    Toast.makeText(registerActivity.this, "중복되는 아이디입니다.", Toast.LENGTH_SHORT).show();
                }else{
                    database.execSQL("insert into member "+"values ('"+id+"','"+pw+"','"+name+"','"+age+"');");
                    Toast.makeText(registerActivity.this, "가입이 완료되었습니다. 로그인을 해주세요.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),loginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
