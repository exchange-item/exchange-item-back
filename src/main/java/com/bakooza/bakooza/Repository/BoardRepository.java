package com.bakooza.bakooza.Repository;

import com.bakooza.bakooza.DTO.BoardResponseDTO;
import com.bakooza.bakooza.Entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllByTitleContaining(final String keyword, final Pageable pageable);

    @Query(value = "select * from Board b where category_id = ?1 order by post_id DESC",
           countQuery = "select count(post_id) from Board",
           nativeQuery = true)
    Page<Board> findByCategoryId(final int categoryId, final Pageable pageable);
}
