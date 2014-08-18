package com.apprevelations.floatingapps;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

public class RetrievePackages {
	
	private Context _ctx;
 DatabaseHandler db;
	Contact contact;
	String pName;
	public RetrievePackages(Context ctx) {
		_ctx = ctx;
	}
	
    public ArrayList<PInfo> getPackages() {
        ArrayList<PInfo> apps = getInstalledApps(false); /* false = no system packages */
        final int max = apps.size();
        for (int i=0; i<max; i++) {
            apps.get(i).prettyPrint();
        }
        return apps;
    }

    public ArrayList<PInfo> getInstalledApplication(boolean getSysPackages) {
        ArrayList<PInfo> res = new ArrayList<PInfo>();        
        List<PackageInfo> packs = _ctx.getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            	PInfo newInfo = new PInfo();
                newInfo.appname = p.applicationInfo.loadLabel(_ctx.getPackageManager()).toString();
                newInfo.pname = p.packageName;
                newInfo.icon = p.applicationInfo.loadIcon(_ctx.getPackageManager());
                res.add(newInfo);
            
        }
        return res; 
    }
    
    public ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
        ArrayList<PInfo> res = new ArrayList<PInfo>();        
        //PInfo newInfo = new PInfo();
        // add here the code for pinned items
        

        PackageManager pm = _ctx.getPackageManager();
        
        db = new DatabaseHandler(_ctx);
        List<Contact> a= db.getAllContacts();
        for (int i = 0; i < a.size(); i++) {
        	
        	if(a.get(i).getStatus()==1)
        	{
        		PInfo newInfo = new PInfo();
        		 try {
        			 
        			 newInfo.pname = a.get(i).getName();
					Drawable ico = pm.getApplicationIcon(a.get(i).getName());
					newInfo.icon= ico;
					res.add(newInfo);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}        		
        	}        		
        }    
        return res;
    }    
}
