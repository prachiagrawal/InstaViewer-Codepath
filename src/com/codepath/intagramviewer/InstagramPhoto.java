package com.codepath.intagramviewer;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InstagramPhoto {
	public String userName;
	public String caption;
	public String imageUrl;
	public int imageHeight;
	public int likesCount;
	public String userImageUrl;
	public String location;
	public long createdTime;
	public int commentsCount;
	public List<Comment> latestComments;
	
	public static class Comment {
		public long createdTime;
		public String userName;
		public String commentText;
		
		public Comment (long createdTime, String userName, String commentText) {
			this.createdTime = createdTime;
			this.userName = userName;
			this.commentText = commentText;
		}
	}

	public InstagramPhoto(String userName, String caption, String imageUrl,
			int imageHeight, int likes_count, String userImageUrl,
			String location, long createdTime, int comments_count, List<Comment> latest_comments) {
		this.userName = userName;
		this.caption = caption;
		this.imageUrl = imageUrl;
		this.imageHeight = imageHeight;
		this.likesCount = likes_count;
		this.userImageUrl = userImageUrl;
		this.location = location;
		this.createdTime = createdTime;
		this.commentsCount = comments_count;
		this.latestComments = latest_comments;
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
					String location = photoJSON.optJSONObject("location") != null ? photoJSON
							.optJSONObject("location").optString("name") : "";
					long createdTime = photoJSON.getLong("created_time");
					int comments_count = 0;
					List<Comment> latest_comments = new ArrayList<InstagramPhoto.Comment>();

					if (photoJSON.optJSONObject("comments") != null) {
						JSONObject commentsObject = photoJSON.optJSONObject("comments");
						JSONArray comments = commentsObject.getJSONArray("data");
						comments_count = comments.length();
						for (int j = comments.length() - 1; j >= 0; j--) {
							JSONObject commentJSON = comments.getJSONObject(j);
							long commentCreatedTime = commentJSON.getLong("created_time");
							String commentText = commentJSON.getString("text");
							String commenterName = commentJSON.optJSONObject("from") != null ? 
									commentJSON.optJSONObject("from").getString("username") : "";							
							Comment comment = new Comment(commentCreatedTime, commenterName, commentText);
							latest_comments.add(comment);
						}
					}

					InstagramPhoto photo = new InstagramPhoto(userName,
							caption, imageUrl, imageHeight, likes_count,
							userImageUrl, location, createdTime, comments_count, latest_comments);
					photos.add(photo);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return photos;
	}
}
