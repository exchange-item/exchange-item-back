package com.bakooza.bakooza.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private int reportId; // PK

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "report_reason")
    private String reportReason;

    @Builder
    public Report(int reportId, Long postId, String reportReason) {
        this.reportId = reportId;
        this.postId = postId;
        this.reportReason = reportReason;
    }
}
