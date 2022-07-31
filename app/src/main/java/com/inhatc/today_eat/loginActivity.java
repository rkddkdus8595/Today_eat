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

public class loginActivity extends AppCompatActivity {
    final String dbName="food";
    SQLiteDatabase db;
    Cursor record;
    Button registerButton, loginButton, searchButton, searchButton2;
    EditText idText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db=this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

        idText = (EditText)findViewById(R.id.idText);
        passwordText = (EditText)findViewById(R.id.passwordText);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button)findViewById(R.id.registerButton);
        searchButton = (Button)findViewById(R.id.searchButton); // 아이디 / 비밀번호 찾기 버튼
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String id = idText.getText().toString();
                String pw = passwordText.getText().toString();

                if(id.length() == 0 || pw.length() == 0) {
                    //아이디와 비밀번호는 필수 입력사항입니다.
                    Toast.makeText(loginActivity.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                record=db.query("member", null, null, null,null,null,null,null );

                if(record!=null){
                    if(record.moveToFirst()){
                        do{
                            if(!id.equals(record.getString(0))){
                                Toast.makeText(loginActivity.this, "없는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }while(record.moveToNext());
                    }
                }

                if(record!=null){
                    if(record.moveToFirst()){
                        do{
                            if(id.equals(record.getString(0))&&!pw.equals(record.getString(1))){
                                Toast.makeText(loginActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }while(record.moveToNext());
                    }
                }

                if(record!=null){
                    if(record.moveToFirst()){
                        do{
                            if(id.equals(record.getString(0))&&pw.equals(record.getString(1))){
                                Toast.makeText(loginActivity.this, "로그인성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                intent.putExtra("loginId",record.getString(0));
                                startActivity(intent);
                                finish();
                                if(db!=null) db.close();
                                break;
                            }
                        }while(record.moveToNext());
                    }
                }
            }

        });

        searchButton.setOnClickListener(new View.OnClickListener(){ // 아이디 찾기 버튼 리스너
            public void onClick(View view) {
                Toast.makeText(loginActivity.this,"아이디 / 비밀번호 찾기 화면으로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //회원가입 버튼 클릭
                Toast toast = Toast.makeText(loginActivity.this, "회원가입 화면으로 이동", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getApplicationContext(),registerActivity.class);
                startActivity(intent);
            }
        });
    }
}
