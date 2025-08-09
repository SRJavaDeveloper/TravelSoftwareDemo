package com.apollotech.travel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX / main class for the app.
 */
public class TravelApplication extends Application 
{
	/**
	 * Spacing between controls.
	 */
	private static final int STANDARD_SPACING = 5;
	
	/**
	 * Highest pref rating possible.
	 */
	private static final double MAX_PREF = 7.0;
	
	/**
	 * Lowest pref rating possible.
	 */
	private static final double MIN_PREF = 0.0;
	
	/**
	 * Tick unit on pref sliders.
	 */
	private static final double TICK_UNIT = 1.0;
	
	/**
	 * For the name of the traveler.
	 */
	private TextField m_textTravelerName;
	
	/**
	 * Gets the budget for the traveller.
	 */
	private TextField m_textBudget;
	
	/**
	 * Checkbox for whether the traveler has a passport.
	 */
	private CheckBox m_cbPassport;
	
	/**
	 * Beach preference slider.
	 */
	private Slider m_sliderBeachPref;
	
	/**
	 * Mountain preference slider.
	 */
	private Slider m_sliderMountainPref;
	
	/**
	 * City preference slider.
	 */
	private Slider m_sliderCityPref;

	/**
	 * Label for showing the best calculated experience.
	 */
	private Label m_labelBestExperience;
	
	/**
	 * Label for showing the best calculated value.
	 */
	private Label m_labelBestValue;
	
	/**
	 * Main routine for the application.
	 * @param args Any parameters passed to the application.
	 */
	public static void launch(String[] arrArgs)
	{
		Application.launch(arrArgs);
	}
	
	@Override
	public void start(Stage stagePrimary) throws Exception 
	{
		setupAndShowWindow(stagePrimary);
	}
	
	/**
	 * Sets up the app's UI / shows its window.
	 * @param stagePrimary Primary stage for rendering.
	 */
	private void setupAndShowWindow(Stage stagePrimary)
	{
		stagePrimary.setTitle("Travel Destination Matcher");
		
		VBox vbOuter = new VBox();
		vbOuter.setSpacing(STANDARD_SPACING);
		vbOuter.setPadding(new Insets(STANDARD_SPACING));
		
		setupTravelerInfoControls(vbOuter);
		
        Scene scene = new Scene(vbOuter);
        stagePrimary.setScene(scene);
        stagePrimary.sizeToScene();        
        stagePrimary.show();
	}
	
	/**
	 * Sets up the UI controls for traveler info input.
	 * @param vbOuter Outer VBox for the screen.
	 */
	private void setupTravelerInfoControls(VBox vbOuter)
	{
		HBox hbInfo = new HBox();
		hbInfo.setSpacing(STANDARD_SPACING);
		
		m_textTravelerName = new TextField();
		m_textTravelerName.setPromptText("Name");
		hbInfo.getChildren().add(m_textTravelerName);
		
		m_textBudget = new TextField();
		m_textBudget.setPromptText("Budget (USD)");
		hbInfo.getChildren().add(m_textBudget);
		
		m_cbPassport = new CheckBox();
		m_cbPassport.setText("Has Passport");
		hbInfo.getChildren().add(m_cbPassport);
		
		vbOuter.getChildren().add(hbInfo);
		
		HBox hbSliders = new HBox();
		hbSliders.setSpacing(STANDARD_SPACING);
		
		Label labelBeach = new Label("Beach Preference:");
		hbSliders.getChildren().add(labelBeach);
		m_sliderBeachPref = new Slider();
		m_sliderBeachPref.setMax(MAX_PREF);
		m_sliderBeachPref.setMin(MIN_PREF);
		m_sliderBeachPref.setMajorTickUnit(TICK_UNIT);
		m_sliderBeachPref.setSnapToTicks(true);
		hbSliders.getChildren().add(m_sliderBeachPref);
		
		Label labelMountains = new Label("Mountain Preference:");
		hbSliders.getChildren().add(labelMountains);
		m_sliderMountainPref = new Slider();
		m_sliderMountainPref.setMax(MAX_PREF);
		m_sliderMountainPref.setMin(MIN_PREF);
		m_sliderMountainPref.setMajorTickUnit(TICK_UNIT);
		m_sliderMountainPref.setSnapToTicks(true);
		hbSliders.getChildren().add(m_sliderMountainPref);
		
		Label labelCity = new Label("City Preference:");
		hbSliders.getChildren().add(labelCity);
		m_sliderCityPref = new Slider();
		m_sliderCityPref.setMax(MAX_PREF);
		m_sliderCityPref.setMin(MIN_PREF);
		m_sliderCityPref.setMajorTickUnit(TICK_UNIT);
		m_sliderCityPref.setSnapToTicks(true);
		hbSliders.getChildren().add(m_sliderCityPref);
		
		vbOuter.getChildren().add(hbSliders);
		
		Button buttonCalculate = new Button("Find Best Destinations");
		buttonCalculate.setOnAction(event -> 
		{
	        //Create a traveler from data on the screen.
			Traveler traveler = getTravelerFromInput();
			if(traveler == null)
			{
				showInputErrorDialog();
				return;
			}
			
			//Calculate overall ratings and value ratings for each destination.
			Destination[] arrAllDestinations = Destination.values();
			TravelExperienceCalculator calculator = new TravelExperienceCalculator();
			
			Destination destinationOverallChosen = null;
			Destination destinationValueChosen = null;
			double dBestOverall = 0.0;
			double dBestValue = 0.0;
			for(Destination destination : arrAllDestinations)
			{
				double dOverall = calculator.calculateOverallExperience(traveler, destination);
				if(dOverall > dBestOverall)
				{
					dBestOverall = dOverall;
					destinationOverallChosen = destination;
				}
				
				double dValue = calculator.calculateExperiencePerDollar(traveler, destination);
				if(dValue > dBestValue)
				{
					dBestValue = dValue;
					destinationValueChosen = destination;
				}
			}
			
			if(destinationOverallChosen == null)
			{
				m_labelBestExperience.setText("No destination possible.");
			}
			else
			{
				String sDestination = destinationOverallChosen.getName();
				m_labelBestExperience.setText("Best Overall Experience: " + sDestination);
			}
			
			if(destinationValueChosen == null)
			{
				m_labelBestValue.setText("No destination possible.");
			}
			else
			{
				String sDestination = destinationValueChosen.getName();
				m_labelBestValue.setText("Best Value Experience: " + sDestination);
			}			
	    });
		
		vbOuter.getChildren().add(buttonCalculate);
		
		//Add labels for showing calculated results.
		m_labelBestExperience = new Label("Best Experience");
		vbOuter.getChildren().add(m_labelBestExperience);
		
		m_labelBestValue = new Label("Best Value:");
		vbOuter.getChildren().add(m_labelBestValue);
		
		//Add buttons for saving / loading travelers.
		HBox hbPersistence = new HBox();
		hbPersistence.setSpacing(STANDARD_SPACING);
		
		Button buttonSave = new Button("Save Traveler");
		buttonSave.setOnAction(event -> 
		{
	        //Create a traveler from data on the screen.
			Traveler traveler = getTravelerFromInput();
			if(traveler == null)
			{
				showInputErrorDialog();
				return;
			}
			
			TravelerRepository repo = new FileTravelerRepository();
			boolean bContains = repo.containsTraveler(traveler);
			if(bContains)
			{
				repo.updateTraveler(traveler);
			}
			else
			{
				repo.addTraveler(traveler);				
			}
		});
		hbPersistence.getChildren().add(buttonSave);
		
		Button buttonLoad = new Button("Load Traveler");
		buttonLoad.setOnAction(event -> 
		{
			TravelerRepository repo = new FileTravelerRepository();
			Collection<Traveler> collAll = repo.getAllTravelers();
			
			String sDefault = "";
			List<String> listNames = new ArrayList<>();
			for(Traveler traveler : collAll)
			{
				String sName = traveler.getName();
				listNames.add(sName);
				sDefault = sName;
			}
			
			ChoiceDialog<String> dialog = new ChoiceDialog<>(sDefault, listNames);
			dialog.setTitle("Load a Traveler");
			dialog.setContentText("Choose a traveler.");
			
			Optional<String> result = dialog.showAndWait();
		    if(result.isPresent()) 
		    {
		        String sChosen = result.get();
		        for(Traveler traveler : collAll)
		        {
		        	String sCurrentName = traveler.getName();
		        	if(sChosen.equals(sCurrentName))
		        	{
		        		populateControlsForTraveler(traveler);
		        		break;
		        	}
		        }
		    }
			
		});
		hbPersistence.getChildren().add(buttonLoad);
		
		vbOuter.getChildren().add(hbPersistence);
	}
	
	/**
	 * Populates controls on the screen for the given traveler.
	 * @param traveler Traveler used to populate controls.
	 */
	private void populateControlsForTraveler(Traveler traveler)
	{
		String sName = traveler.getName();
		m_textTravelerName.setText(sName);
		
		int nBudget = traveler.getMaxBudgetUSD();
		m_textBudget.setText("" + nBudget);
		
		boolean bPassport = traveler.hasPassport();
		m_cbPassport.setSelected(bPassport);
		
		int nBeach = traveler.getBeachPref();
		m_sliderBeachPref.setValue(nBeach);
		
		int nMountains = traveler.getMountainPref();
		m_sliderMountainPref.setValue(nMountains);
		
		int nCity = traveler.getCityPref();
		m_sliderCityPref.setValue(nCity);
	}
	
	/**
	 * Builds a traveler from input on the screen.
	 * @return Traveler built from screen input, null if one couldn't be made.
	 */
	private Traveler getTravelerFromInput()
	{
		String sName = m_textTravelerName.getText();
		String sBudget = m_textBudget.getText();
		if(sName == null || sBudget == null)
		{
			return null;
		}
		
		int nBudget = 0;
		
		try
		{
			nBudget = Integer.parseInt(sBudget);				
		}
		catch(Exception e)
		{
			return null;
		}
		
		int nBeachPref = (int)m_sliderBeachPref.getValue();
		int nMountainPref = (int)m_sliderMountainPref.getValue();
		int nCityPref = (int)m_sliderCityPref.getValue();
		
		boolean bPassport = m_cbPassport.isSelected();
		
		Traveler traveler = new Traveler(sName, nBeachPref, nMountainPref, nCityPref, nBudget, bPassport);
		return traveler;
	}
	
	/**
	 * Shows an error popup for invalid input.
	 */
	private void showInputErrorDialog()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("Please input valid values.");
	}

}
