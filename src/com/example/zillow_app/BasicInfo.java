package com.example.zillow_app;

import java.text.DecimalFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import android.view.WindowManager;

public class BasicInfo extends Fragment {
	TextView prop_type, prop_name, year_built, lot_size, finished_area, bathrooms, bedrooms, tax_asse_year, tax_asses, last_sold_price, last_sold_date, 
		zestimate_prop, overall_change, property_range, rent_zestimate, rent_change, rent_range,
		zestimate_prop_text, rent_zestimate_text;
	TextView zillow_footer;
	ImageView image_change, image_rent_change;
	
	//ScrollView scroller1;
	
	JSONObject my_parsed_data=TheApp.parsed_data; 
	
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	                           Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.basic_tab, container, false);
		
		
		//get ids
		/*
		 scroller1=(ScrollView)view.findViewById(R.id.ScrollLayout1);
		
		ScrollView.LayoutParams params = (ScrollView.LayoutParams) scroller1.getLayoutParams();
		DisplayMetrics dm = new DisplayMetrics();
		this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int height_=(dm.heightPixels)/6;
		params.height =height_*6;
		scroller1.setLayoutParams(params);*/
		
		
		
		prop_type=(TextView)view.findViewById(R.id.prop_type);
		prop_name=(TextView)view.findViewById(R.id.prop_name);
		year_built=(TextView)view.findViewById(R.id.year_built);
		lot_size=(TextView)view.findViewById(R.id.lot_size);
		finished_area=(TextView)view.findViewById(R.id.finished_area);
		bathrooms=(TextView)view.findViewById(R.id.bathrooms);
		bedrooms=(TextView)view.findViewById(R.id.bedrooms);
		tax_asse_year=(TextView)view.findViewById(R.id.tax_asse_year);
		tax_asses=(TextView)view.findViewById(R.id.tax_asses);
		last_sold_price=(TextView)view.findViewById(R.id.last_sold_price);
		last_sold_date=(TextView)view.findViewById(R.id.last_sold_date);
		zestimate_prop=(TextView)view.findViewById(R.id.zestimate_prop);
		overall_change=(TextView)view.findViewById(R.id.overall_change);
		property_range=(TextView)view.findViewById(R.id.property_range);
		rent_zestimate=(TextView)view.findViewById(R.id.rent_zestimate);
		rent_change=(TextView)view.findViewById(R.id.rent_change);
		rent_range=(TextView)view.findViewById(R.id.rent_range);
		zestimate_prop_text=(TextView)view.findViewById(R.id.zestimate_prop_text);
		rent_zestimate_text=(TextView)view.findViewById(R.id.rent_zestimate_text);
		zillow_footer=(TextView)view.findViewById(R.id.zillow_footer);
		
		String prop_text_str=null, prop_name_str=null, year_built_str=null, lot_size_str=null, finished_area_str=null, 
				bathrooms_str=null, bedrooms_str=null,
				tax_asse_year_str=null, tax_asses_str=null, last_sold_price_str=null, last_sold_date_str=null, 
				zestimate_prop_str=null, overall_change_str=null, property_range_str=null, rent_zestimate_str=null, rent_change_str=null, rent_range_str=null,
				zestimate_prop_text_str=null, rent_zestimate_text_str=null;
		
		String zillow_footer_str=null;
		image_change=(ImageView)view.findViewById(R.id.overall_sign);
		image_rent_change=(ImageView)view.findViewById(R.id.rent_sign);
		try {
			
			System.out.println(my_parsed_data.get("estimateValueChangeSign")+" "+ my_parsed_data.get("restimateValueChangeSign"));
			if((my_parsed_data.get("estimateValueChangeSign")).equals("+"))
			{
				image_change.setImageResource(R.drawable.up_g);
			}
			else 
			{
				image_change.setImageResource(R.drawable.down_r);
			}
			
			if((my_parsed_data.get("restimateValueChangeSign")).equals("+"))
			{
				image_rent_change.setImageResource(R.drawable.up_g);
			}
			else 
			{
				image_rent_change.setImageResource(R.drawable.down_r);
			}
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			prop_text_str = (String) my_parsed_data.get("useCode");
			prop_name_str = "<a href='"+((String) my_parsed_data.get("homedetails"))+"'>"+((String) my_parsed_data.get("street"))+", "+((String) my_parsed_data.get("city"))+", "+((String) my_parsed_data.get("state"))+"-"+((String) my_parsed_data.get("zipcode"))+" </a>";
			year_built_str= (String) my_parsed_data.get("yearBuilt");
			
			double amount = Double.parseDouble((String) my_parsed_data.get("lotSizeSqFt"));
			DecimalFormat formatter = new DecimalFormat("#,###");
			lot_size_str=formatter.format(amount)+" sq. ft.";
			
			amount = Double.parseDouble((String) my_parsed_data.get("finishedSqFt"));
			formatter = new DecimalFormat("#,###");
			finished_area_str=formatter.format(amount)+" sq. ft.";
			
			bathrooms_str=(String) my_parsed_data.get("bathrooms");
			bedrooms_str=(String) my_parsed_data.get("bedrooms");
			tax_asse_year_str=(String) my_parsed_data.get("taxAssessmentYear");
			tax_asses_str="$"+(String) my_parsed_data.get("taxAssessment");
			last_sold_price_str="$"+(String) my_parsed_data.get("lastSoldPrice");
			last_sold_date_str=(String) my_parsed_data.get("lastSoldDate");
			zestimate_prop_str="$"+(String) my_parsed_data.get("estimateAmount");
			overall_change_str="$"+(String) my_parsed_data.get("estimateValueChange");
			property_range_str="$"+((String) my_parsed_data.get("estimateValuationRangeLow"))+"-"+"$"+((String) my_parsed_data.get("estimateValuationRangeHigh"));
			rent_zestimate_str="$"+(String) my_parsed_data.get("restimateValueChange");
			rent_change_str="$"+(String) my_parsed_data.get("restimateAmount");
			rent_range_str="$"+((String) my_parsed_data.get("restimateValuationRangeLow"))+"-"+"$"+((String) my_parsed_data.get("restimateValuationRangeHigh"));;
			zestimate_prop_text_str=" Property Estimate as of "+(String) my_parsed_data.get("estimateLastUpdate");
			rent_zestimate_text_str=" Valuation as of "+(String) my_parsed_data.get("restimateLastUpdate");
			zillow_footer_str="&#169; Zillow, Inc., 2006-2014.<br /> Use is subject to <a href='http://www.zillow.com/corp/Terms.htm'>Terms of Use</a><br /> <a href='http://www.zillow.com/zestimate/'>What's a Zestimate?</a>";
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//re-write --------------------------------------------
		prop_type.setText(prop_text_str);
		
		prop_name.setText(prop_name_str);
		prop_name.setClickable(true);
		prop_name.setMovementMethod(LinkMovementMethod.getInstance());
		prop_name.setText(Html.fromHtml(prop_name_str));
		
		year_built.setText(year_built_str);
		lot_size.setText(lot_size_str);
		finished_area.setText(finished_area_str);
		bathrooms.setText(bathrooms_str);
		bedrooms.setText(bedrooms_str);
		tax_asse_year.setText(tax_asse_year_str);
		tax_asses.setText(tax_asses_str);
		last_sold_price.setText(last_sold_price_str);
		last_sold_date.setText(last_sold_date_str);
		zestimate_prop.setText(zestimate_prop_str);
		
		
		overall_change.setText(overall_change_str);
		property_range.setText(property_range_str);
		rent_zestimate.setText(rent_zestimate_str);
		rent_change.setText(rent_change_str);
		rent_range.setText(rent_range_str);
		zestimate_prop_text.setText("Zestimate "+Html.fromHtml("&#174;")+zestimate_prop_text_str);
		rent_zestimate_text.setText("Rent Estimate "+Html.fromHtml("&#174;")+rent_zestimate_text_str);
		
		
		zillow_footer.setClickable(true);
		zillow_footer.setMovementMethod(LinkMovementMethod.getInstance());
		zillow_footer.setText(Html.fromHtml(zillow_footer_str));
		 
		
		
		return view;
	  }
	}