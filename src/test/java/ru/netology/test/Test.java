package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.data.DataGenerator.Registration.getUser;
import static ru.netology.data.DataGenerator.getRandomLogin;
import static ru.netology.data.DataGenerator.getRandomPassword;

public class Test {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @org.junit.jupiter.api.Test
    public void shouldSuccessRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id = 'login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldBe(Condition.visible).shouldHave(Condition.exactText("Личный кабинет"));
    }

    @org.junit.jupiter.api.Test
    public void shouldGetErrorMessageIfUserBlocked() {
        var registeredUser = getRegisteredUser("blocked");
        $("[data-test-id = 'login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"));
    }

    @org.junit.jupiter.api.Test
    public void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id = 'login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @org.junit.jupiter.api.Test
    public void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id = 'login'] input").setValue(wrongLogin);
        $("[data-test-id = 'password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @org.junit.jupiter.api.Test
    public void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id = 'login'] input").setValue(wrongPassword);
        $("[data-test-id = 'password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }
}
