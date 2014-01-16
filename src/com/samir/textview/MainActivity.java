package com.samir.textview;

import com.samir.util.UILImageGetter;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	protected String html = "<h1>This is a simple demo</h1> " 
			+"<p>A Method to implement TextView which have a innner image </p>"
	        + "<img src='http://g.hiphotos.baidu.com/image/w%3D2048/sign=971235f034d3d539c13d08c30ebfe850/203fb80e7bec54e768290f10bb389b504ec26af2.jpg' />" 
			+"<font color='red'>My Github:https://github.com/wanghuabing/</font>" ;  
	protected TextView content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViews();
		initValues();
	}
	
	public void findViews() {
		// TODO Auto-generated method stub
		content    = (TextView)findViewById(R.id.content);
	}

	public void initValues() {
		// TODO Auto-generated method stub
		UILImageGetter p = new UILImageGetter(content, this);
		Spanned htmlSpan = Html.fromHtml(html, p, null);
		content.setText(htmlSpan);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
