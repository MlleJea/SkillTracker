package com.skills.karate;

import com.intuit.karate.junit5.Karate;

public class SkillsApiTest {

    @Karate.Test
    Karate testSkillsApi() {
        return Karate.run("classpath:karate/skills.feature");
    }
}
