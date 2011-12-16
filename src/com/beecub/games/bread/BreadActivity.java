package com.beecub.games.bread;

import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.beecub.games.bread.model.Balloon;
import com.beecub.games.bread.model.Bread;

public class BreadActivity extends Activity {
	
	private static final String TAG = "beecub";
	public static final String PREFS_NAME = "PlanetPrefs";
	
	public static SharedPreferences mSettings;
    public static Editor mEditor;
	
	public static int mAge = 0;
	public static int mNeed = 0;
	public static long mLastNeed = new Date().getTime();
	public static float mMoney = 0.0f;
	
	public static Bread mBread;
	public static Balloon mBalloon;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        initData();
        setContentView(R.layout.main);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stop:
//                android.os.Process.killProcess(android.os.Process.myPid());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
	@Override
	protected void onDestroy() {
		Log.d(TAG, "Destroying...");
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "Stopping...");
		super.onStop();
	}
	
	@Override
    protected void onPause() {
        super.onPause();
    }
	
    @Override
    protected void onResume() {
        super.onResume();
//        mGamePanel = new MainGamePanel(this);
//        setContentView(mGamePanel);
    }
    
    public static void fulfillNeed() {
        mNeed = 0;
        if(mAge >= 20) {
            mAge -= 5;
        } else {
            mAge += 2;
        }
        mMoney += 1;
        mLastNeed = new Date().getTime();
        mBread.setFace(1, 5 * 1000);
        
        saveSingleData("mAge", mAge);
        saveSingleData("mLastNeed", mLastNeed);
        saveSingleData("mMoney", mMoney);
    }
    
    private void initData() { 
        mSettings = getSharedPreferences(PREFS_NAME, 0);
        mEditor = mSettings.edit();
        
        mAge = mSettings.getInt("mAge", 0);
        mLastNeed = mSettings.getLong("mLastNeed", new Date().getTime());
        mMoney = mSettings.getFloat("mMoney", 0);
    }
    
    public static void saveSingleData(String name, String data) {     
        mEditor.putString(name, data);
        mEditor.commit();
    }
    
    public static void saveSingleData(String name, float data) {    
        mEditor.putFloat(name, data);
        mEditor.commit();
    }
    
    public static void saveSingleData(String name, long data) {
        mEditor.putLong(name, data);
        mEditor.commit();
    }
    
    public static void saveSingleData(String name, int data) {                
        mEditor.putInt(name, data);
        mEditor.commit();
    }
    
    public static void saveSingleData(String name, Boolean data) {               
        mEditor.putBoolean(name, data);
        mEditor.commit();
    }
    
}