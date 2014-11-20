package com.example.NRIC;

import com.example.nrictest.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class CoverActivity extends Activity implements OnClickListener{

	//define button which can lead to PreConfiguration class
	private Button buttonWelcome;


	public void onCreate(Bundle savedInstanceState) {

		//Remove title bar and notification bar,with reference from http://stackoverflow.com
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cover);
		//make button visible
		buttonWelcome = (Button)findViewById(R.id.welcomebutton);
		buttonWelcome.setOnClickListener(this);

	}

	//get Internet connection state
	public boolean isOnline() {
		ConnectivityManager cm =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null && 
				cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	//if Internet connected, press button and go to next page. If not connected, a message will pop up
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(isOnline()!=false){
			Intent bb = new Intent(CoverActivity.this, PreConfiguration.class);
			startActivity(bb);
		}

		else{
			Toast.makeText(getApplicationContext(), 
					"Internet is not connected", Toast.LENGTH_LONG).show();

		}
	}

}