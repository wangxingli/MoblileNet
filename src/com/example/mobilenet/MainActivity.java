package com.example.mobilenet;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


//�򿪹ر��ƶ����磬���Բ�ͨ��
public class MainActivity extends Activity implements OnClickListener
{
	private Button mMobileDataButton;
	// �ƶ��������øı�ϵͳ���͵Ĺ㲥
	private static final String NETWORK_CHANGE = "android.intent.action.ANY_DATA_STATE";
	private TestChange mTestChange;
	private IntentFilter mIntentFilter;
	private boolean isMobileDataEnable;
	private ConnectivityManager cm;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		mTestChange = new TestChange();
		mIntentFilter = new IntentFilter();
		// ��ӹ㲥���������˵Ĺ㲥
		mIntentFilter.addAction("android.intent.action.ANY_DATA_STATE");

		mMobileDataButton = (Button) findViewById(R.id.mobile_data);
		refreshButton();
		mMobileDataButton.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() 
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		// ����㲥������
		unregisterReceiver(mTestChange);
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		// ע��㲥������
		registerReceiver(mTestChange, mIntentFilter);
	}

	private void refreshButton()
	{
		mMobileDataButton.setText(getMobileDataStatus() ? "�ر��ƶ�����" : "���ƶ�����");
	}

	//��ȡ�ƶ����ݿ���״̬
//	private boolean getMobileDataStatus()
//	{
//		String methodName = "getMobileDataEnabled";
//		Class cmClass = cm.getClass();
//		Boolean isOpen = null;
//		
//		try 
//		{
//			Method method = cmClass.getMethod(methodName, null);
//
//			isOpen = (Boolean) method.invoke(cm, null);
//		} 
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
//		return isOpen;
//	}
		
	//��ȡ�ƶ����ݿ���״̬
		private boolean getMobileDataStatus()
		{
			Object[] arg = null;
			try {
				isMobileDataEnable = invokeMethod("getMobileDataEnabled", arg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return isMobileDataEnable;
		}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if(!getMobileDataStatus()){
			setMobileNetEnable(true);
			mMobileDataButton.setText("�ر��ƶ�����");
			}
		else{
			setMobileNetEnable(false);
			mMobileDataButton.setText("���ƶ�����");
		}
		
	}

	private class TestChange extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) 
		{
			// TODO Auto-generated method stub
			String action = intent.getAction();

			if (NETWORK_CHANGE.equals(action))
			{
				Toast.makeText(MainActivity.this, "�ƶ����������иı�",
						Toast.LENGTH_SHORT).show();
			}
		}

	}
	
	public final void setMobileNetEnable(boolean isenable ) {

		Object[] arg = null;
		try {
				invokeBooleanArgMethod("setMobileDataEnabled", isenable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean invokeMethod(String methodName, Object[] arg)
			throws Exception {

		Class ownerClass = cm.getClass();

		Class[] argsClass = null;
		if (arg != null) {
			argsClass = new Class[1];
			argsClass[0] = arg.getClass();
		}

		Method method = ownerClass.getMethod(methodName, argsClass);
		Boolean isOpen = (Boolean) method.invoke(cm, arg);
		return isOpen;
	}

	public Object invokeBooleanArgMethod(String methodName, boolean value)
			throws Exception {

		Class ownerClass = cm.getClass();
		Class[] argsClass = new Class[1];
		argsClass[0] = boolean.class;

		Method method = ownerClass.getMethod(methodName, argsClass);

		return method.invoke(cm, value);
	}
	
	//3G����IP
//	public static String getIpAddress() {
//	try {
//	            for (Enumeration<NetworkInterface> en = NetworkInterface
//	                    .getNetworkInterfaces(); en.hasMoreElements();) {
//	                NetworkInterface intf = en.nextElement();
//	                for (Enumeration<InetAddress> enumIpAddr = intf
//	                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
//	                    InetAddress inetAddress = enumIpAddr.nextElement();
//	                    if (!inetAddress.isLoopbackAddress()
//	                            && inetAddress instanceof Inet4Address) {
//	                        // if (!inetAddress.isLoopbackAddress() && inetAddress
//	                        // instanceof Inet6Address) {
//	                        return inetAddress.getHostAddress().toString();
//	                    }
//	                }
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	        return null;
//	    }
}
