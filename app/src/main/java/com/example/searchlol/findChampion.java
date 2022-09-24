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


//--------------챔피언 코드 : 이름 에따른 정보 저장
public class findChampion {
    HashMap<String, String> map = new HashMap<String, String>();
    public findChampion()
    {
        dataAddChampion();
    }
    public void dataAddChampion()
    {
        String url = "http://ddragon.leagueoflegends.com/cdn/12.13.1/data/en_US/champion.json";

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
                while(i.hasNext())
                {
                    String temp = i.next().toString();
                    JSONObject jsonTemp = jsonObject2.getJSONObject(temp);
                    String keyTemp = jsonTemp.getString("key");
                    map.put(keyTemp, temp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
