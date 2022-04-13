package com.epam.esm.DAO.impl;

import com.epam.esm.DAO.TagDAO;
import com.epam.esm.config.DevRepositoryConfig;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DevRepositoryConfig.class)
@ActiveProfiles("dev")
public class TagDAOImplTest {

    @Autowired
    private TagDAO tagDAO;

    @Test
    public void testCreateTag() {
        Tag tag = new Tag();
        tag.setName("beauty");
        int actual = tagDAO.createTag(tag);
        int expected = 1;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGetTagById() {
        long id = 1;
        Tag tag = tagDAO.getTagById(id);
        assertThat(tag).isNotNull();
        assertThat(tag.getName()).isEqualTo("sport");
    }

    @Test
    public void testGetTagList() {
        List<Tag> tags = tagDAO.getTagList();
        int actual = tags.size();
        int expected = 4;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDeleteTag() {
        long id = 5;
        int actual = tagDAO.deleteTag(id);
        int expected = 0;
        assertThat(actual).isEqualTo(expected);
        assertThat(tagDAO.getTagById(id)).isNull();
    }

    @Test
    public void testAddNewTagFromGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        Set<Tag> tags = new HashSet<>();
        Tag firstTag = new Tag();
        Tag secondTag = new Tag();
        firstTag.setName("sport");
        secondTag.setName("ice");
        tags.add(firstTag);
        tags.add(secondTag);
        giftCertificate.setTags(tags);

        tagDAO.addNewTagFromGiftCertificate(giftCertificate);

        assertThat(tagDAO.getTagIdByTagName("sport")).isEqualTo(1);
        assertThat(tagDAO.getTagIdByTagName("ice")).isEqualTo(5);

    }

    @Test
    public void testGetTagIdsListByGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        Set<Tag> tags = new HashSet<>();
        Tag firstTag = new Tag();
        Tag secondTag = new Tag();
        firstTag.setName("sport");
        secondTag.setName("books");
        tags.add(firstTag);
        tags.add(secondTag);
        giftCertificate.setTags(tags);

        List<Long> tagsIds = tagDAO.getTagIdsListByGiftCertificate(giftCertificate);

        assertThat(tagsIds.size()).isEqualTo(2);
        assertThat(tagsIds.get(0)).isEqualTo(1);
        assertThat(tagsIds.get(1)).isEqualTo(4);
    }

    @Test
    public void testGetTagSetByGiftCertificateId() {
        long certificateId = 3;
        Tag firstTag = new Tag();
        Tag secondTag = new Tag();
        firstTag.setId(4);
        firstTag.setName("books");
        secondTag.setId(3);
        secondTag.setName("development");
        Set<Tag> tags = tagDAO.getTagSetByGiftCertificateId(certificateId);

        assertThat(tags.size()).isEqualTo(2);
        assertThat(tags.contains(firstTag)).isEqualTo(true);
        assertThat(tags.contains(secondTag)).isEqualTo(true);
    }

    @Test
    public void testGetTagIdByTagName() {
        String tagName = "sport";
        long id = tagDAO.getTagIdByTagName(tagName);

        assertThat(id).isEqualTo(1);
    }

}
