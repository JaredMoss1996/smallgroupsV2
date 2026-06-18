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
public class GroupForm {
    private Long id;
    private String title;
    private String description;
    private String schedule;
    private String frequency;
    private String location;
    private String address;
    private List<Long> leaderIds;
    private List<String> categories;
    private String gender;
    private List<String> ages;
    private Long churchId;
}
