package com.jamsoftware.smallgroups.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    private Long id;
    private String title;
    private String description;
    private String schedule;
    private String frequency;
    private String location;
    private String address;
    private List<Member> leaders;
    private List<Member> members;
    private List<String> categories;
    private String gender;
    private List<String> ages;

    public String truncatedDescription() {
        if (description == null) {
            return "";
        }
        return description.length() > StaticVars.maxDescriptionLength ? description.substring(0, StaticVars.maxDescriptionLength) + "..." : description;
    }
}
