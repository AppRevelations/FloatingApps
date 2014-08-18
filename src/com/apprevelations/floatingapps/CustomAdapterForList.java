package com.apprevelations.floatingapps;

import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomAdapterForList extends ArrayAdapter{

    private int resource;
    private LayoutInflater inflater;
    private Context context;
    DatabaseHandler db;
    private ArrayList<Boolean> checkList = new ArrayList<Boolean>();

    public CustomAdapterForList ( Context ctx, int resourceId, List apps) {

        super( ctx, resourceId, apps );
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context=ctx;
        db = new DatabaseHandler(ctx);
        List<Contact> a= db.getAllContacts();
        for (int i = 0; i < a.size(); i++) {
        	
        	if(a.get(i).getStatus()==1)
        		checkList.add(true);
        	else 
        		checkList.add(false);
        }
        
        
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) {

        convertView = (RelativeLayout) inflater.inflate( resource, null );

        PInfo app = (PInfo) getItem(position);

        
        //store all packages name in database here
        
        
        
        TextView txtName = (TextView) convertView.findViewById(R.id.tvappname);
        txtName.setText(app.appname);
        
        
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
        checkBox.setTag(Integer.valueOf(position)); // set the tag so we can identify the correct row in the listener
        checkBox.setChecked(checkList.get(position)); // set the status as we stored it        
        checkBox.setOnCheckedChangeListener(mListener);
        
        ImageView imageCity = (ImageView) convertView.findViewById(R.id.imageView1);
        imageCity.setImageDrawable(app.icon);
        return convertView;
    }
    
    
  
    
    OnCheckedChangeListener mListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			 checkList.set((Integer)buttonView.getTag(),isChecked); // get the tag so we know the row and store the status
		}
	};
    
}