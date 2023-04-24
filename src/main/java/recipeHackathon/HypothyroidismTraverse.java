package recipeHackathon;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import recipeHackathon.Helper.ComorbidityScraper;
import recipeHackathon.Model.Comorbidity;

@Test
public class HypothyroidismTraverse extends ComorbidityTraverser {

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
		driver.get("https://tarladalal.com/");
		driver.findElement(By.xpath("//div[contains(text(),'RECIPES')]")).click();
		driver.findElement(By.id("ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht226")).click();
		String pageURL = driver.getCurrentUrl();

		return pageURL;
	}

	@Override
	public Comorbidity SetCoMorbidity() {
		return Comorbidity.HYPOTHYROIDISM;

	}
}
