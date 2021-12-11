import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        // On récupére le système d'explotation
        String os = System.getProperty("os.name")
                .toLowerCase()
                .split(" ")[0];
        // générer le chemin du fichier du driver
        String pathMarionette = Paths.get(".").toAbsolutePath().normalize() + "/lib/chromedriver-" + os;

        // enregistre le chemin dans une propriété qui est webdriver.chrome.driver
        // Firefox : webdriver.gecko.driver
        System.setProperty("webdriver.chrome.driver", pathMarionette);

        // options pour mettre le navigateur en pleine écran
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        // on créé l'objet ChromeDriver
        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    public void ex1() throws Exception {
        driver.get("https://www.glisshop.com/ski/ski-nu/");
        Thread.sleep(1500);
        driver.findElement(By.id("tarteaucitronPersonalize2")).click();
        Thread.sleep(1000);
        List<WebElement> labels = driver.findElements(By.className("checkbox_label"));
        for (WebElement label : labels) {
            String marque = label.getText();
            if (marque.toLowerCase().contains("Armada".toLowerCase())) ;
            label.click();
            Thread.sleep(1000);
            break;
        }
    }

    @Test
    public void ex2() throws Exception {
        driver.get("https://www.glisshop.com/identification/");
        WebElement login = driver.findElement(By.id("block2-login"));
        login.click();
        login.clear();
        login.sendKeys("nchevalier@formateur-humanbooster.com");
        Thread.sleep(1000);
        WebElement password = driver.findElement(By.id("block2-password"));
        password.click();
        password.clear();
        password.sendKeys("humanB@63");
        Thread.sleep(1000);
        driver.findElement(By.className("btn_l1_quaternary")).click();
        Thread.sleep(1000);
        WebElement welcome = null;
        try {
            welcome = driver.findElement(By.className("background_topography"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(welcome);
    }

    @Test
    public void ex3() throws Exception {
        boolean found = false;
        driver.get("https://www.glisshop.com/ski/salomon/qst-922230536");
        Thread.sleep(1500);
        driver.findElement(By.id("tarteaucitronPersonalize2")).click();
        Thread.sleep(1000);
        driver.findElement(By.className("add-to-cart-actions")).click();
        Thread.sleep(1000);
        driver.findElement(By.tagName("a")).click();
        Thread.sleep(1000);
        WebElement webElement = driver.findElement(By.className("cart-table"));
        List<WebElement> titles = webElement.findElements(By.tagName("span"));
        for (WebElement title : titles) {
            String text = title.getText();
            if (text.toLowerCase().contains("salomon".toLowerCase())) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void ex4() throws Exception {
        boolean check = false;
        driver.get("https://www.glisshop.com/boots-snowboard/burton/moto-boa-black19538894?refSrc=18250874&nosto=productpage-nosto-2");
        Thread.sleep(1000);
        driver.findElement(By.id("tarteaucitronPersonalize2")).click();
        Thread.sleep(1000);
        List<WebElement> webElements = driver.findElements(By.className("axis-value-selector-container"));
        List<WebElement> tailles = webElements.get(1).findElements(By.tagName("span"));
        for (WebElement taille : tailles) {
            String pointureCart = taille.getText();
            if (pointureCart.contains("7")) {
                taille.click();
               break;
            }
        }
        driver.findElement(By.className("add-to-cart-actions")).click();
        Thread.sleep(1000);
        driver.findElement(By.tagName("a")).click();
        Thread.sleep(1000);
        WebElement webElement = driver.findElement(By.className("product-label"));
        String pointure = webElement.getText();
        if (pointure.contains("7")) {
            check = true;
        }
        assertTrue(check);
    }

    @Test
    public void ex5() throws Exception {
        String prixPlanche = "";
        driver.get("https://www.glisshop.com/pack-selector-snowboard.html");
        Thread.sleep(1000);
        driver.findElement(By.id("tarteaucitronPersonalize2")).click();
        Thread.sleep(1000);

        List<WebElement> imgs = driver.findElements(By.id("img"));
        for (WebElement img : imgs) {
            String alt = img.getAttribute("alt");
            if (alt.toLowerCase().contains("Yes Planche Snowboard Basic Profil")) {
                img.click();
                break;
            }
            Thread.sleep(1000);
        }
        List<WebElement> spans = driver.findElements(By.tagName("span"));
        for (WebElement span : spans) {
            String clas = span.getAttribute("class");
            if (clas.toLowerCase().contains("price-value")) {
                prixPlanche = span.getText();
                break;
            }

        }
    }

    @After
    public void tearDown() {
        // on ferme le driver ouvert
        driver.close();
        driver.quit();
    }

}
