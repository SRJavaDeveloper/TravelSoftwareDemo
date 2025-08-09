package com.apollotech.travel;

/**
 * Models someone considering vacation destinations.
 */
public class Traveler 
{
	/**
	 * Preference for beaches.
	 */
	private int m_nBeachPref;
	
	/**
	 * Preference for mountains.
	 */
	private int m_nMountainPref;
	
	/**
	 * Preference for city features.
	 */
	private int m_nCityPref;
	
	/**
	 * Max budget in US dollars.
	 */
	private int m_nMaxBudgetUSD;
	
	/**
	 * Whether the traveler has a passport.
	 */
	private boolean m_bHasPassport;
	
	/**
	 * Name of the traveler.
	 */
	private String m_sName;
	
	/**
	 * Creates a traveler from its parts.
	 * @param sName Full name of the traveler.
	 * @param nBeachPref Beach preference rating.
	 * @param nMountainPref Mountain preference rating.
	 * @param nCityPref City preference rating.
	 * @param nMaxBudgetUSD Budget for the vacation in USD.
	 * @param bPassport True if the traveler has a passport, false otherwise.
	 */
	public Traveler(String sName, int nBeachPref, int nMountainPref, int nCityPref, int nMaxBudgetUSD, 
		boolean bPassport)
	{
		m_sName = sName;
		m_nBeachPref = nBeachPref;
		m_nMountainPref = nMountainPref;
		m_nCityPref = nCityPref;
		m_nMaxBudgetUSD = nMaxBudgetUSD;
		m_bHasPassport = bPassport;
	}
	
	/**
	 * Gets the city pref score.
	 * @return Score for liking cities.
	 */
	public int getCityPref()
	{
		return m_nCityPref;
	}
	
	/**
	 * Gets the mountain pref score.
	 * @return Score for liking mountains.
	 */
	public int getMountainPref()
	{
		return m_nMountainPref;
	}
	
	/**
	 * Gets the beach pref score.
	 * @return Score for liking beaches.
	 */
	public int getBeachPref()
	{
		return m_nBeachPref;
	}
	
	/**
	 * Gets the traveler's name.
	 * @return Full name of the traveler.
	 */
	public String getName()
	{
		return m_sName;
	}
	
	/**
	 * Gets the traveler's budget.
	 * @return Max budget in US dollars.
	 */
	public int getMaxBudgetUSD()
	{
		return m_nMaxBudgetUSD;
	}
	
	/**
	 * Tells whether the traveler has a passport.
	 * @return True if a passport is held, false otherwise.
	 */
	public boolean hasPassport()
	{
		return m_bHasPassport;
	}
	
	@Override
	public int hashCode()
	{
		int nReturn = m_sName.hashCode();
		return nReturn;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Traveler == false)
		{
			return false;
		}
		
		Traveler travelerCompare = (Traveler)obj;
		if(m_sName.equals(travelerCompare.m_sName))
		{
			return true;
		}
		
		return false;
	}
}
