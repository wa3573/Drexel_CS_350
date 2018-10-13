package alarmclockradio;

import java.util.*;


public class Clock {
	protected Date currentTime;
	
	public Clock() {
		this.currentTime = new Date();
	}
	
	public Date getCurrentTime() {
		this.currentTime = new Date();
		return this.currentTime;
	}
	
	public void setCurrentTime(Date t) {
		this.currentTime = t;
	}
}
