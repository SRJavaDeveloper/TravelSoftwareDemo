package com.apollotech.travel;

import java.util.Collection;

/**
 * Mechanism for persisting travelers, uses the repository pattern.
 */
public interface TravelerRepository 
{
	/**
	 * Gets all saved travelers.
	 * @return All travelers.
	 */
	public Collection<Traveler> getAllTravelers();

	/**
	 * Adds the given traveler.
	 * @param traveler Traveler to add.
	 */
	public void addTraveler(Traveler traveler);
	
	/**
	 * Updates the given traveler.
	 * @param traveler Traveler to update.
	 */
	public void updateTraveler(Traveler traveler);
	
	/**
	 * Deletes the given traveler.
	 * @param traveler Traveler to delete.
	 */
	public void deleteTraveler(Traveler traveler);
	
	/**
	 * Tells whether this contains the given traveler.
	 * @param traveler Traveler used in the check.
	 * @return True if the given traveler is in the repository, false otherwise.
	 */
	public boolean containsTraveler(Traveler traveler);
}
