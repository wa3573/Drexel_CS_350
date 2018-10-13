package alarmclockradio;

public class Radio {
	private double currentRadioStaion;
	private double volume;
	private boolean isOn;
	
	public Radio()
	{
		this.isOn = false;
		this.currentRadioStaion = 88.0;
		this.volume = 1.0;
	}
	
	public void setRadioStation(double station) {
		if (station <  88.0 || station > 108.0) {
			System.out.println("Station MHz should be in the range [88 - 108]");
			return;
		}
		
		this.currentRadioStaion = station;
	}
	
	public double getRadioStaion() {
		return this.currentRadioStaion;
	}
	
	public void setVolume(double volume) {
		if (volume < 0.0 || volume > 1.0) {
			System.out.println("Volume should be in the range [0 - 1]");
			return;
		}
		
		this.volume = volume;
	}
	
	public double getVolume() {
		return this.volume;
	}
	
	public void turnOn() {
		this.isOn = true;
	}
	
	public void turnOff() {
		this.isOn = false;
	}
	
	public boolean isOn() {
		return this.isOn;
	}
}
