package com.example.ssboy14.earthquake;

import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    private static final String LINK_URL="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);
        EarthQuakeAsyn earthQuakeAsyn = new EarthQuakeAsyn();
        earthQuakeAsyn.execute(LINK_URL);

    }

    public class EarthQuakeAsyn extends AsyncTask<String, Void, List<EarthQuake>>{


        @Override
        protected List<EarthQuake> doInBackground(String... args) {
            try{
                URL url = new URL(args[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String json = creatJsonString(bufferedReader);
                Log.v("EarthQuakeAsyn","after buffer Reader");
                List<EarthQuake> list = fetchJsonData(json);
                return list;

            }catch (Exception e){

            }finally {
            }

            return null;
        }

        private List<EarthQuake> fetchJsonData(String json) {
            List<EarthQuake> earthQuakeList = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray features = jsonObject.getJSONArray("features");
                for (int i = 0 ; i < features.length(); i++){
                    JSONObject earthQuake = features.getJSONObject(i);
                    JSONObject properties = earthQuake.getJSONObject("properties");
                    String mag = String.valueOf(properties.getDouble("mag"));

                    Long date = properties.getLong("time");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String date1 = simpleDateFormat.format(new Date(date));
                    String[] date2 = date1.split(" ");

                    String loc = properties.getString("place");
                    String url = properties.getString("url");
                    earthQuakeList.add(new EarthQuake(mag, loc, date2[0]+"\n"+date2[1], url));
                }
                return earthQuakeList;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String creatJsonString(BufferedReader bufferedReader) {
            StringBuilder stringBuilder = new StringBuilder();
            String line ="";
            try{
                while((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            }catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(List<EarthQuake> s) {
            listView.setAdapter(new EarthQuakeAdapter(MainActivity.this, s));
        }




    }
}
