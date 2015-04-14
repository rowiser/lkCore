/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/kaili/Documents/workspace/WeatherService/src/com/lk/weatherservice/aidl/IWeatherService.aidl
 */
package com.lk.weatherservice.aidl;
public interface IWeatherService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.lk.weatherservice.aidl.IWeatherService
{
private static final java.lang.String DESCRIPTOR = "com.lk.weatherservice.aidl.IWeatherService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.lk.weatherservice.aidl.IWeatherService interface,
 * generating a proxy if needed.
 */
public static com.lk.weatherservice.aidl.IWeatherService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.lk.weatherservice.aidl.IWeatherService))) {
return ((com.lk.weatherservice.aidl.IWeatherService)iin);
}
return new com.lk.weatherservice.aidl.IWeatherService.Stub.Proxy(obj);
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
case TRANSACTION_test:
{
data.enforceInterface(DESCRIPTOR);
this.test();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.lk.weatherservice.aidl.IWeatherService
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
@Override public void test() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_test, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_test = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void test() throws android.os.RemoteException;
}
