package ita.tw.afs.spark.ErrorHandler;

import ita.tw.afs.spark.dto.CustomError;
import ita.tw.afs.spark.exception.ExistingCredentialException;
import ita.tw.afs.spark.exception.InvalidCredentialsException;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    // TODO: 10/25/2019 check why 204 if NOT_FOUND
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public CustomError handleInvalidCredentialException(InvalidCredentialsException e){
        return new CustomError(404, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public CustomError handleNotFoundException(NotFoundException e){
        return new CustomError(404, e.getMessage());
    }

    @ExceptionHandler(ExistingCredentialException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public CustomError handleExistingCredentialException(ExistingCredentialException e){
        return new CustomError(404, e.getMessage());
    }
}
