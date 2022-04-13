package com.epam.esm.service.impl;

import com.epam.esm.DAO.TagDAO;
import com.epam.esm.DTOs.TagDTO;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.AlreadyExistElementException;
import com.epam.esm.exception.NoSuchIdException;
import com.epam.esm.exception.NotInsertedException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;
    private final Mapper mapper;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, Mapper mapper) {
        this.tagDAO = tagDAO;
        this.mapper = mapper;
    }

    @Override
    public void createTag(TagDTO tagDTO) throws NotInsertedException, AlreadyExistElementException {
        int returnCode = tagDAO.createTag(mapper.mapToTag(tagDTO));
        if (returnCode == 0) {
            throw new NotInsertedException();
        } else if (returnCode == -1) {
            throw new AlreadyExistElementException();
        }
    }

    @Override
    public TagDTO getTagById(long id) throws NoSuchIdException {
        Tag tag = tagDAO.getTagById(id);
        if (Objects.isNull(tag)) {
            throw new NoSuchIdException();
        }
        return mapper.mapToTagDTO(tag);
    }

    @Override
    public List<TagDTO> getTagList() {
        return mapper.mapToTagDTOList(tagDAO.getTagList());
    }

    @Override
    public void deleteTag(long id) throws NoSuchIdException {
        if (tagDAO.deleteTag(id) == 0) {
            throw new NoSuchIdException();
        }
    }
}
