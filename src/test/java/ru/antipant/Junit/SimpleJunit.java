package ru.antipant.Junit;

import org.junit.jupiter.api.*;
import ru.antipant.SimpleTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SimpleJunit {

    public static void main(String[] args) throws Exception {

        // Находит классы с тестами
        Method[] declaredMethods = SimpleTest.class.getDeclaredMethods();
        Method beforeAllMethod = null;
        Method beforeEachMethod = null;
        Method afterAllMethod = null;
        Method afterEachMethod = null;

        for (Method method : declaredMethods) {
            method.setAccessible(true);
            BeforeAll beforeAll = method.getAnnotation(BeforeAll.class);
            BeforeEach beforeEach = method.getAnnotation(BeforeEach.class);
            AfterEach afterEach = method.getAnnotation(AfterEach.class);
            AfterAll afterAll = method.getAnnotation(AfterAll.class);
            if (beforeAll != null) {
                beforeAllMethod = method;
            }
            if (beforeEach != null) {
                beforeEachMethod = method;
            }
            if (afterAll != null) {
                afterAllMethod = method;
            }
            if (afterEach != null) {
                afterEachMethod = method;
            }
        }

        if (beforeAllMethod != null) {
            try {
                beforeAllMethod.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
            } catch (InvocationTargetException e) {
                // Если тест упал, сообщает нам
                System.out.println("BeforeAll упал: ");
                throw e;
            }
        }

        for (Method method : declaredMethods) {
            method.setAccessible(true);
            // Смотрит есть ли над методом @Test
            Test testAnnotation = method.getAnnotation(Test.class);
            Disabled disabled = method.getAnnotation(Disabled.class);

            if (beforeEachMethod != null) {
                try {
                    beforeEachMethod.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    // Если тест упал, сообщает нам
                    System.out.println("BeforeEach упал: ");
                    throw e;
                }
            }

            if (testAnnotation != null && disabled == null) {
                // Запускает
                try {
                    method.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    // Если тест упал, сообщает нам
                    System.out.println("Тест упал: ");
                    throw e;
                }
            }

            if (afterEachMethod != null) {
                try {
                    afterEachMethod.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    // Если тест упал, сообщает нам
                    System.out.println("AfterEach упал: ");
                    throw e;
                }
            }

        }

        if (afterAllMethod != null) {
            try {
                afterAllMethod.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
            } catch (InvocationTargetException e) {
                // Если тест упал, сообщает нам
                System.out.println("AfterAll упал: ");
                throw e;
            }
        }
    }
    // Если не упал, то все ок
}
