package modulos.produto;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import telas.LoginTela;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@DisplayName("Testes Mobile do Módulo de Produto")
public class ProdutoTest {

    private WebDriver app;

    @BeforeEach
    public void beforeEach() throws MalformedURLException {
        // abrir o app
        DesiredCapabilities capacidades = new DesiredCapabilities();
        capacidades.setCapability("deviceName", "Google Pixel 3");
        capacidades.setCapability("platform", "Android");
        capacidades.setCapability("udid","192.168.172.101:5555");
        capacidades.setCapability("appPackage","com.lojinha");
        capacidades.setCapability("appActivity","com.lojinha.ui.MainActivity");
        capacidades.setCapability("app","Z:\\Estudos\\TSPI\\Módulo 11 - Android\\Lojinha Nativa\\lojinha-nativa.apk");

        this.app = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capacidades);
        this.app.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @DisplayName("Validação do valor de produto não permitido")
    @Test
    public void testValidacaoDoValorDeProdutoNaoPermitido() {
        String mensagemApresentada = new LoginTela(app)
                .preencherUsuario("admin")
                .preencherSenha("admin")
                .efetuarLogin()
                .abrirTelaAdicaoProduto()
                .preencherNomeProduto("Teste Cervi")
                .preencherValorProduto("700001")
                .preencherCoresProduto("branco,laranja")
                .submissaoComErro()
                .obterMensagemDeErro();


        // validar que a mensagem de valor inválido fora apresentada
        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);
    }

    @AfterEach
    public void afterEach() {
        app.quit();
    }
}
