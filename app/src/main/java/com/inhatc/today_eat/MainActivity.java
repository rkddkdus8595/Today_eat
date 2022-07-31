package com.inhatc.today_eat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final String dbName="food";
    SQLiteDatabase db;
    Cursor recordChinese;
    Cursor recordKorean;
    Cursor recordWestern;
    Cursor recordJapanese;

    ArrayList<String> arrlist=new ArrayList<String>();

    public static final int ID_GROUP_MENU_KIND=1;
    public static final int ID_GROUP_TALK=2;

    public static final int ID_GROUP_MENU_KOREAN=11;
    public static final int ID_GROUP_MENU_WESTERN=12;
    public static final int ID_GROUP_MENU_CHINESE=13;
    public static final int ID_GROUP_MENU_JAPANESE=14;

    public static final int ID_GROUP_FREETALK=21;
    public static final int ID_GROUP_MEMO=22;

    TextView Date1;
    TextView txtRandom1;

    public void btnRandom(View v){
        db=this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

        recordChinese=db.query("chinese", null, null, null,null,null,null,null );
        recordKorean=db.query("korean", null, null, null,null,null,null,null );
        recordWestern=db.query("western", null, null, null,null,null,null,null );
        recordJapanese=db.query("japanese", null, null, null,null,null,null,null );

        if(recordChinese!=null) { // 리스트에 넣어주기 위해 일일이 다 테이블들을 돌며 데이터를 넣어줌
            if (recordChinese.moveToFirst()) {
                do {
                    arrlist.add(recordChinese.getString(0)); // 가게 이름
                } while (recordChinese.moveToNext());
            }
        }
        if(recordKorean!=null){
            if(recordKorean.moveToFirst()){
                do{
                    arrlist.add(recordKorean.getString(0)); // 가게 이름
                }while(recordKorean.moveToNext());
            }
        }
        if(recordWestern!=null){
            if(recordWestern.moveToFirst()){
                do{
                    arrlist.add(recordWestern.getString(0)); // 가게 이름
                }while(recordWestern.moveToNext());
            }
        }
        if(recordJapanese!=null){
            if(recordJapanese.moveToFirst()){
                do{
                    arrlist.add(recordJapanese.getString(0)); // 가게 이름
                }while(recordJapanese.moveToNext());
            }
        }

        Random rand=new Random(); // 랜덤함수
        String randFood=arrlist.get(rand.nextInt(arrlist.size())); // 리스트 사이즈 만큼 랜덤하게 돌린다.
        txtRandom1.setText(randFood); // 랜덤으로 나온 음식점을 텍스트로 보여주기
        if(db!=null) db.close(); // 데이터베이스 닫아주기
    }
    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case ID_GROUP_MENU_KOREAN:
                Intent korean=new Intent(this, KoreanMaps.class);
                startActivity(korean);
                return true;
            case ID_GROUP_MENU_WESTERN:
                Intent western=new Intent(this, WesternMap.class);
                startActivity(western);
                return true;
            case ID_GROUP_MENU_CHINESE:
                Intent chinese=new Intent(this, ChineseMap.class);
                startActivity(chinese);
                return true;
            case ID_GROUP_MENU_JAPANESE:
                Intent japanese=new Intent(this, JapaneseMap.class);
                startActivity(japanese);
                return true;
            case ID_GROUP_FREETALK:
                Intent talk=new Intent(this, FreeTalk.class);
                Intent intent=getIntent();
                String loginId=intent.getStringExtra("loginId"); // 로그인 창에서 보낸 인텐트로 부터 값 받기
                talk.putExtra("loginId", loginId); // 다시 넘어갈 창으로 인텐트로 값 전송
                startActivity(talk);
                return true;
            case ID_GROUP_MEMO:
                Intent memo=new Intent(this, Memo.class);
                Intent intent2=getIntent();
                String id=intent2.getStringExtra("loginId"); // 로그인 창에서 보낸 인텐트로 부터 값 받기
                memo.putExtra("loginId", id); // 다시 넘어갈 창으로 인텐트로 값 전송
                startActivity(memo);

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Prepare the Screen's standard options menu to be displayed.  This is
     * called right before the menu is shown, every time it is shown.  You can
     * use this method to efficiently enable/disable items or otherwise
     * dynamically modify the contents.
     *
     * <p>The default implementation updates the system menu items based on the
     * activity's state.  Deriving classes should always call through to the
     * base class implementation.
     *
     * @param menu The options menu as last shown or first initialized by
     *             onCreateOptionsMenu().
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     *
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     *
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     *
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu mnuKind=menu.addSubMenu("음식 종류");
        SubMenu mnuTalk=menu.addSubMenu("게시판");

        mnuKind.add(ID_GROUP_MENU_KIND, ID_GROUP_MENU_KOREAN,1,"한식");
        mnuKind.add(ID_GROUP_MENU_KIND, ID_GROUP_MENU_WESTERN,2,"양식");
        mnuKind.add(ID_GROUP_MENU_KIND, ID_GROUP_MENU_CHINESE, 3,"중식");
        mnuKind.add(ID_GROUP_MENU_KIND, ID_GROUP_MENU_JAPANESE,4,"일식");

        mnuTalk.add(ID_GROUP_TALK, ID_GROUP_FREETALK,1,"자유게시판");
        mnuTalk.add(ID_GROUP_TALK, ID_GROUP_MEMO, 2, "메모장");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date1=(TextView)findViewById(R.id.Date);
        txtRandom1=(TextView)findViewById(R.id.txtRandom);

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Calendar calendar = Calendar.getInstance(); // 캘린더 변수 선언
                            int month = calendar.get(Calendar.MONTH)+1; // 월
                            int day = calendar.get(Calendar.DAY_OF_MONTH); // 일
                            int hour = calendar.get(Calendar.HOUR_OF_DAY); // 시
                            int min = calendar.get(Calendar.MINUTE); // 분
                            int sec = calendar.get(Calendar.SECOND); // 초

                            Date1.setText(month + "월 " + day + "일 " +
                                    hour + ": " + min + ": " +
                                    sec + "");
                        }
                    });
                    try {
                        Thread.sleep(1000); // 1초
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread.start();

    }
}
