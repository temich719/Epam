package com.epam.esm.DAO;

import com.epam.esm.domain.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDAO {

    /**
     * inserts GiftCertificate object to gift_certificate table
     *
     * @param giftCertificate is a GiftCertificate object
     * @return count of inserted lines
     */
    int insertGiftCertificate(GiftCertificate giftCertificate);

    /**
     * finds GiftCertificate in database according to given id
     *
     * @param id is the GiftCertificate id
     * @return GiftCertificate object that has the same id as given one's
     */
    GiftCertificate getGiftCertificateByID(long id);

    /**
     * finds all existing GiftCertificate objects in database
     *
     * @return list of all GiftCertificate objects
     */
    List<GiftCertificate> getGiftCertificatesList();

    /**
     * deletes GiftCertificate according to given id
     *
     * @param id is the GiftCertificate id
     * @return count of deleted lines
     */
    int deleteGiftCertificate(long id);

    /**
     * updates GiftCertificate info
     *
     * @param id              is the id of GiftCertificate that need to be updated
     * @param giftCertificate is the object that contains new data
     * @return count of updated fields
     */
    int updateGiftCertificate(long id, GiftCertificate giftCertificate);

    /**
     * finds GiftCertificate objects according to given params
     *
     * @param mapOfSearchParams is the map of searching params
     * @return list of GiftCertificate objects that are corresponding given params
     */
    List<GiftCertificate> getCertificatesListAccordingToInputParams(Map<String, String> mapOfSearchParams);

}
