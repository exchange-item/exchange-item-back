package com.bakooza.bakooza.Repository;

import com.bakooza.bakooza.Domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class RatingRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean isRating(Long fromMemberId, Long toMemberId) {
        String sql = "select EXISTS (select ? from member_estimation_list where from_member_id=? limit 1)";

        return jdbcTemplate.queryForObject(sql, Boolean.class, toMemberId, fromMemberId);
    }

    public void rating(Long fromMemberId, Long toMemberId, int grade) {
        String sql = "insert into member_estimation_list values(?, ?, ?);";

        jdbcTemplate.update(sql, toMemberId, fromMemberId, grade);
    }

    public Integer getSumRating(Long toMemberId) {
        String sql = "select sum(grade) from member_estimation_list where to_member_id=?;";

        return jdbcTemplate.queryForObject(sql, Integer.class, toMemberId);
    }

    public Integer getCntRating(Long toMemberId) {
        String sql = "select count(grade) from member_estimation_list where to_member_id=?;";

        return jdbcTemplate.queryForObject(sql, Integer.class, toMemberId);
    }

    public void updateRating(Long memberId, int newRating) {
        String sql = "update member set member_reliability=? where member_id=?;";

        jdbcTemplate.update(sql, newRating, memberId);
    }


}
