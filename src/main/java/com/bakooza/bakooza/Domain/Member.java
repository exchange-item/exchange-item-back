package com.bakooza.bakooza.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private Long id;
    private String nickname;
    private String email;
    private String profileImage;
}
