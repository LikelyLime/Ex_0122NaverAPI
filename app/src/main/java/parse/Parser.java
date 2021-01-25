package parse;

import android.widget.EditText;

import com.sihun.ex_0122naverapi.MainActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import VO.BookVO;

public class Parser {

    //xml파싱 (웹에서 요소 (제목, 저자, 가격)를 검색하여 co에 담는 과정)을 위한 클래스
    BookVO vo;
    String myQuery = "";//검색어

    public ArrayList<BookVO> connectNaver( ArrayList<BookVO> list ){

        try{
            //EditText에 쓰여져 있는 문장을 UTF-8구조로 인코딩 해서 서버로 전송
            myQuery = URLEncoder.encode( MainActivity.search.getText().toString(), "UTF8");

            int count = 100;//검색결과 100건을 가지고 옴

            //정보를 얻기위한 URL준비
            String urlstr = "https://openapi.naver.com/v1/search/book.xml?query="+myQuery+"&display=" + count;
            //위의 경로를 URL클래스를 통해서 웹으로 연결
            URL url = new URL(urlstr);

            //연결객체를 통해 실제로 url경로로의 접속을 시도
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            //발급받은 id를 connection에게 전달
            connection.setRequestProperty("X-Naver-Client-Id", "3kwVwiR9lMFRAJE__hnF");

            //발급받은 시크릿을 connection에게 전달
            connection.setRequestProperty("X-Naver-Client-Secret", "w_kwyKjO6u");

            //인증까지완료한 결과를 xml를 통해 받게되는데,
            //이를 받기위한 클래스가 필요
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //실제 데이터에 접근
            XmlPullParser parser = factory.newPullParser();

            parser.setInput( connection.getInputStream(), null );

            //parser를 통해 서버에서 얻어온 각각의 요소들을 반복수행 처리
            int parserEvent = parser.getEventType();
            //현제 parser의 커서 위치가 XML문서의 끝을 만날때 까지만 while문을 반복
            while (parserEvent != XmlPullParser.END_DOCUMENT){

                //시작태그( <aaa> )를 만났을 때 이름을 얻어온다
                if ( parserEvent == XmlPullParser.START_TAG ){

                    String tagName = parser.getName();//시작태그의 이름(<title>, <link>, ...)

                    if (tagName.equalsIgnoreCase("title")){
                        vo = new BookVO();
                        String title = parser.nextText();//시작태그안의 내용을 가져온다
                        vo.setB_title(title);//vo에 가져온 값을 저장
                    }else if (tagName.equalsIgnoreCase("image")){
                        String img = parser.nextText();
                        vo.setB_img(img);
                    }else if (tagName.equalsIgnoreCase("author")){
                        String author = parser.nextText();
                        vo.setB_author(author);
                    }else if (tagName.equalsIgnoreCase("price")){
                        String price = parser.nextText();
                        vo.setB_price(price);
                        list.add(vo);//arraylist에 담기
                    }

                }
                parserEvent = parser.next();//다음요소로 커서를 이동


            }//while

        }catch (Exception e){

        }

        return list;

    }//connectNaver

}
