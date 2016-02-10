package com.lockersoft.BMI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * Created by Dave.Jones on 2/8/2016.
 */
public class DavesChart extends BaseActivity {

  @Override
  public void onCreate( Bundle savedInstanceState ){
    super.onCreate( savedInstanceState );
    setContentView( R.layout.daves_chart );

    ArrayList<BarEntry> entries = new ArrayList<>();
    entries.add( new BarEntry( 4f, 0 ));
    entries.add( new BarEntry( (float)6.0, 1 ));
    entries.add( new BarEntry( (float)3, 2 ));
    entries.add( new BarEntry( 45.0f, 3 ));
    entries.add( new BarEntry( 35.0f, 4 ));
    entries.add( new BarEntry( 24.0f, 5 ));

    BarDataSet dataSet = new BarDataSet( entries, "Weight");

    ArrayList<String> labels = new ArrayList<String>();
    labels.add( "Jan" );
    labels.add( "Feb" );
    labels.add( "Mar" );
    labels.add( "Apr" );
    labels.add( "May" );
    labels.add( "Jun" );

    HorizontalBarChart chart = new HorizontalBarChart( this );
    setContentView( chart );

    BarData data = new BarData( labels, dataSet );
    chart.setData( data );
    chart.setDescription( "How Fat I Am" );
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
