package com.codepath.intagramviewer;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
	// View lookup cache
    private static class ViewHolder {
        ImageView photo;
        TextView caption;
        TextView username;
        ImageView userDP;
        TextView likes;
        TextView location;
    }
    
	public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
		super(context, R.layout.item_photo, photos);
	}

	// Takes a data item at a position and converts it to a row on the listview
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Take the data source at position (ie. 0)
		// Get the data item
		InstagramPhoto photo = getItem(position);
		// Check if we are using a recycled view
		ViewHolder viewholder; // View lookup cache
		if (convertView == null) {
			viewholder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
			viewholder.username = (TextView) convertView.findViewById(R.id.tvUsernameTop);
			viewholder.caption = (TextView) convertView.findViewById(R.id.tvCaption);
			viewholder.photo = (ImageView) convertView.findViewById(R.id.imgPhoto);
			viewholder.userDP = (ImageView) convertView.findViewById(R.id.imgUserDP);
			viewholder.likes = (TextView) convertView.findViewById(R.id.tvLikes);
			viewholder.location = (TextView) convertView.findViewById(R.id.tvLocation);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag(); 
		}

		viewholder.username.setText(photo.userName);
		viewholder.caption.setText(Html.fromHtml("<font color=\"#206199\"><b>" + photo.userName
                + "  " + "</b></font>" + "<font color=\"#000000\">" + photo.caption + "</font>"));
		viewholder.likes.setText(Integer.toString(photo.likes_count) + " likes");
		viewholder.location.setText(photo.location);

		// Set the image height before loading
		viewholder.photo.getLayoutParams().height = parent.getWidth();
		
		// Reset the image from the recycled view
		viewholder.photo.setImageResource(0);
		viewholder.userDP.setImageResource(0);

		// Ask for the photo to be added to the image view based on the photo URL
		// Send a network request to the URL, download the image bytes
		// Convert the bytes to a bitmap, resize the image, insert the bitmap into imageview
		Picasso.with(getContext()).load(photo.imageUrl).into(viewholder.photo);
		Picasso.with(getContext()).load(photo.userImageUrl)
			.transform(new RoundedTransformation(50, 30)).into(viewholder.userDP);
		
		// Return the view for that data item
		return convertView;
	}

}
