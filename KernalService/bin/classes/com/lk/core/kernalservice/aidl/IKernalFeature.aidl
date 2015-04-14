package com.lk.core.kernalservice.aidl;

import com.lk.core.kernalservice.aidl.IKernalCallback;
interface IKernalFeature{

	/**
	*注册回调
	*/
	void registCallBack(IKernalCallback mCallback);
	
	/**
	*取消注册回调
	*/
	void unRegistCallBack(IKernalCallback callback);
	
	/**
	*service
	*/
	void service(int abc);

}