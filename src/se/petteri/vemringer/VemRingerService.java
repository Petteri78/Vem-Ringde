package se.petteri.vemringer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class VemRingerService extends Service {

	private static final String VEM_RINGER_SERVICE = "VemRingerService";
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		Log.d(VEM_RINGER_SERVICE, "onCreate");
		TelephonyManager mTelephonyMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		mTelephonyMgr.listen(new VemRingerListener(this), PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Vem Ringer Stoppad", Toast.LENGTH_LONG).show();
		Log.d(VEM_RINGER_SERVICE, "onDestroy");
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "Vem Ringer startad", Toast.LENGTH_LONG).show();
		Log.d(VEM_RINGER_SERVICE, "onStart");
	}
}
