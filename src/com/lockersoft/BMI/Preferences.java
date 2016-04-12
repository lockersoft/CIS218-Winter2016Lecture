package com.lockersoft.BMI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

/**
 * Created by Dave.Jones on 4/5/2016.
 */
public class Preferences extends BaseActivity {

  Button lockersoft;
  EditText editDrEmail;

  @Override
  public void onCreate( Bundle savedInstanceState ){
    super.onCreate( savedInstanceState );
    setContentView( R.layout.preferences );
    lockersoft = (Button)findViewById( R.id.btnLockersoft );
    editDrEmail = (EditText)findViewById( R.id.editDrEmail );
  }

  public void clickLockersoft( View v ){
    Intent browserIntent = new Intent( Intent.ACTION_VIEW,
        Uri.parse("http://battlegameserver.com"));
    startActivity( browserIntent );
  }

  public void savePreferencesClick( View v ){
    drEmail = editDrEmail.getText().toString();
    savesPreferences();
    toastIt( "Preferences Saved: " + drEmail );
  }

  public void savesPreferences(){
    // Save preferences to a file
  }
}
