package com.doubleos.night.util;

public class ImageInfo
{

    public String m_Name = "";
    public PNGInfo m_info;

    public PNGInfo m_standInfo;

    public ImageInfo(String name, String url, String url2)
    {
        m_Name = name;
        m_info = new PNGInfo(url);
        m_standInfo = new PNGInfo(url2);
    }

    public ImageInfo(String name)
    {
        m_Name = name;
    }
}
