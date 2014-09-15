package com.codepath.intagramviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


public class PhotosActivity extends Activity {	
	public static final String CLIENT_ID = "e405e7f76ea34167a084c588e570e0e6";
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotosAdapter aPhotos;
	
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        fetchPopularPhotos();
        
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchPopularPhotos();
            } 
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, 
                android.R.color.holo_green_light, 
                android.R.color.holo_orange_light, 
                android.R.color.holo_red_light);
        
    }


    private void fetchPopularPhotos() {
    	photos = new ArrayList<InstagramPhoto>();
    	// Create adapter and bind it to the data in the arraylist
    	// As the arraylist changes, the data in the adapter changes as well
    	aPhotos =  new InstagramPhotosAdapter(this, photos);
    	// Populate the data in the list view
    	ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
    	// Set the adapter to the listview (triggers population of items)
    	lvPhotos.setAdapter(aPhotos);
    	
    	// https://api.instagram.com/v1/media/popular?client_id=<clientid>
    	String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
    	AsyncHttpClient client = new AsyncHttpClient();
    	client.get(popularUrl, new JsonHttpResponseHandler() {
    		// define the success and failure callbacks
    		@Override
    		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
    			// Fired when the successful response comes back
    			// response == popular photos json
    			JSONArray photosJSON = null;
    			try {
    				photosJSON = response.getJSONArray("data");
    				photos.clear();
    				ArrayList<InstagramPhoto> newPhotos = InstagramPhoto.fromJson(photosJSON);
    				photos.addAll(newPhotos);
    				// Notify the adapter to populate new changes in the listview
    				aPhotos.notifyDataSetChanged(); 
    				swipeContainer.setRefreshing(false);
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}
    		}
    		
    		@Override
    		public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
    			super.onFailure(statusCode, headers, throwable, errorResponse);
    		}
    	});
    	
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
