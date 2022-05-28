package com.bakooza.bakooza.Controller;

import com.bakooza.bakooza.DTO.ReportRequestDTO;
import com.bakooza.bakooza.Service.ReportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/report")
@ResponseBody
@RequiredArgsConstructor
public class ReportController {

    private final ReportServiceImpl reportService;

    @PostMapping()
    public int report(@RequestBody final ReportRequestDTO params) {
        return reportService.report(params);
    }
}
