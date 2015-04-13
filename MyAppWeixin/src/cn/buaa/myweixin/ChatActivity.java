package cn.buaa.myweixin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 
 *  
 */
public class ChatActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */

	private Button mBtnSend;
	private Button mBtnBack;
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_xiaohei);
        //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        initView();
        
        initData();
  
        
        //实例化SharedPreferences对象（第一步） 
        SharedPreferences mySharedPreferences= getSharedPreferences("test", 
        Activity.MODE_PRIVATE); 
        //实例化SharedPreferences.Editor对象（第二步） 
        SharedPreferences.Editor editor = mySharedPreferences.edit(); 
        //用putString的方法保存数据 
        editor.putString("name", "Karl"); 
        editor.putString("habit", "sleep"); 
        //提交当前数据 
        editor.commit(); 
        //使用toast信息提示框提示成功写入数据 
        Toast.makeText(this, "数据成功写入SharedPreferences！" , 
        Toast.LENGTH_LONG).show(); 

        //////////////////////////////////////////////////////
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象 
        SharedPreferences sharedPreferences= getSharedPreferences("test", 
        Activity.MODE_PRIVATE); 
        // 使用getString方法获得value，注意第2个参数是value的默认值 
        String name =sharedPreferences.getString("name", ""); 
        String habit =sharedPreferences.getString("habit", ""); 
        //使用toast信息提示框显示信息 

        Toast.makeText(this, "读取数据如下："+"\n"+"name：" + name + "\n" + "habit：" + habit, 
        Toast.LENGTH_LONG).show(); 
        
    }
    
    
    public void initView()
    {
    	mListView = (ListView) findViewById(R.id.listview);
    	mBtnSend = (Button) findViewById(R.id.btn_send);
    	mBtnSend.setOnClickListener(this);
    	mBtnBack = (Button) findViewById(R.id.btn_back);
    	mBtnBack.setOnClickListener(this);
    	
    	mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
    }
    
    private String[]msgArray = new String[]{"您好，请在此输入要发布的消息！", "行贷款利率2015？", "贷款利率", "首套房贷利率", 
    										"首套房贷利率", "首套房贷利率？首套房贷利率？",
    										"首套房贷利率", "贷款利率",};
    
    private String[]dataArray = new String[]{"2012-09-01 18:00", "2012-09-01 18:10", 
    										"2012-09-01 18:11", "2012-09-01 18:20", 
    										"2012-09-01 18:30", "2012-09-01 18:35", 
    										"2012-09-01 18:40", "2012-09-01 18:50"}; 
    private final static int COUNT = 1;
    public void initData()
    {
    	for(int i = 0; i < COUNT; i++)
    	{
    		ChatMsgEntity entity = new ChatMsgEntity();
    		entity.setDate(dataArray[i]);
    		if (i % 2 == 0)
    		{
    			entity.setName("我的广告屏");
    			entity.setMsgType(true);
    		}else{
    			entity.setName("我的手机");
    			entity.setMsgType(false);
    		}
    		
    		entity.setText(msgArray[i]);
    		mDataArrays.add(entity);
    	}

    	mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
		
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.btn_send:
			send();
			send_SMS_Along();
			break;
		case R.id.btn_back:
			finish();
			break;
		}
	}
	
	private void send()
	{
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0)
		{
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(getDate());
			entity.setName("我的手机");
			entity.setMsgType(false);
			entity.setText(contString);
			
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			
			//mEditTextContent.setText("");
			
			mListView.setSelection(mListView.getCount() - 1);
			
		}
	}
	public void send_SMS_Along() {      //
		TextView numberText = (TextView)findViewById(R.id.number_single);
		String content = mEditTextContent.getText().toString();
		
		String numbers = numberText.getText().toString();

		SmsManager smsManager = SmsManager.getDefault();
		ArrayList<String> texts = smsManager.divideMessage(content);
		String[] number = numbers.split(";");
		if((!numbers.isEmpty()) & (!content.isEmpty())){
			for(int i=0; i<number.length; i++){
				for(String text: texts){
					smsManager.sendTextMessage(number[i], null, text, null, null);
				}
			}
			mEditTextContent.setText("");
			Toast.makeText(ChatActivity.this, R.string.success, Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(ChatActivity.this, R.string.SMSfail, Toast.LENGTH_LONG).show();
		}
      }  
    private String getDate() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));
        
        
        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins); 
        						
        						
        return sbBuffer.toString();
    }
    
    
    public void head_xiaohei(View v) {     //标题栏 返回按钮
    	Intent intent = new Intent (ChatActivity.this,InfoXiaohei.class);			
		startActivity(intent);	
      } 
}