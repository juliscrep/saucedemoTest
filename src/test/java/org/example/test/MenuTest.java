package org.example.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class MenuTest {

    @Test
    public void irSeccionAbout(){

        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\webdriver\\chromedriver.exe");

        // creacion del driver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");

        //Localizacion del elemento
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        driver.findElement(By.id("react-burger-menu-btn")).click();

        // Espera explícita para el botón logout
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("about_sidebar_link")));

        // Cierre de sesión
        driver.findElement(By.id("about_sidebar_link")).click();

        if(driver.findElement(By.xpath("//img[@src='/images/logo.svg']")).isDisplayed())
        {
            System.out.println("Se encuentra en la sección About");
        }
        else {
            System.out.println("No se pudo ingresar a la sección About");
        }
    }

    @Test
    public void logout(){

        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\webdriver\\chromedriver.exe");


        // creacion del driver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");

        //Localizacion del elemento
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        driver.findElement(By.id("react-burger-menu-btn")).click();

        // Espera explícita para el botón logout
        WebDriverWait wait1 = new WebDriverWait(driver,Duration.ofSeconds(15));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));

        // Cierre de sesión
        driver.findElement(By.id("logout_sidebar_link")).click();

        System.out.println("Se ha cerrado la sesión de manera exitosa!");
    }

    @Test
    public void errorSeccionAbout(){

        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\webdriver\\chromedriver.exe");

        // creacion del driver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");

        //Localizacion del elemento
        driver.findElement(By.id("user-name")).sendKeys("problem_user"); // se utiliza otro user
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        driver.findElement(By.id("react-burger-menu-btn")).click();

        // Espera explícita para el botón logout
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("about_sidebar_link")));

        // Cierre de sesión
        driver.findElement(By.id("about_sidebar_link")).click();

        if(driver.findElement(By.xpath("//h2[@class='MuiTypography-root MuiTypography-h3 css-ghnq6o']")).isDisplayed())
        {
            System.out.println("Aparecio un mensaje de error. No se encuentra la pagina");
        }
        else {
            System.out.println("Se pudo ingresar a la sección About");
        }
    }


}
