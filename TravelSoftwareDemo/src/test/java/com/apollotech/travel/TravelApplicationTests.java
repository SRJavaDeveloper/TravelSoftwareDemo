package com.apollotech.travel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

/**
 * Unit tests for the travel app.
 */
public class TravelApplicationTests 
{
	/**
	 * Margin used for floating point number equality checks.
	 */
	private static final double MARGIN = 0.01;
	
	/**
	 * Makes sure currency conversion works correctly.
	 */
	@Test
	public void testCurrencyConversion()
	{
		CurrencyConverter converter = new PreloadedValueCurrencyConverter();
		int nConverted = converter.convertToUSD(100, CurrencyType.EURO);
		assertEquals(116, nConverted);
	}
	
	/**
	 * Makes overall destination experience calculations work right.
	 */
	@Test
	public void testOverallExperienceCalculation()
	{
		Traveler traveler = new Traveler("Sam", 7, 0, 0, 2000, true);
		TravelExperienceCalculator calculator = new TravelExperienceCalculator();
		double dExperience = calculator.calculateOverallExperience(traveler, Destination.MIAMI);
		assertEquals(49.0, dExperience, MARGIN);
	}
	
	/**
	 * Makes value destination experience calculations work right.
	 */
	@Test
	public void testValueCalculation()
	{
		Traveler traveler = new Traveler("Sam", 7, 0, 0, 2000, true);
		TravelExperienceCalculator calculator = new TravelExperienceCalculator();
		double dExperience = calculator.calculateExperiencePerDollar(traveler, Destination.MIAMI);
		assertEquals(0.049, dExperience, MARGIN);
	}
	
	/**
	 * Makes overall a zero rating is returned for un-affordable destinations.
	 */
	@Test
	public void testZeroForUnaffordableDestinations()
	{
		Traveler traveler = new Traveler("Sam", 7, 0, 0, 700, true);
		TravelExperienceCalculator calculator = new TravelExperienceCalculator();
		double dExperience = calculator.calculateOverallExperience(traveler, Destination.MIAMI);
		assertEquals(0.0, dExperience, MARGIN);
	}
	
	/**
	 * Makes overall a zero rating is returned for inaccessable destinations.
	 */
	@Test
	public void testZeroForInaccessibleDestinations()
	{
		Traveler traveler = new Traveler("Sam", 7, 0, 0, 10000, false);
		TravelExperienceCalculator calculator = new TravelExperienceCalculator();
		double dExperience = calculator.calculateOverallExperience(traveler, Destination.PARIS);
		assertEquals(0.0, dExperience, MARGIN);
	}
	
	/**
	 * Tests add persistence.
	 */
	@Test
	public void testAddTraveler()
	{
		Traveler traveler = new Traveler("Sam", 7, 0, 0, 10000, false);
		TravelerRepository repo = new FileTravelerRepository();
		repo.addTraveler(traveler);
		
		Collection<Traveler> collLoaded = repo.getAllTravelers();
		boolean bContains = collLoaded.contains(traveler);
		assertTrue(bContains);
	}
	
	/**
	 * Tests remove persistence.
	 */
	@Test
	public void testRemoveTraveler()
	{
		//Add the traveler / make sure he's there.
		Traveler traveler = new Traveler("Sam", 7, 0, 0, 10000, false);
		String sName = traveler.getName();
		TravelerRepository repo = new FileTravelerRepository();
		repo.addTraveler(traveler);
		
		Collection<Traveler> collLoaded = repo.getAllTravelers();
		boolean bContains = collLoaded.contains(traveler);
		assertTrue(bContains);
		
		//Remove the traveler / make sure he's gone.
		repo.deleteTraveler(traveler);
		
		boolean bFound = false;
		collLoaded = repo.getAllTravelers();
		for(Traveler travelerCurrent : collLoaded)
		{
			String sNameCurrent = travelerCurrent.getName();
			if(sNameCurrent.equals(sName))
			{
				bFound = true;
				break;
			}
		}
		assertFalse(bFound);
	}
}
