package com.lockersoft.BMI;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Dave.Jones on 1/20/2016.
 */
public class BaseActivity extends Activity {

  String delimiter = "<=>";

  String weightLogFilename = "weightLog.txt";
  public void toastIt( String msg ){
    Toast.makeText( getApplicationContext(),
        msg, Toast.LENGTH_SHORT).show();
  }
}
