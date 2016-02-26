package com.lockersoft.BMI;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.w3c.dom.Text;

public class MyActivity extends BaseActivity {
  /**
   * Called when the activity is first created.
   */

  Button calculateBMI;
  TextView txtBMIValue;
  EditText edtTextHeight;
  public static EditText edtTextWeight;
  TextView txtStatus;
  RadioButton rdoAmerican;
  RadioButton rdoWorld;
  RadioGroup weightLocationGroup;
  private boolean american = true;
  public static Double bmi = 0.0;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    calculateBMI = (Button) findViewById(R.id.btnCalculateBMI);
    txtBMIValue = (TextView) findViewById(R.id.txtBMIValue);
    edtTextHeight = (EditText) findViewById(R.id.edtTextHeight);
    edtTextWeight = (EditText) findViewById(R.id.edtTextWeight);
    txtStatus = (TextView) findViewById(R.id.txtStatus);
    rdoAmerican = (RadioButton) findViewById(R.id.rdoAmerican);
    rdoWorld = (RadioButton) findViewById(R.id.rdoWorld);
    weightLocationGroup = (RadioGroup) findViewById(R.id.weightLocationGroup);

    // Create focus listeners
    edtTextHeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        handleOnFocusChange(v, hasFocus);
      }
    });

    edtTextWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        handleOnFocusChange(v, hasFocus);
      }
    });
  }

  private void handleOnFocusChange(View v, boolean hasFocus) {
    if (hasFocus) {
      toastIt(v.toString() + " got the focus");
    } else {
      toastIt(v.toString() + " lost the focus");
    }
  }



  public void radioButtonClicked(View v) {
    Log.i("BMI", "Radio Button Clicked");
    int selectedId = weightLocationGroup.getCheckedRadioButtonId();
    RadioButton selected = (RadioButton) findViewById(selectedId);
    toastIt(selected.getText().toString());
//    if( rdoAmerican.isChecked()){
//      american = true;
//    } else {
//      american = false;
//    }
    american = rdoAmerican.isChecked();
    CalulateBMI(v);
  }

  public void CalulateBMI(View v) {
    // Get data from the textview
    try {
      Double height = Double.parseDouble(edtTextHeight.getText().toString());
      Double weight = Double.parseDouble(edtTextWeight.getText().toString());

      if (rdoAmerican.isChecked()) {
        weight += 20;
      }

      Log.i("BMI", getString( R.string.logInsideCalculateBMI) + weight.toString());
      // (weight / (height * height)) * 703.0
      bmi = (weight / (height * height)) * 703.0;
      txtBMIValue.setText(String.format("%.2f", bmi));

      txtStatus.setText(getStatus(bmi));
      txtStatus.setVisibility(View.VISIBLE);
    } catch (Exception ex) {
      toastIt(getString( R.string.youAreAnIdiot));
    }
  }

  private String getStatus(Double bmi) {
    String status = getString( R.string.Intheworldyouare);
    if (rdoAmerican.isChecked()) {
      status = "In American you are: " + status;
    }
//     status = rdoAmerican.isChecked() ?
//         "In American you are: " :
//         "In the World you are: ";
    // Set status
    if (bmi < 16.0) {
      status = getString(R.string.seriously_underweight); //getString(  ); R.string.seriously_underweight);
    } else if (bmi >= 16.0 && bmi < 18.0) {
      status = getString(R.string.underweight);  //getString( R.string.underweight );
    } else if (bmi >= 18.0 && bmi < 24.0) {
      status = getString( R.string.normalWeight); //getString( R.string. );
    } else if (bmi >= 24.0 && bmi < 29.0) {
      status = "overweight";// getString( R.string. );
    } else if (bmi >= 29.0 && bmi < 35.0) {
      status = "seriously_overweight"; //getString( R.string. );
    } else {
      status = "gravely_overweight"; //getString( R.string. );
    }

    return status;
  }

  @Override
  public void onPause() {
    super.onPause();
    //     savedState.putDouble( "BMI", bmi );
  }

  @Override
  protected void onStop() {
    super.onStop();
  }
}
