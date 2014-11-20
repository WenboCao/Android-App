package com.example.NRIC;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.nrictest.R;



import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SearchAllNews extends Activity {

	ListView list1;

	ArrayList<String> title; 
	ArrayList<String> description;
	ArrayList<String> datechoose;
	ArrayList<String> linkname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//Remove title bar and notification bar,with reference from http://stackoverflow.com
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result);

		list1 = (ListView)this.findViewById(R.id.main_lista3);

		list1.setOnItemClickListener(new OnItemClickListener() {

			//click each row which contains a specific weblink and go to this website using webview controller
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				ParsingArrayAdapter weblink=(ParsingArrayAdapter) arg0.getAdapter();

				Uri uri = Uri.parse(weblink.getlinkname(arg2));
				Intent i = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(i);  

			}

		});  


		ParsingPaginaWeb parsing = new ParsingPaginaWeb();
		parsing.execute("");
	}

	
	//http://www.androidbegin.com/tutorial/android-basic-jsoup-tutorial/
	private class ParsingPaginaWeb extends AsyncTask<String,String,String> {

		@Override
		protected void onPreExecute()
		{
			title = new ArrayList<String>();
			description = new ArrayList<String>();
			datechoose = new ArrayList<String>();
			linkname= new ArrayList<String>();
		}

		@Override
		protected String doInBackground(String... params) {

			try {
				//connect to home page of NRIC 
				Document doc = Jsoup.connect("http://dev.nric.org.uk/IntegratedCRD_dev.nsf/NewsArchive_APP?Open")
						.userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
						.timeout(30000).get();	

				//grab titles, descriptions weblinkks and date from the website
				Elements tabella = doc.select("[border]");
				Elements column = tabella.select("[valign]");

				for(Element columnNo:column){

					Element result=columnNo.select("td").get(2);//important 
					Element link = result.select("a").first();
					String link_name = link.attr("href"); 


					Element resultdes1=columnNo.getElementsByTag("td").get(2);
					String ArticleTitle=resultdes1.text();

					Element resultdes=columnNo.getElementsByTag("td").get(4);
					String ArticleDes=resultdes.text();

					Element resultdate=columnNo.getElementsByTag("td").get(0);
					String date=resultdate.text();
					title.add(ArticleTitle);
					linkname.add(link_name);
					description.add(ArticleDes);
					datechoose.add(date);

				}
			} catch (Exception e) {

				Log.e("ESEMPIO", "ERRORE NEL PARSING");
			}
			return null;
		}

		//pass variables to array adapter
		@Override
		protected void onPostExecute(String result)
		{

			ParsingArrayAdapter adapter = new ParsingArrayAdapter(SearchAllNews.this, title,linkname,description,datechoose);
			list1.setAdapter(adapter);
		}

	}

}

