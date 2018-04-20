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

	@Test
	public void testMultiCountry() {
		// RU
		Assert.assertTrue(geoIp2Instance.isCountryAllowed("46.173.218.64", "BR;RU;FR"));
		Assert.assertTrue(geoIp2Instance.isCountryAllowed("46.173.218.64", "BR;RU"));
		Assert.assertTrue(geoIp2Instance.isCountryAllowed("46.173.218.64", "RU;FR"));
		Assert.assertTrue(geoIp2Instance.isCountryAllowed("46.173.218.64", "RU"));
		Assert.assertFalse(geoIp2Instance.isCountryAllowed("46.173.218.64", "BR;FR"));
		Assert.assertFalse(geoIp2Instance.isCountryAllowed("46.173.218.64", "BR"));
		Assert.assertFalse(geoIp2Instance.isCountryAllowed("46.173.218.64", "FR"));

		// BR
		Assert.assertTrue(geoIp2Instance.isCountryAllowed("191.252.110.123", "BR;RU;FR"));
		Assert.assertTrue(geoIp2Instance.isCountryAllowed("191.252.110.123", "BR;FR"));
		Assert.assertTrue(geoIp2Instance.isCountryAllowed("191.252.110.123", "BR;RU"));
		Assert.assertTrue(geoIp2Instance.isCountryAllowed("191.252.110.123", "BR"));
		Assert.assertFalse(geoIp2Instance.isCountryAllowed("191.252.110.123", "RU;FR"));
		Assert.assertFalse(geoIp2Instance.isCountryAllowed("191.252.110.123", "RU"));
		Assert.assertFalse(geoIp2Instance.isCountryAllowed("191.252.110.123", "FR"));

		// FR
		Assert.assertTrue(geoIp2Instance.isCountryAllowed("199.247.11.234", "BR;RU;FR"));
		Assert.assertTrue(geoIp2Instance.isCountryAllowed("199.247.11.234", "RU;FR"));
		Assert.assertTrue(geoIp2Instance.isCountryAllowed("199.247.11.234", "BR;FR"));
		Assert.assertTrue(geoIp2Instance.isCountryAllowed("199.247.11.234", "FR"));
		Assert.assertFalse(geoIp2Instance.isCountryAllowed("199.247.11.234", "BR;RU"));
		Assert.assertFalse(geoIp2Instance.isCountryAllowed("199.247.11.234", "BR"));
		Assert.assertFalse(geoIp2Instance.isCountryAllowed("199.247.11.234", "RU"));
	}
}
