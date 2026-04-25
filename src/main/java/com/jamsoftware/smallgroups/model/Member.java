package com.jamsoftware.smallgroups.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Member {
    long id;
    long churchId;
    String firstName;
    String lastName;
    String email;
    String homePhone;
    String mobilePhone;
    //TODO later add a profile pic
}
