package org.example.test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CarritoComprasTest {

    @Test
    public void screenshotProductos() //throws IOException -- se utiliza para generar una excepción explícitamente y manejarla
    {

        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\webdriver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("problem_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        //Espera implicita
       // driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); -- deprecado en ultima version de selenium
        new WebDriverWait(driver, Duration.ofSeconds(15));

        // Se realiza la captura de pantalla
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            // Ruta donde se guardará el screenshot
            Path destinationPath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources","screenshots","screenshotProductos.png");

            // Se Copia el screenshot al destino especificado
            Files.copy(src.toPath(), destinationPath);

            System.out.println("Screenshot guardado en: " + destinationPath);

        } catch (IOException e) {
           // e.printStackTrace(); -- imprime la traza completa de la excepción
            System.out.println(e.getMessage());
           // throw(e);

        }finally {
            // Cerrar el navegador
            //driver.quit();
        }

    }

    @Test
    public void verDetalleProducto(){
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
            driver.findElement(By.linkText("Sauce Labs Backpack")).click();
            WebElement nombreProducto = driver.findElement(By.xpath("//div[@class='inventory_details_name large_size']"));
            Assert.assertTrue(nombreProducto.isDisplayed(),"Se visualiza información sobre el producto");
            Assert.assertEquals(nombreProducto.getText(),"Sauce Labs Backpack");

            //Volver al home de productos
            driver.findElement(By.name("back-to-products")).click();

        }
        else{
            System.out.println("No se pudo iniciar sesion!");
        }
    }

    @Test
    public void agregarProductosAlCarrito(){

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

            List<WebElement> productos = driver.findElements(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']"));

            for (WebElement producto : productos) {
                producto.click();
                new WebDriverWait(driver, Duration.ofSeconds(15));
            }

            driver.findElement(By.className("shopping_cart_link")).click();

        }
        else{
            System.out.println("No se pudo iniciar sesion!");
        }
    }

    @Test
    public void realizarCompraExitosa(){

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

            List<WebElement> productos = driver.findElements(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']"));

            for (WebElement producto : productos) {
                producto.click();
                new WebDriverWait(driver, Duration.ofSeconds(15));
            }

            driver.findElement(By.className("shopping_cart_link")).click();
            driver.findElement(By.id("checkout")).click();

            //Se cargan datos del cliente
            driver.findElement(By.id("first-name")).sendKeys("Juan");
            driver.findElement(By.id("last-name")).sendKeys("Perez");
            driver.findElement(By.id("postal-code")).sendKeys("5000");
            driver.findElement(By.id("continue")).click();

            if(driver.findElement(By.className("summary_info")).isDisplayed()){
                driver.findElement(By.id("finish")).click();

                WebElement mensajeCompra= driver.findElement(By.xpath("//h2[@class='complete-header']"));
                Assert.assertEquals(mensajeCompra.getText(),"Thank you for your order!");
                System.out.println("Compra realizada con exito!");

            }
            else {
                System.out.println("No se pueden visualizar los productos del carrito!");
            }
        }
        else{
            System.out.println("No se pudo iniciar sesion!");
        }
    }

    @Test
    public void cancelarCompra(){

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

            List<WebElement> productos = driver.findElements(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']"));

            for (WebElement producto : productos) {
                producto.click();
                new WebDriverWait(driver, Duration.ofSeconds(15));
            }

            driver.findElement(By.className("shopping_cart_link")).click();
            driver.findElement(By.id("checkout")).click();

            //Se cargan datos del cliente
            driver.findElement(By.id("first-name")).sendKeys("Juan");
            driver.findElement(By.id("last-name")).sendKeys("Perez");
            driver.findElement(By.id("postal-code")).sendKeys("5000");
            driver.findElement(By.id("continue")).click();

            if(driver.findElement(By.className("summary_info")).isDisplayed()){
                driver.findElement(By.name("cancel")).click();

                WebElement tituloProductos= driver.findElement(By.className("title"));
                Assert.assertEquals(tituloProductos.getText(),"Products");
                System.out.println("Compra cancelada");

            }
            else {
                System.out.println("No se pueden visualizar los productos del carrito!");
            }

        }
        else{
            System.out.println("No se pudo iniciar sesion!");
        }

    }

    @Test
    public void compraSinDatosDeCliente(){

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

            List<WebElement> productos = driver.findElements(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']"));

            for (WebElement producto : productos) {
                producto.click();
                new WebDriverWait(driver, Duration.ofSeconds(15));
            }

            driver.findElement(By.className("shopping_cart_link")).click();
            driver.findElement(By.id("checkout")).click();

            if(driver.findElement(By.className("title")).isDisplayed()){
                driver.findElement(By.id("continue")).click();

                WebElement mensajeError= driver.findElement(By.xpath("//h3[@data-test='error']"));
                Assert.assertEquals(mensajeError.getText(),"Error: First Name is required");

            }
            else {
                System.out.println("No se pueden visualizar los productos del carrito!");
            }

        }
        else{
            System.out.println("No se pudo iniciar sesion!");
        }

    }

    @Test
    public void compraSinApellidoYCPDeCliente(){

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

            List<WebElement> productos = driver.findElements(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']"));

            for (WebElement producto : productos) {
                producto.click();
                new WebDriverWait(driver, Duration.ofSeconds(15));
            }

            driver.findElement(By.className("shopping_cart_link")).click();
            driver.findElement(By.id("checkout")).click();

            if(driver.findElement(By.className("title")).isDisplayed()){
                driver.findElement(By.id("first-name")).sendKeys("Juan");
                driver.findElement(By.id("continue")).click();

                WebElement mensajeError= driver.findElement(By.xpath("//h3[@data-test='error']"));
                Assert.assertEquals(mensajeError.getText(),"Error: Last Name is required");

            }
            else {
                System.out.println("No se pueden visualizar los productos del carrito!");
            }

        }
        else{
            System.out.println("No se pudo iniciar sesion!");
        }
    }

    @Test
    public void compraSinCodigoPostalDeCliente(){

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

            List<WebElement> productos = driver.findElements(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']"));

            for (WebElement producto : productos) {
                producto.click();
                new WebDriverWait(driver, Duration.ofSeconds(15));
            }

            driver.findElement(By.className("shopping_cart_link")).click();
            driver.findElement(By.id("checkout")).click();

            if(driver.findElement(By.className("title")).isDisplayed()){
                driver.findElement(By.id("first-name")).sendKeys("Juan");
                driver.findElement(By.id("last-name")).sendKeys("Perez");
                driver.findElement(By.id("continue")).click();

                WebElement mensajeError= driver.findElement(By.xpath("//h3[@data-test='error']"));
                Assert.assertEquals(mensajeError.getText(),"Error: Postal Code is required");

            }
            else {
                System.out.println("No se pueden visualizar los productos del carrito!");
            }
        }
        else{
            System.out.println("No se pudo iniciar sesion!");
        }
    }

    @Test
    public void compraCarritoVacio(){

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

            driver.findElement(By.className("shopping_cart_link")).click();
            driver.findElement(By.id("checkout")).click();

            //Se cargan datos del cliente
            driver.findElement(By.id("first-name")).sendKeys("Luis");
            driver.findElement(By.id("last-name")).sendKeys("Gomez");
            driver.findElement(By.id("postal-code")).sendKeys("5001");
            driver.findElement(By.id("continue")).click();

            if(driver.findElement(By.className("summary_info")).isDisplayed()){
                driver.findElement(By.id("finish")).click();

                WebElement mensajeCompra= driver.findElement(By.xpath("//h2[@class='complete-header']"));
                Assert.assertEquals(mensajeCompra.getText(),"Thank you for your order!");
                System.out.println("Compra realizada con carrito vacio!");

            }
            else {
                System.out.println("No se pueden visualizar los productos del carrito!");
            }
        }
        else{
            System.out.println("No se pudo iniciar sesion!");
        }
    }

    @Test
    public void removerProductosCarrito(){

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

            List<WebElement> productos = driver.findElements(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']"));

            for (WebElement producto : productos) {  //se agregan productos al carrito
                producto.click();
                new WebDriverWait(driver, Duration.ofSeconds(15));
            }

            driver.findElement(By.className("shopping_cart_link")).click();

            //se remueven los productos del carrito

            List<WebElement> removerProductos = driver.findElements(By.xpath("//button[@class='btn btn_secondary btn_small cart_button']"));

            for (WebElement producto : removerProductos ) {
                producto.click();
                new WebDriverWait(driver, Duration.ofSeconds(15));
            }

            System.out.println("Se eliminaron todos los productos del carrito!");
        }
        else{
            System.out.println("No se pudo iniciar sesion!");
        }
    }

    @Test
    public void ordernarProductosPorNombreZA(){

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

            driver.findElement(By.className("product_sort_container")).click();

            driver.findElement(By.xpath("//option[@value='za']")).click();

            if(driver.findElement(By.linkText("Test.allTheThings() T-Shirt (Red)")).isDisplayed()){
                System.out.println("Se ordenaron todos los productos de acuerdo al nombre desde la Z a la A!");
            }
            else {
                System.out.println("No se pudo ordenar los productos");
            }

        }
        else{
            System.out.println("No se pudo iniciar sesion!");
        }
    }

    @Test
    public void ordenarProductosPorPrecioBajo(){

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

            driver.findElement(By.className("product_sort_container")).click();

            driver.findElement(By.xpath("//option[@value='lohi']")).click();

            List<WebElement> ordenarProd = driver.findElements(By.className("inventory_item_price"));

            Assert.assertEquals(ordenarProd.get(0).getText(),"$7.99");

            System.out.println("Se ordenaron todos los productos de acuerdo al precio desde el mas bajo al mas alto");

        }
        else{
            System.out.println("No se pudo iniciar sesion!");
        }

    }

    @Test
    public void ordenarProductosPorPrecioAlto(){

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

            driver.findElement(By.className("product_sort_container")).click();

            driver.findElement(By.xpath("//option[@value='hilo']")).click();

            List<WebElement> ordenarProd = driver.findElements(By.className("inventory_item_price"));

            Assert.assertEquals(ordenarProd.get(0).getText(),"$49.99");

            System.out.println("Se ordenaron todos los productos de acuerdo al precio desde el mas alto al mas bajo");

        }
        else{
            System.out.println("No se pudo iniciar sesion!");
        }
    }


}


