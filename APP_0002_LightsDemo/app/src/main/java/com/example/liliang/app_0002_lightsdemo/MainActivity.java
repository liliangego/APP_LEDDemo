package com.example.liliang.app_0002_lightsdemo;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import android.app.NotificationManager;
import android.app.Notification;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    /* 定义Button的变量 */
    private Button mLightButton = null;
    private boolean bLightFlashing = false;  /* 闪烁标志位 */
    private Handler mLightHandler = new Handler();
    int LED_NOTIFICATION_ID = 123;

    private LightRunnable mLightRunnable = new LightRunnable();

    private SeekBar mBacklightseekBar = null;


    /* 快捷键 : ctrl + o */
    class LightRunnable implements Runnable {
        @Override
        public void run() {
            if (bLightFlashing) {
                /* 发出通知 */
                FlashingLight();
            } else {
                ClearLED();
            }
        }
    };

    private void FlashingLight()
    {
        NotificationManager nm = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE );
        Notification notif = new Notification();
        notif.flags = Notification.FLAG_SHOW_LIGHTS;
        notif.ledARGB   = 0xFF0000ff;
        notif.ledOnMS   = 100;
        notif.ledOffMS  = 100;
        nm.notify(LED_NOTIFICATION_ID, notif);
    }

    private void ClearLED()
    {
        NotificationManager nm = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE);
        nm.cancel( LED_NOTIFICATION_ID );
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
        /* onCreate方法里面获得 mLightButton */
        mLightButton = (Button) findViewById(R.id.button);
        mLightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                bLightFlashing = !bLightFlashing;

                if(bLightFlashing) {
                    mLightButton.setText("Stop flashing light");
                }
                else{
                    mLightButton.setText("Flashing light at 20s");
                }
                /* 20s 后执行mLightRunnable中的run方法 */
                mLightHandler.postDelayed(mLightRunnable, 20000);
            }
        });



        try {
            /* 关闭自动调光模式 */
            Settings.System.putInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);

            /* 当前值是50, 并反映亮度的情况 */
            int brightness = Settings.System.getInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS); /* 取出数据库里面的亮度值 范围 : 0~255*/
            mBacklightseekBar.setProgress(brightness * 100 / 255); /* 反映亮度的情况  值范围是0~100 */
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        /* 让 mBacklightseekBar指向控件 */
        mBacklightseekBar = (SeekBar) findViewById(R.id.backlightControl);
        mBacklightseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                /* 根据滑动块的值调整亮度 */
                int brightness = mBacklightseekBar.getProgress();/* 取出当前值,设置是50 */
                brightness = brightness * 255 / 100; /* 得到亮度值 */
                /* 设置亮度 */
                android.provider.Settings.System.putInt(getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS,
                        brightness);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


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
