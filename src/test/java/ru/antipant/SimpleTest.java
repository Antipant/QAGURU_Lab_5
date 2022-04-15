package ru.antipant;
import org.junit.jupiter.api.*;

@DisplayName("Класс с демонстрационными тестами")
public class SimpleTest {

    @BeforeAll
    void beforeAll(){
        Assertions.assertTrue(2<3);
    }

    @BeforeEach
    void beforeEach(){
        Assertions.assertTrue(2<3);
    }

    @DisplayName("Демонстрационный тест")
    @Test
    void firstTest() {
        Assertions.assertTrue(3>2);
        Assertions.assertFalse(3<2);
        Assertions.assertEquals("Foo", "Foo");
        Assertions.assertAll(
                () ->         Assertions.assertTrue(3>2),
                ()->        Assertions.assertFalse(3<2)
        );
    }
    @DisplayName("Демонстрационный тест №2")
    @Test
    void secondTest() {
        Assertions.assertTrue(3<2);
    }

    @AfterEach
    void afterEach(){
        Assertions.assertTrue(2<3);
    }

    @AfterAll
    void afterAll(){
        Assertions.assertTrue(2<3);
    }
}
