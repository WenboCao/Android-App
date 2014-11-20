package com.example.NRIC;

import java.util.ArrayList;

import com.example.nrictest.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ParsingArrayAdapter extends ArrayAdapter<String>{

	private final static int LAYOUT = R.layout.show_box;
	private final static int Title = R.id.listview_title;
	private final static int Description = R.id.listview_description;
	private final static int Date = R.id.listview_date;
	//private final static int LINKNAME = R.id.linkname;

	ArrayList<String> title; 
	ArrayList<String> description; 
	ArrayList<String> datechoose;
	ArrayList<String> linkname;


	Context c; //context
	LayoutInflater inflater; //layout inflater

	//create method which can pass variables from one activity to this activity
	public ParsingArrayAdapter(Context context,ArrayList<String> title,ArrayList<String> linkname,ArrayList<String> description,ArrayList<String> datechoose)

	{
		super(context,Title);
		this.c = context;
		this.title = title;
		this.linkname=linkname;
		this.description = description;
		this.datechoose = datechoose;
		this.inflater = LayoutInflater.from(c);
	}

	//get title and linkname size
	@Override
	public int getCount()
	{
		return title.size(); 
	}
	public String getlinkname(int i)
	{
		return linkname.get(i); 
	}	

	//make title,description and date visible, link is not visible
	@Override
	public View getView(int pos,View view,ViewGroup parent)
	{
		Field cache; 
		if(view==null)
		{

			view = inflater.inflate(LAYOUT, parent,false); 
			cache = new Field(); 
			cache.title = (TextView) view.findViewById(Title); 
			//cache.linkname = (TextView) view.findViewById(LINKNAME); 
			cache.description = (TextView) view.findViewById(Description);
			cache.datechoose = (TextView) view.findViewById(Date);
			view.setTag(cache);
		}
		else
		{
			cache = (Field) view.getTag(); 
		}

		//if nothing get from website, jump out of if statement
		if(title.size()>0){
			cache.title.setText(title.get(pos)); 
			//cache.linkname.setText(linkname.get(pos));
		}
		if(description.size()>0){
			cache.description.setText(description.get(pos));
		}
		if(datechoose.size()>0){
			cache.datechoose.setText(datechoose.get(pos));
		}
		return view;
	}

	private class Field { 
		public TextView datechoose;
		//public TextView linkname;
		public TextView title; 
		public TextView description; 
	}

}
