package pages;

import org.openqa.selenium.By;
import java.util.List;

public class DashboardPage extends BasePage {
    private final By header = By.xpath("//h6[contains(@class,'oxd-topbar-header-breadcrumb-module')]");
    private final By pimMenu = By.xpath("//span[text()='PIM']");
    private final By adminMenu = By.xpath("//span[text()='Admin']");
    private final By footerLink = By.xpath("//a[contains(text(),'OrangeHRM, Inc')]");
    private final By sidebarItems = By.xpath("//ul[@class='oxd-main-menu']//span");

    public String getHeaderText() { return getText(header); }
    public void openPim() { click(pimMenu); }
    public void openAdmin() { click(adminMenu); }
    public void clickFooter() { click(footerLink); }

    public boolean IsSideMenuDisplayingAllFields() {
        List<String> items = driver.findElements(sidebarItems).stream().map(e -> e.getText()).toList();
        return items.containsAll(List.of("Admin", "PIM", "Leave", "Time", "Recruitment", "My Info", "Performance", "Dashboard", "Directory"));
    }
}