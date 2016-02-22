package com.lockersoft.BMI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by Dave.Jones on 2/8/2016.
 */
public class DavesChart extends BaseActivity{

  ArrayList<BarEntry> entries = new ArrayList<>();
  ArrayList<String> labels = new ArrayList<String>();
  private Calendar c = Calendar.getInstance();
  EditText edtStartDate;
  BarChart chart;
  boolean firstTime = true;

  DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
    @Override
    public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth ){
      c.set( Calendar.YEAR, year );
      c.set( Calendar.MONTH, monthOfYear );
      c.set( Calendar.DAY_OF_MONTH, dayOfMonth );
      setCurrentDateOnView();
    }
  };

  @Override
  public void onCreate( Bundle savedInstanceState ){
    super.onCreate( savedInstanceState );
    setContentView( R.layout.daves_chart );
    chart = (BarChart) findViewById( R.id.chartViewID );
    edtStartDate = (EditText) findViewById( R.id.edtStartDate );
    showChart();
    firstTime = false;
//
//    entries.add( new BarEntry( 4f, 0 ));
//    entries.add( new BarEntry( (float)6.0, 1 ));
//    entries.add( new BarEntry( (float)3, 2 ));
//    entries.add( new BarEntry( 45.0f, 3 ));
//    entries.add( new BarEntry( 35.0f, 4 ));
//    entries.add( new BarEntry( 24.0f, 5 ));



//    labels.add( "Jan" );
//    labels.add( "Feb" );
//    labels.add( "Mar" );
//    labels.add( "Apr" );
//    labels.add( "May" );
//    labels.add( "Jun" );

//    setContentView( chart );
//    addContentView( chart, null );
  }

  private void showChart(){
    labels.clear();
    entries.clear();
    chart.clear();
    // Filter the data
    readWeightLog();
    BarDataSet dataSet = new BarDataSet( entries, "Weight" );
    BarData data = new BarData( labels, dataSet );
    chart.setData(data);
    chart.setDescription("How Fat I Am");
  }

  public void chartDateOnClick( View v ){
    new DatePickerDialog( this, date,
        c.get( Calendar.YEAR ), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
  }

  private void setCurrentDateOnView() {
    // String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat( "MM/dd/yyyy", Locale.US );
    edtStartDate.setText( sdf.format( c.getTime()) );
    showChart();
  }

private void readWeightLog(){
  FileInputStream inputStream = null;

  try {
    inputStream = openFileInput( weightLogFilename );
    byte[] rawData = new byte[ inputStream.available() ];

    while( inputStream.read( rawData ) != -1 ){
    }
    // Read a line
    // convert date/time into dateobject
    // compare dates with the 2 inputs fields to make sure it is "between"
    // if it is add to the array
    // otherwise skip


    // rawData holds the entire log file
    Scanner s = new Scanner( new String( rawData ) );
    s.useDelimiter( "\\n" );
    int count = 0;
    while( s.hasNext() ){
      String temp = s.next();
      String a[] = temp.split(delimiter);
      // date = a[0], weight = a[2]
      // yAxis
      entries.add( new BarEntry( Float.parseFloat( a[2]), count ));
      count += 1;
      // xAxis
      labels.add( a[0] );
    }

    if( !firstTime ){
      entries.add( new BarEntry( 42.0f, count ) );
      labels.add( "fake" );
    }

    s.close();
  } catch( Exception e ){
    Log.e( "CHART", e.getMessage());
  } finally {
    if( inputStream != null ){
      try {
        inputStream.close();
      } catch( IOException e ){
        Log.e( "CHART", e.getMessage());
      }
    }
  }

}










/*
  private void initializeView(){
    paint = new Paint();
    paint.setColor( Color.BLUE);
    paint.setStrokeWidth( 2 );

    setContentView( new Panel( this ) );
  }

  class Panel extends View {

    public Panel( Context context ){
      super( context);
    }

    @Override
    public void onDraw( Canvas canvas ){
      canvas.drawColor( Color.WHITE );
      canvas.drawCircle( 300, 80, 30, paint );
    }
  }
  */
}
