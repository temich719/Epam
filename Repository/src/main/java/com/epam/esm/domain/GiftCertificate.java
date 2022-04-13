package com.epam.esm.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class GiftCertificate {
    long id;
    String name;
    String description;
    String price;
    String duration;
    String createDate;
    String lastUpdateDate;
    Set<Tag> tags = new HashSet<>();

    public GiftCertificate(long id, String name, String description, String price, String duration, String createDate, String lastUpdateDate, Tag... tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        Collections.addAll(this.tags, tags);
    }

    public GiftCertificate(long id, String name, String description, String price, String duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

    public String getTagsInString() {
        StringBuilder tagsString = new StringBuilder();
        for (Tag tag : tags) {
            tagsString.append(tag.getName()).append(",");
        }
        return tagsString.substring(0, tagsString.toString().length() - 1);
    }

    public List<Long> getTagIdsList() {
        List<Long> tagIds = new ArrayList<>();
        for (Tag tag : tags) {
            tagIds.add(tag.getId());
        }
        return tagIds;
    }

}