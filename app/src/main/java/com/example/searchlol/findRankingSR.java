package com.example.searchlol;

import android.util.Log;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ResponseHandler;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.BasicResponseHandler;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClientBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//-------------------------자유랭크 랭킹저장 솔로랭크와 동일
public class findRankingSR {
    String api_key;
    int ranking = 0;
    String name;
    HashMap<String, Integer> map = new HashMap<>();
    public findRankingSR(String api_key, String name)
    {
        this.api_key = api_key;
        this.name = name;
        findRank();
    }
    public void findRank()
    {
        String url = "https://kr.api.riotgames.com/lol/league/v4/challengerleagues/by-queue/RANKED_FLEX_SR?" + api_key;
        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(url); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                JSONObject jsonObject = new JSONObject(body);
                JSONArray json = jsonObject.getJSONArray("entries");

                for(int i=0; i<json.length(); i++)
                {
                    JSONObject temp = json.getJSONObject(i);
                    map.put(temp.getString("summonerName"), temp.getInt("leaguePoints"));
                }
                List<Map.Entry<String, Integer>> list_entries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());

                // 비교함수 Comparator를 사용하여 내림 차순으로 정렬
                Collections.sort(list_entries, new Comparator<Map.Entry<String, Integer>>() {
                    // compare로 값을 비교
                    public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2)
                    {
                        // 내림 차순으로 정렬
                        return obj2.getValue().compareTo(obj1.getValue());
                    }
                });
                for(Map.Entry<String, Integer> entry : list_entries) {
                    ranking++;
                    if(entry.getKey().equals(name))
                    {
                        return;
                    }
                    Log.d("wpqkf", entry.getKey() + " : " + entry.getValue());

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

