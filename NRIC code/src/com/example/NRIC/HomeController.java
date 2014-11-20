package com.example.NRIC;

import java.util.ArrayList;

import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.nrictest.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera.Parameters;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

public class HomeController extends Activity implements OnClickListener{

	private EditText keyword;
	private Spinner spinner11,spinner22,spinner33;
	private Button   newssearch;
	private ImageButton  keywordsearch1,UserSettings,filtersearch2,resignup;
	private PopupWindow popupwindow;  
	private ProgressDialog pDialog;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//Remove title bar and notification bar,with reference from http://stackoverflow.com
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_controlller);

		keyword = (EditText)findViewById(R.id.keyword);

		keywordsearch1 = (ImageButton)findViewById(R.id.keywordsearch);
		keywordsearch1.setOnClickListener(this);

		UserSettings = (ImageButton)findViewById(R.id.settings_popup);
		UserSettings.setOnClickListener(this);

		filtersearch2 = (ImageButton)findViewById(R.id.filtersearch);
		filtersearch2.setOnClickListener(this);

		newssearch = (Button)findViewById(R.id.newssearch);
		newssearch.setOnClickListener(this);

		resignup=(ImageButton)findViewById(R.id.ReSignUp);
		resignup.setOnClickListener(this);





		// spinner11 setting which contains user "settings" information,adapter is used to put all the choices in the spinner
		spinner11 = (Spinner) findViewById(R.id.spinner11);

		List<String> list1 = new ArrayList<String>();
		list1.add("ANY");
		list1.add("Ambulance Trust");
		list1.add("Care Homes");
		list1.add("Community Settings");
		list1.add("Dental Practices");
		list1.add("Estates Facilities");
		list1.add("High Risk");
		list1.add("Home Hygiene");
		list1.add("Hospice");
		list1.add("Hospitals");
		list1.add("Independence Care");
		list1.add("Mental Health");
		list1.add("Occupational Exposure");
		list1.add("Port Health");
		list1.add("Primary Care");
		list1.add("Prisons");

		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_item,list1);

		dataAdapter1.setDropDownViewResource
		(android.R.layout.simple_spinner_dropdown_item);

		spinner11.setAdapter(dataAdapter1);	



		//spinner22 setting which contains disease information
		spinner22 = (Spinner) findViewById(R.id.spinner22);
		List<String> list2 = new ArrayList<String>();
		list2.add("ANY");
		list2.add("Acinetobacter");
		list2.add("Avian Influenza");
		list2.add("Bacteraemia");
		list2.add("Campylobacter_jejuni");
		list2.add("Chickenpox_VZV");
		list2.add("Chlamydia_psittaci");
		list2.add("Chlamydia_trachomatis");
		list2.add("Clostridium_difficile");
		list2.add("Community Acquired Pneumonia");
		list2.add("Diarrhoea");
		list2.add("CJD");
		list2.add("Ecoli_O157");
		list2.add("Fleas");
		list2.add("HCAI");
		list2.add("Hepatitis A");
		list2.add("Hepatitis B");
		list2.add("Hepatitis C");
		list2.add("Helicobacter Pylori");
		list2.add("Herpes Zoster");
		list2.add("HIV");
		list2.add("Influenza");
		list2.add("Legionella");
		list2.add("Lice");
		list2.add("Measles");
		list2.add("Meningitis");
		list2.add("MRSA");
		list2.add("Mumps");
		list2.add("Mycoplasma");
		list2.add("Norovirus");
		list2.add("Rubella");
		list2.add("Salmonella");
		list2.add("SARS");
		list2.add("Scabies");
		list2.add("Staphylococcus Aureus");
		list2.add("Streptococcal Infection");
		list2.add("Surgical SiteInfection");
		list2.add("TB");
		list2.add("TSE");
		list2.add("VRE");

		ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_item,list2);

		dataAdapter2.setDropDownViewResource
		(android.R.layout.simple_spinner_dropdown_item);

		spinner22.setAdapter(dataAdapter2);	

		//spinner33 setting which contains clinical practice information
		spinner33 = (Spinner) findViewById(R.id.spinner33);
		List<String> list3 = new ArrayList<String>();
		list3.add("ANY");
		list3.add("Aseptic Technique");
		list3.add("Cleaning");
		list3.add("Decontamination");
		list3.add("Handwashing");
		list3.add("Immunisation");
		list3.add("Invasive Devices");
		list3.add("Isolation");
		list3.add("Laborarory Specimen");
		list3.add("Laundry");
		list3.add("Needles");
		list3.add("Ward Closure");
		list3.add("Waste");

		ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_item,list3);

		dataAdapter3.setDropDownViewResource
		(android.R.layout.simple_spinner_dropdown_item);

		spinner33.setAdapter(dataAdapter3);	
	}



	@Override
	public void onClick(View v) {

		//select the button pressed
		switch (v.getId()) {
		case R.id.keywordsearch:
			EditText keyword = (EditText) findViewById(R.id.keyword);
			Intent i = new Intent(HomeController.this, SearchNews.class);
			i.putExtra("location", keyword.getText().toString());
			startActivity(i);

			break;

			//split the string chosen from spinner and combine thease strings into a new one	
		case R.id.filtersearch:
			Intent j = new Intent(HomeController.this, SearchFilter.class);

			//get string selected from spinner1 
			String[] storespinner11=spinner11.getSelectedItem().toString().split(" ");
			StringBuffer result1 = new StringBuffer();

			for (int num = 0; num < storespinner11.length; num++) {

				result1.append( storespinner11[num] );

			}
			String mynewstring11 = result1.toString();

			//get string selected from spinner2
			String[] storespinner22=spinner22.getSelectedItem().toString().split(" ");
			String Spinner22=storespinner22.toString();
			StringBuffer result2 = new StringBuffer();

			for (int num = 0; num < storespinner22.length; num++) {

				result2.append( storespinner22[num] );

			}
			String mynewstring22 = result2.toString();

			//get string selected from spinner3
			String[] storespinner33=spinner33.getSelectedItem().toString().split(" ");
			String Spinner33=storespinner33.toString();
			StringBuffer result3 = new StringBuffer();

			for (int num = 0; num < storespinner33.length; num++) {

				result3.append( storespinner33[num] );

			}
			String mynewstring33 = result3.toString();

			//give strings addition prefix to fit search html link
			j.putExtra("location11", ("Settings-"+mynewstring11));
			j.putExtra("location22", ("Diseases-"+mynewstring22));
			j.putExtra("location33", ("ClinicalPractice-"+mynewstring33));

			j.putExtra("location111", ("+"+spinner11.getSelectedItem().toString()));
			j.putExtra("location222", ("+"+spinner22.getSelectedItem().toString()));
			j.putExtra("location333", ("+"+spinner33.getSelectedItem().toString()));
			startActivity(j);
			break;
		case R.id.newssearch:
			Intent kk = new Intent(HomeController.this, SearchAllNews.class);
			startActivity(kk);
			break;
		case R.id.ReSignUp:
			SharedPreferences settings = getSharedPreferences(PreConfiguration.PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("hasLoggedIn", false);
			editor.commit();
			Intent back = new Intent(HomeController.this, PreConfiguration.class);
			startActivity(back);
			break;
		case R.id.settings_popup:
			if (popupwindow != null&&popupwindow.isShowing()) {  
				popupwindow.dismiss();  
				return;  
			} else {  
				initmPopupWindowView();  
				popupwindow.showAsDropDown(v, 0, 5);  
			}  
			break;  
		case R.id.aboutus:  
			Intent aboutus = new Intent(HomeController.this, AboutUs.class);
			startActivity(aboutus);
			break;

		case R.id.help:  
			Intent help = new Intent(HomeController.this, Helping.class);
			startActivity(help);
			break;
		case R.id.contactus:  
			Intent contactus = new Intent(HomeController.this, ContactUs.class);
			startActivity(contactus);
			break;
		default:
			break;
		}

	}

	//create a context menu when "settings_popup" button is pressed
	public void initmPopupWindowView() {  

		View customView = getLayoutInflater().inflate(R.layout.popview,  
				null, false);  
		popupwindow = new PopupWindow(customView, 250,290);  

		customView.setOnTouchListener(new OnTouchListener() {  

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {

				if (popupwindow != null && popupwindow.isShowing()) {  
					popupwindow.dismiss();  
					popupwindow = null;  
				}  

				return false;  

			}  
		});  

		//define buttons we need
		Button contactus = (Button) customView.findViewById(R.id.contactus);  
		Button btton3 = (Button) customView.findViewById(R.id.aboutus);  
		Button btton4 = (Button) customView.findViewById(R.id.help);  
		contactus.setOnClickListener(this);  
		btton3.setOnClickListener(this);  
		btton4.setOnClickListener(this);
	}

	protected void onPostExecute(String file_url) {
		// dismiss the dialog once product deleted
		pDialog.dismiss();
		if (file_url != null){
			Toast.makeText(HomeController.this, file_url, Toast.LENGTH_LONG).show();
		}

	}

}



