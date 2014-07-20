package com.apprevelations.floatingapps;

import java.util.ArrayList;
import java.util.List;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.v7.internal.widget.ListPopupWindow;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


public class ChatHeadService extends Service {

	public static  int ID_NOTIFICATION = 2018;

	int flag=0;
	ListPopupWindow popup;
	private WindowManager windowManager;
	private ImageView chatHead;
	//private PopupWindow pwindo;

	boolean mHasDoubleClicked = false;
	long lastPressTime;

	WindowManager.LayoutParams params[]= new WindowManager.LayoutParams[1000];
	ArrayList<PInfo> apps;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override 
	public void onCreate() {
		super.onCreate();
		

		RetrievePackages getInstalledPackages = new RetrievePackages(getApplicationContext());
		apps = getInstalledPackages.getInstalledApps(false);
		
		
		for(int i=0;i<apps.size();i++)
		{
		
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

		chatHead = new ImageView(this);
		chatHead.setId(i);
		Drawable icon= apps.get(i).icon;
		Bitmap bmp= drawableToBitmap(icon);
		Bitmap b= getRoundedShape(bmp);
		chatHead.setImageBitmap(b);
		
		
		params[i] = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
	            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		
		
		params[i].gravity = Gravity.TOP | Gravity.LEFT;
		params[i].x = 0;
		params[i].y = 100;

		windowManager.addView(chatHead, params[i]);

		
		try {
			chatHead.setOnTouchListener(new View.OnTouchListener() {
				
				
				private int initialX;
				private int initialY;
				private float initialTouchX;
				private float initialTouchY;

				@Override public boolean onTouch(View v, MotionEvent event) {
					
					WindowManager.LayoutParams paramsF = params[v.getId()];
					
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:

						// Get current time in nano seconds.
						long pressTime = System.currentTimeMillis();


						
						
						    // If not double click....
							mHasDoubleClicked = false;
						
						lastPressTime = pressTime; 
						initialX = paramsF.x;
						initialY = paramsF.y;
						initialTouchX = event.getRawX();
						initialTouchY = event.getRawY();
						break;
					case MotionEvent.ACTION_UP:
						break;
					case MotionEvent.ACTION_MOVE:
						paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
						paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
						windowManager.updateViewLayout(chatHead, paramsF);
						break;
					}
					return false;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}

		
		chatHead.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int position= v.getId();
				Intent i;
				PackageManager manager = getPackageManager();
				try {
					i = manager.getLaunchIntentForPackage(apps.get(position).pname.toString());
					if (i == null)
						throw new PackageManager.NameNotFoundException();
					i.addCategory(Intent.CATEGORY_LAUNCHER);
					startActivity(i);
				} catch (PackageManager.NameNotFoundException e) {

				}
				
			}
		});
	}
	
	}
    
    Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
	    int targetWidth = scaleBitmapImage.getWidth();
	    int targetHeight =scaleBitmapImage.getHeight();
	    Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, 
	                        targetHeight,Bitmap.Config.ARGB_8888);

	    Canvas canvas = new Canvas(targetBitmap);
	    Path path = new Path();
	    path.addCircle(((float) targetWidth - 1) / 2,
	        ((float) targetHeight - 1) / 2,
	        (Math.min(((float) targetWidth), 
	        ((float) targetHeight)) / 2),
	        Path.Direction.CCW);

	    canvas.clipPath(path);
	    Bitmap sourceBitmap = scaleBitmapImage;
	    canvas.drawBitmap(sourceBitmap, 
	        new Rect(0, 0, sourceBitmap.getWidth(),
	        sourceBitmap.getHeight()), 
	        new Rect(0, 0, targetWidth, targetHeight), null);
	    return targetBitmap;
	}
	
    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap); 
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
    
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (chatHead != null) windowManager.removeView(chatHead);
	}

}