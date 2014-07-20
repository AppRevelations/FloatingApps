package com.apprevelations.floatingapps;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	Button b;
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
		
		
		
		
		b= (Button) findViewById(R.id.b);
		
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				for (int i = 0; i < lv.getCount(); i++) {
			        v = lv.getAdapter().getView(i, null, null);
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
			}
		});
		
		
			}

}
