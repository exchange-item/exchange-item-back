package com.winwin.exchange.Repository;

import com.winwin.exchange.Domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("member_id", member.getId());
        parameters.put("nickname", member.getNickname());
        parameters.put("email", member.getEmail());
        parameters.put("profile_image", member.getProfileImage());

        jdbcInsert.execute(parameters);

        return member;
    }

    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where member_id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("member_id"));
            member.setNickname(rs.getString("nickname"));
            member.setEmail(rs.getString("email"));
            member.setProfileImage(rs.getString("profile_image"));

            return member;
        };
    }
}
