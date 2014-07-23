package com.apprevelations.floatingapps;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	ArrayList<String> myArray;
	CheckBox ch;
	ArrayList<PInfo> apps;
	Contact contact;
	DatabaseHandler db;
	List listCity;
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar actionbar= getSupportActionBar();
		actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C4CD")));

        db = new DatabaseHandler(this);
		
        
		RetrievePackages getInstalledPackages = new RetrievePackages(getApplicationContext());
		apps = getInstalledPackages.getInstalledApplication(true);
		myArray = new ArrayList<String>();

		for(int i=0 ; i<apps.size() ; ++i) {
			myArray.add(apps.get(i).appname);

	        db.addPackage(new Contact(0, apps.get(i).pname));
		}

		listCity = new ArrayList();
		for(int i=0 ; i<apps.size() ; ++i) {
			listCity.add(apps.get(i));
		}
		//popup.setAnimationStyle(Anima);
		//ArrayAdapter<String> arrayAdapter = 
		//new ArrayAdapter<String>(this,R.layout.list_item, myArray);

		 lv = (ListView) findViewById(R.id.lvlist);
		
		 
		lv.setAdapter(new CustomAdapterForList(getApplicationContext(), R.layout.list_row, listCity));
		
		
		
		
	
			}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_done) {
			

			for (int i = 0; i < lv.getCount(); i++) {
		        View v = lv.getAdapter().getView(i, null, null);
		        ch = (CheckBox) v.findViewById(R.id.checkBox1);
		        
		        if(ch.isChecked())
		        {
		        	contact= new Contact();
		        	String x=apps.get(i).pname;
		        	contact.setName(x);
		        	contact.setStatus(1);
		        	
		        	db.updateContact(contact);
		        	
		        }
		        else
		        {
		        	contact= new Contact();
		        	String x=apps.get(i).pname;
		        	contact.setName(x);
		        	contact.setStatus(0);
		        	
		        	db.updateContact(contact);
		        }
		    }

			
			startService(new Intent(MainActivity.this, ChatHeadService.class));	
			
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	
}
