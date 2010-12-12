package se.petteri.vemringer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VemRinger extends Activity {
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);      
    }
    
    public void onClick(View view){
    	
    	switch (view.getId()) {
		case R.id.StartButton:
			startService(new Intent(this, VemRingerService.class));
			break;

		case R.id.StopButton:
			stopService(new Intent(this, VemRingerService.class));
			break;
		}
    }
}