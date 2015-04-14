package com.lk.core.kernalservice;

import android.content.Context;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.lk.core.kernalservice.aidl.IKernalCallback;
import com.lk.core.kernalservice.aidl.IKernalFeature;
import com.lk.utils.log.JLog;

public class KernalManager extends IKernalFeature.Stub {

	private RemoteCallbackList<IKernalCallback> mRemoteCallbackList = new RemoteCallbackList<IKernalCallback>();

	private Context mContext;

	private final static JLog LOG = new JLog("KernalManager", true,
			JLog.LogType.DEBUG);

	public KernalManager(Context context) {
		this.mContext = context;
	}

	@Override
	public void registCallBack(IKernalCallback callback) throws RemoteException {
		if (mRemoteCallbackList != null && callback != null) {
			mRemoteCallbackList.register(callback);
		}
	}

	@Override
	public void unRegistCallBack(IKernalCallback callback)
			throws RemoteException {
		if (mRemoteCallbackList != null && callback != null) {
			mRemoteCallbackList.unregister(callback);
		}
	}

	@Override
	public void service(int abc) throws RemoteException {
		LOG.print("------service------");
		if (mRemoteCallbackList != null) {
			LOG.print("mRemoteCallbackList != null");
			int i = mRemoteCallbackList.beginBroadcast();
			while (i > 0) {
				i--;
				try {
					mRemoteCallbackList.getBroadcastItem(i).notifyDataChanged(
							99);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			mRemoteCallbackList.finishBroadcast();
		} else {
			LOG.print("mRemoteCallbackList == null");
		}

	}

}
