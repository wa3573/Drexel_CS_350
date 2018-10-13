package alarmclockradio;

import java.util.*;

public class AlarmClock extends Clock {
	static final long ONE_MINUTE_MS = 60000;
	static final long ONE_SECOND_MS = 1000;
	static final int SNOOZE_MINUTES = 9;
	
	protected Date alarmTime;
	
	protected boolean isAlarmOn;
	
	public void setAlarm(Date t) {
		this.alarmTime = t;
	}
	
	public Date getAlarm() {
		return this.alarmTime;
	}
	
	public void turnAlarmOn() {
		this.isAlarmOn = true;
		this.alarmLoop();
	}
	
	public void turnAlarmOff() {
		this.isAlarmOn = false;
	}
	
	public boolean isAlarmOn() {
		return this.isAlarmOn;
	}
	
	public void snooze()
	{
		Date newAlarmTime = new Date(this.getAlarm().getTime() 
				+ SNOOZE_MINUTES * ONE_MINUTE_MS);
		
		this.setAlarm(newAlarmTime);
		
		System.out.println("Snooze button pressed, new alarm time: "
				+ this.getAlarm().toString());
		
		this.turnAlarmOn();
	}
	
	/* Note: in a more "real-world" situation, this would be better
	 * implemented with a Thread, so other operations could be handled
	 * concurrently. Since this was not asked for, this is the simpler
	 * solution.
	 */
	private void alarmLoop() {
		int secondsSinceLoop = 0;
		int minutesSinceStart = 0;
		
		while (this.getCurrentTime().before(alarmTime)) {
			try {
				Thread.sleep(ONE_SECOND_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			secondsSinceLoop++;
			
			if (secondsSinceLoop >= 60) {
				minutesSinceStart++;
				System.out.println("Minutes since alarm set: "
						+ Integer.toString(minutesSinceStart));
				secondsSinceLoop = 0;
			}
		}
		
		System.out.println("Buzz Buzz Buzz");
		this.turnAlarmOff();
	}
	

}
