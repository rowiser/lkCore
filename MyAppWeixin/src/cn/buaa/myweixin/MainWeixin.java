package cn.buaa.myweixin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xu.ye.bean.SMSBean;
import xu.ye.uitl.BaseIntentUtil;
import xu.ye.uitl.RexseeSMS;
import xu.ye.view.sms.MessageBoxList;
import cn.buaa.myweixin.MainActivity.ButtonClickListener;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainWeixin extends Activity {
	
	public static MainWeixin instance = null;
	 
	private ViewPager mTabPager;	
	private ImageView mTabImg;// 动画图片
	private ImageView mTab1,mTab2,mTab3,mTab4;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;//单个水平动画位移
	private int two;
	private int three;
	private LinearLayout mClose;
    private LinearLayout mCloseBtn;
    private View layout;	
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;
	//private Button mRightBtn;
//	private EditText numberText;
//	private EditText contentText;
	
	private ListView listView;
	private HomeSMSAdapter adapter;
	private RexseeSMS rsms;
	private Button newSms;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_weixin);
        				
	//	send_SMS_ALL = (Button)this.findViewById(R.id.send_SMS_button1);		
	//	send_SMS_ALL.setOnClickListener(new ButtonClickListener());	
      	//启动activity时不自动弹出软键盘
      //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        instance = this;
        /*
        mRightBtn = (Button) findViewById(R.id.right_btn);
        mRightBtn.setOnClickListener(new Button.OnClickListener()
		{	@Override
			public void onClick(View v)
			{	showPopupWindow (MainWeixin.this,mRightBtn);
			}
		  });*/
        
        mTabPager = (ViewPager)findViewById(R.id.tabpager);
        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
        
        mTab1 = (ImageView) findViewById(R.id.img_weixin);
        mTab2 = (ImageView) findViewById(R.id.img_address);
        mTab3 = (ImageView) findViewById(R.id.img_friends);
        mTab4 = (ImageView) findViewById(R.id.img_settings);
        mTabImg = (ImageView) findViewById(R.id.img_tab_now);
        mTab1.setOnClickListener(new MyOnClickListener(0));
        mTab2.setOnClickListener(new MyOnClickListener(1));
        mTab3.setOnClickListener(new MyOnClickListener(2));
        mTab4.setOnClickListener(new MyOnClickListener(3));
        Display currDisplay = getWindowManager().getDefaultDisplay();//获取屏幕当前分辨率
        int displayWidth = currDisplay.getWidth();
        int displayHeight = currDisplay.getHeight();
        one = displayWidth/4; //设置水平动画平移大小
        two = one*2;
        three = one*3;
        //Log.i("info", "获取的屏幕分辨率为" + one + two + three + "X" + displayHeight);
        
        //InitImageView();//使用动画
      //将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.main_tab_weixin, null);
        View view2 = mLi.inflate(R.layout.main_tab_address, null);
        View view3 = mLi.inflate(R.layout.main_tab_friends, null);
        View view4 = mLi.inflate(R.layout.main_tab_settings, null);
        
      //每个页面的view数据
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
      //填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}
			
			//@Override
			//public CharSequence getPageTitle(int position) {
				//return titles.get(position);
			//}
			
			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};
		
		mTabPager.setAdapter(mPagerAdapter);
    }
    
	public void init(){

		setContentView(R.layout.main_tab_weixin);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		
		listView = (ListView) findViewById(R.id.list);
		adapter = new HomeSMSAdapter(MainWeixin.this);
		
		rsms = new RexseeSMS(MainWeixin.this);
		List<SMSBean> list_mmt = rsms.getThreadsNum(rsms.getThreads(0));
		adapter.assignment(list_mmt);

		listView.setAdapter(adapter);
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String, String> map = new HashMap<String, String>();
				SMSBean sb = adapter.getItem(position);
				map.put("phoneNumber", sb.getAddress());
				map.put("threadId", sb.getThread_id());
				BaseIntentUtil.intentSysDefault(MainWeixin.this, MessageBoxList.class, map);
			}
		});
		
	/*	
		newSms = (Button) findViewById(R.id.newSms);
		newSms.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BaseIntentUtil.intentSysDefault(MainWeixin.this, NewSMSActivity.class, null);
			}
		});*/
		
		
	}
    /**
     * 
     * 
	 * 头标点击监听
	 * 
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}
		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};
    
	 /* 页卡切换监听(原作者:D.Winter)
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_pressed));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_normal));
				}
				else if (currIndex == 3) {
					animation = new TranslateAnimation(three, 0, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_normal));
				}
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_normal));
				}
				else if (currIndex == 3) {
					animation = new TranslateAnimation(three, one, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_normal));
				}
				break;
			case 2:
				mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, two, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_normal));
				}
				else if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_normal));
				}
				break;
			case 3:
				mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_settings_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, three, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_weixin_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, three, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_address_normal));
				}
				else if (currIndex == 2) {
					animation = new TranslateAnimation(two, three, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_find_frd_normal));
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(150);
			mTabImg.startAnimation(animation);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  //获取 back键
    		
        	if(menu_display){         //如果 Menu已经打开 ，先关闭Menu
        		menuWindow.dismiss();
        		menu_display = false;
        		}
        	else {
        		Intent intent = new Intent();
            	intent.setClass(MainWeixin.this,Exit.class);
            	startActivity(intent);
        	}
    	}
    	
    	else if(keyCode == KeyEvent.KEYCODE_MENU){   //获取 Menu键			
			if(!menu_display){
				//获取LayoutInflater实例
				inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
				//这里的main布局是在inflate中加入的哦，以前都是直接this.setContentView()的吧？呵呵
				//该方法返回的是一个View的对象，是布局中的根
				layout = inflater.inflate(R.layout.main_menu, null);
				
				//下面我们要考虑了，我怎样将我的layout加入到PopupWindow中呢？？？很简单
				menuWindow = new PopupWindow(layout,LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT); //后两个参数是width和height
				//menuWindow.showAsDropDown(layout); //设置弹出效果
				//menuWindow.showAsDropDown(null, 0, layout.getHeight());
				menuWindow.showAtLocation(this.findViewById(R.id.mainweixin), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
				//如何获取我们main中的控件呢？也很简单
				mClose = (LinearLayout)layout.findViewById(R.id.menu_close);
				mCloseBtn = (LinearLayout)layout.findViewById(R.id.menu_close_btn);
				
				
				//下面对每一个Layout进行单击事件的注册吧。。。
				//比如单击某个MenuItem的时候，他的背景色改变
				//事先准备好一些背景图片或者颜色
				mCloseBtn.setOnClickListener (new View.OnClickListener() {					
					@Override
					public void onClick(View arg0) {						
						//Toast.makeText(Main.this, "退出", Toast.LENGTH_LONG).show();
						Intent intent = new Intent();
			        	intent.setClass(MainWeixin.this,Exit.class);
			        	startActivity(intent);
			        	menuWindow.dismiss(); //响应点击事件之后关闭Menu
					}
				});				
				menu_display = true;				
			}else{
				//如果当前已经为显示状态，则隐藏起来
				menuWindow.dismiss();
				menu_display = false;
				}
			
			return false;
		}
    	return false;
    }
	//设置标题栏右侧按钮的作用
	public void btnmainright(View v) {  
		//Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
		//startActivity(intent);	
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }  	
	public void startchat(View v) {      //小黑  对话界面
		Intent intent = new Intent (MainWeixin.this,ChatActivity.class);			
		startActivity(intent);	
		//Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
      }  
	public void exit_settings(View v) {                           //退出  伪“对话框”，其实是一个activity
		Intent intent = new Intent (MainWeixin.this,ExitFromSettings.class);			
		startActivity(intent);	
	 }
	public void btn_shake(View v) {                                   //手机摇一摇
		Intent intent = new Intent (MainWeixin.this,ShakeActivity.class);			
		startActivity(intent);	
	}
	
	public void send_SMS_ALL(View v) {      //
		EditText numberText = (EditText)this.findViewById(R.id.number);
		EditText contentText = (EditText)this.findViewById(R.id.content);
		
		String numbers = numberText.getText().toString();
		String content = contentText.getText().toString();
		SmsManager smsManager = SmsManager.getDefault();
		ArrayList<String> texts = smsManager.divideMessage(content);
		String[] number = numbers.split(";");
		if((!numbers.isEmpty()) & (!content.isEmpty())){
			for(int i=0; i<number.length; i++){
				for(String text: texts){
					smsManager.sendTextMessage(number[i], null, text, null, null);
				}
			}
			Toast.makeText(MainWeixin.this, R.string.success, Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(MainWeixin.this, R.string.SMSfail, Toast.LENGTH_LONG).show();
		}
		contentText.setText("");
		//Intent intent = new Intent (MainWeixin.this,MainActivity.class);			
		//startActivity(intent);	
		//Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
      }  
	public void send_ANI_ALL(View v) {      //
		EditText numberText = (EditText)this.findViewById(R.id.number);
		
		TextView argText1 = (TextView)this.findViewById(R.id.LED_Ani_type);
		TextView argText2 = (TextView)this.findViewById(R.id.LED_Speed_type);
		TextView argText3 = (TextView)this.findViewById(R.id.LED_Frame_type);
		
		String argAni = argText1.getText().toString();
		String argSpeed = argText2.getText().toString();
		String argFrame = argText3.getText().toString();
		
		String numbers = numberText.getText().toString();
		SmsManager smsManager = SmsManager.getDefault();
		String content = "#" + "set" + "#"  + argAni + "#" + argSpeed + "#"  +  argFrame + "#";
		ArrayList<String> texts = smsManager.divideMessage(content);
		String[] number = numbers.split(";");
		if(!numbers.isEmpty()){
			for(int i=0; i<number.length; i++){
				for(String text: texts){
					smsManager.sendTextMessage(number[i], null, text, null, null);
				}
			}
			Toast.makeText(MainWeixin.this, R.string.success, Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(MainWeixin.this, R.string.fail, Toast.LENGTH_LONG).show();
		}
      }  
	//send_SET_ALL
	public void send_HDW_ALL(View v) {      //
		EditText numberText = (EditText)this.findViewById(R.id.number2);
		
		EditText argWidth = (EditText)this.findViewById(R.id.LED_Width_dot);
		EditText argHeight = (EditText)this.findViewById(R.id.LED_Height_dot);
		TextView argText1 = (TextView)this.findViewById(R.id.LED_Board_type);
		TextView argText2 = (TextView)this.findViewById(R.id.LED_Eth_type);
		TextView argText3 = (TextView)this.findViewById(R.id.LED_OE_type);
		
		String argW = argWidth.getText().toString();
		String argH = argHeight.getText().toString();
		String argT1 = argText1.getText().toString();
		String argT2 = argText2.getText().toString();
		String argT3 = argText3.getText().toString();
		
		String numbers = numberText.getText().toString();
		SmsManager smsManager = SmsManager.getDefault();
		String content = "#" + "hdw" + "#"  + argW + "#" + argH + "#"  +  argT1 + "#" +  argT2 + "#" +  argT3 + "#";
		ArrayList<String> texts = smsManager.divideMessage(content);
		String[] number = numbers.split(";");
		if(!numbers.isEmpty()){
			for(int i=0; i<number.length; i++){
				for(String text: texts){
					smsManager.sendTextMessage(number[i], null, text, null, null);
				}
			}
			Toast.makeText(MainWeixin.this, R.string.success, Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(MainWeixin.this, R.string.fail, Toast.LENGTH_LONG).show();
		}
      }  
	//send_Admin_ALL
	public void send_Admin_ALL(View v) {      //
		EditText numberText = (EditText)this.findViewById(R.id.number2);
		EditText contentText = (EditText)this.findViewById(R.id.adminNumber);
		
		//获取本机号码,这种方法新卡获取不了，移动公司没有写入SIM卡
	    //TelephonyManager mTelephonyMgr;   
	    //mTelephonyMgr = (TelephonyManager)  getSystemService(Context.TELEPHONY_SERVICE);    
			    	    
		String numbers = numberText.getText().toString();
		String admNumber = contentText.getText().toString();
		String content = "#" + "adm" + "#"  + admNumber + "#";  
		SmsManager smsManager = SmsManager.getDefault();
		ArrayList<String> texts = smsManager.divideMessage(content);
		String[] number = numbers.split(";");
		if((!numbers.isEmpty()) & (!admNumber.isEmpty())){
			for(int i=0; i<number.length; i++){
				for(String text: texts){
					smsManager.sendTextMessage(number[i], null, text, null, null);
				}
			}
			Toast.makeText(MainWeixin.this, R.string.success, Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(MainWeixin.this, R.string.SMSfail, Toast.LENGTH_LONG).show();
		}
      }  
	public void add_phone_number(View v) {      //
		EditText numberText = (EditText)this.findViewById(R.id.number);
		
		//Intent intent = new Intent();
		Intent intent = new Intent (MainWeixin.this,contactActivity.class);
		intent.setClassName("cn.buaa.myweixin", "cn.buaa.myweixin.contactActivity");
		Bundle bundle = new Bundle();
		bundle.putString("number", numberText.getText().toString());
		intent.putExtras(bundle);
		startActivityForResult(intent, 200);
		
		//Intent intent = new Intent (MainWeixin.this,contactActivity.class);			
		//startActivity(intent);	
		//Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
      }  
	

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		EditText numberText = (EditText)this.findViewById(R.id.number);
		EditText numberText2 = (EditText)this.findViewById(R.id.number2);
		EditText contentText = (EditText)this.findViewById(R.id.content);
		try{
			String result = data.getStringExtra("result");
			numberText.setText(result);
			numberText2.setText(result);
		}catch(Exception e){
			
		}
	}
	//LED_Ani_type
	public void LED_Ani_type(View v) {      //
		final TextView contentText = (TextView)this.findViewById(R.id.LED_Ani_type);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainWeixin.this);
        //builder.setIcon(R.drawable.icon);
        builder.setTitle("请选择移动方式");
        //    指定下拉列表的显示数据
        final String[] cities = {"向左移动", "静止显示"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
            	contentText.setText(cities[which]);
            	contentText.setTextColor(android.graphics.Color.GRAY);
                Toast.makeText(MainWeixin.this, "选择的动画效果为：" + cities[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
      }  
	
	//LED_Speed_type
	public void LED_Speed_type(View v) {
		final TextView contentText = (TextView)this.findViewById(R.id.LED_Speed_type);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainWeixin.this);
        //builder.setIcon(R.drawable.icon);
        builder.setTitle("请选择移动速度");
        //    指定下拉列表的显示数据
        final String[] cities = { "静止","超低速", "低速", "中低速", "中速", "中高速", "高速", "超高速"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
            	contentText.setText(cities[which]);
            	contentText.setTextColor(android.graphics.Color.GRAY);
                Toast.makeText(MainWeixin.this, "选择的速度为：" + cities[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
      }  
	
	//LED_Frame_type
	public void LED_Frame_type(View v) {      //
		final TextView contentText = (TextView)this.findViewById(R.id.LED_Frame_type);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainWeixin.this);
        //builder.setIcon(R.drawable.icon);
        builder.setTitle("请选择边框特效");
        //    指定下拉列表的显示数据
        final String[] cities = {"没有边框", "线性"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
            	contentText.setText(cities[which]);
            	contentText.setTextColor(android.graphics.Color.GRAY);
                Toast.makeText(MainWeixin.this, "选择的边框效果为：" + cities[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
      }  
	//send_SET_ALL
	
}
    
    

