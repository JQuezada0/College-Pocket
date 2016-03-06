package com.Campus.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapHandler {
	
	private String Path;
	private int x;
	private int y;
	private Matrix Mat;
	
	public BitmapHandler(String Path, int x, int y){
		this.Path = Path;
		this.x = x;
		this.y = y;
	}
	
	public BitmapHandler(String Path, int x, int y, Matrix m){
		this.Path = Path;
		this.x = x;
		this.y = y;
		this.Mat = m;
	}
	
	public Bitmap getBitmap(){
		BitmapFactory.Options Options = new BitmapFactory.Options();
		Options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(Path, Options);
		Options.inSampleSize = CalculateSampleSize(Options, x, y);
		Options.inJustDecodeBounds = false;
		if (Mat!=null){
			return Bitmap.createBitmap(BitmapFactory.decodeFile(Path, Options), 0, 0, x, y, Mat, true);
		}
		return BitmapFactory.decodeFile(Path, Options);
	}
	
	private int CalculateSampleSize(BitmapFactory.Options Options, int reqWidth, int reqHeight){
		// Raw height and width of image
	    final int height = Options.outHeight;
	    final int width = Options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {

	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;

	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }

	    return inSampleSize;
	}

}
