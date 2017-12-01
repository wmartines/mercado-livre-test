package page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import param.ItemParam;
import pojo.ItemPojo;
import util.ScreenShot;

public class MercadoLivreHomePage {

	WebDriver driver;
	private List<ItemPojo> lsPojo = new ArrayList<>();

	public MercadoLivreHomePage(WebDriver driver) {

		this.driver = driver;
	}

	/**
	 * Metodo responsavel por pesquisar um item
	 * 
	 * @param parametros de manipulacao da pagina
	 */
	public void searchItem(ItemParam param) {

		driver.findElement(By.xpath("html/body/header/div/form/input")).sendKeys(param.getItemName());

		driver.findElement(By.xpath("html/body/header/div/form/button[3]")).click();

	}

	/**
	 * Metodo responsavel por imprimir os 5 primeros itens
	 */
	public void findFiveFirstItemsAndPrint() {

		for (int i = 1; i <= 5; i++) {

			ItemPojo foundItem = new ItemPojo();

			foundItem.setPrice(driver.findElement(By.xpath("(.//span[@class='price-fraction'])[" + i + "]")).getText());
			foundItem.setTitle(driver.findElement(By.xpath("(.//span[@class='main-title'])[" + i + "]")).getText());
			foundItem.setIndex(i);
			lsPojo.add(foundItem);

		}

		printItems(lsPojo);
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

		for (ItemPojo itemPojo : lsPojo) {
			System.out.println("item nº " + itemPojo.getIndex());
			System.out.println("Preço: R$" + itemPojo.getPrice());
			System.out.println("Descrição: " + itemPojo.getTitle());
		}
	}
	
	/**
	 * Metodo responsavel por selecionar um item da pagina decordo com index informado 
	 * @param param
	 */
	public void selectItem(ItemParam param){
		
		// Verifica o index informado antes de executar o scrolldown
		if(Integer.parseInt(param.getItemIndex())>25){
			scrollBottom();
		}
		
		driver.findElement(By.xpath("(.//a[@class='item-link item__js-link'])["+param.getItemIndex()+"]")).click();
	}
	
	/**
	 * Metodo responsavel por selecionar paginacao
	 * @param parametros de manipulacao da pagina
	 */
	public void openPaginaionIndex(ItemParam param){
		
		scrollBottom();
		
		driver.findElement(By.xpath(".//*[@id='results-section']/div[2]/ul/li["+param.getPaginationIndex()+"]/a")).click();
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
