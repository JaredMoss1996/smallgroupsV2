package com.jamsoftware.smallgroups.model;

import lombok.Data;

import java.util.List;

@Data
public class GroupCard {
    private long id;
    private String title;
    private String description;
    private String schedule;
    private Frequency frequency;
    private String location;
    private String address;
    private String contactInfo;
    private List<Leader> leaders;
    private List<String> categories;
    private Gender gender;
    private Age age;
}
