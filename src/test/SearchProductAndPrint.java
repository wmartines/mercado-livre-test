package test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.FirefoxDriverManager;
import page.MercadoLivreHomePage;
import param.ItemParam;

public class SearchProductAndPrint {
	
	public WebDriver driver;
	private MercadoLivreHomePage mercadoLivreHomePage;
	private ItemParam param;
	
	@BeforeClass
	private void setUp(){
		
		String url = "https://www.mercadolivre.com.br/";
		FirefoxDriverManager.getInstance().setup();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);			
		driver.get(url);
		driver.manage().window().setSize(new Dimension(1366,768));
		mercadoLivreHomePage = new MercadoLivreHomePage(driver);
		param = new ItemParam();
	}
	
	@AfterClass
	private void tearDown(){
		
		driver.quit();
	}
	
	@Test
	public void searchItemsAndPrintFiveFirst(){
		
		// Informa produto para pesquisa
		param.setItemName("arduino");
		
		// Realiza pesquisa e printa resultados
		mercadoLivreHomePage.searchItem(param);
		mercadoLivreHomePage.findFiveFirstItemsAndPrint();
		
		Assert.assertTrue("pass".equals("fail"));
	}
	
	@Test
	public void takeScreenShot() throws IOException{
		
		// Informa produto para pesquisa
		param.setItemName("tenis");
		// Informa index da paginação
		param.setPaginationIndex("3");
		// Iforma index do item para ser selecionado
		param.setItemIndex("50");
		
		mercadoLivreHomePage.searchItem(param);
		mercadoLivreHomePage.selectPaginationItemAndPrint(param);
	
	}

}
