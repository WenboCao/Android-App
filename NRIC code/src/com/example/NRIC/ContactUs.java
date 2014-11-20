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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ContactUs extends Activity implements OnClickListener{

	private String Submit_Name,Submit_Email;
	private TextView your_name,your_email;
	private EditText your_feedback;
	private Button  Submit_Feedback;
	private ProgressDialog pDialog;
	private SharedPreferences mySharedPreferences;
	int prefMode = Activity.MODE_PRIVATE;

	// create JSON parser
	JSONParser jsonParser = new JSONParser();

	//set webserver link
	private static final String POST_COMMENT_URL = "http://www.mydoggydoggy.net63.net/contact_us.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//Remove title bar and notification bar,with reference from http://stackoverflow.com
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_comments);

		mySharedPreferences = getSharedPreferences(PreConfiguration.PREFS_NAME, prefMode);
		Submit_Name = mySharedPreferences.getString("Submit_Name", null);
		Submit_Email = mySharedPreferences.getString("Submit_Email", null);

		your_feedback = (EditText)findViewById(R.id.your_feedback);

		Submit_Feedback = (Button)findViewById(R.id.add_commentbutton);
		Submit_Feedback.setOnClickListener(this);

		your_name=(TextView)findViewById(R.id.your_name);
		your_name.setText(Submit_Name);

		your_email=(TextView)findViewById(R.id.your_email);
		your_email.setText(Submit_Email);


	}

	@Override
	public void onClick(View v) {
		new PostComment().execute();
	}


	class PostComment extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ContactUs.this);
			pDialog.setMessage("Posting Comment...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			try {
				//build up the values we need to pass by JSON
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username",Submit_Name ));
				params.add(new BasicNameValuePair("useremail", Submit_Email));
				params.add(new BasicNameValuePair("usercomment", your_feedback.getText().toString()));

				//pass values to JSON 
				JSONObject json = jsonParser.makeHttpRequest(
						POST_COMMENT_URL, "POST", params);

				//JSON response 
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("Comment Added!", json.toString());    
					finish();
					return json.getString(TAG_MESSAGE);
				}else{
					Log.d("Comment Failure!", json.getString(TAG_MESSAGE));
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
				Toast.makeText(ContactUs.this, file_url, Toast.LENGTH_LONG).show();
			}

		}

	}


}
