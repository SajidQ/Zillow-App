package com.example.zillow_app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;


public class MainActivity extends ActionBarActivity {
	EditText street, city;
	Button search_button;
	TextView streetError, cityError, stateError, searchError;
	ProgressDialog progressDialog;
	String url = "http://sajidqandeel-hw8-env.elasticbeanstalk.com/";
	String street_input, city_input, state_input;
	private final String USER_AGENT = "Mozilla/5.0";
	//JSONObject parsed_data=null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        street = (EditText)findViewById(R.id.editStreetText);
        streetError=(TextView)findViewById(R.id.streetError);
      
        
        city = (EditText)findViewById(R.id.editCityText);
        cityError=(TextView)findViewById(R.id.cityError);
        
        stateError=(TextView)findViewById(R.id.stateError);
	    
        search_button = (Button)findViewById(R.id.submit);
        //-----------------------------------------
        //image
        ImageView img = (ImageView)findViewById(R.id.zillow_image);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.zillow.com/"));
                startActivity(intent);
            }
        });
        
        //------------------------------------------------------------
        //Spinner
        
        Spinner spinner = (Spinner) findViewById(R.id.state_spinner);
        
        List<String> list = new ArrayList<String>();
        list.add("Choose State");
        list.add("AL");
        list.add("AK");
        list.add("AZ");
        list.add("AR");
        list.add("CA");
        list.add("CO");
        list.add("CT");
        list.add("DE");
        list.add("DC");
        list.add("FL");
        list.add("GA");
        list.add("HI");
        list.add("ID");
        list.add("IL");
        list.add("IN");
        list.add("IA");
        list.add("KS");
        list.add("KY");
        list.add("LA");
        list.add("ME");
        list.add("MD");
        list.add("MA");
        list.add("MI");
        list.add("MN");
        list.add("MS");
        list.add("MO");
        list.add("MT");
        list.add("NE");
        list.add("NV");
        list.add("NH");
        list.add("NJ");
        list.add("NM");
        list.add("NY");
        list.add("NC");
        list.add("ND");
        list.add("OH");
        list.add("OK");
        list.add("OR");
        list.add("PA");
        list.add("RI");
        list.add("SC");
        list.add("SD");
        list.add("TN");
        list.add("TX");
        list.add("UT");
        list.add("VT");
        list.add("VA");
        list.add("WA");
        list.add("WV");
        list.add("WI");
        list.add("WY");
         
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                     (this, android.R.layout.simple_spinner_item,list);
                      
        dataAdapter.setDropDownViewResource
                     (android.R.layout.simple_spinner_dropdown_item);
                      
        spinner.setAdapter(dataAdapter);
        
        
        //----------------------------------------------
        //Search
        search_button.setOnClickListener(
    	        new View.OnClickListener()
    	        {
    	            public void onClick(View view)
    	            {
    	            	
    	            	boolean empty=false;
    	            	
    	            	if((street.getText().toString()).equals(""))
    	            	{
    	            		streetError.setText("This field is required.");
    	            		empty=true;
    	            	}
    	            	else
    	            	{
    	            		streetError.setText("");
    	            		street_input=street.getText().toString();
    	            	}
    	            	
    	            	if((city.getText().toString()).equals(""))
    	            	{
    	            		cityError.setText("This field is required.");
    	            		empty=true;
    	            	}
    	            	else
    	            	{
    	            		cityError.setText("");
    	            		city_input=city.getText().toString();
    	            	}
    	            	
    	            	Spinner state = (Spinner) findViewById(R.id.state_spinner);
    	            	
    	            	if((state.getSelectedItem().toString()).equals("Choose State"))
    	            	{
    	            		stateError.setText("This field is required.");
    	            		empty=true;
    	            	}
    	            	else
    	            	{
    	            		stateError.setText("");
    	            		state_input=state.getSelectedItem().toString();
    	            	}
    	            	
    	            	if(!empty)
    	            	{
    	            		GetData task = new GetData();
    	                    task.execute(new String[] {url});
    	                    
    	            	}
    	            }
    	        }
    	        
        		);
    	    
    		   
    }
    
    
   private class GetData extends AsyncTask<String, Void, String> {

    	String output = null;
    	HttpResponse httpResponse=null;
    	
        @Override
        protected String doInBackground(String... urls) {
        	String full_url=url+"?street="+street_input.replace(' ', '+')+"&city="+city_input.replace(' ', '+')+"&state="+state_input;
        	//http://sajidqandeel-hw8-env.elasticbeanstalk.com/?street=2636+menlos&city=los+angeles&state=CA
        	
        	System.out.println(full_url);
     
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost post = new HttpPost(full_url);
                post.setHeader("User-Agent", USER_AGENT);
                
                List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            	urlParameters.add(new BasicNameValuePair("street", street_input));
            	urlParameters.add(new BasicNameValuePair("city", city_input));
            	urlParameters.add(new BasicNameValuePair("state", state_input));
            
                httpResponse = httpClient.execute(post);        
             
               
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output;
       }
       
       @Override
       protected void onPostExecute(String output) {
    	   
        	
        	try{
        			searchError=(TextView)findViewById(R.id.FailSearchError);
        			BufferedReader rd = new BufferedReader(
        					new InputStreamReader(httpResponse.getEntity().getContent()));
        			StringBuilder builder = new StringBuilder();
        		
        			for (String line = null; (line = rd.readLine()) != null;) {
        				builder.append(line).append("\n");
        			}
        		
        			JSONObject parsed_data = new JSONObject(builder.toString());
        			TheApp.setParsedData(parsed_data);
        		
        			//Log.d("response:",parsed_data.toString());
        		
        		
        			if((parsed_data.get("results")).equals("fail"))
        			{
        			
        			searchError.setText("No exact match found--Verify that the given address is correct.");
        			}
        			else
        			{
        				searchError.setText("");
        				Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
        				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
        				intent.putExtra("parsed_data", parsed_data.toString());
        				getApplicationContext().startActivity(intent);
        			}	
        	}
        	catch(IOException e)
        	{
        		System.out.println("IO Error here!");
        	} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        	
        }
    }
       

      

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    @Override
    protected void onStop()
    {
        super.onStop();
    }
}
