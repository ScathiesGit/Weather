package git.scathiesgit.weather.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClient;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgException(Model model, HttpServletResponse resp, IllegalArgumentException e) {
        resp.setStatus(SC_NOT_FOUND);
        model.addAttribute("reason", e.getMessage());
        return "home";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String test(Model model) {
        model.addAttribute("reason", "Эта локация уже добавлена");
        return "found-weathers";
    }
}
