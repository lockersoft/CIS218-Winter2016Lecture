package com.lockersoft.BMI;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
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

  @Override
  public void onCreate( Bundle savedInstanceState ){
    super.onCreate( savedInstanceState );
    setContentView( R.layout.alarmlayout );
    switchRecurring = (Switch) findViewById(R.id.switchRecurring );
    switchActive = (Switch) findViewById(R.id.switchEnabled );

    br = new BroadcastReceiver(){
      @Override
      public void onReceive( Context context, Intent intent ){
        toastIt( "Wake UP:" );
        createNotification();
      }
    };
    registerReceiver( br, new IntentFilter( getPackageName() ));//"com.lockersoft.BMI") );

    am = (AlarmManager)this.getSystemService( Context.ALARM_SERVICE );
    Intent alarmIntent = new Intent( getPackageName() );// "com.lockersoft.BMI" );
    PendingIntent pi = PendingIntent.getBroadcast( this, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT );
    c.add(Calendar.SECOND, 5);
    am.set( AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi );
  }

  public void activeOnClick( View v ){
    if( switchActive.isChecked() )
      toastIt( "Alarm is Active" );
    else
      toastIt( "Alarm is Off" );
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
