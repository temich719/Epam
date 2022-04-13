package com.epam.esm.jdbcMappers;

import com.epam.esm.domain.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.stringsStorage.RepositoryStringsStorage.*;

@Component
public class TagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(ID);
        String name = rs.getString(NAME);
        return new Tag(id, name);
    }
}
