package com.jamsoftware.smallgroups.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
@Builder
public class GroupCard {
    private long id;
    private String title;
    private String description;
    private String schedule;
    private String frequency;
    private String location;
    private String address;
    private List<Member> leaders;
    private List<String> categories;
    private String gender;
    private String ages;

    public String truncatedDescription() {
        if (description == null) {
            return "";
        }
        return description.length() > StaticVars.maxDescriptionLength ? description.substring(0, StaticVars.maxDescriptionLength) + "..." : description;
    }
}
