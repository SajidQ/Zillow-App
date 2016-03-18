package com.example.zillow_app;


import java.io.InputStream;
import java.lang.ref.WeakReference;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract.Colors;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ViewSwitcher.ViewFactory;

public class Zestimates extends Fragment {
	private static final String[] TEXTS = { "Historical Zestimates for the past 1 year", "Historical Zestimates for the past 5 years", "Historical Zestimates for the past 10 years" };
	private static final int[] IMAGES = { R.drawable.down_r, R.drawable.share,
			R.drawable.up_g };
	private  String[] IMAGES_URL;//= {(String) (TheApp.parsed_data).get("1year"), (String) (TheApp.parsed_data).get("5years"), (String) (TheApp.parsed_data).get("10years")};;
	private Drawable[] DrawableImages;
	private ImageSwitcher imageSwitcher;
	private int position = 0;
	private Context context;
	private TextSwitcher textSwitcher;
	private Button next_button, prev_button;
	TextView  prop_name;
	JSONObject my_parsed_data=TheApp.parsed_data; 
	
	public Zestimates(Context context){
	    this.context=context;
	    
	    IMAGES_URL = new String[3];
		DrawableImages = new Drawable[3];
		try {
			IMAGES_URL[0]=(String)my_parsed_data.get("1year");
			IMAGES_URL[1]=(String) my_parsed_data.get("5years");
			IMAGES_URL[2]=(String) my_parsed_data.get("10years");
			
		
			
			DownloadImages task = new DownloadImages(0, IMAGES_URL[0]);
	        task.execute();
	        
	        DownloadImages task2 = new DownloadImages(1, IMAGES_URL[1]);
	        task2.execute();
	        
	        DownloadImages task3 = new DownloadImages(2, IMAGES_URL[2]);
	        task3.execute();
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	                           Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.zest_tab, container, false);
		
		
		
		
		prop_name=(TextView)view.findViewById(R.id.prop_name);
		String prop_name_str=null;
		try {
			prop_name_str = ((String) my_parsed_data.get("street"))+", "+((String) my_parsed_data.get("city"))+", "+((String) my_parsed_data.get("state"))+"-"+((String) my_parsed_data.get("zipcode"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prop_name.setText(prop_name_str);
		
		textSwitcher = (TextSwitcher) view.findViewById(R.id.textSwitcher);
		textSwitcher.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				TextView textView = new TextView(context);
				textView.setGravity(Gravity.CENTER);
				textView.setTextColor(Color.BLACK);
				textView.setTextSize((float) 28.0);
				textView.setHeight(100);
		
				return textView;
			}
		});

		//textSwitcher.setInAnimation(context, android.R.anim.fade_in);
		//textSwitcher.setOutAnimation(context, android.R.anim.fade_out);

		
		imageSwitcher = (ImageSwitcher)view.findViewById(R.id.my_imageSwitcher);
		imageSwitcher.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				ImageView imageView = new ImageView(context);
				return imageView;
			}
		});
		


    	textSwitcher.setText(TEXTS[position]);
    	//imageSwitcher.setBackgroundResource(IMAGES[position]);
    	imageSwitcher.setImageDrawable(DrawableImages[position]);
    	
    	
		next_button = (Button)view.findViewById(R.id.next);
		next_button.setOnClickListener(
    	        new View.OnClickListener()
    	        {
    	            public void onClick(View view)
    	            {
    	            	position = (position + 1) % TEXTS.length;
    	            	
    	                //imageSwitcher.setInAnimation(context, android.R.anim.slide_in_left);
    	        		//imageSwitcher.setOutAnimation(context, android.R.anim.slide_out_right);
    	        		
    	            	
    	            	textSwitcher.setText(TEXTS[position]);
    	            	//imageSwitcher.setBackgroundResource(IMAGES[position]);
    	            	imageSwitcher.setImageDrawable(DrawableImages[position]);
    	            	
    	            }
    	        });
		
		prev_button = (Button)view.findViewById(R.id.prev);
		prev_button.setOnClickListener(
    	new View.OnClickListener()
            {
                public void onClick(View view)
   	            {
    	            if(position==0)
    	            {
    	            	position=2;
    	            }
    	            else
    	            {
   	            	position = (position - 1);
    	            }
    	            
    	            //imageSwitcher.setOutAnimation(context, android.R.anim.slide_in_left);
    	    		//imageSwitcher.setInAnimation(context, android.R.anim.slide_out_right);
    	    		
   	            	textSwitcher.setText(TEXTS[position]);
   	            	//imageSwitcher.setBackgroundResource(IMAGES[position]);   
   	            	//DownloadImages task = new DownloadImages(imageSwitcher, IMAGES_URL[position]);
	                //task.execute();
   	            	imageSwitcher.setImageDrawable(DrawableImages[position]);
    	         }
           });
		
		
		TextView zillow_footer=(TextView)view.findViewById(R.id.zillow_footer);
		String zillow_footer_str="&#169; Zillow, Inc., 2006-2014.<br /> Use is subject to <a href='http://www.zillow.com/corp/Terms.htm'>Terms of Use</a><br /> <a href='http://www.zillow.com/zestimate/'>What's a Zestimate?</a>";
		zillow_footer.setClickable(true);
		zillow_footer.setMovementMethod(LinkMovementMethod.getInstance());
		zillow_footer.setText(Html.fromHtml(zillow_footer_str));
		
		
		 
		return view;
	  }
	
	private class DownloadImages extends AsyncTask<String, Void, Bitmap> {

		/*
		
		private String url;
		private final WeakReference<ImageSwitcher> imageRef;

		public DownloadImages(ImageSwitcher imageView, String URL) {
			imageRef = new WeakReference<ImageSwitcher>(imageView);
			url=URL;
		}
	 
		@Override
		protected Bitmap doInBackground(String... params) {
			try {
				InputStream in = new java.net.URL(url).openStream();
				Bitmap bitmap = BitmapFactory.decodeStream(in);
				return bitmap;
			} catch (Exception e) {
				Log.e("ImageDownload", e.getMessage());
			}
			return null;
		}
	 
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			
			
			if (isCancelled()) {
				bitmap = null;
			}
	 
			if (imageRef != null) {
				ImageSwitcher imageView = imageRef.get();
				if (imageView != null) {
					
					Drawable drawable=(Drawable)new BitmapDrawable(context.getResources(),bitmap); 
					imageView.setImageDrawable(drawable);
				}
			}
		}*/
		
		
		private String url;
		private int pos;
		//private final WeakReference<Drawable> imageRef;

		public DownloadImages(int pos_, String URL) {
			//imageRef = new WeakReference<Drawable>(imageView);
			url=URL;
			pos=pos_;
		}
	 
		@Override
		protected Bitmap doInBackground(String... params) {
			try {
				InputStream in = new java.net.URL(url).openStream();
				Bitmap bitmap = BitmapFactory.decodeStream(in);
				return bitmap;
			} catch (Exception e) {
				Log.e("ImageDownload", e.getMessage());
			}
			return null;
		}
	 

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			
			
			if (isCancelled()) {
				bitmap = null;
			}
	 
			
					DrawableImages[pos]=(Drawable)new BitmapDrawable(context.getResources(),bitmap); 
					//imageView.setImageDrawable(drawable);
				
		}
	}
	 
}
	