package com.epam.esm.service;

import com.epam.esm.DAO.TagDAO;
import com.epam.esm.DTOs.TagDTO;
import com.epam.esm.domain.Tag;
import com.epam.esm.util.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {

    @Mock
    private TagDAO tagDAO;
    @Mock
    private Mapper mapper;

    @Test
    public void testCreateTag() {
        TagDTO tagDTO = new TagDTO(1, "dancing");
        Tag tag = new Tag(1, "dancing");
        when(mapper.mapToTag(tagDTO)).thenReturn(tag);
        when(tagDAO.createTag(tag)).thenReturn(1);

        int countOfInsertedObjects = tagDAO.createTag(mapper.mapToTag(tagDTO));

        verify(mapper, times(1)).mapToTag(tagDTO);
        verify(tagDAO, times(1)).createTag(tag);

        assertThat(countOfInsertedObjects).isEqualTo(1);
    }

    @Test
    public void testGetTagById() {
        long id = 1;
        Tag tag = new Tag(id, "sport");
        TagDTO tagDTO = new TagDTO(id, "sport");
        when(tagDAO.getTagById(id)).thenReturn(tag);
        when(mapper.mapToTagDTO(tag)).thenReturn(tagDTO);

        TagDTO testTagDTO = mapper.mapToTagDTO(tagDAO.getTagById(id));

        verify(mapper, times(1)).mapToTagDTO(tag);
        verify(tagDAO, times(1)).getTagById(id);

        assertThat(testTagDTO.getName()).isNotNull();
    }

    @Test
    public void testGetTagList() {
        List<Tag> tags = new ArrayList<>();
        Tag firstTag = new Tag(1, "sport");
        Tag secondTag = new Tag(2, "dancing");
        tags.add(firstTag);
        tags.add(secondTag);
        List<TagDTO> tagDTOs = new ArrayList<>();
        TagDTO firstTagDTO = new TagDTO(1, "sport");
        TagDTO secondTagDTO = new TagDTO(2, "dancing");
        tagDTOs.add(firstTagDTO);
        tagDTOs.add(secondTagDTO);
        when(tagDAO.getTagList()).thenReturn(tags);
        when(mapper.mapToTagDTOList(tags)).thenReturn(tagDTOs);

        List<TagDTO> testTagDTOs = mapper.mapToTagDTOList(tagDAO.getTagList());

        verify(mapper, times(1)).mapToTagDTOList(tags);
        verify(tagDAO, times(1)).getTagList();

        assertThat(testTagDTOs.get(1).getId()).isEqualTo(2);
    }

    @Test
    public void testDeleteTag() {
        long id = 1;
        when(tagDAO.deleteTag(id)).thenReturn(1);

        int countOfDeletedObjects = tagDAO.deleteTag(id);

        verify(tagDAO, times(1)).deleteTag(id);

        assertThat(countOfDeletedObjects).isEqualTo(1);
    }
}
