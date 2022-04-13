package com.epam.esm.service;

import com.epam.esm.DTOs.GiftCertificateDTO;
import com.epam.esm.exception.NoSuchIdException;
import com.epam.esm.exception.NotInsertedException;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {

    /**
     * inserts GiftCertificate object to gift_certificate table
     *
     * @param giftCertificateDTO is a GiftCertificateDTO object
     * @throws NotInsertedException is the exception that means that object wasn't inserted in database
     */
    void insertGiftCertificate(GiftCertificateDTO giftCertificateDTO) throws NotInsertedException;

    /**
     * finds GiftCertificate in database according to given id
     *
     * @param id is the GiftCertificate id
     * @return GiftCertificateDTO object that was made from GiftCertificate object that has the same id as given
     * @throws NoSuchIdException is the exception that means that given id doesn't exist in database
     */
    GiftCertificateDTO getGiftCertificateByID(long id) throws NoSuchIdException;

    /**
     * finds all existing GiftCertificate objects in database
     *
     * @return list of all GiftCertificate objects mapped to GiftCertificateDTO
     */
    List<GiftCertificateDTO> getGiftCertificatesDTOList();

    /**
     * deletes GiftCertificate according to given id
     *
     * @param id is the GiftCertificate id
     * @throws NoSuchIdException is the exception that means that given id doesn't exist in database
     */
    void deleteGiftCertificate(long id) throws NoSuchIdException;

    /**
     * updates GiftCertificate info
     *
     * @param id                 is the id of GiftCertificate that need to be updated
     * @param giftCertificateDTO is the object that contains new data
     * @throws NoSuchIdException is the exception that means that given id doesn't exist in database
     */
    void updateGiftCertificate(long id, GiftCertificateDTO giftCertificateDTO) throws NoSuchIdException;

    /**
     * finds GiftCertificate objects according to given params
     *
     * @param mapOfSearchParams is the map of searching params
     * @return list of GiftCertificate objects that are corresponding given params mapped to GiftCertificateDTO
     */
    List<GiftCertificateDTO> getCertificatesDTOListAccordingToInputParams(Map<String, String> mapOfSearchParams);

}
