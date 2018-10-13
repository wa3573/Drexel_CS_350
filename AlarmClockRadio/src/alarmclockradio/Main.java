package alarmclockradio;

import alarmclockradio.AlarmClockRadio;

import java.util.*;

public class Main {
	static final long ONE_SECOND_MS = 1000;
	static final int SECONDS_UNTIL_ALARM = 3 * 60;
	static final int MINUTES_UNTIL_ALARM = SECONDS_UNTIL_ALARM / 60;
	static final int SECONDS_UNTIL_ALARM_REMAINDER = SECONDS_UNTIL_ALARM % 60;
	
	static String TERMINAL_DIVIDER = "===================="
			+ "===================="
			+ "====================";
	
	public static void printDivider() {
		System.out.println(TERMINAL_DIVIDER);
	}
	
	public static void main(String [] args) {
		AlarmClockRadio alarmClockRadio = new AlarmClockRadio();
		
		printDivider();
		System.out.println("Alarm Clock Radio, Test Program");
		printDivider();
		
		System.out.println("Current time is: "
				+ alarmClockRadio.getCurrentTime());
		
		System.out.println("Radio is on: "
				+ alarmClockRadio.isRadioOn());
		
		printDivider();
		System.out.println("Turning on radio. Setting to 92.5 MHz. "
				+ "Setting volume to 50.0%");
		printDivider();
		
		alarmClockRadio.turnRadioOn();
		alarmClockRadio.setRadioStation(92.5);
		alarmClockRadio.setRadioVolume(0.5);
		
		
		System.out.println("Radio is on: "
				+ alarmClockRadio.isRadioOn());
		System.out.println("Radio station is set to: "
				+ alarmClockRadio.getRadioStation());
		System.out.println("Volume is set to: "
				+ (alarmClockRadio.getRadioVolume() * 100)
				+ "%");
		
		printDivider();
		System.out.println("Setting alarm for "
				+ MINUTES_UNTIL_ALARM + " Minutes, "
				+ SECONDS_UNTIL_ALARM_REMAINDER + " Seconds from now."
				+ " Turning on alarm.");
		printDivider();
		
		/* Set Alarm Time for SECONDS_UNTIL_ALARM from now */
		Date alarmTime = new Date(alarmClockRadio.getCurrentTime().getTime() 
				+ SECONDS_UNTIL_ALARM * ONE_SECOND_MS);
		
		alarmClockRadio.setAlarm(alarmTime);
		
		System.out.println("Alarm set for: "
				+ alarmClockRadio.getAlarm());
		
		alarmClockRadio.turnAlarmOn();
		
		/* Program loops until alarm rings, then we snooze */
		alarmClockRadio.snooze();
		
		/* Once the alarm goes off again, we exit.
		 * As a sanity check: verify the alarm is off.*/
		
		if (!alarmClockRadio.isAlarmOn()) {
			System.out.println("Exitting...");
			System.exit(0);
		} else {
			System.err.println("Error: Alarm is not off");
			System.exit(1);
		}
	}
}
