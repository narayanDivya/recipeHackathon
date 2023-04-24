package recipeHackathon.Model;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recipe {

	public String getId() {
		return Uri.getQuery().split("=")[1];
	}
	public String Name;
	public String RecipeIngredients;
	public String PrepTime;
	public String CookTime;
	public String PrepMethod;
	public String NutritionValue;
	public String Tags;
	public URL Uri;

	public RecipeCategory getRecipeCategory() {
		if (Tags.toLowerCase().contains("breakfast")) {
			return RecipeCategory.BREAKFAST;

		} else if (Tags.toLowerCase().contains("lunch")) {
			return RecipeCategory.LUNCH;

		} else if (Tags.toLowerCase().contains("snack")) {
			return RecipeCategory.SNACK;
		} else if (Tags.toLowerCase().contains("dinner")) {
			return RecipeCategory.DINNER;
		}
		return RecipeCategory.NONE;
	}
	
	public FoodCategory getFoodCategory() {
		if (Tags.toLowerCase().contains("jain")) {
			return FoodCategory.JAIN;

		} else if (RecipeIngredients.toLowerCase().contains("egg")) {
			return FoodCategory.EGGETARIAN;
		}
		Pattern p= Pattern.compile("(milk|cheese|yogurt|icecream|buttermilk|paneer|ghee|chocolate|butter|dahi)");
		Matcher regexMatcher = p.matcher(RecipeIngredients.toLowerCase());
		if(!regexMatcher.find()) {
			return FoodCategory.VEGAN;                
		}
		return FoodCategory.VEGETARIAN;
	}
	
	public void PrintRecipe() {
		System.out.println("Recipe Uri: " + Uri.toString());
		System.out.println("Recipe name: " + Name);
		System.out.println("Recipe ID: " + getId());
		System.out.println("Recipe category: " + getRecipeCategory());
		System.out.println("Food Category: "+ getFoodCategory());
		System.out.println("Ingredients: " +  RecipeIngredients);
		System.out.println("Preparation Time: " +PrepTime);
		System.out.println("Cooking Time: " + CookTime);
		System.out.println("Preparation Method: " + PrepMethod);
		System.out.println("Nutrient Values: " + NutritionValue);	
	}

}
