package com.sihun.ex_0122naverapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import VO.BookVO;
import parse.Parser;

public class MainActivity extends AppCompatActivity {

    public static EditText search;
    Button btn_search;
    ListView myListView;
    Parser parser;
    ArrayList<BookVO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.search);
        btn_search = findViewById(R.id.btn_search);
        myListView = findViewById(R.id.myListView);

        parser = new Parser();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list = new ArrayList<>();
                //async클래스를 통한 서버 통신
                new NaverAsync().execute();//doInBackground메서드를 호출

            }
        });
    }//oncreate

    //통신 전용 스레드 생성
    //AsyncTask클래스는 생성시에 세개의 제너릭 타입을 가진다.
    //1. doInBackground
    //2. UI의 진행 상태를 관리하는 onProgressUpdate() 라는 메서드가 오버라이딩 되어있는 경우
    // 이 메서드에서 사용할 자료형 타입
    //3. doInBackground의 반환형이자, onPostExecute의 파라미터 타입
    class NaverAsync extends AsyncTask<Void, Void, ArrayList<BookVO>>{


        @Override
        protected ArrayList<BookVO> doInBackground(Void... voids) {
            //각종 반복이나 제어 등 서버 접속과 관련된 주요 처리로직을 담당하는 메서드

            return parser.connectNaver(list);//통신 시작
        }//odInBack

        @Override
        protected void onPostExecute(ArrayList<BookVO> bookVOS) {
            //doinbackground의 작업 결과가 현재 메서드의 파라미터로 넘어온다

            for (int i = 0; i < bookVOS.size(); i++) {
                Log.i("MY", bookVOS.get(i).getB_img());
            }
        }
    }//NaverSyanc

}