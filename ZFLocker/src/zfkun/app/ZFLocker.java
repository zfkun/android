package zfkun.app;

import zfkun.receiver.ZFAdminReceiver;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ZFLocker extends Activity {
	
	private static final String TAG = "ZFLocker";
	private static final int RESULT_ENABLE = 1;
	
//	private ActivityManager _am;
	private DevicePolicyManager _dpm;
	private ComponentName _componentName;
//	private Button _lockBtn;
	private Button _adminBtn;

	
	/**
	 * init manager
	 */
	private void initManager() {
//		_am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		_dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		_componentName = new ComponentName(this, ZFAdminReceiver.class);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// check root
		if (ActivityManager.isUserAMonkey()) {
			toast("非管理员模式，不能锁屏，赶紧Root吧。");
			return;
		}
		
		// init managers
		initManager();
		
		// init title
		setTitle(R.string.title);
		
		// to lock
		if (isAdminActived()) { // auto lock
			lockScreen();
		} else { // need device admin
			// show layout
			setContentView(R.layout.main);
			
			// show deviceadmin request btn
			_adminBtn = (Button) findViewById(R.id.device_admin);
			if (_adminBtn != null) {
				_adminBtn.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						deviceAdminChange(true);
					}					
				});
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_ENABLE) {
			if (resultCode == Activity.RESULT_OK) {
				Log.d(TAG, "deviceAdmin ok");
				lockScreen();
			} else {
				toast("设备管理权限申请失败");
				Log.e(TAG, "deviceAdmin fail");
			}
		}
	}
	
	private boolean isAdminActived() {
		return _dpm.isAdminActive(_componentName);
	}
	
	private void lockScreen() {
		_dpm.lockNow();
		finish();
	}
	
	private void deviceAdminChange(boolean isOn) {
		if (isOn) {
//			if (!isAdminActived()) {
				// request device admin
				Intent it = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
				it.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, _componentName);
				it.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "我是影之迷惑，相信我，你懂的！");
				startActivityForResult(it, RESULT_ENABLE);
//			}
		} else {
			_dpm.removeActiveAdmin(_componentName);
		}
	}

	private void toast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	
}
