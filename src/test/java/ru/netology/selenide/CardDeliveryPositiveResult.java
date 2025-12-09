package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;

public class CardDeliveryPositiveResult {

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999");
    }

    private String generationDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldBeSuccessfullyCompleted() {
        $("[data-test-id='city'] input").setValue("Москва");
        String planningDate = generationDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE).setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+79876543210");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));

    }


}
