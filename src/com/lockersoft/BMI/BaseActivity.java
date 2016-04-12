package com.lockersoft.BMI;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Dave.Jones on 1/20/2016.
 */
public class BaseActivity extends Activity{

  String delimiter = "<=>";
  String weightLogFilename = "weightLog.txt";
  public static String drEmail;

  @Override
  public boolean onCreateOptionsMenu( Menu menu ){
    getMenuInflater().inflate( R.menu.mastermenu, menu );
    return true;
  }

  @Override
  public boolean onOptionsItemSelected( MenuItem item ){
    Log.i( "BMI", item.getTitle().toString() );
    switch( item.getItemId() ){
      case R.id.switchToPreferences :
        switchToPreferences( null );
        break;
      case R.id.switchToBMI:
        switchToBMI( null );
        break;
      case R.id.switchToLog:
        switchToLogger( null );
        break;
      case R.id.switchToChart:
        switchToChart( null );
        break;
      case R.id.switchToAlarm:
        switchToAlarm( null );
        break;
      default:
        return super.onOptionsItemSelected( item );
    }
    return true;
  }

  public void switchToChart( View v ){
    startActivity( new Intent( this, DavesChart.class ) );
  }
  public void switchToAlarm( View v ){
    startActivity( new Intent( this, AlarmActivity.class ) );
  }
  public void switchToBMI( View v ){
    startActivity( new Intent( this, MyActivity.class ) );
  }
  public void switchToPreferences( View v ){
    startActivity( new Intent( this, Preferences.class ) );
  }

  public void switchToLogger(View v) {
    Intent extras = new Intent(this, WeightLogActivity.class);
    extras.setFlags( extras.FLAG_ACTIVITY_CLEAR_TASK );
    extras.putExtra("Weight", MyActivity.edtTextWeight.getText().toString());
    extras.putExtra("BMI", MyActivity.bmi);
    startActivity(extras);
  }

  public void toastIt( String msg ){
    Toast.makeText( getApplicationContext(),
        msg, Toast.LENGTH_SHORT ).show();
  }
  
}
