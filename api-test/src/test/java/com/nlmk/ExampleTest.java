package com.nlmk;

import com.nlmk.config.DemoComponent;
import com.nlmk.config.GlobalConfig;
import com.nlmk.config.Setup;
import io.qameta.allure.Story;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;

@Story("API")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Setup.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@SpringBootTest
public class ExampleTest {

    @Autowired
    GlobalConfig config;

    @Autowired
    DemoComponent demoComponent;

    @ParameterizedTest
    @ValueSource(ints = {1,2})
    void simpleGetRequest(int post_id){
        for(String value:config.getServers()){
            System.out.println(value);
        }
        String resp = given().baseUri(config.getBaseURL())
               .pathParam("post_id", post_id)
                .pathParam("posts", "posts")
               .when().get("/{posts}/{post_id}")
               .then().statusCode(200)
               .and().extract().asString();
        System.out.println(resp);
    }

}
