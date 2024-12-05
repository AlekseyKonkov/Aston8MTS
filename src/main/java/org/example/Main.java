package org.example;

import org.openqa.selenium.WebDriver;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            WebDriverSetup webDriverSetup = new WebDriverSetup();
            driver = webDriverSetup.initializeDriver();
            MtsPage mtsPage = new MtsPage(driver);

            mtsPage.navigateToSite();
            mtsPage.acceptCookies();
            mtsPage.verifyBlockTitle();
            mtsPage.checkLogosPresence();
            mtsPage.verifyMoreInfoLink();
            mtsPage.verifyEmptyFieldLabels();
            mtsPage.verifyEmptyFieldLabelsForHomeInternet();
            mtsPage.verifyEmptyFieldLabelsForInstallment();
            mtsPage.verifyEmptyFieldLabelsForDebt();
            mtsPage.fillFormAndSubmit();
            mtsPage.verifyPaymentForm();


            mtsPage.printResults();
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}

