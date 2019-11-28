package com.example.a1018demo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TwoFragment extends Fragment {

    private StringBuilder response;
    private InputStream in;
    private HttpURLConnection connection = null;
    private BufferedReader reader = null;
    private OkHttpClient okHttpClient;
    private ListView listView;
    private ListViewAdapter adapter;
    private List<Goods> goods = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        listView = view.findViewById(R.id.listView);
        okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)          //设置连接超时时间
                .readTimeout(20, TimeUnit.SECONDS)                      //设置读取超时时间
                .build();
        String url = "https://api.myjson.com/bins/8ru3p";
        getJson2(url);
        return view;
    }

    private void getJson(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d("chen",json);
                showInListview(json);
            }
        });
    }               //okhttp

    private void getJson2(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url1 = new URL(url);
                    connection = (HttpURLConnection) url1.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                   in = connection.getInputStream();

                   reader = new BufferedReader(new InputStreamReader(in));
                   response = new StringBuilder();
                   String line;
                   while ((line = reader.readLine()) != null){
                       response.append(line);
                   }
                    Log.d("chen", response.toString());
                   showInListview(response.toString());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (reader != null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null){
                        connection.disconnect();
                    }
                }

            }
        }).start();
    }        //原生

    private void showInListview(final String responce){
        try {
            JSONArray jas = new JSONArray(responce);
            for (int i = 0; i < jas.length(); i++) {
                JSONObject jsonObject = jas.getJSONObject(i);
                String nameJson = jsonObject.getString("name");
                String priceJson = jsonObject.getString("price");
                final String imageJson = jsonObject.getString("imageUrl");

                goods.add(new Goods(nameJson, priceJson, imageJson));

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ListViewAdapter(getContext(), R.layout.item, goods);
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
