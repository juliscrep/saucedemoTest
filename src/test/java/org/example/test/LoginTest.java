package org.example.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {
    @Test
    public void loginExitoso() {

        //configuracion del driver
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\webdriver\\chromedriver.exe");


        // creacion del driver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");

        //Localizacion del elemento
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();


        //indicamos que cierre las ventanas
        //driver.quit();

        System.out.println("Se ha iniciado sesión de manera exitosa! ");

    }

    @Test
    public void loginError(){

        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\webdriver\\chromedriver.exe");


        // creacion del driver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");

        //Localizacion del elemento
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.name("password")).sendKeys("pass");
        driver.findElement(By.id("login-button")).click();

        WebElement mensajeErrorLogueo = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]/h3[1]"));
        Assert.assertTrue(mensajeErrorLogueo.isDisplayed(),"La contraseña es incorrecta!");

        Assert.assertEquals(mensajeErrorLogueo.getText(),"Epic sadface: Username and password do not match any user in this service");


    }

    @Test
    public void passwordVacia(){

        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\webdriver\\chromedriver.exe");


        // creacion del driver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");

        //Localizacion del elemento
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("login-button")).click();

        WebElement mensajeErrorLogueo = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]/h3[1]"));
        Assert.assertTrue(mensajeErrorLogueo.isDisplayed(),"Es necesario ingresar una contraseña!");

        Assert.assertEquals(mensajeErrorLogueo.getText(),"Epic sadface: Password is required");

    }

    @Test
    public void usernameVacio(){

        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\webdriver\\chromedriver.exe");


        // creacion del driver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");

        //Localizacion del elemento
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement mensajeErrorLogueo = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]/h3[1]"));
        Assert.assertTrue(mensajeErrorLogueo.isDisplayed(),"Es necesario ingresar una usuario!");

        Assert.assertEquals(mensajeErrorLogueo.getText(),"Epic sadface: Username is required");

    }

    @Test
    public void userYPassVacia(){
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\webdriver\\chromedriver.exe");


        // creacion del driver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");

        //Localizacion del elemento
        driver.findElement(By.id("user-name")).sendKeys("");
        driver.findElement(By.name("password")).sendKeys("");
        driver.findElement(By.id("login-button")).click();

        WebElement mensajeErrorLogueo = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]/h3[1]"));
        Assert.assertTrue(mensajeErrorLogueo.isDisplayed(),"Es necesario ingresar usuario y contraseña!");
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

        // Espera explícita para el botón del menú
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("react-burger-menu-btn")));

        // Cierre de sesión
        driver.findElement(By.id("react-burger-menu-btn")).click();
        WebDriverWait wait1 = new WebDriverWait(driver,Duration.ofSeconds(15));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));
        driver.findElement(By.id("logout_sidebar_link")).click();

        System.out.println("Se ha cerrado la sesión de manera exitosa!");
    }

}
