package com.epam.esm.DAO;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;

import java.util.List;
import java.util.Set;

public interface TagDAO {

    /**
     * creates Tag object to tag table
     *
     * @param tag is the Tag object that need to be inserted to database
     * @return count of inserted lines
     */
    int createTag(Tag tag);

    /**
     * finds Tag object according to given id
     *
     * @param id is the Tag object id
     * @return Tag object that has id the same as given one's
     */
    Tag getTagById(long id);

    /**
     * finds all Tag objects
     *
     * @return list of all Tag objects that exist in database
     */
    List<Tag> getTagList();

    /**
     * deletes Tag according to given id
     *
     * @param id is the Tag id
     * @return count of deleted lines
     */
    int deleteTag(long id);

    /**
     * insert Tag from GiftCertificate object(according to its set of tags)
     *
     * @param giftCertificate is the GiftCertificate object
     */
    void addNewTagFromGiftCertificate(GiftCertificate giftCertificate);

    /**
     * finds ids of Tag objects that are contained in given GiftCertificate
     *
     * @param giftCertificate is the GiftCertificate
     * @return list of Tag ids
     */
    List<Long> getTagIdsListByGiftCertificate(GiftCertificate giftCertificate);

    /**
     * finds Tag objects that contained in GiftCertificate according to its given id
     *
     * @param id is the GiftCertificate id
     * @return set of Tag objects
     */
    Set<Tag> getTagSetByGiftCertificateId(long id);

    /**
     * finds Tag id by name of this Tag
     *
     * @param tagName is the Tag name
     * @return Tag id
     */
    Long getTagIdByTagName(String tagName);

}
