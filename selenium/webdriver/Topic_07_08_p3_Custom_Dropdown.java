package webdriver;

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

public class Topic_07_08_p3_Custom_Dropdown {
    WebDriver driver;
    WebDriverWait explicitWait;
    JavascriptExecutor jsExecutor;

    public WebElement findVisibleElement(By locator) {
        return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForInvisibility(By locator) {
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void clickOnElement(By locator) {
        explicitWait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        jsExecutor = (JavascriptExecutor) driver;
    }

    public void selectDropdown(String optionsLocator, String optionValue) {
        List<WebElement> dropdownOptions = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(optionsLocator)));
        for (WebElement option : dropdownOptions) {
            if (option.getText().equals(optionValue)) {
                option.click();
                break;
            }
        }
    }
    @Test
    public void TC_07_Required() {
        // JQuery
        driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");

        clickOnElement(By.cssSelector("span#speed-button"));
        findVisibleElement(By.cssSelector("ul#speed-menu"));
        selectDropdown("ul#speed-menu div", "Fast");
        waitForInvisibility(By.cssSelector("ul#speed-menu"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("span#speed-button span.ui-selectmenu-text")).getText(),"Fast");

        clickOnElement(By.cssSelector("span#files-button"));
        findVisibleElement(By.cssSelector("ul#files-menu"));
        selectDropdown("ul#files-menu div", "ui.jQuery.js");
        waitForInvisibility(By.cssSelector("ul#files-menu"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("span#files-button span.ui-selectmenu-text")).getText(),"ui.jQuery.js");

        clickOnElement(By.cssSelector("span#number-button"));
        findVisibleElement(By.cssSelector("ul#number-menu"));
        selectDropdown("ul#number-menu div", "16");
        waitForInvisibility(By.cssSelector("ul#number-menu"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("span#number-button span.ui-selectmenu-text")).getText(),"16");

        clickOnElement(By.cssSelector("span#salutation-button"));
        findVisibleElement(By.cssSelector("ul#salutation-menu"));
        selectDropdown("ul#salutation-menu div", "Dr.");
        waitForInvisibility(By.cssSelector("ul#salutation-menu"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("span#salutation-button span.ui-selectmenu-text")).getText(),"Dr.");

        // ReactJS
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");

        clickOnElement(By.cssSelector("i.dropdown.icon"));
        findVisibleElement(By.cssSelector("div.menu.transition"));
        selectDropdown("div.item span.text", "Stevie Feliciano");
        waitForInvisibility(By.cssSelector("div.menu.transition"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("div.divider.text")).getText(),"Stevie Feliciano");

        // VueJS
        driver.get("https://mikerodham.github.io/vue-dropdowns/");

        clickOnElement(By.cssSelector("li.dropdown-toggle"));
        findVisibleElement(By.cssSelector("ul.dropdown-menu"));
        selectDropdown("ul.dropdown-menu a", "Third Option");
        waitForInvisibility(By.cssSelector("ul.dropdown-menu"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("li.dropdown-toggle")).getText(),"Third Option");

        // default dropdown can be handled with the same way
        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");

        clickOnElement(By.cssSelector("select[name='DateOfBirthDay']"));
        selectDropdown("select[name='DateOfBirthDay']>option", "15");
        Assert.assertTrue(findVisibleElement(By.xpath("//select[@name='DateOfBirthDay']/option[text() = '15']")).isSelected());

        clickOnElement(By.cssSelector("select[name='DateOfBirthMonth']"));
        selectDropdown("select[name='DateOfBirthMonth']>option", "October");
        Assert.assertTrue(findVisibleElement(By.xpath("//select[@name='DateOfBirthMonth']/option[text() = 'October']")).isSelected());

        clickOnElement(By.cssSelector("select[name='DateOfBirthYear']"));
        selectDropdown("select[name='DateOfBirthYear']>option", "1989");
        Assert.assertTrue(findVisibleElement(By.xpath("//select[@name='DateOfBirthYear']/option[text() = '1989']")).isSelected());
    }

    public void selectEditableDropdown(String optionValue) {
        findVisibleElement(By.cssSelector("input.search")).clear();
        findVisibleElement(By.cssSelector("input.search")).sendKeys(optionValue);
        findVisibleElement(By.cssSelector("div.menu"));
        List<WebElement> dropdownOptions = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div.item span")));
        for (WebElement option : dropdownOptions) {
            if (option.getText().equals(optionValue)) {
                option.click();
                waitForInvisibility(By.cssSelector("div.menu"));
                explicitWait.until(ExpectedConditions.attributeToBe(By.cssSelector("div.divider.text"), "textContent", optionValue));
                break;
            }
        }
    }
    @Test
    public void TC_07_required_Editable() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");

        selectEditableDropdown("Angola");
        Assert.assertEquals(findVisibleElement(By.cssSelector("div.divider.text")).getText(),"Angola");

        driver.navigate().refresh();

        selectEditableDropdown("Argentina");
        Assert.assertEquals(findVisibleElement(By.cssSelector("div.divider.text")).getText(),"Argentina");

        driver.navigate().refresh();

        selectEditableDropdown("Bangladesh");
        Assert.assertEquals(findVisibleElement(By.cssSelector("div.divider.text")).getText(),"Bangladesh");

        driver.navigate().refresh();

        selectEditableDropdown("Andorra");
        Assert.assertEquals(findVisibleElement(By.cssSelector("div.divider.text")).getText(),"Andorra");
    }

    public void selectDropdownByJs(String optionValue) {
        jsExecutor.executeScript("arguments[0].click()", driver.findElement(By.cssSelector("button#selectize-input")));
        findVisibleElement(By.cssSelector("div.dropdown-menu"));
        List<WebElement> dropdownOptions = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div.dropdown-menu.show>a")));
        for (WebElement option : dropdownOptions) {
            if (option.getText().equals(optionValue)) {
                jsExecutor.executeScript("arguments[0].click()", option);
                waitForInvisibility(By.cssSelector("div.dropdown-menu"));
                explicitWait.until(ExpectedConditions.attributeToBe(By.cssSelector("button#selectize-input"), "textContent", optionValue));
                break;
            }
        }
    }
    @Test
    public void TC_08_optional_JQuery_Honda() {
        driver.get("https://www.honda.com.vn/o-to/du-toan-chi-phi");

        jsExecutor.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.cssSelector("div[class='container']")));

        selectDropdownByJs("HR-V RS (Đen ánh độc tôn/ xám phong cách)");
        Assert.assertEquals(findVisibleElement(By.cssSelector("button#selectize-input")).getText(), "HR-V RS (Đen ánh độc tôn/ xám phong cách)");

        Select provinceDropdown = new Select(findVisibleElement(By.cssSelector("select#province")));
        provinceDropdown.selectByVisibleText("Đà Nẵng");
        explicitWait.until(ExpectedConditions.elementToBeSelected(By.xpath("//option[text()='Đà Nẵng']")));
        Assert.assertEquals(provinceDropdown.getFirstSelectedOption().getText(), "Đà Nẵng");

        Select areaDropdown = new Select(findVisibleElement(By.cssSelector("select#registration_fee")));
        areaDropdown.selectByVisibleText("Khu vực III");
        explicitWait.until(ExpectedConditions.elementToBeSelected(By.xpath("//option[text()='Khu vực III']")));
        Assert.assertEquals(areaDropdown.getFirstSelectedOption().getText(), "Khu vực III");

        Assert.assertEquals(driver.findElement(By.cssSelector("div.cost-price span.total")).getText(), "");
        clickOnElement(By.cssSelector("input.btn-cost-estimates"));
        Assert.assertNotEquals(driver.findElement(By.cssSelector("div.cost-price span.total")).getText(), "");

        driver.navigate().refresh();

        jsExecutor.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.cssSelector("div[class='container']")));

        selectDropdownByJs("CIVIC TYPE R (Trắng/Đỏ/Xanh/Xám/Đen)");
        Assert.assertEquals(findVisibleElement(By.cssSelector("button#selectize-input")).getText(), "CIVIC TYPE R (Trắng/Đỏ/Xanh/Xám/Đen)");

        provinceDropdown = new Select(findVisibleElement(By.cssSelector("select#province")));
        provinceDropdown.selectByVisibleText("Điện Biên");
        explicitWait.until(ExpectedConditions.elementToBeSelected(By.xpath("//option[text()='Điện Biên']")));
        Assert.assertEquals(provinceDropdown.getFirstSelectedOption().getText(), "Điện Biên");

        areaDropdown = new Select(findVisibleElement(By.cssSelector("select#registration_fee")));
        areaDropdown.selectByVisibleText("Khu vực II");
        explicitWait.until(ExpectedConditions.elementToBeSelected(By.xpath("//option[text()='Khu vực II']")));
        Assert.assertEquals(areaDropdown.getFirstSelectedOption().getText(), "Khu vực II");

        Assert.assertEquals(driver.findElement(By.cssSelector("div.cost-price span.total")).getText(), "");
        clickOnElement(By.cssSelector("input.btn-cost-estimates"));
        Assert.assertNotEquals(driver.findElement(By.cssSelector("div.cost-price span.total")).getText(), "");
    }

    /*public void scrollAndSelectDropdown(String dropdownLocator, String optionsLocator, String optionValue) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(false)", driver.findElement(By.xpath(dropdownLocator)));
        driver.findElement(By.xpath(dropdownLocator)).click();
        // wait
        List<WebElement> dropdownOptions = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(optionsLocator)));
        for (WebElement option : dropdownOptions) {
            if (option.getText().equals(optionValue)) {
                option.click();
                // wait
                break;
            }
        }
    }*/
    // this page unavailable since 01/01/2024
    /*@Test
    public void TC_08_optional_Angular_Covid() {
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        // can not click dropdown by javascript
        // can not wait for visibility

        scrollAndSelectDropdown("//div[text()='Đăng kí mũi tiêm thứ']/parent::div", "//ng-dropdown-panel//span", "Mũi tiêm thứ nhất");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Đăng kí mũi tiêm thứ']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Mũi tiêm thứ nhất");

        scrollAndSelectDropdown("//div[text()='Giới tính']/parent::div", "//ng-dropdown-panel//span", "Nam");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Giới tính']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Nam");

        scrollAndSelectDropdown("//div[text()='Tỉnh/Thành phố']/parent::div", "//ng-dropdown-panel//span", "Thành phố Đà Nẵng");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Tỉnh/Thành phố']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Thành phố Đà Nẵng");

        scrollAndSelectDropdown("//div[text()='Quận/Huyện']/parent::div", "//ng-dropdown-panel//span", "Quận Cẩm Lệ");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Quận/Huyện']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Quận Cẩm Lệ");

        scrollAndSelectDropdown("//div[text()='Xã/Phường']/parent::div", "//ng-dropdown-panel//span", "Phường Hòa Xuân");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Xã/Phường']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Phường Hòa Xuân");

        scrollAndSelectDropdown("//div[text()='Dân tộc']/parent::div", "//ng-dropdown-panel//span", "Lào");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Dân tộc']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Lào");

        scrollAndSelectDropdown("//div[text()='Quốc tịch']/parent::div", "//ng-dropdown-panel//span", "Cuba");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Quốc tịch']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Cuba");

        scrollAndSelectDropdown("//div[text()='Nhóm ưu tiên']/parent::div", "//ng-dropdown-panel//div[@class='ng-star-inserted']", "15. Người lao động tự do");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Nhóm ưu tiên']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "15. Người lao động tự do");

        scrollAndSelectDropdown("//div[text()='Quan hệ']/parent::div", "//ng-dropdown-panel//span", "Người giám hộ");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Quan hệ']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Người giám hộ");

        scrollAndSelectDropdown("//div[text()='Buổi tiêm mong muốn']/parent::div", "//ng-dropdown-panel//span", "Cả ngày");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Buổi tiêm mong muốn']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Cả ngày");

        scrollAndSelectDropdown("//div[text()='Đăng kí mũi tiêm thứ']/parent::div", "//ng-dropdown-panel//span", "Mũi tiêm tiếp theo");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Đăng kí mũi tiêm thứ']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Mũi tiêm tiếp theo");

        scrollAndSelectDropdown("//div[text()='Tên vắc xin']/parent::div", "//ng-dropdown-panel//span", "COVID-19 Vaccine Astrazeneca");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Tên vắc xin']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "COVID-19 Vaccine Astrazeneca");

        scrollAndSelectDropdown("//div[text()='Địa điểm tiêm']/parent::div", "//ng-dropdown-panel//span", "01015-Bệnh viện Quân Y 354");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Địa điểm tiêm']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "01015-Bệnh viện Quân Y 354");
    }*/

    public void multipleSelectDropdown(String[] options) {
        By dropdownLocator = By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul/parent::div/preceding-sibling::button");
        By optionsLocator = By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul//input[@data-name='selectItem']");
        clickOnElement(dropdownLocator); // open dropdown
        findVisibleElement(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul/parent::div")); // wait for visibility
        List<WebElement> allCheckboxes = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionsLocator)); // 12 checkboxes not include 'Select All'
        for (WebElement checkbox : allCheckboxes) { // select checkboxes
            if (!checkbox.isSelected() && List.of(options).contains(checkbox.getAttribute("value"))) {
                checkbox.click();
                explicitWait.until(ExpectedConditions.elementToBeSelected(checkbox));
            } else if (checkbox.isSelected() && !List.of(options).contains(checkbox.getAttribute("value"))) {
                checkbox.click();
                explicitWait.until(ExpectedConditions.elementSelectionStateToBe(checkbox, false));
            }
        }
        allCheckboxes = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionsLocator));
        for (WebElement checkbox : allCheckboxes) { // verify checkboxes
            if (List.of(options).contains(checkbox.getAttribute("value"))) {
                Assert.assertTrue(checkbox.isSelected());
            }
            else {
                Assert.assertFalse(checkbox.isSelected());
            }
        }
        clickOnElement(dropdownLocator); // close dropdown
        waitForInvisibility(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul/parent::div"));
    }
    @Test
    public void TC_08_optional_Multiple_Select() {
        driver.get("https://multiple-select.wenzhixin.net.cn/templates/template.html?v=189&url=basic.html");

        By dropdownText = By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul/parent::div/preceding-sibling::button/span");

        multipleSelectDropdown(new String[]{});
        Assert.assertEquals(driver.findElement(dropdownText).getText(), "");

        multipleSelectDropdown(new String[]{"1"});
        Assert.assertEquals(findVisibleElement(dropdownText).getText(), "January");

        multipleSelectDropdown(new String[]{"2", "3", "4"});
        Assert.assertEquals(findVisibleElement(dropdownText).getText(), "February, March, April");

        multipleSelectDropdown(new String[]{"3", "4", "5", "6"});
        Assert.assertEquals(findVisibleElement(dropdownText).getText(), "4 of 12 selected");

        multipleSelectDropdown(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"});
        Assert.assertEquals(findVisibleElement(dropdownText).getText(), "11 of 12 selected");

        multipleSelectDropdown(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
        Assert.assertEquals(findVisibleElement(dropdownText).getText(), "All selected");

        multipleSelectDropdown(new String[]{"2", "4", "6", "8", "10", "12"});
        Assert.assertEquals(findVisibleElement(dropdownText).getText(), "6 of 12 selected");

        multipleSelectDropdown(new String[]{"1", "3", "5", "7", "9", "11"});
        Assert.assertEquals(findVisibleElement(dropdownText).getText(), "6 of 12 selected");

        multipleSelectDropdown(new String[]{"6", "9"});
        Assert.assertEquals(findVisibleElement(dropdownText).getText(), "June, September");

        multipleSelectDropdown(new String[]{});
        Assert.assertEquals(driver.findElement(dropdownText).getText(), "");

        driver.navigate().refresh();

        clickOnElement(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul/parent::div/preceding-sibling::button"));
        findVisibleElement(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul/parent::div")); // wait for visibility
        clickOnElement(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul//input[@data-name='selectAll']"));
        explicitWait.until(ExpectedConditions.elementToBeSelected(
                By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul//input[@data-name='selectAll']")));
        List<WebElement> allCheckboxes = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul//input"))); // 13 checkboxes include 'Select All'
        for (WebElement checkbox : allCheckboxes) {
            Assert.assertTrue(checkbox.isSelected());
        }
        clickOnElement(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul/parent::div/preceding-sibling::button"));
        waitForInvisibility(By.xpath("//span[text()='January']/parent::label/parent::li/parent::ul/parent::div"));
        Assert.assertEquals(findVisibleElement(dropdownText).getText(), "All selected");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
