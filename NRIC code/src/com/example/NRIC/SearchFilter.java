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
import android.widget.Toast;

public class SearchFilter extends Activity {

	TextView view1;
	String newString11,newString22,newString33;

	String newString111,newString222,newString333;

	String newString11des,newString22des,newString33des;

	String newString111des,newString222des,newString333des;

	String newStringHtml;



	ListView list;
	ListView list2;
	String newString;

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

		//define button which can lead to PreConfiguration class
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

			public void onItemClick(AdapterView<?> arg00, View arg11, int arg22,
					long arg33)
			{
				ParsingArrayAdapter2 weblink=(ParsingArrayAdapter2) arg00.getAdapter();

				Uri uri = Uri.parse(weblink.getlinkname2(arg22));
				Intent j = new Intent(Intent.ACTION_VIEW, uri);

				startActivity(j);  

			}

		});   

		//get variables values from HomeController class
		Bundle extras = getIntent().getExtras();
		newString11=extras.getString("location11");
		newString22=extras.getString("location22");
		newString33=extras.getString("location33");

		newString111=extras.getString("location111");
		newString222=extras.getString("location222");
		newString333=extras.getString("location333");

		if(newString11.equals("Settings-ANY")){
			newString11des="";
			newString111des="";
		}
		else {
			newString11des=("+CONTAINS+"+newString11);
			newString111des=newString111;
		}

		if(newString22.equals("Diseases-ANY")){
			newString22des="";
			newString222des="";
		}
		else {
			newString22des="+CONTAINS+"+newString22;
			newString222des=newString222;
		}


		if(newString33.equals("ClinicalPractice-ANY")){
			newString33des="";
			newString333des="";
		}
		else {
			newString33des="+CONTAINS+"+newString33;
			newString333des=newString333;
		}


		ParsingPaginaWeb parsing = new ParsingPaginaWeb();
		parsing.execute("");
	}


	private class ParsingPaginaWeb extends AsyncTask<String,String,String> {



		@Override
		protected void onPreExecute()
		{
			title = new ArrayList<String>();
			description = new ArrayList<String>();
			datechoose = new ArrayList<String>();
			linkname= new ArrayList<String>();

			title2 = new ArrayList<String>();
			linkname2 = new ArrayList<String>();
			description2 = new ArrayList<String>();
			datechoose2 = new ArrayList<String>();
		}

		@Override
		protected String doInBackground(String... params) {

			try {

				Document doc = Jsoup.connect("http://dev.nric.org.uk/IntegratedCRD_dev.nsf/NRIC_All_APP?SearchView&SearchOrder=4&Query=FIELD+NRIC_ResourcePlacement"+newString11des+newString22des+newString33des)
						.userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
						.timeout(30000).get();	



				Elements tabella = doc.select("[border]");
				Elements column = tabella.select("[valign]");

				for(Element columnNo:column){

					Element result=columnNo.select("td").get(4);//important 
					Element link = result.select("a").first();
					String link_name = link.attr("href"); 				

					Element resultdes1=columnNo.getElementsByTag("td").get(1);
					String ArticleTitle=resultdes1.text();

					Element resultdes=columnNo.getElementsByTag("td").get(5);
					String ArticleDes=resultdes.text();

					Element resultdate=columnNo.getElementsByTag("td").get(2);
					String date=resultdate.text();
					
					title.add(ArticleTitle);
					linkname.add(link_name);
					description.add(ArticleDes);
					datechoose.add(date);


				}



				Document doc2 = Jsoup.connect("http://dev.nric.org.uk/IntegratedCRD_dev.nsf/NewsArchive_APP?SearchView&Query="+ newString111des+ newString222des+ newString333des)
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
					linkname2.add(link_name2);
					description2.add(ArticleDes2);
					datechoose2.add(date2);
				}	
			} catch (Exception e) {

				Log.e("ESEMPIO", "ERRORE NEL PARSING");

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{

			if((title.size() <1)&&(title2.size() <1)){
				Toast.makeText(getApplicationContext(), 
						"Sorry,No Result", Toast.LENGTH_LONG).show();;
			}

			ParsingArrayAdapter adapter = new ParsingArrayAdapter(SearchFilter.this, title,linkname,description,datechoose);
			list.setAdapter(adapter);
			ParsingArrayAdapter2 adapter2 = new ParsingArrayAdapter2(SearchFilter.this, title2,linkname2,description2,datechoose2);
			list2.setAdapter(adapter2);
		}

	}

}

