package com.epam.esm.controller;

import com.epam.esm.dtos.TagDTO;
import com.epam.esm.errors.AnswerMessageJson;
import com.epam.esm.exception.*;
import com.epam.esm.service.TagService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(TagController.class);

    private static final String TAG_HAS_BEEN_CREATED = "New tag has been created!";
    private static final String DELETED = "Deleted successfully";
    private static final String DEFAULT_CREATE_TAG_CODE = "11";
    private static final String DEFAULT_DELETE_TAG_CODE = "12";

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService, AnswerMessageJson answerMessageJson) {
        super(answerMessageJson);
        this.tagService = tagService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerMessageJson createTag(@Valid @RequestBody TagDTO tagDTO, BindingResult bindingResult) throws NotInsertedException, AlreadyExistElementException, InvalidInputDataException {
        LOGGER.info("Start tag creation");
        bindingResultCheck(bindingResult);
        tagService.createTag(tagDTO);
        answerMessageJson.setMessage(TAG_HAS_BEEN_CREATED);
        HttpStatus httpStatus = HttpStatus.CREATED;
        answerMessageJson.setStatus(httpStatus.toString());
        answerMessageJson.setCode(httpStatus.value() + DEFAULT_CREATE_TAG_CODE);
        LOGGER.info(TAG_HAS_BEEN_CREATED);
        return answerMessageJson;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TagDTO getTagById(@PathVariable long id) throws NoSuchIdException {
        LOGGER.info("Getting tag by id");
        return tagService.getTagById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<TagDTO> getTagList() {
        LOGGER.info("Getting tag list");
        return tagService.getTagList();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public AnswerMessageJson deleteTag(@PathVariable long id) throws NoSuchIdException {
        LOGGER.info("Tag deletion by id");
        tagService.deleteTag(id);
        HttpStatus httpStatus = HttpStatus.OK;
        answerMessageJson.setMessage(DELETED);
        answerMessageJson.setStatus(httpStatus.toString());
        answerMessageJson.setCode(httpStatus.value() + DEFAULT_DELETE_TAG_CODE);
        return answerMessageJson;
    }
}
