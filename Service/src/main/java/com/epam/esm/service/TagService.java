package com.epam.esm.service;

import com.epam.esm.DTOs.TagDTO;
import com.epam.esm.exception.AlreadyExistElementException;
import com.epam.esm.exception.NoSuchIdException;
import com.epam.esm.exception.NotInsertedException;

import java.util.List;

public interface TagService {

    /**
     * creates Tag object to tag table
     *
     * @param tagDTO is the TagDTO object that contains data need to be inserted to database
     * @throws NotInsertedException         is the exception that means that object wasn't inserted in database
     * @throws AlreadyExistElementException is the exception that means that such element already exists in database
     */
    void createTag(TagDTO tagDTO) throws NotInsertedException, AlreadyExistElementException;

    /**
     * finds Tag object according to given id
     *
     * @param id is the Tag object id
     * @return Tag object that has id the same as given one's mapped to TagDTO
     * @throws NoSuchIdException is the exception that means that given id doesn't exist in database
     */
    TagDTO getTagById(long id) throws NoSuchIdException;

    /**
     * finds all Tag objects
     *
     * @return list of all Tag objects that exist in database mapped to TagDTO
     */
    List<TagDTO> getTagList();

    /**
     * deletes Tag according to given id
     *
     * @param id is the Tag id
     * @throws NoSuchIdException is the exception that means that given id doesn't exist in database
     */
    void deleteTag(long id) throws NoSuchIdException;

}
