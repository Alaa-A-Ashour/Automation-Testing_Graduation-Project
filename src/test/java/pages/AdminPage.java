package pages;

import org.openqa.selenium.By;

public class AdminPage extends BasePage {
    private final By addBtn = By.xpath("//button[contains(.,'Add')]");
    private final By userRoleLabel = By.xpath("//label[text()='User Role']");
    private final By employeeNameLabel = By.xpath("//label[text()='Employee Name']");
    private final By usernameLabel = By.xpath("//label[text()='Username']");
    private final By passwordLabel = By.xpath("//label[text()='Password']");

    public void clickAdd() { click(addBtn); }
    public boolean isFormDisplayingAllFields() {
        return isDisplayed(userRoleLabel) && isDisplayed(employeeNameLabel) &&
                isDisplayed(usernameLabel) && isDisplayed(passwordLabel);
    }
}