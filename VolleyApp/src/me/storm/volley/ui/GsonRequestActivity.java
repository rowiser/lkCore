package me.storm.volley.ui;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import me.storm.volley.R;
import me.storm.volley.data.GsonRequest;
import me.storm.volley.model.MyClass;
import me.storm.volley.vendor.VolleyApi;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.google.gson.Gson;

public class GsonRequestActivity extends BaseActivity {
	private TextView mTvResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gson_request);

		mTvResult = (TextView) findViewById(R.id.tv_result);

		Button btnRequest = (Button) findViewById(R.id.btn_gson_request);
		btnRequest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				executeRequest(new GsonRequest<MyClass>(VolleyApi.GSON_TEST,
						MyClass.class, responseListener(), errorListener()) {
					@Override
					protected String doParseResponseToJson(
							NetworkResponse response)
							throws UnsupportedEncodingException {
						return super.doParseResponseToJson(response);
					}

					@Override
					public Map<String, String> getHeaders()
							throws AuthFailureError {
						// TODO Auto-generated method stub
						return super.getHeaders();
					}
				});
			}
		});
	}

	private Response.Listener<MyClass> responseListener() {
		return new Response.Listener<MyClass>() {
			@Override
			public void onResponse(MyClass response) {
				mTvResult.setText(new Gson().toJson(response));
			}
		};
	}
}
