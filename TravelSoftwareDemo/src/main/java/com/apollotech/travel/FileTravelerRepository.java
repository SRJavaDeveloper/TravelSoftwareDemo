package com.apollotech.travel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.google.gson.Gson;

/**
 * File backed repository implementation.
 */
public class FileTravelerRepository implements TravelerRepository
{
	/**
	 * For JSON serialization.
	 */
	private Gson m_gson;
	
	/**
	 * Creates a new repository.
	 */
	public FileTravelerRepository()
	{
		m_gson = new Gson();
	}
	
	/**
	 * Gets the save file path
	 * @return Save file path for this repository.
	 */
	private String getFilePath()
	{
		String sHome = System.getProperty("user.home");
		String sFullPath = sHome + "/" + "Travelers.txt";
		return sFullPath;
	}
	
	/**
	 * Serializes the given traveler to JSON.
	 * @param traveler Traveler to serialize.
	 * @return JSON for the traveler.
	 */
	private String serializeTravelerToJSON(Traveler traveler)
	{
		String sJson = m_gson.toJson(traveler);
		return sJson;
	}
	
	@Override
	public Collection<Traveler> getAllTravelers() 
	{
		Collection<Traveler> collTravelers = new HashSet<>();
		
		String sFilePath = getFilePath();		
		Path path = Paths.get(sFilePath);
		boolean bFileExists = Files.exists(path);
		if(bFileExists == false)
		{
			return collTravelers;
		}
		
		try (Stream<String> streamLines = Files.lines(Paths.get(sFilePath))) 
		{
            streamLines.forEach(new Consumer<String>() 
            {
				@Override
				public void accept(String sLine)
				{
					if(sLine != null && sLine.isEmpty() == false)
					{
						Traveler traveler = m_gson.fromJson(sLine, Traveler.class);
						collTravelers.add(traveler);
					}
				}
			});
            
        } 
		catch (IOException e) 
		{
            throw new RuntimeException(e);
        }
		
		return collTravelers;
	}

	@Override
	public void addTraveler(Traveler traveler) 
	{
		Collection<Traveler> collAll = getAllTravelers();
		collAll.add(traveler);
		
		saveGiven(collAll);
	}

	@Override
	public void updateTraveler(Traveler traveler) 
	{
		Collection<Traveler> collAll = getAllTravelers();
		collAll.remove(traveler);
		collAll.add(traveler);
		
		saveGiven(collAll);
	}

	@Override
	public void deleteTraveler(Traveler traveler) 
	{
		Collection<Traveler> collAll = getAllTravelers();
		collAll.remove(traveler);
		
		saveGiven(collAll);
	}

	@Override
	public boolean containsTraveler(Traveler traveler) 
	{
		Collection<Traveler> collAll = getAllTravelers();
		boolean bContains = collAll.contains(traveler);
		return bContains;
	}
	
	/**
	 * Saves the given travelers to the file.
	 * @param collTravelers Collection of travelers to save.
	 */
	private void saveGiven(Collection<Traveler> collToSave)
	{
		String sAll = "";
		
		for(Traveler traveler : collToSave)
		{
			String sJson = serializeTravelerToJSON(traveler);
			sAll += sJson + "\n";
		}
		
		String sPath = getFilePath();
		Path path = Paths.get(sPath);
		try 
		{
			Files.write(path, sAll.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, 
				StandardOpenOption.TRUNCATE_EXISTING);
		} 
		catch (IOException e) 
		{
			throw new RuntimeException(e);
		}
	}

}
