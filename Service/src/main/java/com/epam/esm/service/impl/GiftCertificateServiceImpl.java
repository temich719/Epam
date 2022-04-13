package com.epam.esm.service.impl;

import com.epam.esm.DAO.GiftCertificateDAO;
import com.epam.esm.DTOs.GiftCertificateDTO;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.exception.NoSuchIdException;
import com.epam.esm.exception.NotInsertedException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final Mapper mapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, Mapper mapper) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.mapper = mapper;
    }

    @Override
    public void insertGiftCertificate(GiftCertificateDTO giftCertificateDTO) throws NotInsertedException {
        if (giftCertificateDAO.insertGiftCertificate(mapper.mapToGiftCertificate(giftCertificateDTO)) == 0) {
            throw new NotInsertedException();
        }
    }

    @Override
    public GiftCertificateDTO getGiftCertificateByID(long id) throws NoSuchIdException {
        GiftCertificate giftCertificate = giftCertificateDAO.getGiftCertificateByID(id);
        if (Objects.isNull(giftCertificate)) {
            throw new NoSuchIdException();
        }
        return mapper.mapToGiftCertificateDTO(giftCertificate);
    }

    @Override
    public List<GiftCertificateDTO> getGiftCertificatesDTOList() {
        return mapper.mapToGiftCertificateDTOList(giftCertificateDAO.getGiftCertificatesList());
    }

    @Override
    public void deleteGiftCertificate(long id) throws NoSuchIdException {
        if (giftCertificateDAO.deleteGiftCertificate(id) == 0) {
            throw new NoSuchIdException();
        }
    }

    @Override
    public void updateGiftCertificate(long id, GiftCertificateDTO giftCertificateDTO) throws NoSuchIdException {
        if (giftCertificateDAO.updateGiftCertificate(id, mapper.mapToGiftCertificate(giftCertificateDTO)) == 0) {
            throw new NoSuchIdException();
        }
    }

    @Override
    public List<GiftCertificateDTO> getCertificatesDTOListAccordingToInputParams(Map<String, String> mapOfSearchParams) {
        return mapper.mapToGiftCertificateDTOList(giftCertificateDAO.getCertificatesListAccordingToInputParams(mapOfSearchParams));
    }
}
