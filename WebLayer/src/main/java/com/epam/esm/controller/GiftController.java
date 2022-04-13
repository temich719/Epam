package com.epam.esm.controller;

import com.epam.esm.DTOs.GiftCertificateDTO;
import com.epam.esm.errors.AnswerMessageJson;
import com.epam.esm.exception.InvalidInputDataException;
import com.epam.esm.exception.NoSuchIdException;
import com.epam.esm.exception.NotInsertedException;
import com.epam.esm.searchParamContainer.SearchParamContainer;
import com.epam.esm.service.GiftCertificateService;
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
import java.util.Objects;

@Controller
@ResponseBody
public class GiftController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(GiftController.class);

    private static final String GIFT_CERTIFICATE_HAS_BEEN_CREATED = "New gift certificate has been created!";
    private static final String THERE_IS_NO_GIFT_CERTIFICATES = "There is no gift certificates";
    private static final String CERTIFICATE_WITH_ID = "Certificate with id: ";
    private static final String WAS_DELETED = " was deleted";
    private static final String UPDATED = "Updated";
    private static final String THERE_IS_NO_CERTIFICATES_BY_PARAMS = "There is no certificates corresponding to the parameters";
    private static final String DEFAULT_CREATE_GIFT_CERTIFICATE_CODE = "00";
    private static final String DEFAULT_GET_GIFT_CERTIFICATE_LIST_CODE = "01";
    private static final String DEFAULT_SEARCH_GIFT_CERTIFICATE_CODE = "02";

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftController(GiftCertificateService giftCertificateService, Gson gson, AnswerMessageJson answerMessageJson) {
        super(gson, answerMessageJson);
        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping("/new/certificate")
    public ResponseEntity<?> insertCertificate(@Valid @RequestBody GiftCertificateDTO giftCertificateDTO, BindingResult bindingResult) throws NotInsertedException, InvalidInputDataException {
        LOGGER.info("Start gift certificate creation");
        bindingResultCheck(bindingResult);
        giftCertificateService.insertGiftCertificate(giftCertificateDTO);
        HttpStatus httpStatus = HttpStatus.CREATED;
        answerMessageJson.setStatus(httpStatus.toString());
        answerMessageJson.setMessage(GIFT_CERTIFICATE_HAS_BEEN_CREATED);
        answerMessageJson.setCode(httpStatus.value() + DEFAULT_CREATE_GIFT_CERTIFICATE_CODE);
        LOGGER.info(GIFT_CERTIFICATE_HAS_BEEN_CREATED);
        return new ResponseEntity<>(gson.toJson(answerMessageJson), httpStatus);
    }

    @GetMapping("/get/certificate/{id}")
    public ResponseEntity<?> getCertificateByID(@PathVariable long id) throws NoSuchIdException {
        LOGGER.info("Getting certificate by id");
        GiftCertificateDTO giftCertificateDTO = giftCertificateService.getGiftCertificateByID(id);
        return new ResponseEntity<>(gson.toJson(giftCertificateDTO), HttpStatus.OK);
    }

    @GetMapping("/get/certificate/list")
    public ResponseEntity<?> getCertificatesList() {
        LOGGER.info("Getting certificates list");
        List<GiftCertificateDTO> giftCertificateDTOs = giftCertificateService.getGiftCertificatesDTOList();
        HttpStatus httpStatus;
        ResponseEntity<?> responseEntity;
        if (giftCertificateDTOs.isEmpty()) {
            httpStatus = HttpStatus.ACCEPTED;
            answerMessageJson.setStatus(httpStatus.toString());
            answerMessageJson.setMessage(THERE_IS_NO_GIFT_CERTIFICATES);
            answerMessageJson.setCode(httpStatus.value() + DEFAULT_GET_GIFT_CERTIFICATE_LIST_CODE);
            responseEntity = new ResponseEntity<>(gson.toJson(answerMessageJson), httpStatus);
        } else {
            responseEntity = new ResponseEntity<>(gson.toJson(giftCertificateDTOs), HttpStatus.OK);
        }
        return responseEntity;
    }

    @DeleteMapping("/delete/certificate/{id}")
    public ResponseEntity<?> deleteGiftCertificate(@PathVariable long id) throws NoSuchIdException {
        LOGGER.info("Certificate deletion by id");
        giftCertificateService.deleteGiftCertificate(id);
        HttpStatus httpStatus = HttpStatus.ACCEPTED;
        answerMessageJson.setMessage(CERTIFICATE_WITH_ID + id + WAS_DELETED);
        answerMessageJson.setStatus(httpStatus.toString());
        answerMessageJson.setCode(httpStatus.value() + String.valueOf(id));
        return new ResponseEntity<>(gson.toJson(answerMessageJson), httpStatus);
    }


    @PutMapping("/update/certificate/{id}")
    public ResponseEntity<?> updateGiftCertificate(@PathVariable long id, @RequestBody GiftCertificateDTO giftCertificateDTO) throws NoSuchIdException {
        LOGGER.info("Updating certificate");
        giftCertificateService.updateGiftCertificate(id, giftCertificateDTO);
        HttpStatus httpStatus = HttpStatus.OK;
        answerMessageJson.setStatus(httpStatus.toString());
        answerMessageJson.setMessage(UPDATED);
        answerMessageJson.setCode(httpStatus.value() + String.valueOf(id));
        return new ResponseEntity<>(gson.toJson(answerMessageJson), httpStatus);
    }

    @GetMapping("/search/certificate")
    public ResponseEntity<?> searchGiftCertificate(@Valid @RequestBody SearchParamContainer searchParamContainer, BindingResult bindingResult) throws InvalidInputDataException {
        LOGGER.info("Start search certificate by params");
        bindingResultCheck(bindingResult);
        List<GiftCertificateDTO> giftCertificateDTOs = giftCertificateService.getCertificatesDTOListAccordingToInputParams(searchParamContainer.getMapOfSearchParams());
        ResponseEntity<?> responseEntity;
        if (Objects.isNull(giftCertificateDTOs)) {
            HttpStatus httpStatus = HttpStatus.NOT_FOUND;
            answerMessageJson.setStatus(httpStatus.toString());
            answerMessageJson.setMessage(THERE_IS_NO_CERTIFICATES_BY_PARAMS);
            answerMessageJson.setCode(httpStatus.value() + DEFAULT_SEARCH_GIFT_CERTIFICATE_CODE);
            responseEntity = new ResponseEntity<>(gson.toJson(answerMessageJson), httpStatus);
        } else {
            responseEntity = new ResponseEntity<>(gson.toJson(giftCertificateDTOs), HttpStatus.OK);
            LOGGER.info("Certificate has been found by params");
        }
        return responseEntity;
    }

}
