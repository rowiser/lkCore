package com.lk.core.kernalservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.lk.utils.log.JLog;

public class KernalService extends Service {
	
	public final static boolean DEBUG = true;
	private final static String TAG = "KernalService";
	private final static JLog LOG = new JLog(TAG, DEBUG, JLog.LogType.DEBUG);

	private KernalManager mManager;
	
	@Override
	public void onCreate() {
		LOG.print("kernal service oncreate");
		mManager = new KernalManager(this);
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		LOG.print("kernal service onBind");
		return mManager;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LOG.print("Received start id " + startId + ": " + intent);
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		LOG.print("Kernal service onDestroy");
		super.onDestroy();
	}

}
