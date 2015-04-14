/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/kaili/Documents/workspace/KernalService/src/com/lk/core/kernalservice/aidl/IKernalCallback.aidl
 */
package com.lk.core.kernalservice.aidl;
public interface IKernalCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.lk.core.kernalservice.aidl.IKernalCallback
{
private static final java.lang.String DESCRIPTOR = "com.lk.core.kernalservice.aidl.IKernalCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.lk.core.kernalservice.aidl.IKernalCallback interface,
 * generating a proxy if needed.
 */
public static com.lk.core.kernalservice.aidl.IKernalCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.lk.core.kernalservice.aidl.IKernalCallback))) {
return ((com.lk.core.kernalservice.aidl.IKernalCallback)iin);
}
return new com.lk.core.kernalservice.aidl.IKernalCallback.Stub.Proxy(obj);
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
case TRANSACTION_notifyDataChanged:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.notifyDataChanged(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.lk.core.kernalservice.aidl.IKernalCallback
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
@Override public void notifyDataChanged(int data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(data);
mRemote.transact(Stub.TRANSACTION_notifyDataChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_notifyDataChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void notifyDataChanged(int data) throws android.os.RemoteException;
}
