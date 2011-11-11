package zfkun.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ZFAdminReceiver extends DeviceAdminReceiver {
	
	private static final String TAG = "ZFAdminReceiver";

	@Override
	public void onDisabled(Context context, Intent intent) {
		super.onDisabled(context, intent);
		Log.d(TAG, "ZFAdminReceiver disabled");
	}

	@Override
	public void onEnabled(Context context, Intent intent) {
		super.onEnabled(context, intent);
		Log.d(TAG, "ZFAdminReceiver enabled");
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}

}
