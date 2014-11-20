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
import android.content.SharedPreferences.Editor;
import android.hardware.Camera.Parameters;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PreConfiguration extends Activity implements OnClickListener{

	private EditText user_name, user_email,user_profession,user_location;
	private Spinner spinner1;
	private Button  mSignUp;
	private String UserEmail;
	public static String settingschoice;
	int success;


	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();


	private static final String REGISTER_URL = "http://www.mydoggydoggy.net63.net/register.php";


	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	public static final String PREFS_NAME = "MyPrefsFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Remove title bar and notification bar,with reference from http://stackoverflow.com
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		//judge if user have logged in before
		SharedPreferences settings = getSharedPreferences(PreConfiguration.PREFS_NAME, 0);
		boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

		if(hasLoggedIn)
		{
			Intent cc = new Intent(this, HomeController.class);
			startActivity(cc);
			finish();
		}


		super.onCreate(savedInstanceState);
		setContentView(R.layout.pre_configuration);


		user_name = (EditText)findViewById(R.id.namefield);		
		user_email = (EditText)findViewById(R.id.emailfield);		
		user_profession = (EditText)findViewById(R.id.professionfield);		
		user_location = (EditText)findViewById(R.id.locationfield);


		mSignUp = (Button)findViewById(R.id.submit);
		mSignUp.setOnClickListener(this);


		spinner1 = (Spinner) findViewById(R.id.spinner1);
		List<String> list = new ArrayList<String>();
		list.add("ANY");
		list.add("Ambulance Trust");
		list.add("Care Homes");
		list.add("Community Settings");
		list.add("Dental Practices");
		list.add("Estates Facilities");
		list.add("High Risk");
		list.add("Home Hygiene");
		list.add("Hospice");
		list.add("Hospitals");
		list.add("Independence Care");
		list.add("Mental Health");
		list.add("Occupational Exposure");
		list.add("Port Health");
		list.add("Primary Care");
		list.add("Prisons");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
		(this, android.R.layout.simple_spinner_item,list);

		dataAdapter.setDropDownViewResource
		(android.R.layout.simple_spinner_dropdown_item);

		spinner1.setAdapter(dataAdapter);	
	}


	//set action when user press this button
	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.submit:
			if(checkEmail() == true)
			{

				new CreateUser().execute(); 

			}

			else{

				Toast.makeText(getApplicationContext(), 
						"Email is not valid", Toast.LENGTH_LONG).show();

			}
			break;

		default:
			break;
		}

	}

	//check if user email is valid or not
	protected boolean checkEmail(){

		UserEmail = user_email.getText().toString();

		if(UserEmail.contains("@") && UserEmail.contains(".")){
			return true;

		}
		else{ return false;}
	}


	//with reference from http://www.mybringback.com/tutorial-series/12924/android-tutorial-using-remote-databases-php-and-mysql-part-1/
	class CreateUser extends AsyncTask<String, String, String> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(PreConfiguration.this);
			pDialog.setMessage("Creating User...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			String UserName = user_name.getText().toString();
			String UserEmail = user_email.getText().toString();
			String UserProfession = user_profession.getText().toString();
			String UserLocation = user_location.getText().toString();

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", UserName));
				params.add(new BasicNameValuePair("useremail", UserEmail));
				params.add(new BasicNameValuePair("userprofession", UserProfession));
				params.add(new BasicNameValuePair("userlocation", UserLocation));
				params.add(new BasicNameValuePair("userchoice",spinner1.getSelectedItem().toString()));



				JSONObject json = jsonParser.makeHttpRequest(
						REGISTER_URL, "POST", params);

				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					//if successfully log in, store user information

					SharedPreferences settings = getSharedPreferences(PreConfiguration.PREFS_NAME, 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("Submit_Name", UserName);
					editor.putString("Submit_Email", UserEmail);
					editor.putString("Submit_profession", UserProfession);
					editor.putString("Submit_location", UserLocation);
					editor.putBoolean("hasLoggedIn", true);
					editor.commit();
					Intent i = new Intent(PreConfiguration.this, HomeController.class);
					startActivity(i);
					finish();

					return json.getString(TAG_MESSAGE);

				}else{

					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null){
				Toast.makeText(PreConfiguration.this, file_url, Toast.LENGTH_LONG).show();
			}

		}

	}


}
