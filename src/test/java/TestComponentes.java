import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class TestComponentes {

    private WebDriver driver;

    @BeforeMethod
    public void inicializa() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
    }

    @AfterMethod
    public void finaliza() {
        driver.quit();
    }

    @Test
    public void deveInteragirComTextFild() {
        driver.findElement(By.id("elementosForm:nome")).sendKeys("Teste de Digitação!");

        Assert.assertEquals("Teste de Digitação!", driver.findElement(By.id("elementosForm:nome")).getAttribute("value"));
    }

    @Test
    public void deveInteragirComTextArea() {
        driver.findElement(By.id("elementosForm:sugestoes")).sendKeys("Texto com mais de uma linha \nSegunda\nTerceira\n\nFim!");

        Assert.assertEquals("Texto com mais de uma linha \nSegunda\nTerceira\n\nFim!", driver.findElement(By.id("elementosForm:sugestoes")).getAttribute("value"));
    }

    @Test
    public void deveInteragirComRadioButton() {
        driver.findElement(By.id("elementosForm:sexo:0")).click();

        Assert.assertTrue(driver.findElement(By.id("elementosForm:sexo:0")).isSelected());
    }

    @Test
    public void deveInteragirComCheckBox() {
        driver.findElement(By.id("elementosForm:comidaFavorita:0")).click();

        Assert.assertTrue(driver.findElement(By.id("elementosForm:comidaFavorita:0")).isSelected());
    }

    @Test
    public void deveInteragirComComboBox() {
        WebElement elemento = driver.findElement(By.id("elementosForm:escolaridade"));
        Select combo = new Select(elemento);
        combo.selectByVisibleText("2o grau incompleto");

        Assert.assertEquals("2o grau incompleto", combo.getFirstSelectedOption().getText());
    }

    @Test
    public void deveTestarOpcoesDoComboBox() {
        WebElement elemento = driver.findElement(By.id("elementosForm:escolaridade"));
        Select combo = new Select(elemento);
        List<WebElement> options = combo.getOptions();

        Assert.assertEquals(8, options.size());
    }

    @Test
    public void deveTestarOpcoesDoComboBox2() {
        WebElement elemento = driver.findElement(By.id("elementosForm:escolaridade"));
        Select combo = new Select(elemento);
        List<WebElement> options = combo.getOptions();

        boolean encontrou = false;

        for (WebElement option: options) {
            if (option.getText().equals("Mestrado")) {
                encontrou = true;
                break;
            }
        }

        Assert.assertTrue(encontrou);
    }

    @Test
    public void deveInteragirComListBox() {
        WebElement elemento = driver.findElement(By.id("elementosForm:esportes"));
        Select combo = new Select(elemento);

        combo.selectByVisibleText("Natacao");
        combo.selectByVisibleText("Corrida");
        combo.selectByVisibleText("O que eh esporte?");

        List<WebElement> allSelectedOptions = combo.getAllSelectedOptions();

        Assert.assertEquals(3, allSelectedOptions.size());
    }

    @Test
    public void deveInteragirComButtons() {
        WebElement botao = driver.findElement(By.id("buttonSimple"));

        botao.click();

        Assert.assertEquals("Obrigado!", botao.getAttribute("value"));
    }

    @Test
    public void deveInteragirComLinks() {
        driver.findElement(By.linkText("Voltar")).click();

        Assert.assertEquals("Voltou!", driver.findElement(By.id("resultado")).getText());
    }

    @Test
    public void validaCampoNome() {
        driver.findElement(By.id("elementosForm:cadastrar")).click();

        Alert popup = driver.switchTo().alert();

        Assert.assertEquals("Nome eh obrigatorio", popup.getText());
    }

    @Test
    public void validaCampoSobrenome() {
        driver.findElement(By.id("elementosForm:nome")).sendKeys("Nome");
        driver.findElement(By.id("elementosForm:cadastrar")).click();

        Alert popup = driver.switchTo().alert();

        Assert.assertEquals("Sobrenome eh obrigatorio", popup.getText());
    }

    @Test
    public void validaSexoSelecionado() {
        driver.findElement(By.id("elementosForm:nome")).sendKeys("Nome");
        driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Sobrenome");
        driver.findElement(By.id("elementosForm:cadastrar")).click();

        Assert.assertEquals("Sexo eh obrigatorio", driver.switchTo().alert().getText());
    }

    @Test
    public void validaComidaCarneXVegan() {
        driver.findElement(By.id("elementosForm:nome")).sendKeys("Nome");
        driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Sobrenome");
        driver.findElement(By.id("elementosForm:sexo:0")).click();
        driver.findElement(By.id("elementosForm:comidaFavorita:0")).click();
        driver.findElement(By.id("elementosForm:comidaFavorita:3")).click();
        driver.findElement(By.id("elementosForm:cadastrar")).click();

        Alert popup = driver.switchTo().alert();

        Assert.assertEquals("Tem certeza que voce eh vegetariano?", popup.getText());
    }

    @Test
    public void validaComidaFrangoXVegan() {
        driver.findElement(By.id("elementosForm:nome")).sendKeys("Nome");
        driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Sobrenome");
        driver.findElement(By.id("elementosForm:sexo:0")).click();
        driver.findElement(By.id("elementosForm:comidaFavorita:1")).click();
        driver.findElement(By.id("elementosForm:comidaFavorita:3")).click();
        driver.findElement(By.id("elementosForm:cadastrar")).click();

        Alert popup = driver.switchTo().alert();

        Assert.assertEquals("Tem certeza que voce eh vegetariano?", popup.getText());
    }

    @Test
    public void validaEsporteNatacaoXDuvida() {
        driver.findElement(By.id("elementosForm:nome")).sendKeys("Nome");
        driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Sobrenome");
        driver.findElement(By.id("elementosForm:sexo:0")).click();

        WebElement esportes = driver.findElement(By.id("elementosForm:esportes"));
        Select opcoesEsportes = new Select(esportes);
        opcoesEsportes.selectByVisibleText("Natacao");
        opcoesEsportes.selectByVisibleText("O que eh esporte?");
        driver.findElement(By.id("elementosForm:cadastrar")).click();

        Alert alerta = driver.switchTo().alert();

        Assert.assertEquals("Voce faz esporte ou nao?", alerta.getText());
    }

    @Test
    public void validaEsporteFutebolXDuvida() {
        driver.findElement(By.id("elementosForm:nome")).sendKeys("Nome");
        driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Sobrenome");
        driver.findElement(By.id("elementosForm:sexo:0")).click();

        WebElement esportes = driver.findElement(By.id("elementosForm:esportes"));
        Select opcoesEsportes = new Select(esportes);
        opcoesEsportes.selectByVisibleText("Futebol");
        opcoesEsportes.selectByVisibleText("O que eh esporte?");
        driver.findElement(By.id("elementosForm:cadastrar")).click();

        Alert alerta = driver.switchTo().alert();

        Assert.assertEquals("Voce faz esporte ou nao?", alerta.getText());
    }

    @Test
    public void validaEsporteCorridaXDuvida() {
        driver.findElement(By.id("elementosForm:nome")).sendKeys("Nome");
        driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Sobrenome");
        driver.findElement(By.id("elementosForm:sexo:0")).click();

        WebElement esportes = driver.findElement(By.id("elementosForm:esportes"));
        Select opcoesEsportes = new Select(esportes);
        opcoesEsportes.selectByVisibleText("Corrida");
        opcoesEsportes.selectByVisibleText("O que eh esporte?");
        driver.findElement(By.id("elementosForm:cadastrar")).click();

        Alert alerta = driver.switchTo().alert();

        Assert.assertEquals("Voce faz esporte ou nao?", alerta.getText());
    }

    @Test
    public void validaEsporteKarateXDuvida() {
        driver.findElement(By.id("elementosForm:nome")).sendKeys("Nome");
        driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Sobrenome");
        driver.findElement(By.id("elementosForm:sexo:0")).click();

        WebElement esportes = driver.findElement(By.id("elementosForm:esportes"));
        Select opcoesEsportes = new Select(esportes);
        opcoesEsportes.selectByVisibleText("Karate");
        opcoesEsportes.selectByVisibleText("O que eh esporte?");
        driver.findElement(By.id("elementosForm:cadastrar")).click();

        Alert alerta = driver.switchTo().alert();

        Assert.assertEquals("Voce faz esporte ou nao?", alerta.getText());
    }
}
