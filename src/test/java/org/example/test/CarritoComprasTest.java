package org.example.test;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class CarritoComprasTest {

    @Test
    public void screenshotProductos(){

        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\webdriver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("problem_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        //Espera implicita
        WebDriver.Timeouts timeouts = driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        // Take screenshot and store as a file format
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            // Ruta donde se guardará el screenshot
            Path destinationPath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources","screenshots","screenshotProductos.png");

            // Se Copia el screenshot al destino especificado
            Files.copy(src.toPath(), destinationPath);

            System.out.println("Screenshot guardado en: " + destinationPath);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // Cerrar el navegador
            driver.quit();
        }

    }

}
