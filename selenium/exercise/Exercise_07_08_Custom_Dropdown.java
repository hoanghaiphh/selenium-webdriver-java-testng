package exercise;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Exercise_07_08_Custom_Dropdown {
    WebDriver driver;
    WebDriverWait explicitWait;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void sleepInSeconds (long timeInSecond) {
        try {
            Thread.sleep(timeInSecond*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropdownSelect (String dropdownLocator, String optionsLocator, String optionValue) {
        driver.findElement(By.cssSelector(dropdownLocator)).click();
        sleepInSeconds(1);
        /*explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(optionsLocator)));
        List<WebElement> dropdownOptions = driver.findElements(By.cssSelector(optionsLocator));
        explicitWait...presenceOfAllElementsLocatedBy... cũng trả về list elements*/
        List<WebElement> dropdownOptions = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(optionsLocator)));
        for (WebElement option : dropdownOptions) {
            if (option.getText().equals(optionValue)) {
                option.click();
                break;
            }
        }
        sleepInSeconds(1);
    }
    @Test
    public void TC_07_Required() {
        // JQuery
        driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
        sleepInSeconds(1);

        dropdownSelect("span#speed-button", "ul#speed-menu div", "Fast");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button span.ui-selectmenu-text")).getText(),"Fast");

        dropdownSelect("span#files-button", "ul#files-menu div", "ui.jQuery.js");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#files-button span.ui-selectmenu-text")).getText(),"ui.jQuery.js");

        dropdownSelect("span#number-button", "ul#number-menu div", "16");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button span.ui-selectmenu-text")).getText(),"16");

        dropdownSelect("span#salutation-button", "ul#salutation-menu div", "Dr.");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#salutation-button span.ui-selectmenu-text")).getText(),"Dr.");

        // ReactJS
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
        sleepInSeconds(1);

        dropdownSelect("i.dropdown.icon", "div.item span.text", "Stevie Feliciano");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Stevie Feliciano");

        driver.navigate().refresh();
        sleepInSeconds(1);

        dropdownSelect("i.dropdown.icon", "div.item span.text", "Justen Kitsune");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Justen Kitsune");

        // VueJS
        driver.get("https://mikerodham.github.io/vue-dropdowns/");
        sleepInSeconds(1);

        dropdownSelect("li.dropdown-toggle", "ul.dropdown-menu a", "Third Option");
        Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(),"Third Option");

        driver.navigate().refresh();
        sleepInSeconds(1);

        dropdownSelect("li.dropdown-toggle", "ul.dropdown-menu a", "Second Option");
        Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(),"Second Option");

        // Default
        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
        sleepInSeconds(1);

        dropdownSelect("select[name='DateOfBirthDay']", "select[name='DateOfBirthDay']>option", "15");
        Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']/option[text() = '15']")).isSelected());
        dropdownSelect("select[name='DateOfBirthMonth']", "select[name='DateOfBirthMonth']>option", "October");
        Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthMonth']/option[text() = 'October']")).isSelected());
        dropdownSelect("select[name='DateOfBirthYear']", "select[name='DateOfBirthYear']>option", "1989");
        Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthYear']/option[text() = '1989']")).isSelected());

        driver.navigate().refresh();
        sleepInSeconds(1);

        dropdownSelect("select[name='DateOfBirthDay']", "select[name='DateOfBirthDay']>option", "25");
        Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']/option[text() = '25']")).isSelected());
        dropdownSelect("select[name='DateOfBirthMonth']", "select[name='DateOfBirthMonth']>option", "June");
        Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthMonth']/option[text() = 'June']")).isSelected());
        dropdownSelect("select[name='DateOfBirthYear']", "select[name='DateOfBirthYear']>option", "2021");
        Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthYear']/option[text() = '2021']")).isSelected());
    }

    public void editableDropdownSelect (String dropdownLocator, String optionsLocator, String optionValue) {
        driver.findElement(By.cssSelector(dropdownLocator)).clear();
        driver.findElement(By.cssSelector(dropdownLocator)).sendKeys(optionValue);
        sleepInSeconds(1);
        List<WebElement> dropdownOptions = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(optionsLocator)));
        for (WebElement option : dropdownOptions) {
            if (option.getText().equals(optionValue)) {
                option.click();
                break;
            }
        }
        sleepInSeconds(1);
    }
    @Test
    public void TC_07_required_Editable() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
        sleepInSeconds(1);

        dropdownSelect("input.search", "div.item span", "Angola");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Angola");

        driver.navigate().refresh();
        sleepInSeconds(1);

        editableDropdownSelect("input.search", "div.item span", "Argentina");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Argentina");

        driver.navigate().refresh();
        sleepInSeconds(1);

        dropdownSelect("input.search", "div.item span", "Belarus");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Belarus");

        driver.navigate().refresh();
        sleepInSeconds(1);

        editableDropdownSelect("input.search", "div.item span", "Bangladesh");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Bangladesh");

        driver.navigate().refresh();
        sleepInSeconds(1);

        editableDropdownSelect("input.search", "div.item span", "Andorra");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Andorra");
    }

    public void dropdownSelectJsClick(String dropdownLocator, String optionsLocator, String optionValue) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector(dropdownLocator)));
        sleepInSeconds(1);
        List<WebElement> dropdownOptions = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(optionsLocator)));
        for (WebElement option : dropdownOptions) {
            if (option.getText().equals(optionValue)) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                break;
            }
        }
        sleepInSeconds(1);
    }
    @Test
    public void TC_08_optional_JQuery_Honda() {
        driver.get("https://www.honda.com.vn/o-to/du-toan-chi-phi");
        sleepInSeconds(1);

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("div[class='container']")));

        dropdownSelectJsClick("button#selectize-input", "div.dropdown-menu.show>a", "HR-V RS (Đen ánh độc tôn/ xám phong cách)");
        Assert.assertEquals(driver.findElement(By.cssSelector("button#selectize-input")).getText(), "HR-V RS (Đen ánh độc tôn/ xám phong cách)");

        Select provinceDropdown = new Select(driver.findElement(By.cssSelector("select#province")));
        provinceDropdown.selectByVisibleText("Đà Nẵng");
        Assert.assertEquals(provinceDropdown.getFirstSelectedOption().getText(), "Đà Nẵng");

        Select areaDropdown = new Select(driver.findElement(By.cssSelector("select#registration_fee")));
        areaDropdown.selectByVisibleText("Khu vực III");
        Assert.assertEquals(areaDropdown.getFirstSelectedOption().getText(), "Khu vực III");

        WebElement totalCost = driver.findElement(By.cssSelector("div.cost-price span.total"));
        Assert.assertEquals(totalCost.getText(), "");
        driver.findElement(By.cssSelector("input.btn-cost-estimates")).click();
        sleepInSeconds(1);
        Assert.assertNotEquals(totalCost.getText(), "");

        driver.navigate().refresh();
        sleepInSeconds(1);

        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("div[class='container']")));

        dropdownSelectJsClick("button#selectize-input", "div.dropdown-menu.show>a", "CIVIC TYPE R (Trắng/Đỏ/Xanh/Xám/Đen)");
        Assert.assertEquals(driver.findElement(By.cssSelector("button#selectize-input")).getText(), "CIVIC TYPE R (Trắng/Đỏ/Xanh/Xám/Đen)");

        provinceDropdown = new Select(driver.findElement(By.cssSelector("select#province")));
        provinceDropdown.selectByVisibleText("Điện Biên");
        Assert.assertEquals(provinceDropdown.getFirstSelectedOption().getText(), "Điện Biên");

        areaDropdown = new Select(driver.findElement(By.cssSelector("select#registration_fee")));
        areaDropdown.selectByVisibleText("Khu vực II");
        Assert.assertEquals(areaDropdown.getFirstSelectedOption().getText(), "Khu vực II");

        totalCost = driver.findElement(By.cssSelector("div.cost-price span.total"));
        Assert.assertEquals(totalCost.getText(), "");
        driver.findElement(By.cssSelector("input.btn-cost-estimates")).click();
        sleepInSeconds(1);
        Assert.assertNotEquals(totalCost.getText(), "");
    }

    public void dropdownSelectJsScroll(String dropdownLocator, String optionsLocator, String optionValue) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", driver.findElement(By.xpath(dropdownLocator)));
        sleepInSeconds(1);
        driver.findElement(By.xpath(dropdownLocator)).click();
        List<WebElement> dropdownOptions = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(optionsLocator)));
        for (WebElement option : dropdownOptions) {
            if (option.getText().equals(optionValue)) {
                option.click();
                break;
            }
        }
        sleepInSeconds(1);
    }
    @Test
    public void TC_08_optional_Angular_Covid() { // tai sao dropdown page nay khong click bang js dc
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        sleepInSeconds(1);

        dropdownSelectJsScroll("//div[text()='Đăng kí mũi tiêm thứ']/parent::div", "//ng-dropdown-panel//span", "Mũi tiêm thứ nhất");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Đăng kí mũi tiêm thứ']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Mũi tiêm thứ nhất");

        dropdownSelectJsScroll("//div[text()='Giới tính']/parent::div", "//ng-dropdown-panel//span", "Nam");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Giới tính']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Nam");

        dropdownSelectJsScroll("//div[text()='Tỉnh/Thành phố']/parent::div", "//ng-dropdown-panel//span", "Thành phố Đà Nẵng");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Tỉnh/Thành phố']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Thành phố Đà Nẵng");

        dropdownSelectJsScroll("//div[text()='Quận/Huyện']/parent::div", "//ng-dropdown-panel//span", "Quận Cẩm Lệ");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Quận/Huyện']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Quận Cẩm Lệ");

        dropdownSelectJsScroll("//div[text()='Xã/Phường']/parent::div", "//ng-dropdown-panel//span", "Phường Hòa Xuân");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Xã/Phường']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Phường Hòa Xuân");

        dropdownSelectJsScroll("//div[text()='Dân tộc']/parent::div", "//ng-dropdown-panel//span", "Lào");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Dân tộc']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Lào");

        dropdownSelectJsScroll("//div[text()='Quốc tịch']/parent::div", "//ng-dropdown-panel//span", "Cuba");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Quốc tịch']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Cuba");

        dropdownSelectJsScroll("//div[text()='Nhóm ưu tiên']/parent::div", "//ng-dropdown-panel//div[@class='ng-star-inserted']", "15. Người lao động tự do");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Nhóm ưu tiên']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "15. Người lao động tự do");

        dropdownSelectJsScroll("//div[text()='Quan hệ']/parent::div", "//ng-dropdown-panel//span", "Người giám hộ");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Quan hệ']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Người giám hộ");

        dropdownSelectJsScroll("//div[text()='Buổi tiêm mong muốn']/parent::div", "//ng-dropdown-panel//span", "Cả ngày");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Buổi tiêm mong muốn']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Cả ngày");

        dropdownSelectJsScroll("//div[text()='Đăng kí mũi tiêm thứ']/parent::div", "//ng-dropdown-panel//span", "Mũi tiêm tiếp theo");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Đăng kí mũi tiêm thứ']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Mũi tiêm tiếp theo");

        dropdownSelectJsScroll("//div[text()='Tên vắc xin']/parent::div", "//ng-dropdown-panel//span", "COVID-19 Vaccine Astrazeneca");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Tên vắc xin']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "COVID-19 Vaccine Astrazeneca");

        dropdownSelectJsScroll("//div[text()='Địa điểm tiêm']/parent::div", "//ng-dropdown-panel//span", "01015-Bệnh viện Quân Y 354");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Địa điểm tiêm']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "01015-Bệnh viện Quân Y 354");
    }

    public void multipleDropdown(String[] selectedMonths, String dropdownText) {
        driver.findElement(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul/parent::div/preceding-sibling::button")).click(); // open dropdown list
        List<WebElement> allCheckboxes = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul//input[@data-name='selectItem']"))); // 12 checkboxes not include 'Select All'
        for (WebElement checkbox : allCheckboxes) { // select checkboxes
            if ((!checkbox.isSelected() && List.of(selectedMonths).contains(checkbox.getAttribute("value"))) // 1- checkbox: not checked + in selected List
                    || (checkbox.isSelected() && !List.of(selectedMonths).contains(checkbox.getAttribute("value")))) { // 2- checkbox: checked + not in selected List
                checkbox.click(); // --> click to switch status
            } // (checked + in List) (not checked + not in List) --> needn't switch
        }
        for (WebElement checkbox : allCheckboxes) { // verify checkboxes
            if (List.of(selectedMonths).contains(checkbox.getAttribute("value"))) {
                Assert.assertTrue(checkbox.isSelected());
            }
            else {
                Assert.assertFalse(checkbox.isSelected());
            }
        }
        driver.findElement(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul/parent::div/preceding-sibling::button")).click(); // close dropdown list
        Assert.assertEquals(driver.findElement(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul/parent::div/preceding-sibling::button/span")).getText(), dropdownText); // verify the text showed at dropdown
    }
    @Test
    public void TC_08_optional_Multiple_Select() {
        driver.get("https://multiple-select.wenzhixin.net.cn/templates/template.html?v=189&url=basic.html");
        sleepInSeconds(1);

        multipleDropdown(new String[]{}, "");

        multipleDropdown(new String[]{"1"}, "January");

        multipleDropdown(new String[]{"2", "3", "4"}, "February, March, April");

        multipleDropdown(new String[]{"3", "4", "5", "6"}, "4 of 12 selected");

        multipleDropdown(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"}, "11 of 12 selected");

        multipleDropdown(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}, "All selected");

        multipleDropdown(new String[]{"2", "4", "6", "8", "10", "12"}, "6 of 12 selected");

        multipleDropdown(new String[]{"1", "3", "5", "7", "9", "11"}, "6 of 12 selected");

        multipleDropdown(new String[]{"6", "9"}, "June, September");

        multipleDropdown(new String[]{}, "");

        driver.navigate().refresh();
        sleepInSeconds(1);

        driver.findElement(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul/parent::div/preceding-sibling::button")).click();
        sleepInSeconds(1);
        driver.findElement(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul//input[@data-name='selectAll']")).click();
        List<WebElement> allCheckboxes = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul//input"))); // 13 checkboxes include 'Select All'
        for (WebElement checkbox : allCheckboxes) { // verify checkboxes
            Assert.assertTrue(checkbox.isSelected());
        }
        driver.findElement(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul/parent::div/preceding-sibling::button")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul/parent::div/preceding-sibling::button/span")).getText(), "All selected");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
