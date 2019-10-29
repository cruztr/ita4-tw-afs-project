package ita.tw.afs.spark.ErrorHandler;

import ita.tw.afs.spark.Util.SparkUtil;
import ita.tw.afs.spark.dto.GeneralResponse;
import ita.tw.afs.spark.exception.ExistingCredentialException;
import ita.tw.afs.spark.exception.GeneralException;
import ita.tw.afs.spark.exception.InvalidCredentialsException;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;

@ControllerAdvice
public class ControllerExceptionHandler {

    // TODO: 10/25/2019 check why 204 if NOT_FOUND
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public GeneralResponse handleInvalidCredentialException(InvalidCredentialsException e){
        return new GeneralResponse(404, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public GeneralResponse handleNotFoundException(NotFoundException e){
        return new GeneralResponse(404, e.getMessage());
    }

    @ExceptionHandler(ExistingCredentialException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public GeneralResponse handleExistingCredentialException(ExistingCredentialException e){
        return new GeneralResponse(404, e.getMessage());
    }

    @ExceptionHandler(GeneralException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public GeneralResponse GeneralException(GeneralException e){
        return SparkUtil.setGeneralResponseInternalServerError(e.getMessage(), new ArrayList<>());
    }
}
