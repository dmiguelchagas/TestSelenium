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
    private DSL dsl;

    @BeforeMethod
    public void inicializa() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
        dsl = new DSL(driver);
    }

    @AfterMethod
    public void finaliza() {
        driver.quit();
    }

    @Test
    public void deveInteragirComTextFild() {
        dsl.escreve("elementosForm:nome","Teste de Digitação!");

        Assert.assertEquals("Teste de Digitação!", dsl.obtemValorCampo("elementosForm:nome"));
    }

    @Test
    public void deveInteragirComTextArea() {
        dsl.escreve("elementosForm:sugestoes","Texto com mais de uma linha \nSegunda\nTerceira\n\nFim!");

        Assert.assertEquals("Texto com mais de uma linha \nSegunda\nTerceira\n\nFim!", dsl.obtemValorCampo("elementosForm:sugestoes"));
    }

    @Test
    public void deveInteragirComRadioButton() {
        dsl.clicaOpcao("elementosForm:sexo:0");

        Assert.assertTrue(dsl.isOpcaoMarcada("elementosForm:sexo:0"));
    }

    @Test
    public void deveInteragirComCheckBox() {
       dsl.clicaOpcao("elementosForm:comidaFavorita:0");

       Assert.assertTrue(dsl.isOpcaoMarcada("elementosForm:comidaFavorita:0"));
    }

    @Test
    public void deveInteragirComComboBox() {
        dsl.selecionaOpcaoCombo("elementosForm:escolaridade", "2o grau incompleto");

        Assert.assertEquals("2o grau incompleto", dsl.obtemValorCombo("elementosForm:escolaridade"));
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
        dsl.selecionaOpcaoCombo("elementosForm:esportes", "Natacao");
        dsl.selecionaOpcaoCombo("elementosForm:esportes", "Corrida");
        dsl.selecionaOpcaoCombo("elementosForm:esportes", "O que eh esporte?");

        WebElement elemento = driver.findElement(By.id("elementosForm:esportes"));
        Select combo = new Select(elemento);

        List<WebElement> allSelectedOptions = combo.getAllSelectedOptions();

        Assert.assertEquals(3, allSelectedOptions.size());
    }

    @Test
    public void deveInteragirComButtons() {
        dsl.clicaBotao("buttonSimple");

        Assert.assertEquals("Obrigado!", dsl.obtemValorBotao("buttonSimple"));
    }

    @Test
    public void deveInteragirComLinks() {
        dsl.clicaLink("Voltar");

        Assert.assertEquals("Voltou!", dsl.obtemTexto("resultado"));
    }

    @Test
    public void deveBuscarTextosNaPagina() {
        Assert.assertEquals("Campo de Treinamento", dsl.obtemTexto(By.tagName("h3")));
        Assert.assertEquals("Cuidado onde clica, muitas armadilhas...", dsl.obtemTexto(By.className("facilAchar")));
    }

    @Test
    public void validaCampoNome() {
        dsl.clicaBotao("elementosForm:cadastrar");

        Assert.assertEquals("Nome eh obrigatorio", dsl.obtemTextoAlert());
    }

    @Test
    public void validaCampoSobrenome() {
        dsl.escreve("elementosForm:nome", "Diego");
        dsl.clicaBotao("elementosForm:cadastrar");

        Assert.assertEquals("Sobrenome eh obrigatorio", dsl.obtemTextoAlert());
    }

    @Test
    public void validaSexoSelecionado() {
        dsl.escreve("elementosForm:nome", "Diego");
        dsl.escreve("elementosForm:sobrenome", "Chagas");
        dsl.clicaBotao("elementosForm:cadastrar");

        Assert.assertEquals("Sexo eh obrigatorio", dsl.obtemTextoAlert());
    }

    @Test
    public void validaComidaCarneXVegan() {
        dsl.escreve("elementosForm:nome", "Diego");
        dsl.escreve("elementosForm:sobrenome", "Chagas");
        dsl.clicaOpcao("elementosForm:sexo:0");
        dsl.clicaOpcao("elementosForm:comidaFavorita:0");
        dsl.clicaOpcao("elementosForm:comidaFavorita:3");
        dsl.clicaBotao("elementosForm:cadastrar");

        Assert.assertEquals("Tem certeza que voce eh vegetariano?", dsl.obtemTextoAlert());
    }

    @Test
    public void validaComidaFrangoXVegan() {
        dsl.escreve("elementosForm:nome", "Diego");
        dsl.escreve("elementosForm:sobrenome", "Chagas");
        dsl.clicaOpcao("elementosForm:sexo:0");
        dsl.clicaOpcao("elementosForm:comidaFavorita:1");
        dsl.clicaOpcao("elementosForm:comidaFavorita:3");
        dsl.clicaBotao("elementosForm:cadastrar");

        Assert.assertEquals("Tem certeza que voce eh vegetariano?", dsl.obtemTextoAlert());
    }

    @Test
    public void validaEsporteNatacaoXDuvida() {
        dsl.escreve("elementosForm:nome", "Diego");
        dsl.escreve("elementosForm:sobrenome", "Chagas");
        dsl.clicaOpcao("elementosForm:sexo:0");

        dsl.selecionaOpcaoCombo("elementosForm:esportes", "Natacao");
        dsl.selecionaOpcaoCombo("elementosForm:esportes", "O que eh esporte?");
        dsl.clicaBotao("elementosForm:cadastrar");

        Assert.assertEquals("Voce faz esporte ou nao?", dsl.obtemTextoAlert());
    }

    @Test
    public void validaEsporteFutebolXDuvida() {
        dsl.escreve("elementosForm:nome", "Diego");
        dsl.escreve("elementosForm:sobrenome", "Chagas");
        dsl.clicaOpcao("elementosForm:sexo:0");

        dsl.selecionaOpcaoCombo("elementosForm:esportes", "Futebol");
        dsl.selecionaOpcaoCombo("elementosForm:esportes", "O que eh esporte?");
        dsl.clicaBotao("elementosForm:cadastrar");

        Assert.assertEquals("Voce faz esporte ou nao?", dsl.obtemTextoAlert());
    }

    @Test
    public void validaEsporteCorridaXDuvida() {
        dsl.escreve("elementosForm:nome", "Diego");
        dsl.escreve("elementosForm:sobrenome", "Chagas");
        dsl.clicaOpcao("elementosForm:sexo:0");

        dsl.selecionaOpcaoCombo("elementosForm:esportes", "Corrida");
        dsl.selecionaOpcaoCombo("elementosForm:esportes", "O que eh esporte?");
        dsl.clicaBotao("elementosForm:cadastrar");

        Assert.assertEquals("Voce faz esporte ou nao?", dsl.obtemTextoAlert());
    }

    @Test
    public void validaEsporteKarateXDuvida() {
        dsl.escreve("elementosForm:nome", "Diego");
        dsl.escreve("elementosForm:sobrenome", "Chagas");
        dsl.clicaOpcao("elementosForm:sexo:0");

        dsl.selecionaOpcaoCombo("elementosForm:esportes", "Karate");
        dsl.selecionaOpcaoCombo("elementosForm:esportes", "O que eh esporte?");
        dsl.clicaBotao("elementosForm:cadastrar");

        Assert.assertEquals("Voce faz esporte ou nao?", dsl.obtemTextoAlert());
    }
}
