package com.epam.esm.sqlBuilder;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class SQLBuilder {

    private static final String SELECT_FROM_GIFT_CERTIFICATES_WHERE = "SELECT * FROM gift_certificate WHERE ";
    private static final String ID_ASSIGN = "id = ";
    private static final String LEFT_BRACKET = "(";
    private static final String OR = " || ";
    private static final String RIGHT_BRACKET = ")";
    private static final String NAME_PART = "namePart";
    private static final String NAME_PART_LIKE_EXPRESSION_BEGINNING = " && name LIKE '%";
    private static final String DESCRIPTION_PART_LIKE_EXPRESSION_BEGINNING = " && description LIKE '%";
    private static final String DESCRIPTION_PART = "descriptionPart";
    private static final String LIKE_EXPRESSION_ENDING = "%'";
    private static final String SORT_BY_NAME = "sortByName";
    private static final String SORT_BY_DATE = "sortByDate";
    private static final String ASC = "asc";
    private static final String DESC = "desc";
    private static final String ORDER_BY_NAME = " order by name ";
    private static final String ORDER_BY_CREATE_DATE = " order by create_date ";
    private static final String PLUS_CREATE_DATE = ", create_date ";
    private static final String SEMICOLON = ";";

    public String createSearchSQL(Map<String, String> mapOfSearchParams, List<Long> giftCertificateIds) {
        StringBuilder sql = new StringBuilder(SELECT_FROM_GIFT_CERTIFICATES_WHERE);

        if (giftCertificateIds.size() == 1) {
            sql.append(ID_ASSIGN).append(giftCertificateIds.get(0));
        } else {
            sql.append(LEFT_BRACKET);
            for (int i = 0; i < giftCertificateIds.size() - 1; i++) {
                sql.append(ID_ASSIGN).append(giftCertificateIds.get(i)).append(OR);
            }
            sql.append(ID_ASSIGN).append(giftCertificateIds.get(giftCertificateIds.size() - 1)).append(RIGHT_BRACKET);
        }

        String namePart = mapOfSearchParams.get(NAME_PART);
        if (Objects.nonNull(namePart)) {
            sql.append(NAME_PART_LIKE_EXPRESSION_BEGINNING).append(namePart).append(LIKE_EXPRESSION_ENDING);
        }

        String descriptionPart = mapOfSearchParams.get(DESCRIPTION_PART);
        if (Objects.nonNull(descriptionPart)) {
            sql.append(DESCRIPTION_PART_LIKE_EXPRESSION_BEGINNING).append(descriptionPart).append(LIKE_EXPRESSION_ENDING);
        }

        String sortByName = mapOfSearchParams.get(SORT_BY_NAME);
        if (Objects.nonNull(sortByName)) {
            if (sortByName.equalsIgnoreCase(ASC) || sortByName.equalsIgnoreCase(DESC)) {
                sql.append(ORDER_BY_NAME).append(sortByName);
            }
        }

        String sortByDate = mapOfSearchParams.get(SORT_BY_DATE);
        if (Objects.isNull(sortByName)) {
            if (Objects.nonNull(sortByDate)) {
                if (sortByDate.equalsIgnoreCase(ASC) || sortByDate.equalsIgnoreCase(DESC)) {
                    sql.append(ORDER_BY_CREATE_DATE).append(sortByDate);
                }
            }
        } else if (Objects.nonNull(sortByDate)) {
            if (sortByDate.equalsIgnoreCase(ASC) || sortByDate.equalsIgnoreCase(DESC)) {
                sql.append(PLUS_CREATE_DATE).append(sortByDate);
            }
        }

        sql.append(SEMICOLON);

        return sql.toString();
    }

}
