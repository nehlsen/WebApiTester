package me.nehlsen.webapitester.api;

import me.nehlsen.webapitester.persistence.PlanExecutionRecordNotFoundException;
import me.nehlsen.webapitester.persistence.PlanNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EntityNotFoundHandler {
    @ExceptionHandler(PlanNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    EntityNotFoundView planNotFoundHandler(PlanNotFoundException planNotFoundException) {
        return new EntityNotFoundView(404, planNotFoundException.getMessage());
    }

    @ExceptionHandler(PlanExecutionRecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    EntityNotFoundView planExecutionRecordNotFoundHandler(PlanExecutionRecordNotFoundException planExecutionRecordNotFoundException) {
        return new EntityNotFoundView(404, planExecutionRecordNotFoundException.getMessage());
    }
}
