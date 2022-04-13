package com.epam.esm.DAO.impl;

import com.epam.esm.DAO.AbstractDAO;
import com.epam.esm.DAO.TagDAO;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.jdbcMappers.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public class TagDAOImpl extends AbstractDAO implements TagDAO {

    private static final String SELECT_COUNT_FROM_TAG_BY_ID = "SELECT COUNT(*) FROM tag WHERE id = ?;";
    private static final String SELECT_ALL_FROM_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?;";
    private static final String SELECT_ALL_FROM_TAG = "SELECT * FROM tag;";
    private static final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id = ?;";
    private static final String SELECT_COUNT_FROM_TAG_BY_NAME = "SELECT COUNT(*) FROM tag WHERE name = ?;";
    private static final String INSERT_INTO_TAG = "INSERT INTO tag (name) VALUES (?);";
    private static final String SELECT_TAG_ID_BY_NAME = "SELECT id FROM tag WHERE name = ?;";
    private static final String SELECT_TAG_ID_BY_GIFT_CERTIFICATE_ID = "SELECT tag_id FROM certificates_and_tags WHERE gift_certificate_id = ?";
    private static final String DELETE_TAG_FROM_MANY_TO_MANY_TABLE = "DELETE FROM certificates_and_tags WHERE tag_id = ?;";

    private final TagMapper tagMapper;

    @Autowired
    public TagDAOImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        super(jdbcTemplate);
        this.tagMapper = tagMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int createTag(Tag tag) {
        int resultCode;
        Integer countOfTags = jdbcTemplate.queryForObject(SELECT_COUNT_FROM_TAG_BY_NAME, Integer.class, tag.getName());
        if (countOfTags != null && countOfTags != 0) {
            resultCode = -1;
        } else {
            resultCode = jdbcTemplate.update(INSERT_INTO_TAG, tag.getName());
        }
        return resultCode;
    }

    @Override
    public Tag getTagById(long id) {
        Tag tag = null;
        if (isIdExists(id)) {
            tag = jdbcTemplate.queryForObject(SELECT_ALL_FROM_TAG_BY_ID, tagMapper, id);
        }
        return tag;
    }

    @Override
    public List<Tag> getTagList() {
        return jdbcTemplate.query(SELECT_ALL_FROM_TAG, tagMapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteTag(long id) {
        int countOfDeletedLines = 0;
        if (isIdExists(id)) {
            jdbcTemplate.update(DELETE_TAG_FROM_MANY_TO_MANY_TABLE, id);
            countOfDeletedLines = jdbcTemplate.update(DELETE_TAG_BY_ID, id);
        }
        return countOfDeletedLines;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addNewTagFromGiftCertificate(GiftCertificate giftCertificate) {
        for (Tag tag : giftCertificate.getTags()) {
            Integer count = jdbcTemplate.queryForObject(SELECT_COUNT_FROM_TAG_BY_NAME, Integer.class,
                    tag.getName());
            if (count != null && count == 0) {
                createTag(tag);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Long> getTagIdsListByGiftCertificate(GiftCertificate giftCertificate) {
        List<Long> tagsIds = new ArrayList<>();
        String[] tagNames = giftCertificate.getTagsInString().split(",");
        for (String tagName : tagNames) {
            Long tagId = jdbcTemplate.queryForObject(SELECT_TAG_ID_BY_NAME, Long.class, tagName);
            tagsIds.add(tagId);
        }
        return tagsIds;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Set<Tag> getTagSetByGiftCertificateId(long id) {
        Set<Tag> tags = new HashSet<>();
        for (Long tagId : getTagIdsListByGiftCertificateId(id)) {
            Tag tag = jdbcTemplate.queryForObject(SELECT_ALL_FROM_TAG_BY_ID, tagMapper, tagId);
            tags.add(tag);
        }
        return tags;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long getTagIdByTagName(String tagName) {
        Long tagCountByTagName = jdbcTemplate.queryForObject(SELECT_COUNT_FROM_TAG_BY_NAME, Long.class, tagName);
        Long tagIdByTagName = null;
        if (tagCountByTagName != null && tagCountByTagName != 0) {
            tagIdByTagName = jdbcTemplate.queryForObject(SELECT_TAG_ID_BY_NAME, Long.class, tagName);
        }
        return tagIdByTagName;
    }

    private List<Long> getTagIdsListByGiftCertificateId(long id) {
        return jdbcTemplate.queryForList(SELECT_TAG_ID_BY_GIFT_CERTIFICATE_ID, Long.class, id);
    }

    private boolean isIdExists(long id) {
        boolean isExist = false;
        Integer idsCount = jdbcTemplate.queryForObject(SELECT_COUNT_FROM_TAG_BY_ID, Integer.class, id);
        if (Objects.nonNull(idsCount) && idsCount != 0) {
            isExist = true;
        }
        return isExist;
    }

}
