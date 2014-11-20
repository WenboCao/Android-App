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
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchNews extends Activity {

	ListView list;
	ListView list2;
	String newString;
	Elements tabella ;
	Elements column ;

	ArrayList<String> title; 
	ArrayList<String> linkname;
	ArrayList<String> description;
	ArrayList<String> datechoose;

	ArrayList<String> title2; 
	ArrayList<String> linkname2;
	ArrayList<String> description2;
	ArrayList<String> datechoose2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);

		list = (ListView)this.findViewById(R.id.main_list);
		list2 = (ListView)this.findViewById(R.id.main_list2);

		list.setOnItemClickListener(new OnItemClickListener() {

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

		list2.setOnItemClickListener(new OnItemClickListener() {

			//@Override
			public void onItemClick(AdapterView<?> arg00, View arg11, int arg22,
					long arg33)
			{
				ParsingArrayAdapter2 weblink=(ParsingArrayAdapter2) arg00.getAdapter();

				Uri uri = Uri.parse(weblink.getlinkname2(arg22));
				Intent j = new Intent(Intent.ACTION_VIEW, uri);

				startActivity(j);  

			}

		});   

		/*passing parameter*/
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if(extras == null) 
			{
				newString= null;

			} 
			else 
			{
				newString=extras.getString("location");

			}
		} 
		else 
		{
			newString=  (String)savedInstanceState.getSerializable("location");
		}



		ParsingPaginaWeb parsing = new ParsingPaginaWeb();
		parsing.execute("");
	}

	private class ParsingPaginaWeb extends AsyncTask<String,String,String> {



		@Override
		protected void onPreExecute()
		{

			title = new ArrayList<String>();
			linkname = new ArrayList<String>();
			description = new ArrayList<String>();
			datechoose = new ArrayList<String>();

			title2 = new ArrayList<String>();
			linkname2 = new ArrayList<String>();
			description2 = new ArrayList<String>();
			datechoose2 = new ArrayList<String>();
		}

		@Override
		protected String doInBackground(String... params) {

			try {

				Document doc = Jsoup.connect("http://dev.nric.org.uk/IntegratedCRD_dev.nsf/NRIC_All_APP?SearchView&SearchOrder=4&Query="+newString)
						.userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
						.timeout(30000).get();	

				Elements tabella = doc.select("[border]");
				Elements column = tabella.select("[valign]");

				for(Element columnNo:column){


					Element result=columnNo.select("td").get(4);//important 
					Element link = result.select("a").first();
					String link_name = link.attr("href"); 


					Element resultdes2=columnNo.getElementsByTag("td").get(1);
					String ArticleTitle=resultdes2.text();

					Element resultdes=columnNo.getElementsByTag("td").get(2);
					String ArticleDes=resultdes.text();

					Element resultdate=columnNo.getElementsByTag("td").get(5);
					String date=resultdate.text();


					title.add(ArticleTitle);
					linkname.add(link_name);
					description.add(ArticleDes);
					datechoose.add(date);
				}	



				Document doc2 = Jsoup.connect("http://dev.nric.org.uk/IntegratedCRD_dev.nsf/NewsArchive_APP?SearchView&Query="+newString)
						.userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
						.timeout(30000).get();	

				Elements tabella22 = doc2.select("[border]");
				Elements column22 = tabella22.select("[valign]");

				for(Element columnNo2:column22){

					Element result22=columnNo2.select("td").get(3);//important 
					Element link22 = result22.select("a").first();
					String link_name2 = link22.attr("href"); 


					Element resultdes22=columnNo2.getElementsByTag("td").get(3);
					String ArticleTitle2=resultdes22.text();

					Element resultdes33=columnNo2.getElementsByTag("td").get(5);
					String ArticleDes2=resultdes33.text();

					Element resultdate33=columnNo2.getElementsByTag("td").get(1);
					String date2=resultdate33.text();
					title2.add(ArticleTitle2);
					linkname2.add(link_name2 );
					description2.add(ArticleDes2);
					datechoose2.add(date2);
				}	

			}catch (Exception e) {

				Log.e("ESEMPIO", "ERRORE NEL PARSING");
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{


			//Log.d("no result", "contentsize " + title.size()+" "+linkname.size()+ " "+description.size()+" "+ datechoose.size());
			if((title.size() <1)&&(title2.size() <1)){
				Toast.makeText(getApplicationContext(), 
						"Sorry,No Result", Toast.LENGTH_LONG).show();;
			}

			ParsingArrayAdapter adapter = new ParsingArrayAdapter(SearchNews.this, title,linkname,description,datechoose);
			ParsingArrayAdapter2 adapter2 = new ParsingArrayAdapter2(SearchNews.this, title2,linkname2,description2,datechoose2);
			list.setAdapter(adapter);
			list2.setAdapter(adapter2);
		}

	}

}


