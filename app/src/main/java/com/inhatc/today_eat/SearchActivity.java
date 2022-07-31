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

public class SearchActivity extends AppCompatActivity {

    final String dbName="food";
    SQLiteDatabase db;
    Cursor record;

    EditText txtName1, txtAge1, txtid, txtName2, txtAge2;
    Button btnFindPasswd, btnFindId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db=this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

        txtName1=(EditText)findViewById(R.id.txtName1);
        txtAge1=(EditText)findViewById(R.id.txtAge1);
        txtid=(EditText)findViewById(R.id.txtid);

        txtName2=(EditText)findViewById(R.id.txtName2);
        txtAge2=(EditText)findViewById(R.id.txtAge2);


        btnFindPasswd=(Button)findViewById(R.id.btn1);
        btnFindId=(Button)findViewById(R.id.btn2);

        btnFindPasswd.setOnClickListener(new View.OnClickListener(){ // 비밀번호 찾기 버튼
            @Override
            public void onClick(View view) {

                String name = txtName1.getText().toString();
                String age = txtAge1.getText().toString();
                String id = txtid.getText().toString();

                if(name.length() == 0 || age.length()==0 || id.length()==0 ) {
                    Toast.makeText(SearchActivity.this, "모든 입력사항을 채워주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                record=db.query("member", null, null, null,null,null,null,null );

                if(record!=null){
                    if(record.moveToFirst()){
                        do{
                            if(id.equals(record.getString(0)) && name.equals(record.getString(2))
                            && age.equals(record.getString(3))){
                                String passwd123=record.getString(1);
                                Toast.makeText(SearchActivity.this, "비밀번호는 "+passwd123+" 입니다.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),loginActivity.class);
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

        btnFindId.setOnClickListener(new View.OnClickListener(){ // 아이디 찾기 버튼
            @Override
            public void onClick(View view) {

                String name = txtName2.getText().toString();
                String age = txtAge2.getText().toString();

                if(name.length() == 0 || age.length()==0 ) {
                    Toast.makeText(SearchActivity.this, "모든 입력사항을 채워주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                record=db.query("member", null, null, null,null,null,null,null );

                if(record!=null){
                    if(record.moveToFirst()){
                        do{
                            if(name.equals(record.getString(2)) && age.equals(record.getString(3))){
                                String id123=record.getString(0);
                                Toast.makeText(SearchActivity.this, "아이디는 "+id123+" 입니다.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),loginActivity.class);
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

    }
}
