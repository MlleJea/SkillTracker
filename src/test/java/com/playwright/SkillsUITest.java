package com.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
public class SkillsUITest {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;

    private void cleanupSkills() {
        try {
            page.navigate("http://localhost:8080/api/skills");
            String json = page.content();
            page.evaluate(
                    "() => { fetch('http://localhost:8080/api/skills')" +
                            ".then(r => r.json())" +
                            ".then(skills => skills.forEach(s => fetch('http://localhost:8080/api/skills/' + s.id, {method: 'DELETE'}))); }");
            page.waitForTimeout(500);
        } catch (Exception e) {
        }
    }

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(500)
        );
    }
    @AfterAll
    static void closeBrowser(){
        browser.close();
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage(){
        context = browser.newContext();
        page = context.newPage();
        cleanupSkills();
        page.navigate("http://localhost:3000");
    }

    @AfterEach
    void closeContext(){
        context.close();
    }

    @Test
    @DisplayName("Test 1 : L'application se charge correctement")
    void testPageLoad(){
        assertThat(page.locator("h1")).containsText("Skill Progress Tracker");
        assertThat(page.locator("input[placeholder*='Nouvelle compétence']")).isVisible();
        assertThat(page.locator("button:has-text('Ajouter')")).isVisible();
    }

    @Test
    @DisplayName("Test 2 : Créér un nouveau skill")
    void createNewSkill(){
        page.fill("input[placeholder*='Nouvelle compétence']", "Playwright Testing");
        page.click("button:has-text('Ajouter')");
        assertThat(page.locator("text=Playwright Testing")).isVisible();
        assertThat(page.locator(".skill-card:has-text('Playwright Testing') .progress-text")).containsText("0%");
    }
}
