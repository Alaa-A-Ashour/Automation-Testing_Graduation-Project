package pages;

import org.openqa.selenium.By;

public class PimPage extends BasePage {
    private final By addEmpTab = By.xpath("//a[text()='Add Employee']");
    private final By firstNameInput = By.name("firstName");
    private final By lastNameInput = By.name("lastName");
    private final By saveBtn = By.xpath("//button[@type='submit']");
    private final By searchInput = By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div//input");
    private final By searchBtn = By.xpath("//button[@type='submit']");
    private final By tableCard = By.xpath("//div[@class='oxd-table-card']");
    private final By noRecordsMsg = By.xpath("//span[text()='No Records Found']");
    private final By requiredMsg = By.xpath("//span[contains(@class,'oxd-input-group__message')]");

    public void clickAddEmployee() { click(addEmpTab); }
    public void enterName(String firstName, String lastName) {
        write(firstNameInput, firstName);
        write(lastNameInput, lastName);
    }
    public void save() { click(saveBtn); }
    public void search(String name) {
        write(searchInput, name);
        click(searchBtn);
    }
    public boolean hasRecord(String name) { return getText(tableCard).contains(name); }
    public boolean isNoRecordsDisplayed() { return isDisplayed(noRecordsMsg); }
    public boolean isRequiredDisplayed() { return isDisplayed(requiredMsg); }
    public boolean isNameFieldsDisplayed() { return isDisplayed(firstNameInput) && isDisplayed(lastNameInput); }

    public void waitForPersonalDetailsPage() {
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/pim/viewPersonalDetails"));
    }

}