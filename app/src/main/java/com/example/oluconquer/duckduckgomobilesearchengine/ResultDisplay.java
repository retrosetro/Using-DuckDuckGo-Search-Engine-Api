package com.example.oluconquer.duckduckgomobilesearchengine;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

public class ResultDisplay extends AppCompatActivity {

    private TextView searchDisplay;
    String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_display);

        searchDisplay = (TextView)findViewById(R.id.searchText);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle !=null){
            searchTerm = bundle.getString("searchTerms");
            try{
                String encodedSearch = URLEncoder.encode(searchTerm, "UTF-8");
                String searchURL = "https://api.duckduckgo.com/?q="+encodedSearch+"&format=json";
                new GetSearch().execute(searchURL);
            }
            catch (Exception e){
                searchDisplay.setText("Something went wrong");
                e.printStackTrace();
            }

        }else {
            searchDisplay.setText("Enter a search query!");
        }
    }

    private class GetSearch extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder searchFeedBuilder = new StringBuilder();
            for (String searchURL : params){
                HttpClient searchClient = new DefaultHttpClient();
                try{
                    HttpGet searchGet = new HttpGet(searchURL);
                    HttpResponse searchResponse = searchClient.execute(searchGet);
                    StatusLine searchStatus = searchResponse.getStatusLine();

                    if (searchStatus.getStatusCode() == 200){
                        HttpEntity searchEntity = searchResponse.getEntity();
                        InputStream searchContent = searchEntity.getContent();
                        InputStreamReader searchInput = new InputStreamReader(searchContent);
                        BufferedReader searchReader = new BufferedReader(searchInput);

                        String lineIn;
                        while ((lineIn = searchReader.readLine()) != null){
                            searchFeedBuilder.append(lineIn);

                        }

                    }else{}
                    //  searchDisplay.setText("Something went wrong!");



                }
                catch (Exception e){
                    //  searchDisplay.setText("something went wrong!");
                    e.printStackTrace();
                }
            }
            return searchFeedBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            StringBuilder searchResultBuilder = new StringBuilder();

            try{
                JSONObject resultObject = new JSONObject(result);
                JSONArray searchArray = resultObject.getJSONArray("RelatedTopics");

                for (int i = 0; i< searchArray.length(); i++){
                    JSONObject searchObject = searchArray.getJSONObject(i);
                    searchResultBuilder.append(searchObject.get("Text")+ "\n\n");
                }

            }
            catch (Exception e){
                searchDisplay.setText("Something went wrong!");
                e.printStackTrace();
            }
            if (searchResultBuilder.length() > 0){
                searchDisplay.setText(searchResultBuilder.toString());

            }else {
                searchDisplay.setText("Sorry! No search result found");
            }
        }
    }


}
