package com.soapboxrace.core.api.util;

import java.io.File;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CountryResponse;

public class GeoIp2 {

	private static GeoIp2 instance;

	private GeoIp2(String pathToMmdb) {
		setDatabaseReader(pathToMmdb);
	}

	public static GeoIp2 getInstance(String pathToMmdb) {
		if (instance == null) {
			instance = new GeoIp2(pathToMmdb);
		}
		return instance;
	}

	private DatabaseReader dbReader = null;

	private DatabaseReader setDatabaseReader(String pathToMmdb) {
		if (dbReader != null) {
			return dbReader;
		}
		try {
			File database = new File(pathToMmdb);
			dbReader = new DatabaseReader.Builder(database).build();
		} catch (Exception e) {
			System.err.println("error inside geoip class [" + e.getMessage() + "]");
		}
		return dbReader;
	}

	public String getCountryIso(String ip) {
		String countryIso = "";
		try {
			InetAddress ipAddress = InetAddress.getByName(ip);
			CountryResponse country = dbReader.country(ipAddress);
			countryIso = country.getCountry().getIsoCode();
		} catch (Exception e) {
			System.err.println("error inside geoip class [" + e.getMessage() + "]");
		}
		return countryIso;
	}

	public boolean isCountryAllowed(String ip, String countries) {
		try {
			String countryIso = getCountryIso(ip);
			List<String> countriesList = Arrays.asList(countries.split(";"));
			if (countriesList.contains(countryIso)) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("error inside geoip class [" + e.getMessage() + "]");
		}
		return false;
	}
}
