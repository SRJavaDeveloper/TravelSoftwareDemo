package com.apollotech.travel;

/**
 * Travel destinations.
 */
public enum Destination 
{
	MIAMI("Miami", 7, 0, 4, true, 1000, CurrencyType.USD),
	DENVER("Denver", 0, 7, 1, true, 500, CurrencyType.USD),
	PARIS("Paris", 0, 0, 7, false, 1000, CurrencyType.EURO),
	RIO("Rio de Janeiro", 6, 0, 6, false, 2000, CurrencyType.BRAZIL_REAL);
	
	/**
	 * Cost of a stay in local currency units.
	 */
	private int m_nCostInLocalCurrency;
	
	/**
	 * Type of currency for the cost of the stay.
	 */
	private CurrencyType m_currencyType;
	
	/**
	 * Rating (higher is better) of beaches.
	 */
	private int m_nBeachRating;
	
	/**
	 * Rating (higher is better) of mountains.
	 */
	private int m_nMountainRating;
	
	/**
	 * Rating (higher is better) of city features.
	 */
	private int m_nCityRating;
	
	/**
	 * True for domestic destinations, false otherwise.
	 */
	private boolean m_bDomestic;
	
	/**
	 * Human readable name of the destination.
	 */
	private String m_sName;
	
	/**
	 * Creates a destination with the given properties.
	 * @param sName Human readable name of the destination.
	 * @param nBeachRating 0 to 7 beach rating.
	 * @param nMountainRating 0 to 7 mountain rating.
	 * @param nCityRating 0 to 7 city rating.
	 * @param bDomestic True for domestic destinations, false for foreign ones.
	 * @param nCost Cost of the stay in local currency units.
	 * @param currencyType Local currency type.
	 */
	private Destination(String sName, int nBeachRating, int nMountainRating, int nCityRating, boolean bDomestic, int nCost, CurrencyType currencyType)
	{
		m_sName = sName;
		m_nBeachRating = nBeachRating;
		m_nMountainRating = nMountainRating;
		m_nCityRating = nCityRating;
		m_bDomestic = bDomestic;
		m_nCostInLocalCurrency = nCost;
		m_currencyType = currencyType;
	}
	
	/**
	 * Gets the destination's currency type.
	 * @return Type of currency units used at the destination.
	 */
	public CurrencyType getCurrencyType()
	{
		return m_currencyType;
	}
	
	/**
	 * Gets the cost in local currency units.
	 * @return Cost of a stay in local currency units.
	 */
	public int getCostInLocalCurrency()
	{
		return m_nCostInLocalCurrency;
	}
	
	/**
	 * Tells whether this is a domestic destination.
	 * @return True for US destinations, false for foreign ones.
	 */
	public boolean isDomestic()
	{
		return m_bDomestic;
	}
	
	/**
	 * Gets the city rating.
	 * @return 1 to 7 city rating.
	 */
	public int getCityRating()
	{
		return m_nCityRating;
	}
	
	/**
	 * Gets the mountain rating.
	 * @return 1 to 7 mountain rating.
	 */
	public int getMountainRating()
	{
		return m_nMountainRating;
	}
	
	/**
	 * Gets the beach rating.
	 * @return 1 to 7 beach rating.
	 */
	public int getBeachRating()
	{
		return m_nBeachRating;
	}
	
	/**
	 * Gets the destination name.
	 * @return Human readable name.
	 */
	public String getName()
	{
		return m_sName;
	}
}
