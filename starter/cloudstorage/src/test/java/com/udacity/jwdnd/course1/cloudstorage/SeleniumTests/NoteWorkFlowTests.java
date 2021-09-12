package com.udacity.jwdnd.course1.cloudstorage.SeleniumTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteWorkFlowTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() throws InterruptedException {

        this.driver = new ChromeDriver();
        this.webDriverWait = new WebDriverWait (driver, 1000);

        this.signupAndLoginUser();

        this.insertNewNote();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void createAndDeleteNote() throws InterruptedException {

        Assertions.assertDoesNotThrow(() -> {
            this.driver.findElement(By.xpath("//th[text()='Hello Cansu']"));
            this.driver.findElement(By.xpath("//td[text()='This is a test note']"));
        });

        WebElement deleteBtn = this.driver.findElement(
                By.xpath("//*[@id=\"userTable\"]/tbody/tr/td[1]/a"));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteBtn));

        deleteBtn.click();

        this.backToHomeFromResultPage();

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.driver.findElement(By.xpath("//th[text()='Hello Cansu']"));
            this.driver.findElement(By.xpath("//td[text()='This is a test note']"));
        });
    }

    @Test
    @Order(2)
    public void updateNote() throws InterruptedException {

        Assertions.assertDoesNotThrow(() -> {
            this.driver.findElement(By.xpath("//th[text()='Hello Cansu']"));
            this.driver.findElement(By.xpath("//td[text()='This is a test note']"));
        });

        WebElement editBtn = this.driver.findElement(
                By.xpath("//*[@id='userTable']/tbody/tr/td[1]/button"));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(editBtn));

        editBtn.click();

        WebElement noteTitleField = this.driver.findElement(By.id("note-title"));

        this.webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleField));

        noteTitleField.clear();
        noteTitleField.sendKeys("Hello");

        WebElement noteDescriptionField = this.driver.findElement(By.id("note-description"));

        noteDescriptionField.clear();
        noteDescriptionField.sendKeys("Hello World");

        WebElement noteForm = this.driver.findElement(By.id("note-form"));

        noteForm.submit();

        this.backToHomeFromResultPage();

        Assertions.assertDoesNotThrow(() -> {
            this.driver.findElement(By.xpath("//th[text()='Hello']"));
            this.driver.findElement(By.xpath("//td[text()='Hello World']"));
        });
    }


    private void signupUser() {

        this.driver.get("http://localhost:" + this.port + "/signup");

        WebElement fNameField = this.driver.findElement(By.id("inputFirstName"));

        fNameField.sendKeys("Cansu");

        WebElement lNameField = this.driver.findElement(By.id("inputLastName"));

        lNameField.sendKeys("Hello");

        WebElement usrnameField = driver.findElement(By.id("inputUsername"));

        usrnameField.sendKeys("CA");

        WebElement pwdField = driver.findElement(By.id("inputPassword"));

        pwdField.sendKeys("Test12");

        WebElement submitButton = driver.findElement(By.id("signupButton"));

        submitButton.click();
    }

    private void loginUser() {

        driver.get("http://localhost:" + this.port + "/login");

        WebElement usrnameField = driver.findElement(By.id("inputUsername"));

        usrnameField.sendKeys("CA");

        WebElement pwdField = driver.findElement(By.id("inputPassword"));

        pwdField.sendKeys("Test12");

        WebElement submitButton = driver.findElement(By.id("loginBtn"));

        Assertions.assertEquals("Login", submitButton.getText());

        submitButton.click();
    }

    private void signupAndLoginUser() {

        this.signupUser();

        this.loginUser();
    }

    private void insertNewNote() throws InterruptedException {

        this.driver.get("http://localhost:" + this.port + "/home");

        WebElement notesTab = this.driver.findElement(By.id("nav-notes-tab"));

        notesTab.click();

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("note-creation-btn")));

        WebElement noteCreationBtn = driver.findElement(By.id("note-creation-btn"));

        Assertions.assertNotNull(noteCreationBtn);

        noteCreationBtn.click();

        WebElement noteTitleField = this.driver.findElement(By.id("note-title"));

        this.webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleField));

        noteTitleField.sendKeys("Hello Cansu");

        WebElement noteDescriptionField = this.driver.findElement(By.id("note-description"));

        noteDescriptionField.sendKeys("This is a test note");

        WebElement noteForm = this.driver.findElement(By.id("note-form"));

        noteForm.submit();

        this.backToHomeFromResultPage();
    }

    private void backToHomeFromResultPage() throws InterruptedException {

        Assertions.assertEquals("Result", driver.getTitle());

        WebElement backToHomeBtn = this.driver.findElement(By.id("back-to-home-from-success"));

        backToHomeBtn.click();

        this.webDriverWait.until(ExpectedConditions.titleContains("Home"));

        Assertions.assertEquals("Home", driver.getTitle());

        WebElement notesTab = this.driver.findElement(By.id("nav-notes-tab"));

        notesTab.click();
    }


}
