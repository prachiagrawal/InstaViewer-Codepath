package com.codepath.intagramviewer;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

public class CropSquareTransformation implements Transformation {
	private final int width;
	private final int height; // dp

	// width is view width in dp
	// height is view height in dp
	public CropSquareTransformation(final int width, final int height) {
		this.width = width;
		this.height = height;
	}

	@Override public Bitmap transform(Bitmap source) {
	Bitmap result = BitmapScaler.scaleToFill(source, width, height);
 
	/*
	int size = Math.min(source.getWidth(), source.getHeight());
    int x = (source.getWidth() - size) / 2;
    int y = (source.getHeight() - size) / 2;
    Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
    */
	
    if (result != source) {
      source.recycle();
    }
    return result;
  }

	@Override
	public String key() {
		return "square()";
	}
}