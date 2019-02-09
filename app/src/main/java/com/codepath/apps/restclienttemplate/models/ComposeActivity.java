package com.codepath.apps.restclienttemplate.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    public static final int MAX_TWEET_LENGTH = 140;
    EditText etCompose;
    private Button btnTweet;
    private TwitterClient client;
    private TextView wordCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApp.getRestClient(this);

        btnTweet = findViewById(R.id.btnTweet);






            wordCounter =findViewById(R.id.wordCounter);
            wordCounter.setText(String.valueOf(MAX_TWEET_LENGTH));
            etCompose = findViewById(R.id.etCompose);
            etCompose.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int charsLeft = MAX_TWEET_LENGTH - s.length();
                    wordCounter.setText(String.valueOf(charsLeft));
                    // Fires right as the text is being changed (even supplies the range of text)
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Fires right before text is changing
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // Fires right after the text has changed
                }
            });



        //Set click listener on button
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = etCompose.getText().toString();
                //error handling
                if (tweetContent.isEmpty()) {
                    Toast.makeText(ComposeActivity.this, "Your Tweet is empty", Toast.LENGTH_LONG).show();
                    return;
                }

                if (tweetContent.length() > MAX_TWEET_LENGTH) {
                    Toast.makeText(ComposeActivity.this, "Your Tweet is too long", Toast.LENGTH_LONG).show();
                    return;
                }


            //make API call to publish the content in edit text
            client.composeTweet(tweetContent,new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Log.d("twitter client","success in posting tweet" + response.toString());
                    try {
                        Intent data = new Intent();
                        Tweet tweet= Tweet.fromJson(response);
                        data.putExtra("tweet", Parcels.wrap(tweet));
                        //set result code and bundle data for response
                        setResult(RESULT_OK, data);
                        finish();  //closes the activity
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("twitter client","failure in posting tweet" + responseString);
                }





            });




            }
        });



    }



}
