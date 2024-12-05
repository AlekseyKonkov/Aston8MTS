package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MtsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<String> results = new ArrayList<>();

    public MtsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToSite() {
        driver.get("https://www.mts.by/");
        results.add("Переход на сайт успешно выполнен");
    }

    public void acceptCookies() {
        try {
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cookie-agree\"]")));
            acceptCookiesButton.click();
            results.add("Куки успешно приняты");
        } catch (Exception e) {
            results.add("Не удалось принять куки.");
        }
    }

    public void verifyBlockTitle() {
        try {
            WebElement blockTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/h2")));
            if ("Онлайн пополнение без комиссии".equals(blockTitle.getText())) {
                results.add("Название блока корректное");
            } else {
                results.add("Название блока отличается от ожидаемого");
            }
        } catch (Exception e) {
            results.add("Не удалось проверить название блока.");
        }
    }

    public void checkLogosPresence() {
        String[] xpaths = {
                "//*[@id='pay-section']/div/div/div[2]/section/div/div[2]/ul/li[1]/img",
                "//*[@id='pay-section']/div/div/div[2]/section/div/div[2]/ul/li[2]/img",
                "//*[@id='pay-section']/div/div/div[2]/section/div/div[2]/ul/li[3]/img",
                "//*[@id='pay-section']/div/div/div[2]/section/div/div[2]/ul/li[4]/img",
                "//*[@id='pay-section']/div/div/div[2]/section/div/div[2]/ul/li[5]/img"
        };

        boolean allLogosPresent = true;

        for (String xpath : xpaths) {
            try {
                WebElement logo = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                if (logo == null) {
                    allLogosPresent = false;
                    break;
                }
            } catch (Exception e) {
                allLogosPresent = false;
                break;
            }
        }

        if (allLogosPresent) {
            results.add("Все логотипы платежных систем присутствуют");
        } else {
            results.add("Некоторые логотипы платежных систем отсутствуют");
        }
    }

    public void verifyMoreInfoLink() {
        try {
            WebElement moreInfoLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Подробнее о сервисе")));
            moreInfoLink.click();
            wait.until(ExpectedConditions.urlToBe("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/"));
            results.add("Ссылка 'Подробнее о сервисе' работает");
            driver.navigate().back();
            wait.until(ExpectedConditions.urlToBe("https://www.mts.by/"));
        } catch (Exception e) {
            results.add("Проблема с переходом по ссылке 'Подробнее о сервисе'");
        }
    }

    public void selectOption(String optionText) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='pay-section']/div/div/div[2]/section/div/div[1]/div[1]/div[2]")));
            dropdown.click();

            WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id='pay-section']/div/div/div[2]/section/div/div[1]/div[1]/div[2]/ul/li/p[contains(text(), '" + optionText + "')]")
            ));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", option);
            js.executeScript("arguments[0].click();", option);

            results.add("Опция '" + optionText + "' выбрана успешно.");
        } catch (Exception e) {
            results.add("Не удалось выбрать опцию: " + optionText);
        }
    }

    public void verifyEmptyFieldLabels() {
        selectOption("Услуги связи");
        try {
            WebElement phoneNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"connection-phone\"]")));
            WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"connection-sum\"]")));
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"connection-email\"]")));

            if ("Номер телефона".equals(phoneNumberField.getAttribute("placeholder")) &&
                    "Сумма".equals(amountField.getAttribute("placeholder")) &&
                    "E-mail для отправки чека".equals(emailField.getAttribute("placeholder"))) {
                results.add("Надписи в незаполненных полях корректны для услуги связи");
            } else {
                results.add("Надписи в незаполненных полях некорректны для услуги связи");
            }
        } catch (Exception e) {
            results.add("Не удалось проверить надписи в незаполненных полях для услуги связи.");
        }
    }

    public void verifyEmptyFieldLabelsForHomeInternet() {
        selectOption("Домашний интернет");
        try {
            WebElement subscriberNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"internet-phone\"]")));
            WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"internet-sum\"]")));
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"internet-email\"]")));

            if ("Номер абонента".equals(subscriberNumberField.getAttribute("placeholder")) &&
                    "Сумма".equals(amountField.getAttribute("placeholder")) &&
                    "E-mail для отправки чека".equals(emailField.getAttribute("placeholder"))) {
                results.add("Надписи в незаполненных полях корректны для домашнего интернета");
            } else {
                results.add("Надписи в незаполненных полях некорректны для домашнего интернета");
            }
        } catch (Exception e) {
            results.add("Не удалось проверить надписи в незаполненных полях для домашнего интернета.");
        }
    }

    public void verifyEmptyFieldLabelsForInstallment() {
        selectOption("Рассрочка");
        try {
            WebElement accountNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"score-instalment\"]")));
            WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"instalment-sum\"]")));
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"instalment-email\"]")));

            if ("Номер счета на 44".equals(accountNumberField.getAttribute("placeholder")) &&
                    "Сумма".equals(amountField.getAttribute("placeholder")) &&
                    "E-mail для отправки чека".equals(emailField.getAttribute("placeholder"))) {
                results.add("Надписи в незаполненных полях корректны для рассрочки");
            } else {
                results.add("Надписи в незаполненных полях некорректны для рассрочки");
            }
        } catch (Exception e) {
            results.add("Не удалось проверить надписи в незаполненных полях для рассрочки.");
        }
    }

    public void verifyEmptyFieldLabelsForDebt() {
        selectOption("Задолженность");
        try {
            WebElement accountNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"score-arrears\"]")));
            WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"arrears-sum\"]")));
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"arrears-email\"]")));

            if ("Номер счета на 2073".equals(accountNumberField.getAttribute("placeholder")) &&
                    "Сумма".equals(amountField.getAttribute("placeholder")) &&
                    "E-mail для отправки чека".equals(emailField.getAttribute("placeholder"))) {
                results.add("Надписи в незаполненных полях корректны для задолженности");
            } else {
                results.add("Надписи в незаполненных полях некорректны для задолженности");
            }
        } catch (Exception e) {
            results.add("Не удалось проверить надписи в незаполненных полях для задолженности.");
        }

    }

    public void fillFormAndSubmit() {
        try {
            selectOption("Услуги связи");

            WebElement phoneNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='connection-phone']")));
            WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='connection-sum']")));
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='connection-email']")));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", phoneNumberField);

            phoneNumberField.sendKeys("297777777");
            amountField.sendKeys("100");
            emailField.sendKeys("aaa.aa@aaa.ru");

            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='pay-connection']/button")));
            js.executeScript("arguments[0].scrollIntoView(true);", continueButton);
            js.executeScript("arguments[0].click();", continueButton);

            results.add("Кнопка 'Продолжить' работает");

        } catch (Exception e) {
            e.printStackTrace();
            results.add("Ошибка при попытке заполнения формы: " + e.getMessage());
        }
    }


    public boolean verifyPaymentForm() {
        try {
            // Проверка плейсхолдеров полей ввода
            boolean cardNumberPlaceholderCorrect = "Номер карты".equals(
                    driver.findElement(By.xpath("//*[@id=\"cc-number\"]")).getAttribute("placeholder"));

            boolean expiryPlaceholderCorrect = "Срок действия".equals(
                    driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/app-card-page/div/div[1]/app-card-input/form/div[1]/div[2]/div[1]/app-input/div/div/div[1]/input")).getAttribute("placeholder"));

            boolean cvcPlaceholderCorrect = "CVC".equals(
                    driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/app-card-page/div/div[1]/app-card-input/form/div[1]/div[2]/div[3]/app-input/div/div/div[1]/input")).getAttribute("placeholder"));

            // Проверка текста метки для имени держателя карты
            boolean cardHolderLabelCorrect = "Имя держателя (как на карте)".equals(
                    driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/app-card-page/div/div[1]/app-card-input/form/div[1]/div[3]/app-input/div/div/div[1]/label")).getText());

            // Проверка наличия других надписей
            boolean amountTextCorrect = "100.00 BYN".equals(
                    driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/div/div[1]/span[1]")).getText());

            boolean serviceInfoTextCorrect = "Оплата: Услуги связи Номер:375297777777".equals(
                    driver.findElement(By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/div/div[2]")).getText());

            boolean saveCardInfoTextCorrect = "Сохранить данные карты для последующих оплат".equals(
                    driver.findElement(By.xpath("//label[contains(text(), 'Сохранить данные карты')]")).getText());

            boolean payButtonTextCorrect = "Оплатить 100.00 BYN".equals(
                    driver.findElement(By.xpath("//button[contains(text(), 'Оплатить 100.00 BYN')]")).getText());

            boolean securePaymentTextCorrect = "Безопасная оплата обеспечивается bePaid".equals(
                    driver.findElement(By.xpath("//span[contains(text(), 'Безопасная оплата обеспечивается bePaid')]")).getText());

            // Проверка видимости иконок платежных систем
            boolean visaIconVisible = driver.findElement(By.xpath("//img[contains(@src, 'visa')]")).isDisplayed();
            boolean mastercardIconVisible = driver.findElement(By.xpath("//img[contains(@src, 'mastercard')]")).isDisplayed();
            boolean maestroIconVisible = driver.findElement(By.xpath("//img[contains(@src, 'maestro')]")).isDisplayed();

            // Проверка всех условий
            return cardNumberPlaceholderCorrect &&
                    expiryPlaceholderCorrect &&
                    cvcPlaceholderCorrect &&
                    cardHolderLabelCorrect &&
                    amountTextCorrect &&
                    serviceInfoTextCorrect &&
                    saveCardInfoTextCorrect &&
                    payButtonTextCorrect &&
                    securePaymentTextCorrect &&
                    visaIconVisible &&
                    mastercardIconVisible &&
                    maestroIconVisible;
        } catch (NoSuchElementException e) {
            return false;  // Элемент не найден, возвращаем false
        } catch (Exception e) {
            return false;  // Любая другая ошибка, возвращаем false
        }
    }

    // Метод для получения результатов
    public List<String> getResults() {
        return new ArrayList<>(results);
    }

    // Метод для вывода результатов
    public void printResults() {
        for (String result : results) {
            System.out.println(result);
        }
    }
}

