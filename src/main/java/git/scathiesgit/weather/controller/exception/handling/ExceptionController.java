package git.scathiesgit.weather.controller.exception.handling;

import git.scathiesgit.weather.repository.exception.LocationAlreadyExistsException;
import git.scathiesgit.weather.repository.exception.UserAlreadyExistsException;
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

import static jakarta.servlet.http.HttpServletResponse.*;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({IllegalArgumentException.class, HttpClientErrorException.class})
    public String handleIllegalArgException(Model model, HttpServletResponse resp) {
        writeResp(model, resp, SC_NOT_FOUND, "По вашему запросу ничего не нашлось");
        return "home";
    }

    @ExceptionHandler(LocationAlreadyExistsException.class)
    public String handleLocationAlreadyExistsException(Model model, HttpServletResponse resp) {
        writeResp(model, resp, SC_CONFLICT, "Такая локация уже добавлена");
        return "found-weathers";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExistsException(Model model, HttpServletResponse resp) {
        writeResp(model, resp, SC_CONFLICT, "Пользователь с таким именем уже есть");
        return "registration";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationException(Model model, HttpServletResponse resp, MethodArgumentNotValidException e) {
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
