package recipeHackathon.Helper;

import java.util.ArrayList;
import java.util.Hashtable;

import recipeHackathon.Configuration.ConfigurationReader;
import recipeHackathon.Configuration.RecipeConfiguration;
import recipeHackathon.Model.AllergyIngredients;
import recipeHackathon.Model.AllergyType;
import recipeHackathon.Model.Comorbidity;
import recipeHackathon.Model.Recipe;
import recipeHackathon.Model.RecipeByComorbidity;

public class RecipeFilter {

	public static RecipeByComorbidity FilterRecipe(Comorbidity comorbidity, ArrayList<Recipe> recipies) throws Exception {
		RecipeConfiguration config = ConfigurationReader.getConfiguration(comorbidity);
		ArrayList<Recipe> validRecipes = FilterInvalidRecipe(recipies, config);
		
		RecipeByComorbidity list = new RecipeByComorbidity();
		list.Comorbidity = comorbidity;
		list.FilteredRecipies = validRecipes;
		list.RecipesWithBetterIngredients = RecipeWithBetterIngredients(validRecipes, config);
		list.RecipeByAllergy = getRecipesByAllergy(validRecipes, config);
		
		return list;
	}

	private static ArrayList<Recipe> FilterInvalidRecipe(ArrayList<Recipe> recipes, RecipeConfiguration config) {
		ArrayList<Recipe> inValidRecipes = new ArrayList<Recipe>();
		for (Recipe r : recipes) {
			for (String inValidIngrediants : config.EliminatedIngredients) {
				if (r.RecipeIngredients.toLowerCase().contains(inValidIngrediants.trim())) {
					inValidRecipes.add(r);
				}
			}
		}
		recipes.removeAll(inValidRecipes);
		return recipes;
	}

	private static ArrayList<Recipe> RecipeWithBetterIngredients(ArrayList<Recipe> recipes, RecipeConfiguration config) {
		ArrayList<Recipe> betterIngredientsRecipes = new ArrayList<Recipe>();
		for (Recipe r : recipes) {
			for (String betterIngrediants : config.BetterToHaveIngredients) {
				if (r.RecipeIngredients.toLowerCase().contains(betterIngrediants.trim())) {
					betterIngredientsRecipes.add(r);
					break;
				}
			}
		}
		return betterIngredientsRecipes;
	}

	private static Hashtable<AllergyType, ArrayList<Recipe>> getRecipesByAllergy(ArrayList<Recipe> recipes,
			RecipeConfiguration config) {
		Hashtable<AllergyType, ArrayList<Recipe>> recipeByAllergy = new Hashtable<AllergyType, ArrayList<Recipe>>();
		for (AllergyIngredients allergy : config.Allergies) {
			ArrayList<Recipe> allergyRecipes = new ArrayList<Recipe>();
			for (Recipe r : recipes) {
				for (String ingredient : allergy.Ingredients) {
					if (r.RecipeIngredients.toLowerCase().contains(ingredient.trim())) {
						allergyRecipes.add(r);
						break;
					}
				}
			}
			//clone actual recipe
			ArrayList<Recipe> finalrecipie = new ArrayList<Recipe>(recipes);
			// remove recipe with allergies
			finalrecipie.removeAll(allergyRecipes);
			// add it to hashlist of recipe by allergy
			recipeByAllergy.put(allergy.AllergyType, finalrecipie);
		}
		return recipeByAllergy;
	}

}
