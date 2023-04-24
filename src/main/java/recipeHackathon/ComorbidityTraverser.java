package recipeHackathon;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

import recipeHackathon.Helper.ComorbidityScraper;
import recipeHackathon.Helper.ExcelWriter;
import recipeHackathon.Helper.RecipeFilter;
import recipeHackathon.Model.Comorbidity;
import recipeHackathon.Model.Recipe;
import recipeHackathon.Model.RecipeByComorbidity;

public abstract class ComorbidityTraverser {
	protected WebDriver driver;
	protected ComorbidityScraper scraper;
	
	public void LoadRecipeToExcel()throws Exception {
		Comorbidity morbidity = SetCoMorbidity();
		String morbidityPage = GetMorbidityParentPage();		
		ArrayList<Recipe> recipes = scraper.ScrapeRecipies(morbidityPage);
		RecipeByComorbidity recipeList =  RecipeFilter.FilterRecipe(morbidity, recipes);
		ExcelWriter.Write(recipeList);
	}
	
	public abstract String GetMorbidityParentPage();
	
	public abstract Comorbidity SetCoMorbidity();
}
