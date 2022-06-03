package com.bakooza.bakooza.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rating {
    Long toMemberId;
    Long fromMemberId;
}
