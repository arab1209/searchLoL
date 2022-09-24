package com.example.searchlol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ResponseHandler;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.BasicResponseHandler;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClientBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    int recyclerView2HeightSize;//최근5판 이내 전적recyclerView heightSize 변수
    final static int itemLength = 6;//최근 5판 이내 전적 itemImageView 개수
    final static int kdaLength = 3;// Kill, death, Assist 배열 length

    Button featuredGameBtn;// 현재진행중인 게임정보 버튼

    JSONObject jsonObject; // api를 json형태로 가져올때 사용하는 object
    JSONArray jsonArray; // ;; array

    getInformation get;// getInformation객체
    freeRankData free;// freeRankData 객체

    myRecyclerViewAdapter adapter; // 리사이클러뷰 어댑터

    ConstraintLayout infoBackground, tierImg, tierImg3;

    boolean check = false; // itemList2에 값이 다들어갔으면 check를 true

    public RecyclerView recyclerView, recyclerView2;// 리사이클러뷰

    ArrayList<featuredGamesInfo> itemList; // 객체형 Arraylist 진행중인게임중 블루팀 정보가 담기는 arraylist
    ArrayList<featuredGamesInfo2> itemList2; // 객체형 Arraylist 진행중인게임중 레드팀 정보가 담기는 arraylist
    ArrayList<gameMatchData> gameData;// 객체형 Arraylist 최근5판 전적을 불러오기위한 gameData ArrayList

    ArrayList<String> arraylist; // 최근전적중 게임 api요청후 matchId가 담길 변수
    findSpell findS; // 게임 스킬중 SummonerSpell이 담길 객체
    findRune findR; // 게임 특성중 Rune(perks)이 담길 객체

    HashMap<String, String> map = new HashMap<>(); // 유저 정보중 이름과 티어(등급)가 담기는 변수

    findChampion findC; // 롤에 존재하는 챔피언에대한 챔피언코드 : 챔피언이름 형식을 갖고오기위한 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        findC = new findChampion();

        Button btn = findViewById(R.id.btn);
        EditText userName = findViewById(R.id.textView);

        recyclerView = findViewById(R.id.recyclerView);
        featuredGameBtn = findViewById(R.id.featuredGameBtn);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView2 = findViewById(R.id.recyclerView2);

        tierImg = findViewById(R.id.tierImg);
        tierImg3 = findViewById(R.id.tierImg3);




        infoBackground = findViewById(R.id.infoBackground);

        get = new getInformation();
        free = new freeRankData();


        //-------------------------------------전적검색 버튼을 누를시 api요청후 갖가지 정보를 가져온다
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check) {//만약 매치 리사이클러뷰에 값이 들어가있을때 다른 사람의 아이디를 검색해 전적검색을 하면 기존의 리사이클러뷰 아이템을 초기화해줌

                    for(int i =0; i<adapter.getItemCount(); i++)
                    {
                        adapter.notifyItemRemoved(i);
                    }
                    itemList.clear();
                    itemList2.clear();
                    recyclerView2.getLayoutParams().height = recyclerView2HeightSize;
                    adapter.notifyDataSetChanged();
                    check = false;
                }


                String id = String.valueOf(userName.getText());
                String api_Key = "api_key=RGAPI-3e75b3c8-ba06-4c17-b519-72d49bcdad02"; //고유apiKey
                String url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name" + "/" + id + "?" +api_Key;// api요청주소
                String summonerName = ""; // 유저이름
                Log.d("urltest", url);
                    try {
                        HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
                        HttpGet getRequest = new HttpGet(url); //GET 메소드 URL 생성
                        HttpResponse response = client.execute(getRequest);
                        if (response.getStatusLine().getStatusCode() == 200) {
                            ResponseHandler<String> handler = new BasicResponseHandler();
                            String body = handler.handleResponse(response);
                            jsonObject = new JSONObject(body);
                            summonerName = jsonObject.getString("name");//계정 이름
                            get.setPuuid((jsonObject.getString("puuid")));//계정 고유번호
                        }
                        findRanking findR = new findRanking(api_Key, summonerName); // 솔로랭크 랭킹을 찾기위해 api키와 유저 이름을 넘겨줌
                        findRankingSR findRsr = new findRankingSR(api_Key, summonerName); // 자유랭크 랭킹을 찾기위해 api키와 유저 이름을 넘겨줌
                        initDataStorage(jsonObject.getString("id"), api_Key, findR, findRsr, summonerName); // 초기 데이터셋팅
                        getMatchId(api_Key);// matchId를 찾기위함과 그 관련
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
    }
    //----------------------------------------------------------초기데이터셋팅------------------
    public void initDataStorage(String uniqueId, String api_Key, findRanking findR, findRankingSR findRsr, String summonerName) throws JSONException {

        TextView ranking, tierName, leaguePoints, winsLosses2, ranking2, tierName2, leaguePoints2;
        ranking = findViewById(R.id.ranking);
        tierName = findViewById(R.id.tierName);
        leaguePoints = findViewById(R.id.leaguePoints);
        winsLosses2 = findViewById(R.id.winsLosses2);
        ranking2 = findViewById(R.id.ranking2);
        tierName2 = findViewById(R.id.tierName2);
        leaguePoints2 = findViewById(R.id.leaguePoints2);
        TextView winsLosses3 = findViewById(R.id.winsLosses3);
        ImageView tierImg2,  tierImg4;
        tierImg2 = findViewById(R.id.tierImg2);
        tierImg4 = findViewById(R.id.tierImg4);

        get.setProfileIconId(jsonObject.getString("profileIconId")); // 사용자 프로필 아이콘 이미지
        get.setIdLevel(jsonObject.getString("summonerLevel")); // 사용자 레벨

        String url = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + uniqueId + "?" + api_Key;
        Log.d("url", url);
        Log.d("api", api_Key);
        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(url); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                jsonArray = new JSONArray(body); // 해당 api를 받아올때는 array형태이므로 데이터파싱을 array로 함
            }
            if(!(jsonArray.length() == 0)) // 받아온 jsonArray의 사이즈가 0이 아닐때만 실행 예외처리
            {
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject temp = jsonArray.getJSONObject(i); // jsonArray의 i번째방에 들어있는 값을 jsonObject로 변환
                    if(temp.getString("queueType").equals("RANKED_SOLO_5x5")) { // 만약 큐 타입이 솔로랭크면
                        get.setTier(temp.getString("tier")); // 솔로랭크 티어셋팅
                        get.setRank(temp.getString("rank")); // 솔로랭크 티어단계셋팅
                        get.setLeaguePoints(temp.getString("leaguePoints")); // 솔로랭크 점수 셋팅
                        get.setWins(temp.getString("wins")); // 솔로랭크 승리횟수 셋팅
                        get.setLosses(temp.getString("losses")); // 솔로랭크 진 횟수 셋팅
                    }
                }
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject temp = jsonArray.getJSONObject(i);// jsonArray의 i번째방에 들어있는 값을 jsonObject로 변환
                    if(temp.getString("queueType").equals("RANKED_FLEX_SR")) // 큐타입이 자유랭크면 이 아래의 자유랭크 데이터 삽입하는구문을 실행
                    {
                        free.setTier(temp.getString("tier"));
                        free.setRank(temp.getString("rank"));
                        free.setLeaguePoints(temp.getString("leaguePoints"));
                        free.setWins(temp.getString("wins"));
                        free.setLoses(temp.getString("losses"));
                    }
                    else { // 만약큐타입이 자유랭크가 없으면 셋팅을 언랭 기준으로 셋팅함
                        free.setTier("언랭");
                        free.setWins("0");
                        free.setLoses("0");
                        free.setLeaguePoints("-1");
                        free.setRank("");
                    }
                }
            }else { // 솔로랭크 데이터가없으면 언랭기준으로 셋팅함
                    get.setTier("언랭");
                    get.setWins("0");
                    get.setLosses("0");
                    get.setLeaguePoints("-1");
                    get.setRank("");

                    free.setTier("언랭");
                    free.setWins("0");
                    free.setLoses("0");
                    free.setLeaguePoints("-1");
                    free.setRank("");
                    }

            tierImg.setVisibility(View.VISIBLE);// 검색한 아이디 사용자의 솔로랭크 티어 View
            tierImg3.setVisibility(View.VISIBLE); // 검색한 아이디 사용자의 자유랭크 티어 View


            tierSetting(get.getTier(), tierImg2); // 검색한 아이디 사용자의 솔로랭크 티어이미지 셋팅

            //-------------승률계산
            int win = Integer.parseInt(get.getWins());
            int lose = Integer.parseInt(get.getLosses());
            double winRate = 0.0;
            if(!(win == 0 && lose == 0))
            {
                winRate = winRateCalc(win, lose);
            }
            //-------------승률계산

            //-------------만약 티어가 챌린저일때만 랭킹계산가능 그이외는 요청api가 무수히 많아서 속도가 현저히느려져 앱이 실행이안됨 만약 제대로 개발을할거면 따로 db서버를 구축해 값을 불러오면서 해야할거같다
            if(get.getTier().equals("CHALLENGER"))
            {
                ranking.setText(String.valueOf(findR.ranking) + "위");
            }else { ranking.setText("랭킹 산정 불가"); }
            String total =  "(" + win + "승 " + lose + "패 " + String.format("%.2f", winRate) + "%)";

            tierName.setText(get.getTier() + " " + get.getRank()); // 티어이름 셋팅
            if(!(get.getLeaguePoints().equals("-1"))) { leaguePoints.setText(get.getLeaguePoints() + " LP"); } // 만약 리그포인트가-1이 아니면 리그포인트 설정
            else {leaguePoints.setText("0 LP"); } // 리그포인트가 -1일시 0lp로셋팅
            winsLosses2.setText(total); // 승리횟수, 패배횟수 셋팅


            //-----------------------위와동일
            tierSetting(free.getTier(), tierImg4);

            int win2 = Integer.parseInt(free.getWins());
            int lose2 = Integer.parseInt(free.getLoses());
            double winRate2 = 0.0;
            if(!(win2 == 0 && lose2 == 0))
            {
                winRate2 = winRateCalc(win2, lose2);
            }
            if(free.getTier().equals("CHALLENGER"))
            {
                ranking2.setText(String.valueOf(findRsr.ranking) + "위");
            }else { ranking2.setText("랭킹 산정 불가"); }
            String total2 =  "(" + win2 + "승 " + lose2 + "패 " + String.format("%.2f", winRate2) + "%)";

            Log.d("totoal2", total2);
            tierName2.setText(free.getTier() + " " + free.getRank());
            if(!(free.getLeaguePoints().equals("-1"))) { leaguePoints2.setText(free.getLeaguePoints() + " LP"); }
            else {leaguePoints2.setText("0 LP"); }
            winsLosses3.setText(total2);




       } catch (Exception e) {
            // Error calling the rest api
            e.printStackTrace();
        }
        userViewSetting(uniqueId, api_Key, summonerName);//검색한 유저 아이디의 정보를 view로 셋팅해주는 메소드
    }
    private double winRateCalc(int win, int lose) // 승률계산
    {
        double winRate = 0.0;
        return winRate = ((double)win/((double)win + (double)lose)) * 100;
    }
    private void tierSetting(String tier, ImageView tierImg) // 검색한 유저 아이디의 티어(등급) 에 따라 사진 이미지를 다르게 함
    {
        if(tier.equals("CHALLENGER"))       { tierImg.setImageResource(R.drawable.c);  }
        else if(tier.equals("GRANDMASTER")) { tierImg.setImageResource(R.drawable.gm); }
        else if(tier.equals("MASTER"))      { tierImg.setImageResource(R.drawable.m);  }
        else if(tier.equals("DIAMOND"))     { tierImg.setImageResource(R.drawable.d);  }
        else if(tier.equals("PLATINUM"))    { tierImg.setImageResource(R.drawable.p);  }
        else if(tier.equals("GOLD"))        { tierImg.setImageResource(R.drawable.g);  }
        else if(tier.equals("SILVER"))      { tierImg.setImageResource(R.drawable.s);  }
        else if(tier.equals("BRONZE"))      { tierImg.setImageResource(R.drawable.b);  }
        else if(tier.equals("IRON"))        { tierImg.setImageResource(R.drawable.i);  }
        else if(tier.equals("언랭")) { tierImg.setImageResource(R.drawable.unranked); }
    }
    @SuppressLint("NewApi")
    private void userViewSetting (String uniqueId, String api_Key, String summonerName) {//검색한 유저 아이디의 정보를 view로 셋팅해주는 메소드

        TextView userId, level, winsLosses;
        userId = findViewById(R.id.userId);
        level = findViewById(R.id.level);
        winsLosses = findViewById(R.id.winsLosses);
        ImageView iconImage = findViewById(R.id.iconImage);


        runOnUiThread(new Runnable(){
            public void run()
            {
                infoBackground.setVisibility(View.VISIBLE); // 유저정보를 보여줌
                featuredGameBtn.setVisibility(View.VISIBLE); // 진행중인 게임정보 버튼을 보여줌
                featuredGameBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        featuredGamesInfoSetting(uniqueId, api_Key); // 클릭시 진행중인정보셋팅
                    }
                });
                userId.setText(summonerName);// 유저이름 셋팅
                level.setText("Lv" + get.getIdLevel()); // 유저레벨 셋팅
                if(!(get.getWins() == null && get.getLosses() == null)) {
                    winsLosses.setText("전체랭크 : " + (Integer.parseInt(get.getWins()) + Integer.parseInt(free.getWins())) + "승 " + (Integer.parseInt(
                            get.getLosses()) + Integer.parseInt(free.getLoses())) + "패");
                }else { winsLosses.setText("전체랭크 : " + 0 + "승 " + 0 + "패"); }
                String imgUrl = "http://ddragon.leagueoflegends.com/cdn/12.13.1/img/profileicon/" + get.getProfileIconId() + ".png";
                iconImage.setImageBitmap(imageSetting(imgUrl));// 유저프로필 아이콘셋팅
            }
        });
    }
    private Bitmap imageSetting(String imgUrl) // 이미지를 링크를 비트맵으로 반환해주는 메소드 매개변수는 url형태로 됨
    {
            String urlImage = imgUrl;
            Bitmap imgBitmap = null;
            HttpURLConnection connection = null;
            InputStream is = null;
            try {
                URL imageUrl = new URL(urlImage);
                connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setDoInput(true);
                is = connection.getInputStream();
                imgBitmap = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                return imgBitmap;
            }
    }
    private void getMatchId(String api_key) // 최근전적 정보를 알기위한 matchId를 불러오는 메소드
    {
        gameData = new ArrayList<>();
        arraylist = new ArrayList<>();
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + get.getPuuid() + "/ids?start=0&count=5&" + api_key;
        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(url); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                JSONArray exchange = new JSONArray(body);
                if(!(exchange.length() == 0))
                {
                    for (int i = 0; i < exchange.length(); i++)
                    {
                        arraylist.add((String) exchange.get(i)); // 매치 아이디저장
                    }
                }else { return; }
                for(int i=0; i< arraylist.size(); i++)
                {
                    matchIdSearch(api_key, arraylist.get(i)); // 매치아이디 size만큼 반복분을 돌려 매치id안에있는 게임정보를 불러옴
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void matchIdSearch(String api_key, String matchId)// 매치ID에 따른 게임정보를 불러옴
    {
        findS = new findSpell();
        findR = new findRune();
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId + "?" + api_key;
        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(url); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                JSONObject jsonObject = new JSONObject(body);
                JSONObject jsonObject2 = jsonObject.getJSONObject("info");
                JSONArray jsonObject3 = jsonObject2.getJSONArray("participants");
                for(int i=0; i<jsonObject3.length(); i++)
                {

                    JSONObject temp = jsonObject3.getJSONObject(i);
                    if(temp.getString("puuid").equals(get.getPuuid())) // 매치아이디중 전적검색을한 고유ID번호와 같은것이 있으면 아래구문실행
                    {
                        boolean winsLosseswhat = temp.getBoolean("win"); //검색한 아이디가 승리했는지 패배했는지 담는 BOOLEAN형 변수

                        String imgUrl = "https://ddragon.leagueoflegends.com/cdn/12.13.1/img/champion/" + findC.map.get(temp.getString("championId")) + ".png"; // URL에 이미지 사진이 담겨있는 URL정보저장
                        Bitmap championBit = imageSetting(imgUrl); // 위에서 저장한값을 비트맵이미지로변환


                        String spell1ImgUrl = "https://ddragon.leagueoflegends.com/cdn/12.13.1/img/spell/" + findS.map.get(temp.getString("summoner1Id")) + ".png";
                        String spell2ImgUrl = "https://ddragon.leagueoflegends.com/cdn/12.13.1/img/spell/" + findS.map.get(temp.getString("summoner2Id")) + ".png";

                        Bitmap spell1Bit = imageSetting(spell1ImgUrl);
                        Bitmap spell2Bit = imageSetting(spell2ImgUrl);

                        JSONObject perks = temp.getJSONObject("perks"); // 특성을 구하기위한 JSONObject 파싱
                        JSONArray perks2 = perks.getJSONArray("styles"); // 스타일번호를 이용해 특성을 불러옴
                        String perksNum = "0";
                        String perksNum2 = "0";
                        for(int j=0; j<perks2.length(); j++)
                        {
                            JSONObject perksTemp = perks2.getJSONObject(j);
                            if(j == 0)// 만약j가 0일때 0번째 특성을 perksNum에 저장함
                            {
                                perksNum = perksTemp.getString("style");
                            }else if (j == 1) { perksNum2 = perksTemp.getString("style");}  //만약j가 1일때 0번째 특성을 perksNum에 저장함
                        }
                        String rune1ImgUrl = "https://ddragon.leagueoflegends.com/cdn/img/" + findR.map.get(perksNum);
                        String rune2ImgUrl = "https://ddragon.leagueoflegends.com/cdn/img/" + findR.map.get(perksNum2);

                        Bitmap rune1Bit = imageSetting(rune1ImgUrl);
                        Bitmap rune2Bit = imageSetting(rune2ImgUrl);

                        String getQueueId = "";
                        if(jsonObject2.getString("queueId").equals("420")) // queueId에  따른 솔로랭크인지, 자유랭크인지, 일반게임인지, 칼바람인지, 그외모드인지 탐색함
                        {
                            getQueueId = "랭크";
                        }else if(jsonObject2.getString("queueId").equals("440"))
                        {
                            getQueueId = "자랭";
                        }else if(jsonObject2.getString("queueId").equals("450"))
                        {
                            getQueueId = "칼바람";
                        }else if(jsonObject2.getString("queueId").equals("400") || jsonObject2.getString("queueId").equals("430"))
                        {
                            getQueueId = "일반";
                        }else { getQueueId = "그외"; }

                        Bitmap itemImg[] = new Bitmap[itemLength];//아이템창 이미지 6개가 들어가야함
                        String itemImgUrl = "https://ddragon.leagueoflegends.com/cdn/12.13.1/img/item/";
                        String temp2[] = new String[6];
                        for(int q=0; q<itemLength; q++)
                        {
                            if(!(temp.getString("item" + q).equals("0"))) // 아이템1~6번까지 아이템코드가 0이아니면 아래구문실행
                            {
                                itemImg[q] = imageSetting((itemImgUrl + temp.getString("item" + q) + ".png"));
                                temp2[q] = String.valueOf(itemImg[q]);
                            }else { itemImg[q] = imageSetting("http://z.fow.kr/items3/0.png"); temp2[q] = String.valueOf(itemImg[q]); } // 아이템번호가 만약0일시 기본베이스 아이템사진을 가져오기
                        }
                        String kda[] = new String[kdaLength]; // kill death assist를 담는 배열
                        kda[0] = temp.getString("kills");
                        kda[1] = temp.getString("deaths");
                        kda[2] = temp.getString("assists");

                        String recyclerViewRight[] = new String[5];// 설치한 와드개수 와드를 부신개수, 핑크와드를 설치한개수, 돈을 얼마나벌었는지, 미니언을 얼마나잡았는지 저장하는 배열
                        recyclerViewRight[0] = temp.getString("detectorWardsPlaced");
                        recyclerViewRight[1] = temp.getString("wardsKilled");
                        recyclerViewRight[2] = temp.getString("wardsPlaced");
                        recyclerViewRight[3] = temp.getString("goldEarned");
                        recyclerViewRight[4] = temp.getString("totalMinionsKilled");




                        //지금까지 저장한데이터를 gameMatchData객체에 넘겨준후 gameData에 add해줌
                       gameData.add(new gameMatchData(championBit, spell1Bit, spell2Bit, rune1Bit, rune2Bit, winsLosseswhat, getQueueId, itemImg, kda, recyclerViewRight));
                    }
                }

                //Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView2.setVisibility(View.VISIBLE);
        myRecyclerViewAdapter2 adapter2 = new myRecyclerViewAdapter2(this, gameData);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    //------------------------진행중인게임정보셋팅 위와 비슷함
    private void featuredGamesInfoSetting(String uniqueId, String api_Key)//
    {
        recyclerView2HeightSize = recyclerView2.getLayoutParams().height;

        itemList = new ArrayList<>();
        itemList2 = new ArrayList<>();
        String url = "https://kr.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + uniqueId + "?" +api_Key;
        Log.d("url", url);
        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(url); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                jsonObject = new JSONObject(body);
                JSONArray jsonArray2 = jsonObject.getJSONArray("participants");


                for(int i=0; i<jsonArray2.length(); i++)
                {
                    JSONObject temp = jsonArray2.getJSONObject(i);
                    tierFind(temp.getString("summonerId"), api_Key);
                }

                for(int i=0; i<jsonArray2.length(); i++)
                {
                    JSONObject temp = jsonArray2.getJSONObject(i);
                    JSONObject temp2 = temp.getJSONObject("perks");

                    if(temp.getString("teamId").equals("100"))
                    {
                        recyclerView2.getLayoutParams().height = 320;
                        String championId = temp.getString("championId");
                        String spell1Id = temp.getString("spell1Id");
                        String spell2Id = temp.getString("spell2Id");
                        String rune1Id = temp2.getString("perkStyle");
                        String rune2Id = temp2.getString("perkSubStyle");
                        String userName = temp.getString("summonerName");


                        for(int j=0; j<findC.map.size(); j++)
                        {
                            String imgUrl = "https://ddragon.leagueoflegends.com/cdn/12.13.1/img/champion/" + findC.map.get(championId) + ".png";
                            Bitmap championBit = imageSetting(imgUrl);

                            String spell1ImgUrl = "https://ddragon.leagueoflegends.com/cdn/12.13.1/img/spell/" + findS.map.get(spell1Id) + ".png";
                            String spell2ImgUrl = "https://ddragon.leagueoflegends.com/cdn/12.13.1/img/spell/" + findS.map.get(spell2Id) + ".png";
                            Bitmap spell1Bit = imageSetting(spell1ImgUrl);
                            Bitmap spell2Bit = imageSetting(spell2ImgUrl);

                            String rune1ImgUrl = "https://ddragon.leagueoflegends.com/cdn/img/" + findR.map.get(rune1Id);
                            String rune2ImgUrl = "https://ddragon.leagueoflegends.com/cdn/img/" + findR.map.get(rune2Id);
                            Bitmap rune1Bit = imageSetting(rune1ImgUrl);
                            Bitmap rune2Bit = imageSetting(rune2ImgUrl);

                            String tier = map.get(userName);

                            itemList.add(new featuredGamesInfo(championBit, spell1Bit, spell2Bit, rune1Bit, rune2Bit, userName, tier));
                            findC.map.remove(championId);
                            break;
                        }
                    }
                    if(temp.getString("teamId").equals("200"))
                    {
                        String championId = temp.getString("championId");
                        String spell1Id = temp.getString("spell1Id");
                        String spell2Id = temp.getString("spell2Id");
                        String rune1Id = temp2.getString("perkStyle");
                        String rune2Id = temp2.getString("perkSubStyle");
                        String userName = temp.getString("summonerName");


                        for(int j=0; j<findC.map.size(); j++)
                        {
                            String imgUrl = "https://ddragon.leagueoflegends.com/cdn/12.13.1/img/champion/" + findC.map.get(championId) + ".png";
                            Bitmap championBit = imageSetting(imgUrl);

                            String spell1ImgUrl = "https://ddragon.leagueoflegends.com/cdn/12.13.1/img/spell/" + findS.map.get(spell1Id) + ".png";
                            String spell2ImgUrl = "https://ddragon.leagueoflegends.com/cdn/12.13.1/img/spell/" + findS.map.get(spell2Id) + ".png";
                            Bitmap spell1Bit = imageSetting(spell1ImgUrl);
                            Bitmap spell2Bit = imageSetting(spell2ImgUrl);

                            String rune1ImgUrl = "https://ddragon.leagueoflegends.com/cdn/img/" + findR.map.get(rune1Id);
                            String rune2ImgUrl = "https://ddragon.leagueoflegends.com/cdn/img/" + findR.map.get(rune2Id);
                            Bitmap rune1Bit = imageSetting(rune1ImgUrl);
                            Bitmap rune2Bit = imageSetting(rune2ImgUrl);

                            String tier = map.get(userName);

                            itemList2.add(new featuredGamesInfo2(championBit, spell1Bit, spell2Bit, rune1Bit, rune2Bit, userName, tier));
                            findC.map.remove(championId);
                            check = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView.setVisibility(View.VISIBLE);
        adapter = new myRecyclerViewAdapter(this, itemList, itemList2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }
    public void tierFind(String userUniqueId, String api_Key) // 티어에따른 티어이름을 간략하게 수정해주는 메소드
    {
        String url = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + userUniqueId + "?" + api_Key;
        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(url); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                jsonArray = new JSONArray(body);
            }
            for(int i=0; i<jsonArray.length(); i++)
            {
                JSONObject temp = jsonArray.getJSONObject(i);
                if(temp.getString("queueType").equals("RANKED_SOLO_5x5"))
                {
                    String tier = temp.getString("tier") + " " + temp.getString("rank");
                    tierRename re = new tierRename(tier);
                    tier = re.rename();

                    map.put(temp.getString("summonerName"), tier);
                }
            }
        } catch (Exception e) {
            // Error calling the rest api
            Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}