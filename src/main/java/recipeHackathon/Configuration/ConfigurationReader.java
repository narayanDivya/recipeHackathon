

package recipeHackathon.Configuration;


import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.InvalidArgumentException;

import recipeHackathon.Model.AllergyIngredients;
import recipeHackathon.Model.AllergyType;
import recipeHackathon.Model.Comorbidity;

public class  ConfigurationReader {
	private final static String configFilePath = "./src/test/resources/";
	    public static RecipeConfiguration getConfiguration(Comorbidity comorbidity) throws Exception {	        
	        return readConfiguration(getPropertiesConfig(comorbidity));
	    }

		private static Properties getPropertiesConfig(Comorbidity comorbidity) throws Exception {
			Properties properties = new Properties();
			try {
	            FileInputStream fileInputStream = new FileInputStream(configFilePath+ getPropertyFile(comorbidity));
	            properties.load(fileInputStream);
	            fileInputStream.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw e;
	        }
			
			return properties;
		}
	    
	    private static String getPropertyFile(Comorbidity comorbidity) {
			switch(comorbidity)		{
			case DIABETES:
				return "Diabetes.properties";			
				
			case PCOS:
				return "Pcos.properties";
				
			case HYPOTHYROIDISM:
				return "Hypothyroidism.properties";
				
			case HYPERTENSION:
				return "Hypertension.properties";
			
				
				
			default:
				throw new InvalidArgumentException("comorbidity does not exists" + comorbidity)	;		
			}
		}

		private static RecipeConfiguration readConfiguration(Properties properties) {
			
	    	RecipeConfiguration config = new RecipeConfiguration();
	    	config.EliminatedIngredients = properties.getProperty("eliminatedIngredients").toLowerCase().split(",");
	    	config.BetterToHaveIngredients = properties.getProperty("betterToHaveIngredients").toLowerCase().split(",");	    	
	    	for(AllergyType allergyType : AllergyType.values()) {
	    		AllergyIngredients allergy = new AllergyIngredients();
	    		allergy.AllergyType = allergyType;
	    		allergy.Ingredients = properties.getProperty(allergyType.GetPropertyName()).toLowerCase().split(",");
	    		config.Allergies.add(allergy);		
	    	}
	    	return config;
	    }
}
