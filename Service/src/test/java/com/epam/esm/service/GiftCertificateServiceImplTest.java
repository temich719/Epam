package com.epam.esm.service;

import com.epam.esm.DAO.GiftCertificateDAO;
import com.epam.esm.DTOs.GiftCertificateDTO;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.util.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private Mapper mapper;

    private GiftCertificate giftCertificate;
    private GiftCertificateDTO giftCertificateDTO;

    @BeforeEach
    public void initUseCase() {
        giftCertificate = new GiftCertificate(1, "sportCertificate", "to sport",
                "20$", "5 days");
        giftCertificateDTO = new GiftCertificateDTO();
        giftCertificateDTO.setId(1);
        giftCertificateDTO.setName("sportCertificate");
        giftCertificateDTO.setDuration("5 days");
        giftCertificateDTO.setPrice("20$");
        giftCertificateDTO.setDescription("to sport");
    }

    @Test
    public void testInsertGiftCertificate() {
        when(mapper.mapToGiftCertificate(giftCertificateDTO)).thenReturn(giftCertificate);
        when(giftCertificateDAO.insertGiftCertificate(giftCertificate)).thenReturn(1);

        int countOfInsertedObjects = giftCertificateDAO.insertGiftCertificate(mapper.mapToGiftCertificate(giftCertificateDTO));

        verify(mapper, times(1)).mapToGiftCertificate(giftCertificateDTO);
        verify(giftCertificateDAO, times(1)).insertGiftCertificate(giftCertificate);

        assertThat(countOfInsertedObjects).isEqualTo(1);

    }

    @Test
    public void testGetGiftCertificateByID() {
        long id = 1;
        when(mapper.mapToGiftCertificateDTO(giftCertificate)).thenReturn(giftCertificateDTO);
        when(giftCertificateDAO.getGiftCertificateByID(id)).thenReturn(giftCertificate);

        GiftCertificateDTO testGiftCertificateDTO = mapper.mapToGiftCertificateDTO(giftCertificateDAO.getGiftCertificateByID(id));

        verify(mapper, times(1)).mapToGiftCertificateDTO(giftCertificate);
        verify(giftCertificateDAO, times(1)).getGiftCertificateByID(id);

        assertThat(testGiftCertificateDTO).isNotNull();
    }

    @Test
    public void testGetGiftCertificatesDTOList() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificate);
        List<GiftCertificateDTO> giftCertificateDTOs = new ArrayList<>();
        giftCertificateDTOs.add(giftCertificateDTO);
        when(mapper.mapToGiftCertificateDTOList(giftCertificates)).thenReturn(giftCertificateDTOs);
        when(giftCertificateDAO.getGiftCertificatesList()).thenReturn(giftCertificates);

        List<GiftCertificateDTO> testGiftCertificateDTOs = mapper.mapToGiftCertificateDTOList(giftCertificateDAO.getGiftCertificatesList());

        verify(mapper, times(1)).mapToGiftCertificateDTOList(giftCertificates);
        verify(giftCertificateDAO, times(1)).getGiftCertificatesList();

        assertThat(testGiftCertificateDTOs.get(0).getName()).isEqualTo("sportCertificate");
    }

    @Test
    public void testDeleteGiftCertificate() {
        long id = 1;
        when(giftCertificateDAO.deleteGiftCertificate(id)).thenReturn(1);

        int countOfDeletedObjects = giftCertificateDAO.deleteGiftCertificate(id);

        verify(giftCertificateDAO, times(1)).deleteGiftCertificate(id);

        assertThat(countOfDeletedObjects).isEqualTo(1);
    }

    @Test
    public void testUpdateGiftCertificate() {
        long id = 1;
        when(mapper.mapToGiftCertificate(giftCertificateDTO)).thenReturn(giftCertificate);
        when(giftCertificateDAO.updateGiftCertificate(id, mapper.mapToGiftCertificate(giftCertificateDTO))).thenReturn(0);

        int countOfUpdatedFields = giftCertificateDAO.updateGiftCertificate(id, mapper.mapToGiftCertificate(giftCertificateDTO));

        verify(mapper, times(2)).mapToGiftCertificate(giftCertificateDTO);
        verify(giftCertificateDAO, times(1)).updateGiftCertificate(id, mapper.mapToGiftCertificate(giftCertificateDTO));

        assertThat(countOfUpdatedFields).isEqualTo(0);
    }

    @Test
    public void testGetCertificatesDTOListAccordingToInputParams() {
        Map<String, String> mapOfSearchParams = new HashMap<>();
        mapOfSearchParams.put("name", "sportCertificate");
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificate);
        List<GiftCertificateDTO> giftCertificateDTOs = new ArrayList<>();
        giftCertificateDTOs.add(giftCertificateDTO);
        when(giftCertificateDAO.getCertificatesListAccordingToInputParams(mapOfSearchParams)).thenReturn(giftCertificates);
        when(mapper.mapToGiftCertificateDTOList(giftCertificates)).thenReturn(giftCertificateDTOs);

        List<GiftCertificateDTO> testGiftCertificateDTOs =
                mapper.mapToGiftCertificateDTOList(giftCertificateDAO.getCertificatesListAccordingToInputParams(mapOfSearchParams));

        verify(mapper, times(1)).mapToGiftCertificateDTOList(giftCertificates);
        verify(giftCertificateDAO, times(1)).getCertificatesListAccordingToInputParams(mapOfSearchParams);

        assertThat(testGiftCertificateDTOs.get(0).getId()).isEqualTo(1);
    }

}
