package com.nwd.android.myvolley;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lk.utils.net.HttpRequestUtil;

public class VolleyActivity extends Activity {

	protected static final String TAG = "VolleyActivity";
	private RequestQueue mRequestQueue;
	private ImageView singleImg, singleImg2, singleImg3;
	private TextView mMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volley);
		singleImg = (ImageView) findViewById(R.id.volley_img_single_imgeview);
		singleImg2 = (ImageView) findViewById(R.id.volley_img_single_imgeview2);
		singleImg3 = (ImageView) findViewById(R.id.volley_img_single_imgeview3);
		mMsg = (TextView) findViewById(R.id.txt_msg);
		mRequestQueue = Volley.newRequestQueue(this);
		// testVolleyJson();
		// testVolleyImg();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// testHeader();
			}

		}).start();
		testHeaderUseVolley();
		// mHander.sendEmptyMessage(1);
	}

	private void testHeaderUseVolley() {
		String jsonUrl = "http://user.api.colalab.com/";
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				jsonUrl, null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.i(TAG, "testVolleyJson1 = " + response.toString());
						try {
							JSONArray ja = (JSONArray)response.get("data");
							JSONObject jo = (JSONObject)ja.get(0);
							Log.i(TAG, jo.getString("username"));
							mMsg.setText(jo.getString("username"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// parseJSON(response);
						// va.notifyDataSetChanged();
						// pd.dismiss();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i(TAG, error.getMessage());
					}
				}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				byte bs[] = null;
				try {
					bs = new String("morearea").getBytes("US-ASCII");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				int date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
				int hourse = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				for (int i = 0; i < bs.length; i++) {
					bs[i] ^= date;
					bs[i] ^= hourse;
				}
				String encodeStr = Base64.encodeToString(bs, Base64.DEFAULT);
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("USERPWD", encodeStr);
				return headers;
			}
		};
		mRequestQueue.add(jr);
	}

	private Handler mHander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				testHeader();
				break;

			default:
				break;
			}
		};
	};

	private void testHeader() {

		byte bs[] = null;
		try {
			bs = new String("morearea").getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int hourse = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		for (int i = 0; i < bs.length; i++) {
			bs[i] ^= date;
			bs[i] ^= hourse;
		}
		String encodeStr = Base64.encodeToString(bs, Base64.DEFAULT);

		Map<String, String> params = new HashMap<String, String>();
		params.put("name", "xiazdong");
		params.put("age", "10");
		HttpURLConnection conn;
		try {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("HTTPAUTH", encodeStr);
			headers.put("USERPWD", encodeStr);
			conn = (HttpURLConnection) HttpRequestUtil.sendGetRequest(
					"http://user.api.colalab.com", params, headers);
			// int code = conn.getResponseCode();
			InputStream in = conn.getInputStream();
			byte[] data = read2Byte(in);
			Log.i(TAG, new String(data));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 将输入流转为字节数组
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] read2Byte(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	private void testVolleyImg() {

		// ImageRequest imgRequest = new
		// ImageRequest("http://static.colalab.com/admin/images/login_03.jpg"，
		// new Response.Listener<Bitmap>() {
		// @Override
		// public void onResponse(Bitmap arg0) {
		// // TODO Auto-generated method stub
		// // singleImg.setImageBitmap(arg0);
		// }
		// }, 300, 200, Config.ARGB_8888, new ErrorListener() {
		// @Override
		// public void onErrorResponse(VolleyError arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		ImageRequest imgRequest = new ImageRequest(
				"http://static.colalab.com/admin/images/login_03.jpg",
				new Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap response) {
						// TODO Auto-generated method stub
						singleImg.setImageBitmap(response);
					}
				}, 300, 200, Config.ARGB_8888, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e(TAG, "load image error!");
					}
				});
		mRequestQueue.add(imgRequest);
		ImageRequest imgRequest2 = new ImageRequest(
				"http://static.colalab.com/admin/images/login_02.jpg",
				new Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap response) {
						// TODO Auto-generated method stub

						singleImg2.setImageBitmap(response);
					}
				}, 300, 200, Config.ARGB_8888, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e(TAG, "load image error!");
					}
				});
		mRequestQueue.add(imgRequest2);
		ImageRequest imgRequest3 = new ImageRequest(
				"http://static.colalab.com/admin/images/login_02.jpg",
				new Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap response) {
						// TODO Auto-generated method stub
						singleImg3.setImageBitmap(response);
					}
				}, 300, 200, Config.ARGB_8888, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e(TAG, "load image error!");
					}
				});

		mRequestQueue.add(imgRequest3);
	}

	private void testVolleyJson() {

		String jsonUrl = "http://user.api.colalab.com/";
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				jsonUrl, null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.i(TAG, "testVolleyJson = " + response.toString());
						// parseJSON(response);
						// va.notifyDataSetChanged();
						// pd.dismiss();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i(TAG, error.getMessage());
					}
				});

		mRequestQueue.add(jr);
	}

	@Override
	public void onStop() {
		if (mRequestQueue != null) {
			// mRequestQueue.cancelAll(this);
		}
		super.onStop();
	}
}
