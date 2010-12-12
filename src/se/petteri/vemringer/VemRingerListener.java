package se.petteri.vemringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class VemRingerListener extends PhoneStateListener {

	private Service service;
	private Result result;
	private int previousState;

	public VemRingerListener(Service service) {
		this.service = service;
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		switch (state) {
		case TelephonyManager.CALL_STATE_RINGING:
			Log.d("", "RINGING");
			previousState = state;
			loadFromWeb(incomingNumber);
			break;
//		case TelephonyManager.CALL_STATE_IDLE:
//			if (TelephonyManager.CALL_STATE_RINGING == previousState) {
//				previousState = state;
//
//				if (!contactExists(incomingNumber) && result != null) {
//					addContact("test", incomingNumber);
//				}
//			}
//			break;
		}
	}

	void loadFromWeb(String number) {

		result = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://rkseek.oblivioncreations.se/?"
				+ number + "&out=json");
		HttpResponse response;

		try {
			response = client.execute(request);

			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line);
			}
			in.close();

			String graph = str.toString();
			JSONArray jsonArray = new JSONArray(graph);
			result = new Result(jsonArray);

			Toast.makeText(service, result.getName() + " Calling",
					Toast.LENGTH_LONG).show();

		} catch (ClientProtocolException e) {
			Log.e(e.getLocalizedMessage(), e.getMessage(), e);
		} catch (IOException e) {
			Log.e(e.getLocalizedMessage(), e.getMessage(), e);
		} catch (JSONException e) {
			Log.e(e.getLocalizedMessage(), e.getMessage(), e);
		}
	}

//	private boolean contactExists(String number) {
//		ContentResolver contentResolver = service.getContentResolver();
//		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
//				Uri.encode(number));
//		Cursor contacts = contentResolver.query(uri, null, null, null, null);
//		boolean exists = contacts.moveToNext();
//		return exists;
//	}
//
//	/*
//	 * Creates an Intent and adds the contact details to the bundle and then
//	 * starts the activity for the intent
//	 */
//	private void addContact(String name, String phone) {
//
//		Intent intent = new Intent();
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
//
//		intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
//		intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
//
//		service.startActivity(intent);
//	}
}
