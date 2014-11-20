package com.example.NRIC;

import com.example.nrictest.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

public class Helping extends Activity {

	//create a page which shows some help information
	public void onCreate(Bundle savedInstanceState) {

		//Remove title bar and notification bar,with reference from http://stackoverflow.com
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_page);

	}

}