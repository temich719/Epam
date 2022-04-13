package com.epam.esm.DAO.impl;

import com.epam.esm.DAO.AbstractDAO;
import com.epam.esm.DAO.GiftCertificateDAO;
import com.epam.esm.DAO.TagDAO;
import com.epam.esm.dateInISO.DateGenerator;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.jdbcMappers.GiftCertificateMapper;
import com.epam.esm.sqlBuilder.SQLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public class GiftCertificateDAOImpl extends AbstractDAO implements GiftCertificateDAO {

    private static final String SELECT_COUNT_FROM_GIFT_CERTIFICATES_BY_ID = "SELECT COUNT(*) FROM gift_certificate WHERE id = ?;";
    private static final String SELECT_ALL_FROM_GIFT_CERTIFICATES_BY_ID = "SELECT * FROM gift_certificate WHERE id = ?;";
    private static final String SELECT_ALL_FROM_GIFT_CERTIFICATES = "SELECT * FROM gift_certificate;";
    private static final String DELETE_FROM_CERTIFICATES_AND_TAGS_BY_ID = "DELETE FROM certificates_and_tags WHERE gift_certificate_id = ?;";
    private static final String DELETE_FROM_GIFT_CERTIFICATES_BY_ID = "DELETE FROM gift_certificate WHERE id = ?;";
    private static final String UPDATE_GIFT_CERTIFICATE_NAME_BY_ID = "UPDATE gift_certificate SET name = ? WHERE id = ?;";
    private static final String UPDATE_GIFT_CERTIFICATE_DESCRIPTION_BY_ID = "UPDATE gift_certificate SET description = ? WHERE id = ?;";
    private static final String UPDATE_GIFT_CERTIFICATE_PRICE_BY_ID = "UPDATE gift_certificate SET price = ? WHERE id = ?;";
    private static final String UPDATE_GIFT_CERTIFICATE_DURATION_BY_ID = "UPDATE gift_certificate SET duration = ? WHERE id = ?;";
    private static final String UPDATE_GIFT_CERTIFICATE_LAST_UPDATE_DATE_BY_ID = "UPDATE gift_certificate SET last_update_date = ? WHERE id = ?;";
    private static final String SELECT_LAST_GIFT_CERTIFICATE_ID = "SELECT id FROM gift_certificate ORDER BY id DESC limit 1;";
    private static final String SELECT_GIFT_CERTIFICATE_ID_BY_TAG_ID = "SELECT gift_certificate_id FROM certificates_and_tags WHERE tag_id = ?";
    private static final String INSERT_INTO_MANY_TO_MANY_TABLE = "INSERT INTO certificates_and_tags (gift_certificate_id, tag_id) VALUES (?, ?);";
    private static final String INSERT_GIFT_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price," +
            " duration, create_date, last_update_date) " +
            "VALUES (?, ?, ?, ?, ?, ?);";
    private static final String TAG_NAME = "tagName";

    private final TagDAO tagDAO;
    private final DateGenerator dateGenerator;
    private final GiftCertificateMapper giftCertificateMapper;
    private final SQLBuilder sqlBuilder;

    @Autowired
    public GiftCertificateDAOImpl(JdbcTemplate jdbcTemplate, TagDAO tagDAO, DateGenerator dateGenerator, GiftCertificateMapper giftCertificateMapper, SQLBuilder sqlBuilder) {
        super(jdbcTemplate);
        this.tagDAO = tagDAO;
        this.dateGenerator = dateGenerator;
        this.giftCertificateMapper = giftCertificateMapper;
        this.sqlBuilder = sqlBuilder;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertGiftCertificate(GiftCertificate giftCertificate) {
        tagDAO.addNewTagFromGiftCertificate(giftCertificate);
        String dateAsISO = dateGenerator.getCurrentDateAsISO();
        giftCertificate.setCreateDate(dateAsISO);
        giftCertificate.setLastUpdateDate(dateAsISO);

        int countOfUpdatedColumns = jdbcTemplate.update(INSERT_GIFT_CERTIFICATE, giftCertificate.getName(),
                giftCertificate.getDescription(), giftCertificate.getPrice(), giftCertificate.getDuration(),
                giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate());

        Long lastGiftCertificateId = selectLastGiftCertificateId();
        List<Long> tagIds = tagDAO.getTagIdsListByGiftCertificate(giftCertificate);
        insertTagIdsAndGiftCertificateIdToManyToManyTable(lastGiftCertificateId, tagIds);
        return countOfUpdatedColumns;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GiftCertificate getGiftCertificateByID(long id) {
        GiftCertificate giftCertificate;
        if (isIdExists(id)) {
            giftCertificate = jdbcTemplate.queryForObject(SELECT_ALL_FROM_GIFT_CERTIFICATES_BY_ID,
                    giftCertificateMapper, id);
            if (Objects.nonNull(giftCertificate)) {
                giftCertificate.setTags(tagDAO.getTagSetByGiftCertificateId(id));
            }
        } else {
            giftCertificate = null;
        }
        return giftCertificate;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<GiftCertificate> getGiftCertificatesList() {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(SELECT_ALL_FROM_GIFT_CERTIFICATES, giftCertificateMapper);
        setTagsToGiftCertificate(giftCertificates);
        return giftCertificates;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteGiftCertificate(long id) {
        int countOfDeletedCertificates = 0;
        if (isIdExists(id)) {
            deleteFromManyToManyTableByGiftCertificateId(id);
            countOfDeletedCertificates = jdbcTemplate.update(DELETE_FROM_GIFT_CERTIFICATES_BY_ID, id);
        }
        return countOfDeletedCertificates;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateGiftCertificate(long id, GiftCertificate giftCertificate) {
        int updatedFieldsCount = 0;
        if (isIdExists(id)) {
            String updateDate = dateGenerator.getCurrentDateAsISO();
            if (Objects.nonNull(giftCertificate.getTags()) && !giftCertificate.getTags().isEmpty()) {
                tagDAO.addNewTagFromGiftCertificate(giftCertificate);
                updateTagsInManyToManyTable(id, tagDAO.getTagIdsListByGiftCertificate(giftCertificate));
                updatedFieldsCount++;
            }
            if (Objects.nonNull(giftCertificate.getName())) {
                jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_NAME_BY_ID, giftCertificate.getName(), id);
                updatedFieldsCount++;
            }
            if (Objects.nonNull(giftCertificate.getDescription())) {
                jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_DESCRIPTION_BY_ID, giftCertificate.getDescription(), id);
                updatedFieldsCount++;
            }
            if (Objects.nonNull(giftCertificate.getPrice())) {
                jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_PRICE_BY_ID, giftCertificate.getPrice(), id);
                updatedFieldsCount++;
            }
            if (Objects.nonNull(giftCertificate.getDuration())) {
                jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_DURATION_BY_ID, giftCertificate.getDuration(), id);
                updatedFieldsCount++;
            }
            if (updatedFieldsCount != 0) {
                jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_LAST_UPDATE_DATE_BY_ID, updateDate, id);
            }
        }
        return updatedFieldsCount;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<GiftCertificate> getCertificatesListAccordingToInputParams(Map<String, String> mapOfSearchParams) {
        List<GiftCertificate> giftCertificates = null;
        String tagName = mapOfSearchParams.get(TAG_NAME);
        if (Objects.nonNull(tagName)) {
            Long tagId = tagDAO.getTagIdByTagName(tagName);
            if (Objects.nonNull(tagId)) {
                String sql = sqlBuilder.createSearchSQL(mapOfSearchParams, getCertificateIdsByTagId(tagId));
                giftCertificates = jdbcTemplate.query(sql, giftCertificateMapper);
                setTagsToGiftCertificate(giftCertificates);
            }
        }
        return giftCertificates;
    }

    private Long selectLastGiftCertificateId() {
        return jdbcTemplate.queryForObject(SELECT_LAST_GIFT_CERTIFICATE_ID, Long.class);
    }

    private void insertTagIdsAndGiftCertificateIdToManyToManyTable(Long giftCertificateId, List<Long> tagIds) {
        for (Long tagId : tagIds) {
            jdbcTemplate.update(INSERT_INTO_MANY_TO_MANY_TABLE, giftCertificateId, tagId);
        }
    }

    private void updateTagsInManyToManyTable(long giftCertificateId, List<Long> tagIds) {
        deleteFromManyToManyTableByGiftCertificateId(giftCertificateId);
        insertTagIdsAndGiftCertificateIdToManyToManyTable(giftCertificateId, tagIds);
    }

    private void deleteFromManyToManyTableByGiftCertificateId(long id) {
        jdbcTemplate.update(DELETE_FROM_CERTIFICATES_AND_TAGS_BY_ID, id);
    }

    private List<Long> getCertificateIdsByTagId(Long tagId) {
        return jdbcTemplate.queryForList(SELECT_GIFT_CERTIFICATE_ID_BY_TAG_ID, Long.class, tagId);
    }

    private void setTagsToGiftCertificate(List<GiftCertificate> giftCertificates) {
        for (GiftCertificate giftCertificate : giftCertificates) {
            giftCertificate.setTags(tagDAO.getTagSetByGiftCertificateId(giftCertificate.getId()));
        }
    }

    private boolean isIdExists(long id) {
        boolean isExist = false;
        Integer idsCount = jdbcTemplate.queryForObject(SELECT_COUNT_FROM_GIFT_CERTIFICATES_BY_ID, Integer.class, id);
        if (Objects.nonNull(idsCount) && idsCount != 0) {
            isExist = true;
        }
        return isExist;
    }

}
