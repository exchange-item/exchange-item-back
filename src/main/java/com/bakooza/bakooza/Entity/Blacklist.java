package com.bakooza.bakooza.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Blacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blacklist_id")
    private int blackListId; // PK

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "blacklist_member_id")
    private Long blackListMemeberId;

    @Builder
    public Blacklist(int blackListId, Long memberId, Long blackListMemeberId) {
        this.blackListId = blackListId;
        this.memberId = memberId;
        this.blackListMemeberId = blackListMemeberId;
    }
}
