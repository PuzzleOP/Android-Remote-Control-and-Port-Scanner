package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DeviceListAdapter extends ArrayAdapter<Device>
{
    private Context mContext;
    int mResource;

    public DeviceListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Device> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        String ip = getItem(position).getIP();
        String mac = getItem(position).getMAC();
        String brand = getItem(position).getBrand();

        Device d = new Device(ip, mac, brand);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView textViewIP = (TextView) convertView.findViewById(R.id.textView1);
        TextView textViewMAC = (TextView) convertView.findViewById(R.id.textView2);
        TextView textViewBrand = (TextView) convertView.findViewById(R.id.textView3);

        textViewIP.setText(ip);
        textViewMAC.setText(mac);
        textViewBrand.setText(brand);

        return convertView;
    }
}
