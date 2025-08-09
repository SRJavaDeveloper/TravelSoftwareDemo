package com.apollotech.travel;

/**
 * Converts foreign currency values to USD.
 */
public interface CurrencyConverter 
{
	/**
	 * Converts the given foreign currency to USD.
	 * @param nLocal Currency amount in the given currency type.
	 * @param currencyType Type of currency to convert.
	 * @return Amount of currency in US dollars.
	 */
	public int convertToUSD(int nLocal, CurrencyType currencyType);
}
