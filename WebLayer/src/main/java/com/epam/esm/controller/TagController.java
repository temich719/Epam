package com.epam.esm.controller;

import com.epam.esm.DTOs.TagDTO;
import com.epam.esm.errors.AnswerMessageJson;
import com.epam.esm.exception.AlreadyExistElementException;
import com.epam.esm.exception.InvalidInputDataException;
import com.epam.esm.exception.NoSuchIdException;
import com.epam.esm.exception.NotInsertedException;
import com.epam.esm.service.TagService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@ResponseBody
public class TagController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(TagController.class);

    private static final String TAG_HAS_BEEN_CREATED = "New tag has been created!";
    private static final String THERE_IS_NO_TAGS = "There is no tags";
    private static final String DELETED = "Deleted successfully";
    private static final String DEFAULT_CREATE_TAG_CODE = "11";
    private static final String DEFAULT_CREATE_GET_TAG_LIST_CODE = "12";
    private static final String DEFAULT_DELETE_TAG_CODE = "13";

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService, Gson gson, AnswerMessageJson answerMessageJson) {
        super(gson, answerMessageJson);
        this.tagService = tagService;
    }

    @PostMapping("/new/tag")
    public ResponseEntity<?> createTag(@Valid @RequestBody TagDTO tagDTO, BindingResult bindingResult) throws NotInsertedException, AlreadyExistElementException, InvalidInputDataException {
        LOGGER.info("Start tag creation");
        bindingResultCheck(bindingResult);
        tagService.createTag(tagDTO);
        answerMessageJson.setMessage(TAG_HAS_BEEN_CREATED);
        HttpStatus httpStatus = HttpStatus.CREATED;
        answerMessageJson.setStatus(httpStatus.toString());
        answerMessageJson.setCode(httpStatus.value() + DEFAULT_CREATE_TAG_CODE);
        LOGGER.info(TAG_HAS_BEEN_CREATED);
        return new ResponseEntity<>(gson.toJson(answerMessageJson), httpStatus);
    }

    @GetMapping("/get/tag/{id}")
    public ResponseEntity<?> getTagById(@PathVariable long id) throws NoSuchIdException {
        LOGGER.info("Getting tag by id");
        TagDTO tagDTO = tagService.getTagById(id);
        return new ResponseEntity<>(gson.toJson(tagDTO), HttpStatus.OK);
    }

    @GetMapping("/get/tag/list")
    public ResponseEntity<?> getTagList() {
        LOGGER.info("Getting tag list");
        List<TagDTO> tagDTOs = tagService.getTagList();
        ResponseEntity<?> responseEntity;
        HttpStatus httpStatus;
        if (tagDTOs.isEmpty()) {
            httpStatus = HttpStatus.NOT_FOUND;
            responseEntity = new ResponseEntity<>(httpStatus);
            answerMessageJson.setMessage(THERE_IS_NO_TAGS);
            answerMessageJson.setStatus(httpStatus.toString());
            answerMessageJson.setCode(httpStatus.value() + DEFAULT_CREATE_GET_TAG_LIST_CODE);
        } else {
            responseEntity = new ResponseEntity<>(gson.toJson(tagDTOs), HttpStatus.OK);
        }
        return responseEntity;
    }

    @DeleteMapping("/delete/tag/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable long id) throws NoSuchIdException {
        LOGGER.info("Tag deletion by id");
        tagService.deleteTag(id);
        HttpStatus httpStatus = HttpStatus.OK;
        answerMessageJson.setMessage(DELETED);
        answerMessageJson.setStatus(httpStatus.toString());
        answerMessageJson.setCode(httpStatus.value() + DEFAULT_DELETE_TAG_CODE);
        return new ResponseEntity<>(gson.toJson(answerMessageJson), httpStatus);
    }
}
