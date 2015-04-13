package cn.buaa.myweixin;

import android.widget.ImageView;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.Button;

public class MainActivity extends Activity {
	private EditText numberText;
	private EditText contentText;
	private Button send_SMS_ALL;
	private Button send_ANI_ALL;
	private ImageView add_number1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_address);
		numberText = (EditText)this.findViewById(R.id.number);
		contentText = (EditText)this.findViewById(R.id.content);
				
		send_SMS_ALL = (Button)this.findViewById(R.id.send_SMS_button1);		
		send_SMS_ALL.setOnClickListener(new ButtonClickListener());	
		
		//send_ANI_ALL = (Button)this.findViewById(R.id.send_ANI_button1);						
	}
	public final class  ButtonClickListener implements View.OnClickListener{
		public void onClick(View v) {
			String numbers = numberText.getText().toString();
			String content = contentText.getText().toString();
			SmsManager smsManager = SmsManager.getDefault();
			ArrayList<String> texts = smsManager.divideMessage(content);
			String[] number = numbers.split(";");
			for(int i=0; i<number.length; i++){
				for(String text: texts){
					smsManager.sendTextMessage(number[i], null, text, null, null);
				}
			}
			Toast.makeText(MainActivity.this, R.string.success, Toast.LENGTH_LONG).show();
			contentText.setText("");
			
		}
	}
	
	public void sendClick(View v){
		String numbers = numberText.getText().toString();
		String content = contentText.getText().toString();
		SmsManager smsManager = SmsManager.getDefault();
		ArrayList<String> texts = smsManager.divideMessage(content);
		String[] number = numbers.split(";");
		for(int i=0; i<number.length; i++){
			for(String text: texts){
				smsManager.sendTextMessage(number[i], null, text, null, null);
			}
		}
		Toast.makeText(MainActivity.this, R.string.success, Toast.LENGTH_LONG).show();
		contentText.setText("");
		}	
	
	public void addClick(View v){
		
		Intent intent = new Intent();
		intent.setClassName("com.example.duanxinqunfa", "com.example.duanxinqunfa.contactActivity");
		Bundle bundle = new Bundle();
		bundle.putString("number", numberText.getText().toString());
		intent.putExtras(bundle);
		startActivityForResult(intent, 200);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try{
			String result = data.getStringExtra("result");
			numberText.setText(result);
		}catch(Exception e){
			
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

