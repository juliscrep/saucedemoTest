package org.example.test;

import org.openqa.selenium.*;
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

        // Espera explícita
        WebDriverWait wait1 = new WebDriverWait(driver,Duration.ofSeconds(15));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.className("app_logo")));

        if(driver.findElement(By.className("app_logo")).isDisplayed()){
            System.out.println("Se ha iniciado sesión de manera exitosa! ");
        }
        else {
            System.out.println("No se pudo iniciar sesión! ");
        }

        //indicamos que cierre las ventanas
        driver.quit();
    }

    @Test
    public void passwordIncorrecta(){

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
        driver.findElement(By.id("login-button")).click();

        WebElement mensajeErrorLogueo = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]/h3[1]"));
        Assert.assertTrue(mensajeErrorLogueo.isDisplayed(),"Es necesario ingresar usuario y contraseña!");
    }

    @Test
    public void userBloqueado(){
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\webdriver\\chromedriver.exe");

        // creacion del driver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");

        //Localizacion del elemento
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement mensajeError= driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]/h3[1]"));
        Assert.assertTrue(mensajeError.isDisplayed(),"El usuario se encuentra bloqueado!");

        Assert.assertEquals(mensajeError.getText(),"Epic sadface: Sorry, this user has been locked out.");


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


}
