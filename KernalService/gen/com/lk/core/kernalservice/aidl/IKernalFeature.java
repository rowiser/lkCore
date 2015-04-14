/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/kaili/Documents/workspace/KernalService/src/com/lk/core/kernalservice/aidl/IKernalFeature.aidl
 */
package com.lk.core.kernalservice.aidl;
public interface IKernalFeature extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.lk.core.kernalservice.aidl.IKernalFeature
{
private static final java.lang.String DESCRIPTOR = "com.lk.core.kernalservice.aidl.IKernalFeature";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.lk.core.kernalservice.aidl.IKernalFeature interface,
 * generating a proxy if needed.
 */
public static com.lk.core.kernalservice.aidl.IKernalFeature asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.lk.core.kernalservice.aidl.IKernalFeature))) {
return ((com.lk.core.kernalservice.aidl.IKernalFeature)iin);
}
return new com.lk.core.kernalservice.aidl.IKernalFeature.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_registCallBack:
{
data.enforceInterface(DESCRIPTOR);
com.lk.core.kernalservice.aidl.IKernalCallback _arg0;
_arg0 = com.lk.core.kernalservice.aidl.IKernalCallback.Stub.asInterface(data.readStrongBinder());
this.registCallBack(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unRegistCallBack:
{
data.enforceInterface(DESCRIPTOR);
com.lk.core.kernalservice.aidl.IKernalCallback _arg0;
_arg0 = com.lk.core.kernalservice.aidl.IKernalCallback.Stub.asInterface(data.readStrongBinder());
this.unRegistCallBack(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_service:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.service(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.lk.core.kernalservice.aidl.IKernalFeature
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
	*注册回调
	*/
@Override public void registCallBack(com.lk.core.kernalservice.aidl.IKernalCallback mCallback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((mCallback!=null))?(mCallback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registCallBack, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	*取消注册回调
	*/
@Override public void unRegistCallBack(com.lk.core.kernalservice.aidl.IKernalCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unRegistCallBack, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	*service
	*/
@Override public void service(int abc) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(abc);
mRemote.transact(Stub.TRANSACTION_service, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_registCallBack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unRegistCallBack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_service = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
/**
	*注册回调
	*/
public void registCallBack(com.lk.core.kernalservice.aidl.IKernalCallback mCallback) throws android.os.RemoteException;
/**
	*取消注册回调
	*/
public void unRegistCallBack(com.lk.core.kernalservice.aidl.IKernalCallback callback) throws android.os.RemoteException;
/**
	*service
	*/
public void service(int abc) throws android.os.RemoteException;
}
