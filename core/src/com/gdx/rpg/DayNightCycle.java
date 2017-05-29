package com.gdx.rpg;

import com.badlogic.gdx.Gdx;

/**
 * if object is entity, set sprite, health, call
 * createBody(position, sprite.texture), set type
 */
public class DayNightCycle {
    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getDay(){
        return  day;
    }

    private float fseconds;
    private int seconds;
    private int minutes;

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setCampTime(){
        hours = 22;
        minutes = 0;
        seconds = 0;
    }
    private int hours;
    private int day;

    public DayNightCycle(){
        seconds = 0;
        hours = 8;
        minutes = 0;
        day = 1;
    }

    public void updateTime(){
        minutes += Gdx.graphics.getDeltaTime() * 200;
        seconds = (int)fseconds;
        if(fseconds >= 60){
            minutes++;
            fseconds = 0;
        }
        if(minutes >= 60){
            hours++;
            minutes = 0;
        }
        if(hours >= 24){
            minutes = 0;
            seconds = 0;
            hours = 0;
            day++;
        }
    }
}
