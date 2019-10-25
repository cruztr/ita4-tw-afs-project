package ita.tw.afs.spark.ErrorHandler;

import ita.tw.afs.spark.error.CustomError;
import ita.tw.afs.spark.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public CustomError handleInvalidCredentialException(InvalidCredentialsException e){
        return new CustomError(204, e.getMessage());
    }
}
