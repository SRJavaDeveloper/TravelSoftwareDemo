package com.apollotech.travel;

/**
 * Currency converter that works on pre-loaded values.
 */
public class PreloadedValueCurrencyConverter implements CurrencyConverter
{

	@Override
	public int convertToUSD(int nLocal, CurrencyType currencyType) 
	{
		double dConversionFactor;
		
		switch(currencyType)
		{
		case BRAZIL_REAL:
			dConversionFactor = 0.18;
			break;
		case EURO:
			dConversionFactor = 1.16;
			break;
		case USD:
			dConversionFactor = 1.0;
			break;
		default:
			throw new RuntimeException("Unknown currency type.");				
		}
		
		int nReturn = (int)Math.round(nLocal * dConversionFactor);
		return nReturn;
	}

}
