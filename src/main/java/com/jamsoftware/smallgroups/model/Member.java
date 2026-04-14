package com.jamsoftware.smallgroups.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Member {
    long appUserId;
    String firstName;
    String lastName;
}
