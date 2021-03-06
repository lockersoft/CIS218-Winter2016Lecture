package com.lockersoft.BMI;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.w3c.dom.Text;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WeightLogActivity extends BaseActivity{
  /**
   * Called when the activity is first created.
   */
  public static String weight;
  public static Double bmi;
  TextView edtDate;
  TextView edtTime;
  TextView lblBMIOutput;
  EditText edtWeight;
  private Calendar c = Calendar.getInstance();

  DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
    @Override
    public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth ){
      c.set( Calendar.YEAR, year );
      c.set( Calendar.MONTH, monthOfYear );
      c.set( Calendar.DAY_OF_MONTH, dayOfMonth );
      setCurrentDateOnView();
      new TimePickerDialog( WeightLogActivity.this, time, c.get(Calendar.HOUR),c.get( Calendar.MINUTE), false ).show();
    }
  };

  TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener(){
    @Override
    public void onTimeSet( TimePicker view, int hourOfDay, int minute ){
      c.set( Calendar.HOUR_OF_DAY, hourOfDay );
      c.set( Calendar.MINUTE, minute );
      setCurrentDateOnView();
    }
  };

  public void onCreate( Bundle savedInstanceState ){
    super.onCreate( savedInstanceState );
    setContentView( R.layout.weight_log );

    // bmi = savedInstanceState.getDouble( "BMI" );
    lblBMIOutput = (TextView) findViewById( R.id.lblBMIOutput );
    edtWeight = (EditText) findViewById( R.id.edtWeight );
    edtDate = (TextView) findViewById( R.id.edtDate );
    edtTime = (TextView) findViewById( R.id.edtTime );

    getIntentData();
    setCurrentDateOnView();
  }

  public void emailLogToDr( View v ){
    // Send the email
    toastIt( "Email sent to: " + drEmail );
    String subject = "Dave Jones' Weight Data";
    String message = "Dr. Heavy, \n\nHere are my weight logs for you to peruse.\n\nDave";

    // Create an intent to use the existing email system on the user's android.
    final Intent emailIntent = new Intent( Intent.ACTION_SEND );

    emailIntent.setType( "plain/text" );
    emailIntent.putExtra( Intent.EXTRA_EMAIL, new String[]{drEmail} );
    emailIntent.putExtra( Intent.EXTRA_CC, new String[]{"dave.jones@scc.spokane.edu"} );
    emailIntent.putExtra( Intent.EXTRA_BCC, new String[]{"dave.jones@scc.spokane.edu"} );
    emailIntent.putExtra( Intent.EXTRA_SUBJECT, subject );
    emailIntent.putExtra( Intent.EXTRA_TEXT, message );

    startActivityForResult( Intent.createChooser( emailIntent, "Send Email.." ), 42 );

  }
  public void dateOnClick( View v ){
    new DatePickerDialog( this, date,
        c.get( Calendar.YEAR ), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
  }

  public void timeOnClick( View v ){
    new TimePickerDialog( this, time, c.get(Calendar.HOUR),c.get( Calendar.MINUTE), false ).show();
  }

  private void getIntentData(){
    Bundle extras = getIntent().getExtras();
    if( extras != null ){
      weight = extras.getString( "Weight" );
      bmi = extras.getDouble( "BMI" );
      edtWeight.setText( weight );
      lblBMIOutput.setText( String.format( "%.2f", bmi ) );
    }
  }

  private void setCurrentDateOnView() {
   // String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat( "MM/dd/yyyy", Locale.US );
    edtDate.setText( sdf.format( c.getTime()) );

    SimpleDateFormat stf = new SimpleDateFormat( "hh:mm a", Locale.US );
    edtTime.setText( stf.format( c.getTime() ) );
  }

  public void saveData( View v ){
    String dataPoint =
                  edtDate.getText().toString() +
                      delimiter + edtTime.getText().toString() +
                      delimiter + edtWeight.getText().toString() +
                      delimiter + lblBMIOutput.getText().toString() + "\n";
    Log.i( "BMI", dataPoint );

    File file = getApplicationContext().getFileStreamPath(weightLogFilename);
    if(file != null || file.exists()) {
      Log.i("BMI", "Filename Exists");
    }
    try{
      FileOutputStream out = openFileOutput( weightLogFilename, Context.MODE_APPEND );
      out.write( dataPoint.getBytes() );
      out.close();
      toastIt( "Entry Saved" );
    } catch( Exception ex ){
      ex.printStackTrace();
    }
  }

  @Override
  protected void onResume(){
    super.onResume();
    getIntentData();
  }
 }