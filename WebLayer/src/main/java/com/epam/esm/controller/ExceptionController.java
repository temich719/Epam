package com.epam.esm.controller;

import com.epam.esm.errors.AnswerMessageJson;
import com.epam.esm.exception.AlreadyExistElementException;
import com.epam.esm.exception.InvalidInputDataException;
import com.epam.esm.exception.NoSuchIdException;
import com.epam.esm.exception.NotInsertedException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;

@ControllerAdvice
public class ExceptionController {

    private static final Logger LOGGER = Logger.getLogger(ExceptionController.class);

    private static final String SOMETHING_WENT_WRONG = "Something went wrong!";
    private static final String FILE_NOT_FOUND_CODE = "20";
    private static final String SOMETHING_WENT_WRONG_CODE = "21";
    private static final String NO_SUCH_ID_CODE = "22";
    private static final String NOT_INSERTED_CODE = "23";
    private static final String ALREADY_EXIST_ELEMENT_CODE = "24";
    private static final String INVALID_INPUT_DATE_CODE = "25";

    private final AnswerMessageJson answerMessageJson;

    @Autowired
    public ExceptionController(AnswerMessageJson answerMessageJson) {
        this.answerMessageJson = answerMessageJson;
    }

    @ExceptionHandler(value = FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    AnswerMessageJson handleFileNotFoundException(FileNotFoundException fileNotFoundException) {
        LOGGER.error("Handle FileNotFoundException");
        answerMessageJson.setMessage(fileNotFoundException.getMessage());
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        answerMessageJson.setStatus(httpStatus.toString());
        answerMessageJson.setCode(httpStatus.value() + FILE_NOT_FOUND_CODE);
        return answerMessageJson;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    AnswerMessageJson handleNotFoundStatusError() {
        LOGGER.error("Handle Throwable");
        answerMessageJson.setMessage(SOMETHING_WENT_WRONG);
        answerMessageJson.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        answerMessageJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + SOMETHING_WENT_WRONG_CODE);
        return answerMessageJson;
    }

    @ExceptionHandler(NoSuchIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    AnswerMessageJson handleNoSuchIdException(NoSuchIdException e) {
        LOGGER.error("Handle NoSuchIdException");
        answerMessageJson.setMessage(e.getMessage());
        answerMessageJson.setStatus(HttpStatus.NOT_FOUND.toString());
        answerMessageJson.setCode(HttpStatus.NOT_FOUND.value() + NO_SUCH_ID_CODE);
        return answerMessageJson;
    }

    @ExceptionHandler(NotInsertedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    AnswerMessageJson handleNotInsertedException(NotInsertedException e) {
        LOGGER.error("Handle NotInsertedException");
        answerMessageJson.setMessage(e.getMessage());
        answerMessageJson.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        answerMessageJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + NOT_INSERTED_CODE);
        return answerMessageJson;
    }

    @ExceptionHandler(AlreadyExistElementException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    AnswerMessageJson handleAlreadyExistElementException(AlreadyExistElementException e) {
        LOGGER.error("Handle AlreadyExistElementException");
        answerMessageJson.setMessage(e.getMessage());
        answerMessageJson.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        answerMessageJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + ALREADY_EXIST_ELEMENT_CODE);
        return answerMessageJson;
    }

    @ExceptionHandler(InvalidInputDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    AnswerMessageJson handleInvalidInputDataException(InvalidInputDataException e) {
        LOGGER.error("Handle InvalidInputDataException");
        answerMessageJson.setMessage(e.getMessage());
        answerMessageJson.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        answerMessageJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + INVALID_INPUT_DATE_CODE);
        return answerMessageJson;
    }
}
