package recipeHackathon.Helper;

import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import recipeHackathon.Model.AllergyType;
import recipeHackathon.Model.Comorbidity;
import recipeHackathon.Model.Recipe;
import recipeHackathon.Model.RecipeByComorbidity;

import java.io.FileOutputStream;

public class ExcelWriter {
	private final static String configFilePath = "./src/test/resources/";
	public static void Write(RecipeByComorbidity recipeData) throws Exception {
		Workbook workbook = new XSSFWorkbook();
		try
		{
			
		WriteToSheet(workbook, "FilteredRecipes", recipeData.Comorbidity, recipeData.FilteredRecipies);
		WriteToSheet(workbook, "ToAddRecipes", recipeData.Comorbidity, recipeData.RecipesWithBetterIngredients);
		WriteAllergyToExcel(workbook, recipeData);
		WriteExcelFile(workbook , recipeData.Comorbidity);
		}
		catch(Exception e) {
			workbook.close();
			e.printStackTrace();
			throw e;
		}
	}

	private static void WriteExcelFile(Workbook workbook, Comorbidity comorbidity) throws Exception {
		// Write workbook to file
        FileOutputStream outputStream = new FileOutputStream(configFilePath+ comorbidity.toString() + ".xlsx");
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
		
	}

	private static void WriteToSheet(Workbook workBook, String sheetName, Comorbidity comorbidity,
			ArrayList<Recipe> recipes) {
		Sheet filteredSheet = workBook.createSheet(sheetName);
		int rowIndex = 0;
		createHeaderRow(filteredSheet, rowIndex++);

		for (Recipe recipe : recipes) {
			createRow(comorbidity, filteredSheet, recipe, rowIndex++);
		}

	}

	private static void createRow(Comorbidity comorbidity, Sheet filteredSheet, Recipe recipe, int i) {
		
		Row headerRow = filteredSheet.createRow(i);
		headerRow.createCell(0).setCellValue(recipe.getId());
		headerRow.createCell(1).setCellValue(recipe.Name);
		headerRow.createCell(2).setCellValue(recipe.getRecipeCategory().toString());
		headerRow.createCell(3).setCellValue(recipe.getFoodCategory().toString());
		headerRow.createCell(4).setCellValue(recipe.RecipeIngredients);
		headerRow.createCell(5).setCellValue(recipe.PrepTime);
		headerRow.createCell(6).setCellValue(recipe.CookTime);
		headerRow.createCell(7).setCellValue(recipe.PrepMethod);
		headerRow.createCell(8).setCellValue(recipe.NutritionValue == null ? "" :recipe.NutritionValue );
		headerRow.createCell(9).setCellValue(comorbidity.toString());
		headerRow.createCell(10).setCellValue(recipe.Uri.toString());

	}

	private static void createHeaderRow(Sheet filteredSheet, int i) {
		
		Row headerRow = filteredSheet.createRow(i);
		headerRow.createCell(0).setCellValue("Recipe ID");
		headerRow.createCell(1).setCellValue("Recipe Name");
		headerRow.createCell(2).setCellValue("Recipe Category");
		headerRow.createCell(3).setCellValue("Food Category");
		headerRow.createCell(4).setCellValue("Ingredeients");
		headerRow.createCell(5).setCellValue("Preparation Time");
		headerRow.createCell(6).setCellValue("Cooking Time");
		headerRow.createCell(7).setCellValue("Preparation Method");
		headerRow.createCell(8).setCellValue("Nutrient Values");
		headerRow.createCell(9).setCellValue("Targetted Morbid Conidition");
		headerRow.createCell(10).setCellValue("Recipe URL");

	}

	private static void WriteAllergyToExcel(Workbook workBook, RecipeByComorbidity recipeData) {
		Enumeration<AllergyType> e = recipeData.RecipeByAllergy.keys();
		while (e.hasMoreElements()) {
			AllergyType type = e.nextElement();
			WriteToSheet(workBook, type.toString(), recipeData.Comorbidity, recipeData.RecipeByAllergy.get(type));
		}
	}

}
