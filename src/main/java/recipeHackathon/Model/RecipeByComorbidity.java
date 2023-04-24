package recipeHackathon.Model;

import java.util.ArrayList;
import java.util.Hashtable;

public class RecipeByComorbidity {
  public Comorbidity Comorbidity;
  public ArrayList<Recipe> FilteredRecipies = new ArrayList<Recipe>();
  public ArrayList<Recipe> RecipesWithBetterIngredients = new ArrayList<Recipe>();
  public Hashtable<AllergyType, ArrayList<Recipe>> RecipeByAllergy = new Hashtable<AllergyType, ArrayList<Recipe>>();  
}
