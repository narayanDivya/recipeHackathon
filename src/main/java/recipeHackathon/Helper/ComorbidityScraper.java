package recipeHackathon.Helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import recipeHackathon.Model.PaginationDetails;
import recipeHackathon.Model.Recipe;

public class ComorbidityScraper {

	private WebDriver driver;

	public ComorbidityScraper(WebDriver driver) {
		this.driver = driver;
	}

	public ArrayList<Recipe> ScrapeRecipies(String fromComorbidityPage) throws Exception {
		driverGet(fromComorbidityPage);
		PaginationDetails paginationInfo = GetPaginationDetails();
		List<String> recipeUrls = extractRecipeUrl(paginationInfo);
		return extractRecipies(recipeUrls);

	}

	// extract links
	private List<String> extractRecipeUrl(PaginationDetails paginationInfo) {
		List<String> urls = new ArrayList<String>();
		int currentPage = 1;
		do {
			driver.get(paginationInfo.GetPageForPageNumer(currentPage));
			urls.addAll(extractRecipeUrl());
			currentPage++;
		} while (currentPage <= paginationInfo.TotalPages);

		System.out.println("URLS count --->" + urls.size());
		return urls;
	}

	// Get all the links in a page and add it to an array
	private List<String> extractRecipeUrl() {
		List<String> urls = new ArrayList<String>();
		List<WebElement> links = driver.findElements(By.xpath("//span[@class='rcc_recipename']//a"));
		System.out.println("links count --->" + links.size());
		for (WebElement link : links) {
			String url = link.getAttribute("href");
			System.out.println("URL to add --->" + url);
			if (url != null) {
				urls.add(url);
			}
		}
		return urls;
	}

	private PaginationDetails GetPaginationDetails() {
		List<WebElement> paginations = driver.findElements(By.xpath("//div[@id='pagination']/a"));
		// find late page
		WebElement pagination = paginations.get(paginations.size() - 1);
		PaginationDetails paginationDetails = new PaginationDetails();
		// get pageUrl
		paginationDetails.PaginationUri = pagination.getAttribute("href");
		// get last page number
		paginationDetails.TotalPages = Integer.parseInt(pagination.getText());
		return paginationDetails;

	}

	private ArrayList<Recipe> extractRecipies(List<String> recipeUrls) throws Exception {
		ArrayList<Recipe> list = new ArrayList<Recipe>();
		for (String recipeLink : recipeUrls) {
			try {
				list.add(extractRecipies(recipeLink));
			} catch (TimeoutException ex) {
				ex.printStackTrace();
				// if it timeout exception then proceed futher and move to next
			}
		}

		return list;

	}

	private Recipe extractRecipies(String recipeLink) throws Exception {
		try {
			driverGet(recipeLink);
			return ExtractRecipe();
		} catch (org.openqa.selenium.UnhandledAlertException e) {
			try {
				driver.switchTo().alert().dismiss();
			} catch (Exception ex) { // sometimes alert automatically get closes. So simply catching it and
				ex.printStackTrace();						// proceeding further}
			}
			return ExtractRecipe();

		}
	}

	private Recipe ExtractRecipe() throws MalformedURLException {
		Recipe recipe = new Recipe();
		recipe.Name = GetValueFromElement(By.id("ctl00_cntrightpanel_lblRecipeName"));
		recipe.RecipeIngredients = GetValueFromElement(By.id("rcpinglist"));
		recipe.PrepTime = GetValueFromElement(By.xpath("//time[@itemprop='prepTime']"));
		recipe.CookTime = GetValueFromElement(By.xpath("//time[@itemprop='cookTime']"));
		recipe.PrepMethod = GetValueFromElement(By.id("recipe_small_steps"));
		recipe.NutritionValue = GetValueFromElement(By.id("rcpnutrients"));

		recipe.Tags = GetValueFromElement(By.id("recipe_tags"));
		WebElement form = driver.findElement(By.xpath("//form[@name='aspnetForm']"));
		String action = form.getAttribute("action");
		recipe.Uri = new URL(action);
		return recipe;
	}

	private void driverGet(String link) {
		driver.get(link);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	private String GetValueFromElement(By by) {
		List<WebElement> elements = driver.findElements(by);
		if (elements.size() > 0) {
			return elements.get(0).getText();
		}
		return "";
	}

}
