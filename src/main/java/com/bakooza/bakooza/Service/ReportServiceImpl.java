package com.bakooza.bakooza.Service;

import com.bakooza.bakooza.DTO.ReportRequestDTO;
import com.bakooza.bakooza.Entity.Report;
import com.bakooza.bakooza.Repository.BoardRepository;
import com.bakooza.bakooza.Repository.ReportRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final BoardRepository boardRepository;

    public ReportServiceImpl(ReportRepository reportRepository, BoardRepository boardRepository) {
        this.reportRepository = reportRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    @Transactional
    public int report(final ReportRequestDTO params) {
        Report entity = reportRepository.save(params.toEntity()); // 신고 접수
        if (reportRepository.countByPostId(entity.getPostId()) >= 10) { // 신고의 수가 10개 이상이라면
            boardRepository.delete(entity.getPostId());
        }

        return entity.getReportId();
    }
}
