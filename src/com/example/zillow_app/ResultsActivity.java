package com.example.zillow_app;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class ResultsActivity  extends Activity {
	
	JSONObject parsed_data=null; 
	ActionBar.Tab bi, hz;
	Fragment basicInfoTab = new BasicInfo();
	Fragment zestimatesTab = new Zestimates(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results_activity);
		
		try {
			parsed_data = new JSONObject(getIntent().getStringExtra("parsed_data"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d("sent:",TheApp.parsed_data.toString());
		
		ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        bi = actionBar.newTab().setText("Basic Info");
        hz = actionBar.newTab().setText("Historical Zestimates");
        
        bi.setTabListener(new TabListener(basicInfoTab));
        hz.setTabListener(new TabListener(zestimatesTab));
        
        actionBar.addTab(bi);
        actionBar.addTab(hz);
	}
}
