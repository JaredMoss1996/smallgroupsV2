package com.jamsoftware.smallgroups.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Church {
    Long id;
    String name;
    String address;
    String contactInfo;
}

