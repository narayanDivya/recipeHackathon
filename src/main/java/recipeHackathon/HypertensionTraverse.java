package recipeHackathon;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import recipeHackathon.Helper.ComorbidityScraper;
import recipeHackathon.Model.Comorbidity;
@Test
public class HypertensionTraverse extends ComorbidityTraverser {
	@BeforeTest
	public void setUp() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		scraper = new ComorbidityScraper(driver); 		

	}

	public void LoadRecipesToExcel() throws Exception {
		LoadRecipeToExcel();
	}
	
	@Override
	public String GetMorbidityParentPage() {
	return "https://tarladalal.com/recipes-for-high-blood-pressure-644";
	}

	@Override
	public Comorbidity SetCoMorbidity() {
		return Comorbidity.HYPERTENSION;
		
	}	
}



