package alarmclockradio;

import alarmclockradio.Radio;

public class AlarmClockRadio extends AlarmClock {
	public Radio radio = new Radio();
	
	public void setRadioStation(double station) {
		this.radio.setRadioStation(station);
	}
	
	public double getRadioStation() {
		return this.radio.getRadioStaion();
	}
	
	public void setRadioVolume(double volume) {
		this.radio.setVolume(volume);
	}
	
	public double getRadioVolume() {
		return this.radio.getVolume();
	}
	
	public void turnRadioOn() {
		this.radio.turnOn();
	}
	
	public void turnRadioOff() {
		this.radio.turnOff();
	}
	
	public boolean isRadioOn() {
		return this.radio.isOn();
	}
}
