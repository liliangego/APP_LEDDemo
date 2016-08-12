package com.example.liliang.app_0001_leddemo;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
//import com.example.liliang.hardlibrary.*;
import android.os.ILedService;
import android.os.ServiceManager;


public class MainActivity extends AppCompatActivity {

    private Boolean ledon = false;
    private Button button = null;
    private CheckBox checkBoxLED1 = null;
    private CheckBox checkBoxLED2 = null;
    private CheckBox checkBoxLED3 = null;
    private CheckBox checkBoxLED4 = null;
    private ILedService iLedService = null;

    class MyButtonListener implements View.OnClickListener{
        private int i;
        @Override
        public void onClick(View v) {
            //iLedSevice iLedSevice = new iLedSevice();
            ledon = !ledon;
            if(ledon) {
                button.setText("ALL OFF");
                checkBoxLED1.setChecked(true);
                checkBoxLED2.setChecked(true);
                checkBoxLED3.setChecked(true);
                checkBoxLED4.setChecked(true);
                for(i = 0; i < 4; i++ )
                    try {
                        iLedService.ledCtrl(i, 1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

            }
            else {
                button.setText("ALL ON");
                checkBoxLED1.setChecked(false);
                checkBoxLED2.setChecked(false);
                checkBoxLED3.setChecked(false);
                checkBoxLED4.setChecked(false);
                for(i = 0; i < 4; i++ )
                    try {
                        iLedService.ledCtrl(i, 0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
            }

        }
    }
    /* Clicked  Checkbox 处理函数 */
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.LED1:
                if (checked) {
                    // Put some meat on the sandwich
                    Toast.makeText(getApplicationContext(),"LED1 ON", Toast.LENGTH_SHORT).show();
                    try {
                        iLedService.ledCtrl(0, 1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }else{
                // Remove the meat
                    Toast.makeText(getApplicationContext(),"LED1 OFF", Toast.LENGTH_SHORT).show();
                    try {
                        iLedService.ledCtrl(0, 1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.LED2:
                if (checked) {
                    // Put some meat on the sandwich
                    Toast.makeText(getApplicationContext(),"LED2 ON", Toast.LENGTH_SHORT).show();
                    //iLedSevice.ledCtrl(1, 1);
                }else{
                    // Remove the meat
                    Toast.makeText(getApplicationContext(),"LED2 OFF", Toast.LENGTH_SHORT).show();
                    try {
                        iLedService.ledCtrl(1, 1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.LED3:
                if (checked) {
                    // Put some meat on the sandwich
                    Toast.makeText(getApplicationContext(),"LED3 ON", Toast.LENGTH_SHORT).show();
                   // iLedSevice.ledCtrl(2, 1);
                }else{
                    // Remove the meat
                    Toast.makeText(getApplicationContext(),"LED3 OFF", Toast.LENGTH_SHORT).show();
                    try {
                        iLedService.ledCtrl(2, 1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.LED4:
                if (checked) {
                    // Put some meat on the sandwich
                    Toast.makeText(getApplicationContext(),"LED4 ON", Toast.LENGTH_SHORT).show();
                    try {
                        iLedService.ledCtrl(3, 1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }else{
                    // Remove the meat
                    Toast.makeText(getApplicationContext(),"LED4 OFF", Toast.LENGTH_SHORT).show();
                    try {
                        iLedService.ledCtrl(3, 1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            // TODO: Veggie sandwich
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        /*  Button */
        button = (Button) findViewById(R.id.BUTTON);
        //iLedSevice.ledOpen();
        iLedService = ILedService.Stub.asInterface(ServiceManager.getService("led"));
        
        checkBoxLED1 = (CheckBox) findViewById(R.id.LED1); /*  LED */
        checkBoxLED2 = (CheckBox) findViewById(R.id.LED2);
        checkBoxLED3 = (CheckBox) findViewById(R.id.LED3);
        checkBoxLED4 = (CheckBox) findViewById(R.id.LED4);
        button.setOnClickListener(new MyButtonListener());
/*
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                ledon = !ledon;
                if(ledon)
                    button.setText("ALL OFF");
                else
                    button.setText("ALL ON");
            }
        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
