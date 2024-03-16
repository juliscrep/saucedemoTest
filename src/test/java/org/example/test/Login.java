package org.example.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Login {
    @Test
    public void loginExitoso() {

        //configuracion del driver
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\webdriver\\chromedriver.exe");


        // creacion del criver
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com/");

        //Localizacion del elemento
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();


        //indicamos que cierre las ventanas
        //driver.quit();

        System.out.println("Se pudo iniciar sesion de manera exitosa! ");

    }

    @Test
    public void loginError(){

        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\webdriver\\chromedriver.exe");


        // creacion del criver
        WebDriver driver = new ChromeDriver();

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


        // creacion del criver
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com/");

        //Localizacion del elemento
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("login-button")).click();

        WebElement mensajeErrorLogueo = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]/h3[1]"));
        Assert.assertTrue(mensajeErrorLogueo.isDisplayed(),"Es necesario ingresar una contraseña!");

        Assert.assertEquals(mensajeErrorLogueo.getText(),"Epic sadface: Password is required");

    }

}
