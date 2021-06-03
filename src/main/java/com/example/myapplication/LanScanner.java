package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LanScanner extends AppCompatActivity
{
    ArrayList<Device> devicesOnline = new ArrayList<Device>();
    boolean hostIsUp = false;

    int ipStartFirstD;
    int ipStartSecondD;
    int ipEndFirstD;
    int ipEndSecondD;
    int difference;

    String startPoint = "";
    String endPoint = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lan_scanner);


        Button btnMainmenu = (Button) findViewById(R.id.btnMain_Menu);
        Button btnScan = (Button) findViewById(R.id.btnScan);

        final EditText txtStartPoint = (EditText) findViewById(R.id.editTextStartPoint);
        final EditText txtEndPoint = (EditText) findViewById(R.id.editTextEndPoint);

        final TextView txtViewCount = (TextView) findViewById(R.id.txtViewCount);
        //final TextView txtViewMAC = (TextView) findViewById(R.id.txtViewMAC);

        final ListView mListView = (ListView) findViewById(R.id.listView);
        DeviceListAdapter adapter = new DeviceListAdapter(this, R.layout.adapter_view_layout, devicesOnline);
        mListView.setAdapter(adapter);

        btnMainmenu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent iMM = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(iMM);
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startPoint = txtStartPoint.getText().toString();
                endPoint = txtEndPoint.getText().toString();
                //192.168.0.100
                ipStartFirstD = ReturnFirstDigits(startPoint);
                ipStartSecondD = ReturnSecondDigits(startPoint);
                ipEndFirstD = ReturnFirstDigits(endPoint);
                ipEndSecondD = ReturnSecondDigits(endPoint);
                difference = ipEndSecondD - ipStartSecondD + 1;

                String ip = "";
                String mac;
                Process p;
                BufferedReader br;
                String brand;
                Device d;

                try
                {
                    for(int i = ipStartSecondD; i < ipEndSecondD; i++)
                    {
                        ip = "192.168."  + ipStartFirstD + "." + i;
                        p = Runtime.getRuntime().exec("ping -c 1 " + ip);
                        br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String line;
                        while((line = br.readLine()) != null)
                        {
                            if(line.contains("1 packets transmitted, 1 received"))
                            {
                                mac = getMacAddress(ip);
                                brand = getBrand(mac);
                                d = new Device(ip,mac,brand);
                                System.out.println("Host " + d.getIP() + " is online, MAC:" + d.getMAC() + ", brand: " + d.getBrand());
                                devicesOnline.add(d);
                            }
                        }
                    }
                    System.out.println("Done scanning");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                txtViewCount.setText("Count: " + devicesOnline.size());
                mListView.invalidateViews();
            }

        });
    }

    String getBrand(String MAC)
    {
        String brand = "";
        boolean found = false;
        try
        {
            InputStream is = getAssets().open("VENDOR_MAC_LIST.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = br.readLine()) != null)
            {
                if(line.contains(MAC))
                {
                    brand = line.substring(7);
                    found = true;
                }
            }
            if(found == false)
            {
                brand = "<UNKNOWN>";
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return brand;
    }

    static int ReturnFirstDigits(String x)
    {
        String y = x.substring(8);
        String val1 = " ";
        int d = 0;
        for(int i = 0; i < y.length(); i++)
        {
            if(y.charAt(i) != '.')
            {
                val1 += y.charAt(i);
            }
            else
            {
                String val2 = val1.substring(1);
                d = Integer.parseInt(val2);
                break;
            }
        }
        return d;
    }

    static int ReturnSecondDigits(String x)
    {
        String y = x.substring(8);
        String val1 = " ";
        int d = 0;
        for(int i = 0; i < y.length(); i++)
        {
            if(y.charAt(i) != '.' && y.charAt(i) >= '0' && y.charAt(i) <= '9')
            {
                val1 += y.charAt(i);
            }
            else
            {
                val1 = " ";
            }
        }

        String val2 = val1.substring(1);
        d = Integer.parseInt(val2);
        return d;
    }


    public String getMacAddress(String ip)
    {
        String MAC = "";
        BufferedReader br = null;
        ArrayList<String> result = new ArrayList<String>();

        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                if(line.contains(ip))
                {
                    MAC = ParseMAC(line, ip);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
        }finally {
            try {
                br.close();
            } catch (IOException e) {

            }
        }

        return MAC.toUpperCase();
    }

    public String ParseMAC(String x, String ip)
    {
        String y = "";

        if(x.contains(ip))
        {
            y = x.substring(ip.length() + 29);
            y = y.substring(0, 9);
            y = y.replaceAll(":", "");
        }

        return y;
    }
}
