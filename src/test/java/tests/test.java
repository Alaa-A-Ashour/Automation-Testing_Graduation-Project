package tests;

import driver.DriverManager;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.JsonReader;
import java.util.ArrayList;

public class test {

    @BeforeMethod
    public void OpenPage() {
        DriverManager.initDriver();
        DriverManager.getDriver().get(DriverManager.getProperty("base.url") + "auth/login");
    }

    @AfterMethod
    public void ClosePage() { DriverManager.quitDriver(); }

    @DataProvider
    public Object[][] validData() {
        return new Object[][]{{ JsonReader.getValue("validUser", "username"), JsonReader.getValue("validUser", "password") }};
    }
    // test Case #1 Login with valid credentials
    @Test(dataProvider = "validData")
    public void testLoginWithValidCredentials(String user, String pass) {
        new LoginPage().login(user, pass);
        DashboardPage dash = new DashboardPage();
        String DriverURL = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(DriverURL.contains("/dashboard/index"));
        System.out.println("Current URL:" + DriverURL);
        String DashboardHeader = dash.getHeaderText();
        Assert.assertEquals(DashboardHeader, "Dashboard");
        System.out.println("Current Header:"+ DashboardHeader);
    }
    // test Case #2 Login with invalid credentials
    @Test
    public void testLoginWithInvalidCredentials() {
        LoginPage login = new LoginPage();
        login.login(JsonReader.getValue("invalidUser", "username"), JsonReader.getValue("invalidUser", "password"));
        String ErrorMsg = login.getErrorText();
        Assert.assertEquals(ErrorMsg, "Invalid credentials");
        System.out.println("Message displayed is:" + ErrorMsg);
    }
    // test Case #3 Login with empty fields
    @Test
    public void testLoginWithEmptyFields() {
        LoginPage login = new LoginPage();
        login.login("", "");
        Assert.assertTrue(login.isRequiredDisplayed());
        System.out.println(login.isRequiredDisplayed());
    }

    //test Case #4: Search for an employee (PIM module)
    @Test(dataProvider = "validData")
    public void testSearchForEmployee(String user, String pass) {
        new LoginPage().login(user, pass);
        DashboardPage dash = new DashboardPage();
        dash.openPim();
        PimPage pim = new PimPage();
        pim.search("Sara");
        Assert.assertTrue(pim.hasRecord("Sara"));
    }
    // test Case #5: Search for a non-existing employee
    @Test(dataProvider = "validData")
    public void testSearchForNonExistingEmployee(String user, String pass) {
        new LoginPage().login(user, pass);
        DashboardPage dash = new DashboardPage();
        dash.openPim();
        PimPage pim = new PimPage();
        pim.search("Andy");
        Assert.assertTrue(pim.isNoRecordsDisplayed());
    }
    //test Case #6: Open Add Employee page
    @Test(dataProvider = "validData")
    public void testOpenAddEmployeePage(String user, String pass) {
        new LoginPage().login(user, pass);
        DashboardPage dash = new DashboardPage();
        dash.openPim();
        PimPage pim = new PimPage();
        pim.clickAddEmployee();
        String DriverURL = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(DriverURL.contains("/pim/addEmployee"));
        System.out.println("Current URL:"+ DriverURL);
        Assert.assertTrue(pim.isNameFieldsDisplayed());
        System.out.println("The full name is displayed:" +pim.isNameFieldsDisplayed());
    }

    //test Case #7: Add a new employee with an empty required field
    @Test(dataProvider = "validData")
    public void testAddEmployeeWithEmptyFirstName(String user, String pass) {
        new LoginPage().login(user, pass);
        DashboardPage dash = new DashboardPage();
        dash.openPim();
        PimPage pim = new PimPage();
        pim.clickAddEmployee();
        pim.enterName("", "LastNameOnly");
        pim.save();
        Assert.assertTrue(pim.isRequiredDisplayed());
        System.out.println("The first name is required:" + pim.isRequiredDisplayed());
    }
    //test Case #8: End-to-end — Add a new employee successfully
    @Test(dataProvider = "validData")
    public void testAddNewEmployeeSuccessfully(String user, String pass) {
        new LoginPage().login(user, pass);
        DashboardPage dash = new DashboardPage();
        dash.openPim();
        PimPage pim = new PimPage();
        pim.clickAddEmployee();
        String firstName = JsonReader.getValue("newEmployee", "firstName");
        String LastName = JsonReader.getValue("newEmployee", "lastName");
        pim.enterName(firstName,LastName);
        pim.save();
        pim.waitForPersonalDetailsPage();
        Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("/pim/viewPersonalDetails"));
        System.out.println(DriverManager.getDriver().getCurrentUrl());
        dash.openPim();
        pim.search(firstName);
        Assert.assertTrue(pim.hasRecord(firstName));
        System.out.println("The new employee has record: " + pim.hasRecord(firstName));
    }

    // test Case #9: Verify Admin > Add User page
    @Test(dataProvider = "validData")
    public void testVerifyAdminAddUser(String user, String pass) {
        new LoginPage().login(user, pass);
        DashboardPage dash = new DashboardPage();
        dash.openAdmin();
        AdminPage admin = new AdminPage();
        admin.clickAdd();
        Assert.assertTrue(admin.isFormDisplayingAllFields());
        System.out.println("The Add User form contains User Role, Employee Name, Username, and Password fields:" + admin.isFormDisplayingAllFields());
    }

    // Test Case #10: Verify the OrangeHRM footer/branding link
    @Test(dataProvider = "validData")
    public void testFooterLink(String user, String pass) {
        new LoginPage().login(user, pass);
        new DashboardPage().clickFooter();
        ArrayList<String> tabs = new ArrayList<>(DriverManager.getDriver().getWindowHandles());
        DriverManager.getDriver().switchTo().window(tabs.get(1));
        String DriverURL3 = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(DriverURL3.contains("orangehrm.com"));
        System.out.println("Curent URL: "+ DriverURL3);
    }

    //Test Case #11: Verify sidebar menu UI
    @Test(dataProvider = "validData")
    public void testSidebarMenuUI(String user, String pass) {
        new LoginPage().login(user, pass);
        Assert.assertTrue(new DashboardPage().IsSideMenuDisplayingAllFields());
    }
}