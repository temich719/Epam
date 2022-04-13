package com.epam.esm.DAO.impl;

import com.epam.esm.DAO.GiftCertificateDAO;
import com.epam.esm.config.DevRepositoryConfig;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DevRepositoryConfig.class)
@ActiveProfiles("dev")
public class GiftCertificateDAOImplTest {

    @Autowired
    private GiftCertificateDAO giftCertificateDAO;

    private GiftCertificate giftCertificate;

    @BeforeEach
    public void initUseCase() {
        giftCertificate = new GiftCertificate();
        giftCertificate.setName("Ice Palace");
        giftCertificate.setDescription("hockey");
        giftCertificate.setPrice("20$");
        giftCertificate.setDuration("3 days");
        Set<Tag> tags = new HashSet<>();
        Tag firstTag = new Tag();
        Tag secondTag = new Tag();
        firstTag.setName("sport");
        secondTag.setName("ice");
        tags.add(firstTag);
        tags.add(secondTag);
        giftCertificate.setTags(tags);
    }

    @Test
    public void testGetGiftCertificatesList() {
        List<GiftCertificate> giftCertificates;
        giftCertificates = giftCertificateDAO.getGiftCertificatesList();
        int expected = 3;
        int actual = giftCertificates.size();
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void testInsertGiftCertificate() {
        int actual = giftCertificateDAO.insertGiftCertificate(giftCertificate);
        int expected = 1;
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void testGetGiftCertificateByID() {
        long id = 1;
        GiftCertificate giftCertificate = giftCertificateDAO.getGiftCertificateByID(id);
        assertThat(giftCertificate).isNotNull();
        assertThat(giftCertificate.getName()).isEqualTo("SportMaster");
    }

    @Test
    public void testDeleteGiftCertificate() {
        long id = 2;
        int actual = giftCertificateDAO.deleteGiftCertificate(id);
        int expected = 1;
        assertThat(actual).isEqualTo(expected);
        assertThat(giftCertificateDAO.getGiftCertificateByID(id)).isNull();
    }

    @Test
    public void testUpdateGiftCertificate() {
        long id = 3;
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("WildBerries");
        giftCertificate.setDuration("5 days");
        int actual = giftCertificateDAO.updateGiftCertificate(id, giftCertificate);
        int expected = 2;
        assertThat(actual).isEqualTo(expected);
        assertThat(giftCertificateDAO.getGiftCertificateByID(id).getName()).isEqualTo("WildBerries");
    }

    @Test
    public void testGetCertificatesListAccordingToInputParams() {
        Map<String, String> mapOfSearchParams = new HashMap<>();
        mapOfSearchParams.put("tagName", "books");
        List<GiftCertificate> giftCertificates = giftCertificateDAO.getCertificatesListAccordingToInputParams(mapOfSearchParams);
        int actual = giftCertificates.size();
        int expected = 1;
        assertThat(actual).isEqualTo(expected);
        assertThat(giftCertificates.get(0).getName()).isEqualTo("OZ");
    }

}
