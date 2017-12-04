package page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import param.ItemParam;
import pojo.ItemPojo;
import util.ScreenShot;

public class MercadoLivreHomePage {

	WebDriver driver;
	private List<ItemPojo> lsPojo = new ArrayList<>();
	private static final Logger LOG = LoggerFactory.getLogger(MercadoLivreHomePage.class);

	public MercadoLivreHomePage(WebDriver driver) {

		this.driver = driver;
	}

	/**
	 * Metodo responsavel por pesquisar um item
	 * 
	 * @param parametros de manipulacao da pagina
	 */
	public void searchItem(ItemParam param) {
		
		try {

			WebElement searchField = driver.findElement(By.xpath("html/body/header/div/form/input"));
			
			searchField.clear();
			searchField.sendKeys(param.getItemName());

			driver.findElement(By.xpath("html/body/header/div/form/button[3]")).click();

			
		} catch (Exception e) {
			
			LOG.error("[MercadoLivreHomePage] Erro ao pesquisar item: " + e.getCause());
		}
	}

	/**
	 * Metodo responsavel por imprimir os 5 primeros itens
	 */
	public void findFiveFirstItemsAndPrint() {

		try {

			for (int i = 1; i <= 5; i++) {

				ItemPojo foundItem = new ItemPojo();

				foundItem.setPrice(driver.findElement(By.xpath("(.//span[@class='price-fraction'])[" + i + "]")).getText());
				foundItem.setTitle(driver.findElement(By.xpath("(.//span[@class='main-title'])[" + i + "]")).getText());
				foundItem.setIndex(i);
				lsPojo.add(foundItem);

			}

			printItems(lsPojo);

		} catch (Exception e) {
			
			LOG.error("[MercadoLivreHomePage] Erro ao tentar encontrar os cinco elementos: " + e.getCause());

		}
	}
	
	public void selectPaginationItemAndPrint(ItemParam param) throws IOException{
		
		openPaginaionIndex(param);
		selectItem(param);
		ScreenShot.takeScreenshot(driver,param.getPaginationIndex());
	}
	
	/**
	 * Metodo responsavel por imprimir os itens encontrados
	 * @param lsPojo lista de detalhes do item
	 */
	private void printItems(List<ItemPojo> lsPojo) {
		
		try {

			for (ItemPojo itemPojo : lsPojo) {
				System.out.println("item nº " + itemPojo.getIndex());
				System.out.println("Preço: R$" + itemPojo.getPrice());
				System.out.println("Descrição: " + itemPojo.getTitle());
			}

		} catch (Exception e) {
			
			LOG.error("[MercadoLivreHomePage] Erro ao imprimir os resultados: " + e.getCause());
		}
	}
	
	/**
	 * Metodo responsavel por selecionar um item da pagina decordo com index
	 * informado
	 * 
	 * @param param
	 */
	public void selectItem(ItemParam param) {

		try {

			// Verifica o index informado antes de executar o scrolldown
			if (Integer.parseInt(param.getItemIndex()) > 25) {
				scrollBottom();
			}

			driver.findElement(By.xpath("(.//a[@class='item-link item__js-link'])[" + param.getItemIndex() + "]"))
					.click();

		} catch (Exception e) {

			LOG.error("[MercadoLivreHomePage] Erro ao selecionar o item: " + e.getCause());
		}
	}
	
	/**
	 * Metodo responsavel por selecionar paginacao
	 * @param parametros de manipulacao da pagina
	 */
	public void openPaginaionIndex(ItemParam param){
		
		try {

			scrollBottom();
			
			driver.findElement(By.xpath(".//*[@id='results-section']/div[2]/ul/li["+param.getPaginationIndex()+"]/a")).click();
			
		} catch (Exception e) {
			LOG.error("[MercadoLivreHomePage] Erro ao selecionar paginação: " + e.getCause());
		}
	}
	
	/**
	 * Metodo responsaval por realizar scroll down na pagina
	 */
	public void scrollBottom(){
		
		// Inicializando Scroll de pagina
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		// Executa scroll down na pagina
		jse.executeScript("window.scrollBy(0,7500)", "");
		
	}
}
