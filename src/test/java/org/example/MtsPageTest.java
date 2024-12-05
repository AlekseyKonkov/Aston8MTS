package org.example;

import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MtsPageTest {
    private WebDriver driver;
    private MtsPage mtsPage;

    @BeforeEach
    public void setUp() {
        WebDriverSetup webDriverSetup = new WebDriverSetup();
        driver = webDriverSetup.initializeDriver();
        mtsPage = new MtsPage(driver);
    }

    @Test
    public void testPageFeatures() {
        mtsPage.navigateToSite();
        mtsPage.acceptCookies();
        mtsPage.verifyBlockTitle();
        mtsPage.verifyMoreInfoLink();
        mtsPage.checkLogosPresence();
        mtsPage.verifyEmptyFieldLabels();
        mtsPage.verifyEmptyFieldLabelsForHomeInternet();
        mtsPage.verifyEmptyFieldLabelsForInstallment();
        mtsPage.verifyEmptyFieldLabelsForDebt();
        mtsPage.fillFormAndSubmit();

        List<String> results = mtsPage.getResults();

        assertTrue(results.contains("Переход на сайт успешно выполнен"), "Сайт не был открыт корректно");
        assertTrue(results.contains("Куки успешно приняты"), "Куки не были приняты");
        assertTrue(results.contains("Название блока корректное"), "Название блока отличается от ожидаемого");
        // Вставьте остальные проверки
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
