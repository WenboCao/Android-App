package com.example.NRIC;

import java.util.ArrayList;

import com.example.nrictest.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ParsingArrayAdapter2 extends ArrayAdapter<String>{


	private final static int LAYOUT2 = R.layout.showbox2;
	private final static int Title2 = R.id.listview_title2;
	private final static int Description2 = R.id.listview_description2;
	private final static int Date2 = R.id.listview_date2;
	private final static int Linkname2 = R.id.linkname2;

	ArrayList<String> title2;
	ArrayList<String> linkname2; 
	ArrayList<String> description2; 
	ArrayList<String> datechoose2; 

	Context c2; //context
	LayoutInflater inflater2; //layout inflater

	public ParsingArrayAdapter2(Context context,ArrayList<String> title2,ArrayList<String> linkname2,ArrayList<String> description2,ArrayList<String> datechoose2)

	{
		super(context,Title2);
		this.c2 = context;
		this.title2 = title2;
		this.linkname2=linkname2;
		this.description2 = description2;
		this.datechoose2 = datechoose2;
		this.inflater2 = LayoutInflater.from(c2);
	}

	@Override
	public int getCount()
	{
		return title2.size(); 
	}

	public String getlinkname2(int i)
	{
		return linkname2.get(i); 
	}	


	@Override
	public View getView(int pos,View view,ViewGroup parent)
	{
		CacheRiga cache; 
		if(view==null)
		{

			view = inflater2.inflate(LAYOUT2, parent,false); 
			cache = new CacheRiga(); 
			cache.title2 = (TextView) view.findViewById(Title2); 
			//	cache.linkname2 = (TextView) view.findViewById(Linkname2);
			cache.description2 = (TextView) view.findViewById(Description2);
			cache.datechoose2 = (TextView) view.findViewById(Date2);
			view.setTag(cache);
		}
		else
		{
			cache = (CacheRiga) view.getTag();
		}
		if(title2.size()>0){
			cache.title2.setText(title2.get(pos)); 
		}//cache.linkname2.setText(linkname2.get(pos));

		if(description2.size()>0){
			cache.description2.setText(description2.get(pos));
		}

		if(datechoose2.size()>0){
			cache.datechoose2.setText(datechoose2.get(pos));
		}
		return view;
	}

	private class CacheRiga { 
		public TextView datechoose2;
		//public TextView linkname2;
		public TextView title2; 
		public TextView description2; 
	}

}
