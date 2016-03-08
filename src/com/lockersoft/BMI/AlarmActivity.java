package com.lockersoft.BMI;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Calendar;

/**
 * Created by Dave.Jones on 2/25/2016.
 */
public class AlarmActivity extends BaseActivity{

  private AlarmManager am;
  Calendar c = Calendar.getInstance();
  private BroadcastReceiver br;
  private Switch switchRecurring;
  private Switch switchActive;
  private Alarm[] alarms = new Alarm[10];
  private EditText notesText, dateText;

  @Override
  public void onCreate( Bundle savedInstanceState ){
    super.onCreate( savedInstanceState );
    setContentView( R.layout.alarmlayout );
    switchRecurring = (Switch) findViewById(R.id.switchRecurring );
    switchActive = (Switch) findViewById(R.id.switchEnabled );
    notesText = (EditText)findViewById( R.id.editTextNotes );
    dateText = (EditText)findViewById( R.id.editTextDate );

    br = new BroadcastReceiver(){
      @Override
      public void onReceive( Context context, Intent intent ){
        toastIt( "Wake UP:" );

        Bundle extras = intent.getExtras();
        if (extras != null) {
          int alarmID = extras.getInt( "alarm" );
          if( alarms[alarmID].recurring.isChecked()){
            alarms[ alarmID ].cal.add( Calendar.HOUR, 24 );
            am.set( AlarmManager.RTC_WAKEUP, alarms[ alarmID ].cal.getTimeInMillis(), alarms[ alarmID ].pi );
          }
        }
        createNotification();
      }
    };
    registerReceiver( br, new IntentFilter( getPackageName() ));//"com.lockersoft.BMI") );
    am = (AlarmManager)this.getSystemService( Context.ALARM_SERVICE );
    alarms[0] = new Alarm( notesText, dateText, switchRecurring, switchActive, 0, this, Calendar.getInstance());
//    alarms[1] = new Alarm( notesText, dateText, switchRecurring, switchActive, 1, this, Calendar.getInstance());
//    alarms[2] = new Alarm( notesText, dateText, switchRecurring, switchActive, 2, this, Calendar.getInstance());
//    alarms[3] = new Alarm( notesText, dateText, switchRecurring, switchActive, 3, this, Calendar.getInstance());

// For reading in a loop
//    Resources res = getResources();
//    EditText tempNotes = (EditText) findViewById( res.getIdentifier ("notesText" + 1, "id", getPackageName()));
//    tempNotes.setText( a[0])
//    alarms[count] = new Alarm( ..., tempNotes, ...)
  }

  public void activeOnClick( View v ){
    Alarm a = (Alarm)v.getTag();
    Log.i( "BMI", v.getTag().toString());
    if( a.active.isChecked() ){   // switchActive_0, switchActive_1
      toastIt( "Alarm is Active" );
      alarms[a.alarmID].cal.add( Calendar.SECOND, 5);  // Replace with the alarm date!
      am.set( AlarmManager.RTC_WAKEUP, alarms[a.alarmID].cal.getTimeInMillis(), alarms[a.alarmID].pi );
    }
    else{
      toastIt( "Alarm is Off" );
    }
  }

  public void createNotification( ){
    Intent intent = new Intent( this, AlarmActivity.class );
    PendingIntent pIntent = PendingIntent.getActivity( this,0,intent,0 );
    Notification n = new Notification.Builder( this )
        .setContentTitle( "Medicine Alarm" )
        .setContentText( "Medicine Notes")
        .setSmallIcon( R.drawable.ic_alarm )
        .setContentIntent( pIntent )
        .build();

    NotificationManager notificationManager =
        (NotificationManager)getSystemService( NOTIFICATION_SERVICE );
    notificationManager.notify( 0,n );
  }
}
