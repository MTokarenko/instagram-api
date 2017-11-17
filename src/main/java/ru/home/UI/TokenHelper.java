package ru.home.UI;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.CHROME;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static ru.home.properties.Props.getProperty;

public class TokenHelper {
    public static void main(String[] args) {
        getToken();
    }

    public static String getToken() {
        Configuration.timeout = 10000;
        Configuration.savePageSource = false;
        Configuration.fastSetValue = true;
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\drivers\\chromedriver.exe");
        Configuration.browser = CHROME;
        open(getProperty("login_URL"));
        login();
        openLink(String.format(
                "https://api.instagram.com/oauth/authorize/?client_id=%s&redirect_uri=%s&response_type=code",
                getProperty("client_id"),
                getProperty("redirect_URI")));
        $(By.linkText("Instagram")).shouldNot(Condition.visible);
        String code = getWebDriver().getCurrentUrl().split("=")[1];
        openLink(String.format(
                "https://api.instagram.com/oauth/access_token/?client_id=%s&client_secret=%s" +
                        "&grant_type=authorization_code&redirect_uri=%s&code=%s",
                getProperty("client_id"),
                getProperty("client_secret"),
                getProperty("redirect_URI"),
                getProperty(code)));
        return null;
    }

    private static void login() {
        $(By.name("username"))
                .shouldBe(Condition.visible)
                .sendKeys(getProperty("login"));
        $(By.name("password"))
                .shouldBe(Condition.visible)
                .sendKeys(getProperty("password"));
        $(By.xpath(".//button[.='Войти']")).click();
        $(By.xpath(".//button[.='Войти']")).shouldNot(Condition.visible);
    }

    private static void openLink(String link) {
        getWebDriver().navigate().to(link);
    }

}
