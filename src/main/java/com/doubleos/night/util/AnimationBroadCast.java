package com.doubleos.night.util;

public class AnimationBroadCast
{


    public AnimationBroadCast(String message, String type)
    {
        m_String = message;
        m_type = type;
    }
    public String m_String = "";

    public String m_type = "";

    public float m_currentAnimationFrame = 0f;
    public float m_maxAnimationFrame = 100f;

    public float m_curreentAniYPosPer = 0f;


    public boolean m_animationShow = false;
    public boolean m_animationPlay = false;

    public boolean m_animationOpen = true;
    public boolean m_animationClose = false;

    public boolean m_animationDelay = false;






}
