package com.doubleos.night.util;

public class AnimationState
{

    public AnimationState(String message)
    {
        m_String = message;
    }

    public AnimationState(float animationTime, float animationDelayTime)
    {

        m_AnimationFrameTime = 1 / animationTime;
        m_AnimationDelayTime = 1 / animationDelayTime;
    }

    public float m_AnimationFrameTime = 1;
    public float m_AnimationDelayTime = 1;

    public AnimationState(){}
    public String m_String = "";

    public String m_Player = "";

    public String type = "";

    public float m_currentAnimationFrame = 0f;
    public float m_maxAnimationFrame = 100f;

    public float m_curreentAniYPosPer = 0f;


    public boolean m_animationShow = false;
    public boolean m_animationPlay = false;

    public boolean m_animationOpen = true;
    public boolean m_animationClose = false;

    public boolean m_animationDelay = false;




    public float m_currentAnimationDelay = 0f;
    public float m_maxAnimationDelay = 100f;






}
