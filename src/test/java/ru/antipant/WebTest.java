package ru.antipant;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import ru.antipant.domain.MenuItem;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WebTest {

    @ValueSource(strings = {
            "Selenide",
            "Junit"
    })
    @ParameterizedTest(name = "Проверка поиска в яндексе по слову {0}")
    void  yaSearchTest(String testData) {
        Selenide.open("https://ya.ru/");

        $("#text").setValue(testData);
        $("button[type='submit']").click();

        $$(".serp-item")
                .find(Condition.text(testData))
                .shouldBe(visible);
    }

    @CsvSource(value = {
            "Selenide | is an open",
            "Junit | Support Junit"
    },
    delimiter = '|'
    )
    @ParameterizedTest(name = "Проверка поиска в яндексе по слову {0}, ожидаем {1}")
    void  yaSearchComplexTest(String testData, String expectedResult) {
        Selenide.open("https://ya.ru/");

        $("#text").setValue(testData);
        $("button[type='submit']").click();

        $$(".serp-item")
                .find(Condition.text(expectedResult))
                .shouldBe(visible);
    }

    static Stream<Arguments> methodSourceExampleTest() {
        return Stream.of(
                Arguments.of("first string", List.of(42,13)),
                Arguments.of("second string", List.of(1,2))
        );
    }

    @MethodSource
    @ParameterizedTest
    void methodSourceExampleTest(String first, List<Integer> second){
        System.out.println(first +" List: " + second);
    }


    @EnumSource(MenuItem.class)
    @ParameterizedTest()
    void  yaSearchMenuTest(MenuItem testData) {
        Selenide.open("https://ya.ru/");

        $("#text").setValue(testData.rusName);
        $("button[type='submit']").click();

        $$(".serp-item")
                .find(Condition.text(testData.rusName))
                .shouldBe(visible);
    }
    @AfterEach
    void close(){
        Selenide.closeWebDriver();
    }
}
