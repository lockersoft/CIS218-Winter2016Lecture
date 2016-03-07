package com.lockersoft.BMI;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Calendar;

/**
 * Created by Dave.Jones on 3/1/2016.
 */
public class Alarm {

  public Intent alarmIntent;
  public PendingIntent pi;
  public EditText notesText, dateText;
  public Switch recurring, active;
  public Integer alarmID;
  public Context context;
  public Calendar cal;

  public Alarm( EditText notesText, EditText dateText, Switch recurring, Switch active, Integer alarmID, Context context, Calendar cal ){
    this.notesText = notesText;
    this.dateText = dateText;
    this.recurring = recurring;
    this.active = active;
    this.alarmID = alarmID;
    this.context = context;
    this.cal = cal;

    this.alarmIntent = new Intent( "com.lockersoft.BMI" );
    this.alarmIntent.putExtra( "alarm", this.alarmID );
    this.pi = PendingIntent.getBroadcast( context, this.alarmID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT );
    setTags();
  }

  @Override
  public String toString(){
    return "Alarm{" +
        "alarmIntent=" + alarmIntent +
        ", pi=" + pi +
        ", notesText=" + notesText +
        ", dateText=" + dateText +
        ", recurring=" + recurring +
        ", active=" + active +
        ", alarmID=" + alarmID +
        ", context=" + context +
        ", cal=" + cal +
        '}';
  }

  public void setTags(){
    notesText.setTag( this );
    dateText.setTag( this );
    recurring.setTag( this );
    active.setTag( this );
  }
}
