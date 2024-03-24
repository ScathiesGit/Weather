package git.scathiesgit.weather.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.SystemProperties;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static jakarta.servlet.http.HttpServletResponse.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({IllegalArgumentException.class, HttpClientErrorException.class})
    public String handleIllegalArgException(Model model, HttpServletResponse resp) {
        writeResp(model, resp, SC_NOT_FOUND, "По вашему запросу ничего не нашлось");
        return "home";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handlerDatabaseException(Model model, HttpServletResponse resp, HttpServletRequest req) {
        var html = "";
        var message = "";
        if (req.getServletPath().equals("/registration")) {
            message = "Имя занято";
            html = "registration";
        } else {
            message = "Такая локация у вас уже есть";
            html = "home";
        }
        writeResp(model, resp, SC_CONFLICT, message);
        return html;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handlerValidationException(Model model, HttpServletResponse resp, MethodArgumentNotValidException e) {
        var message = Arrays.stream(requireNonNull(e.getDetailMessageArguments()))
                .map(Object::toString)
                .collect(joining(SystemProperties.getLineSeparator()))
                .replaceAll(", and", "");
        writeResp(model, resp, SC_BAD_REQUEST, message);
        return "registration";
    }

    private void writeResp(Model model, HttpServletResponse resp, int statusCode, String message) {
        resp.setStatus(statusCode);
        model.addAttribute("reason", message);
    }
}
