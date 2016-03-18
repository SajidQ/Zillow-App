package com.example.zillow_app;

import android.app.Application;
import java.util.*; 

import org.json.JSONObject;

public class TheApp extends Application {
	public static JSONObject parsed_data;

	
	public TheApp()
	{
		parsed_data = null;
	}
	
	public static void setParsedData(JSONObject parsed_data_sent)
	{
		parsed_data = parsed_data_sent;
	}
	

}
