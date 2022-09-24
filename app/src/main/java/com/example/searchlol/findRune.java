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

import java.util.HashMap;
import java.util.Iterator;


//-------------------------------------룬 정보 찾기
public class findRune {
    HashMap<String, String> map = new HashMap<String, String>();
    public findRune()
    {
        dataAddRune();
    }
    public void dataAddRune() {
        String url = "https://ddragon.leagueoflegends.com/cdn/12.13.1/data/en_US/runesReforged.json";

        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(url); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                JSONArray jsonObject = new JSONArray(body);
                for(int i=0; i<jsonObject.length(); i++)
                {
                    JSONObject json = jsonObject.getJSONObject(i);
                    map.put(json.getString("id"), json.getString("icon"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
