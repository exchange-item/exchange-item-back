package com.bakooza.bakooza.Service;

import com.bakooza.bakooza.DTO.ReportRequestDTO;
import javax.transaction.Transactional;

public interface ReportService {

    int report(final ReportRequestDTO params);

}
