package com.bakooza.bakooza.DTO;

import com.bakooza.bakooza.Entity.Report;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportRequestDTO {

    private int reportId;
    private Long postId;
    private String reportReason;

    public Report toEntity() {
        return Report.builder()
            .reportId(reportId)
            .postId(postId)
            .reportReason(reportReason)
            .build();
    }
}
