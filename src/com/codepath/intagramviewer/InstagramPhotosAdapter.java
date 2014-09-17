package com.codepath.intagramviewer;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;
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
        ImageView location_icon;
        TextView comments_count;
        TextView second_last_comment;
        TextView last_comment;
        TextView created_time;
    }
    
    private Spanned formatComment(String username, String text) {
    	return Html.fromHtml("<font color=\"#206199\"><b>" + username
                + "  " + "</b></font>" + "<font color=\"#000000\">" + text + "</font>");
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
			viewholder.location_icon = (ImageView) convertView.findViewById(R.id.imgLocation);
			viewholder.comments_count = (TextView) convertView.findViewById(R.id.tvCommentsCount);
			viewholder.second_last_comment = (TextView) convertView.findViewById(R.id.tvCommentSecondLast);
			viewholder.last_comment = (TextView) convertView.findViewById(R.id.tvCommentLast);	
			viewholder.created_time = (TextView) convertView.findViewById(R.id.tvTime);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag(); 
		}
		
		viewholder.username.setText(photo.userName);
		viewholder.caption.setText(formatComment(photo.userName, photo.caption));
		viewholder.likes.setText(Integer.toString(photo.likesCount) + " likes");
		viewholder.location.setText(photo.location);
		CharSequence relativeTimespan = DateUtils.getRelativeTimeSpanString(
				photo.createdTime * 1000,
				System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		String[] timespanParts = relativeTimespan.toString().split(" ");
		String formattedTimespan = timespanParts[0] + timespanParts[1].charAt(0);
		viewholder.created_time.setText(formattedTimespan);
		
		if (photo.location == null || photo.location.isEmpty()) {
			viewholder.location_icon.setVisibility(View.GONE);
		} else {
			viewholder.location_icon.setVisibility(View.VISIBLE);
		}

		viewholder.comments_count.setVisibility(View.GONE);
		viewholder.second_last_comment.setVisibility(View.GONE);
		viewholder.last_comment.setVisibility(View.GONE);

		if (photo.commentsCount > 0) {
			viewholder.last_comment.setVisibility(View.VISIBLE);
			viewholder.last_comment.setText(formatComment(
					photo.latestComments.get(0).userName,
					photo.latestComments.get(0).commentText));
		}

		if (photo.commentsCount > 1) {
			viewholder.second_last_comment.setVisibility(View.VISIBLE);
			viewholder.second_last_comment.setText(formatComment(
					photo.latestComments.get(1).userName,
					photo.latestComments.get(1).commentText));
		}
		
		if (photo.commentsCount > 2) {
			viewholder.comments_count.setVisibility(View.VISIBLE);
			viewholder.comments_count.setText("view all "
					+ Integer.toString(photo.commentsCount) + " comments");
		}

		// Set the image height before loading
		viewholder.photo.getLayoutParams().height = parent.getWidth();
						
		viewholder.userDP.getLayoutParams().height = 80;
		viewholder.userDP.getLayoutParams().width = 80;
		
		// Reset the image from the recycled view
		viewholder.photo.setImageResource(0);
		viewholder.userDP.setImageResource(0);

		// Ask for the photo to be added to the image view based on the photo URL
		// Send a network request to the URL, download the image bytes
		// Convert the bytes to a bitmap, resize the image, insert the bitmap into imageview
		Picasso.with(getContext()).load(photo.imageUrl)
			.transform(new CropSquareTransformation(parent.getWidth(),parent.getWidth())).into(viewholder.photo);
		Picasso.with(getContext()).load(photo.userImageUrl)
			.transform(new RoundedTransformation(50, 20)).into(viewholder.userDP);
		
		// Return the view for that data item
		return convertView;
	}

}
