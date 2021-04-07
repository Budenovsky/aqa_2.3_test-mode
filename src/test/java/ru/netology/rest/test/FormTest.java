package ru.netology.rest.test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.rest.data.DataHelper;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FormTest {

    @BeforeEach
    void Setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessLogin() {
        val validUser = DataHelper.getRegisteredUser("active");
        $("[type='text']").setValue(validUser.getLogin());
        $("[type='password']").setValue(validUser.getPassword());
        $(".button__text").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void shouldShowIfUserBlocked() {
        val invalidUser = DataHelper.getUserBlocked("blocked");
        $("[type='text']").setValue(invalidUser.getLogin());
        $("[type='password']").setValue(invalidUser.getPassword());
        $(".button__text").click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    void shouldShowErrorWhenIncorrectPassword() {
        val wrongPasswordUser = DataHelper.getWrongPassword();
        $("[type='text']").setValue(wrongPasswordUser.getLogin());
        $("[type='password']").setValue(wrongPasswordUser.getPassword());
        $(byText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.visible).shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldShowIfIncorrectLogin(){
        val wrongLoginUser = DataHelper.getWrongLogin();
        $("[type='text']").setValue(wrongLoginUser.getLogin());
        $("[type='password']").setValue(wrongLoginUser.getPassword());
        $(byText("Продолжить")).click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.visible).shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
}
