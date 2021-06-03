package com.example.myapplication;

public class Device
{
    private String IP;
    private String MAC;
    private String brand;

    public Device(String _IP, String _MAC, String _brand)
    {
        IP = _IP;
        MAC = _MAC;
        brand = _brand;
    }

    public String getIP()
    {
        return IP;
    }

    public String getMAC()
    {
        return MAC;
    }

    public String getBrand()
    {
        return brand;
    }

    public void setIP(String x)
    {
        IP = x;
    }

    public void setMAC(String x)
    {
        MAC = x;
    }

    public void setBrand(String x)
    {
        brand = x;
    }
}
