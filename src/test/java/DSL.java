import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DSL {
    private WebDriver driver;

    public DSL(WebDriver driver) {
        this.driver = driver;
    }

    public void escreve(String id, String texto) {
        driver.findElement(By.id(id)).sendKeys(texto);
    }

    public String obtemValorCampo(String id) {
        return driver.findElement(By.id(id)).getAttribute("value");
    }

    public void clicaOpcao(String id) {
        driver.findElement(By.id(id)).click();
    }

    public boolean isOpcaoMarcada(String id) {
        return driver.findElement(By.id(id)).isSelected();
    }

    public void selecionaOpcaoCombo(String id, String valor) {
        WebElement elemento = driver.findElement(By.id(id));
        Select combo = new Select(elemento);
        combo.selectByVisibleText(valor);
    }

    public String obtemValorCombo(String id) {
        WebElement elemento = driver.findElement(By.id(id));
        Select combo = new Select(elemento);

        return combo.getFirstSelectedOption().getText();
    }

    public void clicaBotao(String id) {
        WebElement botao = driver.findElement(By.id(id));
        botao.click();
    }

    public String obtemValorBotao(String id) {
        WebElement botao = driver.findElement(By.id(id));
        return botao.getAttribute("value");
    }

    public void clicaLink(String link) {
        driver.findElement(By.linkText(link)).click();
    }

    public String obtemTexto(By by) {
        return driver.findElement(by).getText();
    }

    public String obtemTexto(String id) {
        return driver.findElement(By.id(id)).getText();
    }

    public String obtemTextoAlert() {
        Alert popup = driver.switchTo().alert();
        return popup.getText();
    }
}
