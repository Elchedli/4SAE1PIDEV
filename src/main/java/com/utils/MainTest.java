package com.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

public class MainTest {

	public static void main(String[] args) throws IOException, GeoIp2Exception {
//		String ip = "176.222.34.111";
		 URL ip = new URL("https://checkip.amazonaws.com");
         BufferedReader br = new BufferedReader(new InputStreamReader(ip.openStream()));
         String myip = br.readLine();
		File database = new File("src/main/resources/chedli/GeoLite2-City.mmdb");
		DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
		InetAddress ipAddress = InetAddress.getByName(myip);
		CityResponse response = dbReader.city(ipAddress);
		String countryName = response.getCountry().getName();
		String cityName = response.getCity().getName();
		System.out.println("country : "+countryName+" cityName : "+cityName);
	}

}
