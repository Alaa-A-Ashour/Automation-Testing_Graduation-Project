package pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginBtn = By.xpath("//button[@type='submit']");
    private final By errorMsg = By.xpath("//p[contains(@class,'oxd-alert-content-text')]");
    private final By requiredMsg = By.xpath("//span[contains(@class,'oxd-input-group__message')]");

    public LoginPage login(String user, String pass) {
        write(usernameInput, user);
        write(passwordInput, pass);
        click(loginBtn);
        return this;
    }

    public String getErrorText() { return getText(errorMsg); }
    public boolean isRequiredDisplayed() { return isDisplayed(requiredMsg); }
}