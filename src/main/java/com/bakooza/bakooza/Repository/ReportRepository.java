package com.bakooza.bakooza.Repository;

import com.bakooza.bakooza.Entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    int countByPostId(final Long postId);

}
