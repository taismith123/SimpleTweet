package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {

 public String body;
public long id;
public String createdAt;
public User user;


public Tweet(){

}
public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
    Tweet tweet = new Tweet();
    tweet.body=jsonObject.getString("text");
    tweet.id = jsonObject.getLong("id");
    tweet.createdAt = jsonObject.getString("created_at");
    tweet.user=User.fromJson(jsonObject.getJSONObject("user"));
return tweet;
}
}
