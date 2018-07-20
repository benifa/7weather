package com.example.fabricebenimana.a7weather.models;

public class Coord{
	private double lon;
	private double lat;

	public void setLon(int lon){
		this.lon = lon;
	}

	public double getLon(){
		return lon;
	}

	public void setLat(int lat){
		this.lat = lat;
	}

	public double getLat(){
		return lat;
	}

	@Override
 	public String toString(){
		return 
			"Coord{" + 
			"lon = '" + lon + '\'' + 
			",lat = '" + lat + '\'' + 
			"}";
		}
}
