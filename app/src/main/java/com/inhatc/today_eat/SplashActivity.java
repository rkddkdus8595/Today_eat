package com.inhatc.today_eat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity { // 어플리케이션 로딩 화면
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            Thread.sleep(2000); // 2초 있다가 실행해라
    }catch(InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this, loginActivity.class)); // 로그인 액티비티가 시작됨
        finish();
    }
}
