package com.apollotech.travel;

/**
 * Calculates how much a traveler will like a destination.
 */
public class TravelExperienceCalculator 
{
	/**
	 * Calculates the overall travel experience for the traveler.
	 * A higher number is a better experience.
	 * @param traveler Traveler considering the destination.
	 * @param destination Destination being considered.
	 * @return A number indicating how much the traveler would like the given destination. 0 is returned if the 
	 * destination is not affordable.
	 */
	public double calculateOverallExperience(Traveler traveler, Destination destination)
	{
		//If the destination's cost is over the traveler's budget, return 0.
		boolean bAffordable = isDestinationAffordable(traveler, destination);
		if(bAffordable == false)
		{
			return 0;
		}
		
		//If the traveler can't access the destination, return 0.
		boolean bAccessible = isDestinationAccessible(traveler, destination);
		if(bAccessible == false)
		{
			return 0;
		}
		
		int nBeachPref = traveler.getBeachPref();
		int nMountainPref = traveler.getMountainPref();
		int nCityPref = traveler.getCityPref();
		
		int nBeachRating = destination.getBeachRating();
		int nMountainRating = destination.getMountainRating();
		int nCityRating = destination.getCityRating();
		
		int nScore = nBeachPref * nBeachRating + nMountainPref * nMountainRating + nCityPref * nCityRating;
		return nScore;
	}
	
	/**
	 * Calculates how good of an epxerience the given destination is for the given traveler, per dollar.
	 * @param traveler Traveler considering the destination.
	 * @param destination Destination being considered.
	 * @return Experience rating per dollar, where higher represents a better value.
	 */
	public double calculateExperiencePerDollar(Traveler traveler, Destination destination)
	{
		double dOverallExperience = calculateOverallExperience(traveler, destination);
		double dCostInUSD = getDestinationCostInDollars(destination);
		double dValue = dOverallExperience / dCostInUSD;
		return dValue;
	}
	
	/**
	 * Calculates the destination cost in USD.
	 * @param destination Destination to have its cost calculated.
	 * @return Cost in US dollars.
	 */
	private double getDestinationCostInDollars(Destination destination)
	{
		CurrencyType currencyType = destination.getCurrencyType();
		int nCostInLocalCurrency = destination.getCostInLocalCurrency();
		
		//A factory should be used here or a currency converter should be injected. We can talk about this.
		CurrencyConverter converter = new PreloadedValueCurrencyConverter();
		double dCost = converter.convertToUSD(nCostInLocalCurrency, currencyType);
		
		return dCost;
	}
	
	/**
	 * Figures out whether the given traveler can visit the given destination.
	 * @param traveler Traveler considering the destination.
	 * @param destination Destination the traveler may go to.
	 * @return True if the traveler can access the given destination, false otherwise.
	 */
	private boolean isDestinationAccessible(Traveler traveler, Destination destination)
	{
		boolean bHasPassport = traveler.hasPassport();
		boolean bDomestic = destination.isDomestic();
		
		if(bHasPassport == false && bDomestic == false)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Tells if the destination is affordable for the traveler.
	 * @param traveler Traveler considering the destination.
	 * @param destination Destination being considered.
	 * @return True if the traveler can afford the destination, false otherwise.
	 */
	private boolean isDestinationAffordable(Traveler traveler, Destination destination)
	{
		int nTravelerBudgetUSD = traveler.getMaxBudgetUSD();
		double dCostInDollars = getDestinationCostInDollars(destination);
		
		if(dCostInDollars > nTravelerBudgetUSD)
		{
			return false;
		}
		return true;
	}
}
