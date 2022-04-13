package com.epam.esm.jdbcMappers;

import com.epam.esm.domain.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.stringsStorage.RepositoryStringsStorage.*;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(ID);
        String name = rs.getString(NAME);
        String description = rs.getString(DESCRIPTION);
        String price = rs.getString(PRICE);
        String duration = rs.getString(DURATION);
        String createDate = rs.getString(CREATE_DATE);
        String lastUpdateDate = rs.getString(LAST_UPDATE_DATE);
        return new GiftCertificate(id, name, description, price, duration, createDate, lastUpdateDate);
    }
}
