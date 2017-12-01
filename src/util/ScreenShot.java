package util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreenShot {

	private static final Logger LOG = LoggerFactory.getLogger(ScreenShot.class);

	/**
	 * Metodo responsavel por tirar e armazenar um screenshot
	 * 
	 */
	public static void takeScreenshot(WebDriver driver, String name) throws IOException {

		String path = null;
		
		try {

			// Caminho em que o arquivo sera salvo
			path = "/home/as/Imagens/Screenshots/" + name + ".png";

			// Executa o comando screenShot
			File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			// Copia o arquivo da pasta temporaria para a pasta especificada
			FileUtils.copyFile(sourceFile, new File(path));

		} catch (Exception e) {
			LOG.error("[Screenshot] Erro ao tirar e armazenar foto: " + e.getCause());
		}
	}
}
