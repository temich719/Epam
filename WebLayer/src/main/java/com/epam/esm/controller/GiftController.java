package com.epam.esm.controller;

import com.epam.esm.dtos.GiftCertificateDTO;
import com.epam.esm.errors.AnswerMessageJson;
import com.epam.esm.exception.InvalidInputDataException;
import com.epam.esm.exception.NoSuchIdException;
import com.epam.esm.exception.NotInsertedException;
import com.epam.esm.searchparamcontainer.SearchParamContainer;
import com.epam.esm.service.GiftCertificateService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(GiftController.class);

    private static final String GIFT_CERTIFICATE_HAS_BEEN_CREATED = "New gift certificate has been created!";
    private static final String CERTIFICATE_WITH_ID = "Certificate with id: ";
    private static final String WAS_DELETED = " was deleted";
    private static final String UPDATED = "Updated";
    private static final String DEFAULT_CREATE_GIFT_CERTIFICATE_CODE = "00";

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftController(GiftCertificateService giftCertificateService, AnswerMessageJson answerMessageJson) {
        super(answerMessageJson);
        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerMessageJson insertCertificate(@Valid @RequestBody GiftCertificateDTO giftCertificateDTO, BindingResult bindingResult) throws NotInsertedException, InvalidInputDataException {
        LOGGER.info("Start gift certificate creation");
        bindingResultCheck(bindingResult);
        giftCertificateService.insertGiftCertificate(giftCertificateDTO);
        HttpStatus httpStatus = HttpStatus.CREATED;
        answerMessageJson.setStatus(httpStatus.toString());
        answerMessageJson.setMessage(GIFT_CERTIFICATE_HAS_BEEN_CREATED);
        answerMessageJson.setCode(httpStatus.value() + DEFAULT_CREATE_GIFT_CERTIFICATE_CODE);
        LOGGER.info(GIFT_CERTIFICATE_HAS_BEEN_CREATED);
        return answerMessageJson;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDTO getCertificateByID(@PathVariable long id) throws NoSuchIdException {
        LOGGER.info("Getting certificate by id");
        return giftCertificateService.getGiftCertificateByID(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDTO> getCertificatesList() {
        LOGGER.info("Getting certificates list");
        return giftCertificateService.getGiftCertificatesDTOList();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AnswerMessageJson deleteGiftCertificate(@PathVariable long id) throws NoSuchIdException {
        LOGGER.info("Certificate deletion by id");
        giftCertificateService.deleteGiftCertificate(id);
        HttpStatus httpStatus = HttpStatus.ACCEPTED;
        answerMessageJson.setMessage(CERTIFICATE_WITH_ID + id + WAS_DELETED);
        answerMessageJson.setStatus(httpStatus.toString());
        answerMessageJson.setCode(httpStatus.value() + String.valueOf(id));
        return answerMessageJson;
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public AnswerMessageJson updateGiftCertificate(@PathVariable long id, @RequestBody GiftCertificateDTO giftCertificateDTO) throws NoSuchIdException {
        LOGGER.info("Updating certificate");
        giftCertificateService.updateGiftCertificate(id, giftCertificateDTO);
        HttpStatus httpStatus = HttpStatus.OK;
        answerMessageJson.setStatus(httpStatus.toString());
        answerMessageJson.setMessage(UPDATED);
        answerMessageJson.setCode(httpStatus.value() + String.valueOf(id));
        return answerMessageJson;
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDTO> searchGiftCertificate(@Valid @RequestBody SearchParamContainer searchParamContainer, BindingResult bindingResult) throws InvalidInputDataException {
        LOGGER.info("Start search certificate by params");
        bindingResultCheck(bindingResult);
        List<GiftCertificateDTO> giftCertificateDTOs = giftCertificateService.getCertificatesDTOListAccordingToInputParams(searchParamContainer.getMapOfSearchParams());
        LOGGER.info("Certificate has been found by params");
        return giftCertificateDTOs;
    }

}
