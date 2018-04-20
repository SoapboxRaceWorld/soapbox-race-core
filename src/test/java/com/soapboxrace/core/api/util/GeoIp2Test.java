package com.soapboxrace.core.api.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GeoIp2Test {

	GeoIp2 geoIp2Instance;

	@Before
	public void init() {
		geoIp2Instance = GeoIp2.getInstance("GeoLite2-Country.mmdb");
	}

	@Test
	public void testBR() {
		String countryIso = geoIp2Instance.getCountryIso("191.252.110.123");
		Assert.assertTrue("BR".equals(countryIso));
	}

	@Test
	public void testFR() {
		String countryIso = geoIp2Instance.getCountryIso("199.247.11.234");
		Assert.assertTrue("FR".equals(countryIso));
	}

	@Test
	public void testRU() {
		String countryIso = geoIp2Instance.getCountryIso("46.173.218.64");
		Assert.assertTrue("RU".equals(countryIso));
	}
}
