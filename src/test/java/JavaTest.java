import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JavaTest {

    @Test
    public void testando() {

//        WebDriver googleDriver = new ChromeDriver();
//        googleDriver.get("http://www.google.com");
//        Assert.assertEquals("Google", googleDriver.getTitle());

        WebDriver edgeDriver = new EdgeDriver();
        edgeDriver.get("https://www.google.com");
        edgeDriver.manage().window().setPosition(new Point(1920, 0));
//        edgeDriver.manage().window().setSize(new Dimension(2560, 1080));
        edgeDriver.manage().window().maximize();
        Assert.assertTrue(edgeDriver.getTitle().contains("Google"));
        edgeDriver.quit();

    }
}
