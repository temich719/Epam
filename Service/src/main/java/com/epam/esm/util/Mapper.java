package com.epam.esm.util;

import com.epam.esm.DTOs.GiftCertificateDTO;
import com.epam.esm.DTOs.TagDTO;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public TagDTO mapToTagDTO(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        return tagDTO;
    }

    public Tag mapToTag(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());
        return tag;
    }

    public GiftCertificateDTO mapToGiftCertificateDTO(GiftCertificate giftCertificate) {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();
        giftCertificateDTO.setId(giftCertificate.getId());
        giftCertificateDTO.setName(giftCertificate.getName());
        giftCertificateDTO.setDescription(giftCertificate.getDescription());
        giftCertificateDTO.setPrice(giftCertificate.getPrice());
        giftCertificateDTO.setDuration(giftCertificate.getDuration());
        giftCertificateDTO.setCreateDate(giftCertificate.getCreateDate());
        giftCertificateDTO.setLastUpdateDate(giftCertificate.getLastUpdateDate());

        TagDTO[] tagDTOs = new TagDTO[giftCertificate.getTags().size()];

        int i = 0;
        for (Tag tag : giftCertificate.getTags()) {
            tagDTOs[i] = mapToTagDTO(tag);
            i++;
        }

        giftCertificateDTO.setTags(tagDTOs);

        return giftCertificateDTO;
    }

    public GiftCertificate mapToGiftCertificate(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(giftCertificateDTO.getId());
        giftCertificate.setName(giftCertificateDTO.getName());
        giftCertificate.setDescription(giftCertificateDTO.getDescription());
        giftCertificate.setPrice(giftCertificateDTO.getPrice());
        giftCertificate.setDuration(giftCertificateDTO.getDuration());
        giftCertificate.setCreateDate(giftCertificateDTO.getCreateDate());
        giftCertificate.setLastUpdateDate(giftCertificateDTO.getLastUpdateDate());

        Set<Tag> tags = new HashSet<>();

        TagDTO[] tagDTOs = giftCertificateDTO.getTags();
        if (Objects.nonNull(tagDTOs)) {
            for (TagDTO tagDTO : giftCertificateDTO.getTags()) {
                tags.add(mapToTag(tagDTO));
            }
        }

        giftCertificate.setTags(tags);

        return giftCertificate;
    }

    public List<GiftCertificateDTO> mapToGiftCertificateDTOList(List<GiftCertificate> giftCertificates) {
        return Objects.isNull(giftCertificates) ? null : giftCertificates.stream().map(this::mapToGiftCertificateDTO).collect(Collectors.toList());
    }

    public List<TagDTO> mapToTagDTOList(List<Tag> tags) {
        return tags.stream().map(this::mapToTagDTO).collect(Collectors.toList());
    }
}
