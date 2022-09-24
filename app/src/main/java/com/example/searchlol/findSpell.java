package com.example.searchlol;

import android.util.Log;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ResponseHandler;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.BasicResponseHandler;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClientBuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;


//-------------------------스펠 정보 찾기
public class findSpell {
    HashMap<String, String> map = new HashMap<String, String>();
    public findSpell()
    {
        dataAddSpell();
    }
    public void dataAddSpell() {
        String url = "https://ddragon.leagueoflegends.com/cdn/12.13.1/data/en_US/summoner.json";

        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(url); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                JSONObject jsonObject = new JSONObject(body);
                JSONObject jsonObject2 = jsonObject.getJSONObject("data");

                Iterator i = jsonObject2.keys();
                while (i.hasNext()) {
                    String temp = i.next().toString();
                    JSONObject jsonTemp = jsonObject2.getJSONObject(temp);
                    String keyTemp = jsonTemp.getString("key");
                    map.put(keyTemp, temp);
                    Log.d("map", String.valueOf(map));
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
