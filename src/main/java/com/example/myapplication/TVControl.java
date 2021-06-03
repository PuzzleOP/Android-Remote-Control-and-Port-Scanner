package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.InetAddress;


public class TVControl extends AppCompatActivity
{
    String paramStr = "a parameter";
    Runnable myRunnable = createRunnable(paramStr);

    private Runnable createRunnable(final String paramStr){

        Runnable aRunnable = new Runnable(){
            public void run(){
                try {
                    ExecuteCommand(paramStr);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        return aRunnable;

    }



    TextView mytext;
    static String IP = "";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -20;
        params.height = 1920;
        params.width = 1200;
        params.y = -10;

        this.getWindow().setAttributes(params);

        setContentView(R.layout.activity_t_v_control);


        Button btnInternet = (Button) findViewById(R.id.btnCheckInternet);
        Button btnHome = (Button) findViewById(R.id.btnHome);
        Button btnGuide = (Button) findViewById(R.id.btnGuide);
        Button btnOkay = (Button) findViewById(R.id.btnOkay);
        Button btnReturn = (Button) findViewById(R.id.btnReturn);
        Button btnChannelUp = (Button) findViewById(R.id.btnChnUp);
        Button btnChannelDown = (Button) findViewById(R.id.btnChnDown);
        Button btnVolumeUp = (Button) findViewById(R.id.btnVolUp);
        Button btnVolumeDown = (Button) findViewById(R.id.btnVolDown);
        Button btnMute = (Button) findViewById(R.id.btnMute);
        Button btnZero = (Button) findViewById(R.id.btnZero);
        Button btnOne = (Button) findViewById(R.id.btnOne);
        Button btnTwo = (Button) findViewById(R.id.btnTwo);
        Button btnThree = (Button) findViewById(R.id.btnThree);
        Button btnFour = (Button) findViewById(R.id.btnFour);
        Button btnFive = (Button) findViewById(R.id.btnFive);
        Button btnSix = (Button) findViewById(R.id.btnSix);
        Button btnSeven = (Button) findViewById(R.id.btnSeven);
        Button btnEight = (Button) findViewById(R.id.btnEight);
        Button btnNine = (Button) findViewById(R.id.btnNine);
        Button btnPower = (Button) findViewById(R.id.btnPower);
        Button btnSource = (Button) findViewById(R.id.btnHDMI1);
        Button btnTools = (Button) findViewById(R.id.btnTools);
        Button btnLeft = (Button) findViewById(R.id.btnLeft);
        Button btnUp = (Button) findViewById(R.id.btnUp);
        Button btnRight = (Button) findViewById(R.id.btnRight);
        Button btnDown = (Button) findViewById(R.id.btnDown);
        Button btnHDMI1 = (Button) findViewById(R.id.btnHDMI1);
        Button btnHDMI2 = (Button) findViewById(R.id.btnHDMI2);
        Button btnHDMI3 = (Button) findViewById(R.id.btnHDMI3);
        Button btnHDMI4 = (Button) findViewById(R.id.btnHDMI4);
        Button btnSettings = (Button) findViewById(R.id.btnSettings);
        Button btnConnect = (Button) findViewById(R.id.btnConnect);
        Button btnMenu = (Button) findViewById(R.id.btnMenu);

        final EditText et = (EditText) findViewById(R.id.editTextIP);

        btnMenu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                Intent menu = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(menu);
                finish();
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                IP = String.valueOf(et.getText());
                Snackbar mySnackbar = Snackbar.make(v, "Connected to " + IP, BaseTransientBottomBar.LENGTH_LONG);
                mySnackbar.show();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_MENU"));
                thread.start();
            }
        });

        btnHDMI1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_HDMI"));
                thread.start();
            }
        });

        btnHDMI2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_HDMI2"));
                thread.start();
            }
        });

        btnHDMI3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_HDMI3"));
                thread.start();
            }
        });

        btnHDMI4.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_HDMI4"));
                thread.start();
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_DOWN"));
                thread.start();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_RIGHT"));
                thread.start();
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_LEFT"));
                thread.start();
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_UP"));
                thread.start();
            }
        });

        btnTools.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_TOOLS"));
                thread.start();
            }
        });

        btnSource.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_PANNEL_SOURCE"));
                thread.start();
            }
        });

        btnPower.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_POWEROFF"));
                thread.start();
            }
        });

        btnZero.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_0"));
                thread.start();
            }
        });

        btnOne.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_1"));
                thread.start();
            }
        });

        btnTwo.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_2"));
                thread.start();
            }
        });

        btnThree.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_3"));
                thread.start();
            }
        });

        btnFour.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_4"));
                thread.start();
            }
        });

        btnFive.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_5"));
                thread.start();
            }
        });

        btnSix.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_6"));
                thread.start();
            }
        });

        btnSeven.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_7"));
                thread.start();
            }
        });

        btnEight.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_8"));
                thread.start();
            }
        });

        btnNine.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_9"));
                thread.start();
            }
        });

        btnVolumeUp.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_VOLUP"));
                thread.start();
            }
        });

        btnVolumeDown.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_VOLDOWN"));
                thread.start();
            }
        });

        btnChannelUp.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_CHUP"));
                thread.start();
            }
        });

        btnChannelDown.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_CHDOWN"));
                thread.start();
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_RETURN"));
                thread.start();
            }
        });

        btnOkay.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_ENTER"));
                thread.start();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_HOME"));
                thread.start();
            }
        });

        btnGuide.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_GUIDE"));
                thread.start();
            }
        });

        btnMute.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Thread thread = new Thread(createRunnable("KEY_MUTE"));
                thread.start();
            }
        });

        btnInternet.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String ssid = "";
                int ip = 0;
                String BSSID = "";

                mytext = findViewById(R.id.textViewInternet);
                if(isNetworkConnected() == true)
                {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo;

                    wifiInfo = wifiManager.getConnectionInfo();
                    if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
                        ssid = wifiInfo.getSSID();
                        ip = wifiInfo.getIpAddress();
                        BSSID = wifiInfo.getBSSID();
                        mytext.setText("You are connected to " + ssid + "  " + " " + BSSID);

                    }
                }
                else
                {
                    mytext.setText("No internet connection!");
                }
                //tryToReadSSID();

            }
        });
    }
    public static void ExecuteCommand(String message) throws IOException, InterruptedException
    {
        try
        {
            InetAddress address = InetAddress.getByName(IP); // 192 168 0 18
            SamsungRemote remote = new SamsungRemote(address);
            TVReply reply = remote.authenticate("Samsung Tablet");
            if(reply == TVReply.ALLOWED)
            {
                remote.keycode(message);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}
