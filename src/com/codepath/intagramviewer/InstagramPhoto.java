package com.codepath.intagramviewer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InstagramPhoto {
	public String userName;
	public String caption;
	public String imageUrl;
	public int imageHeight;
	public int likes_count;
	public String userImageUrl;
	public String location;

	public InstagramPhoto(String userName, String caption, String imageUrl,
			int imageHeight, int likes_count, String userImageUrl,
			String location) {
		this.userName = userName;
		this.caption = caption;
		this.imageUrl = imageUrl;
		this.imageHeight = imageHeight;
		this.likes_count = likes_count;
		this.userImageUrl = userImageUrl;
		this.location = location;
	}

	public static ArrayList<InstagramPhoto> fromJson(JSONArray jsonObjects) {
		ArrayList<InstagramPhoto> photos = new ArrayList<InstagramPhoto>();
		for (int i = 0; i < jsonObjects.length(); i++) {
			try {
				JSONObject photoJSON = jsonObjects.getJSONObject(i);
				if (photoJSON != null) {
					String userName = photoJSON.getJSONObject("user") != null ? 
							photoJSON.getJSONObject("user").getString("username") : "";
					String caption = photoJSON.optJSONObject("caption") != null ? 
							photoJSON.optJSONObject("caption").optString("text") : "";
					String imageUrl = photoJSON.getJSONObject("images") != null
							&& photoJSON.getJSONObject("images").getJSONObject("standard_resolution") != null ? 
									photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url") : "";
					int imageHeight = photoJSON.getJSONObject("images") != null
							&& photoJSON.getJSONObject("images").getJSONObject("standard_resolution") != null ? 
									photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height") : 0;
					int likes_count = photoJSON.optJSONObject("likes") != null ? 
							photoJSON.optJSONObject("likes").optInt("count") : 0;
					String userImageUrl = photoJSON.getJSONObject("user") != null ? 
							photoJSON.getJSONObject("user").getString("profile_picture") : "";
					String location = photoJSON.optJSONObject("location") != null ? 
							photoJSON.optJSONObject("location").optString("name") : "";
					InstagramPhoto photo = new InstagramPhoto(userName,
							caption, imageUrl, imageHeight, likes_count,
							userImageUrl, location);
					photos.add(photo);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return photos;
	}
}
