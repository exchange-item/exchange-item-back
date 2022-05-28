package com.bakooza.bakooza.Repository;

import com.bakooza.bakooza.DTO.BoardResponseDTO;
import com.bakooza.bakooza.Entity.Board;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByPostIdAndIsDeleted(Long postId, int yN);

    @Query(value = "SELECT * FROM board WHERE is_deleted = false AND title LIKE :keyword", nativeQuery = true)
    Page<Board> findByTitleContainingAndIsDeleted(@Param("keyword") String keyword,
        final Pageable pageable);

    @Query(value = "SELECT * FROM board WHERE category_id = :categoryId AND is_deleted = false ORDER BY post_id DESC",
        countQuery = "SELECT COUNT(post_id) FROM Board",
        nativeQuery = true)
    Page<Board> findByCategoryId(@Param("categoryId") final int categoryId,
        final Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM board WHERE DATEDIFF(CURDATE(), deleted_date) >= 7", nativeQuery = true)
    int deleteExpiredPost();

    @Query(value = "UPDATE board SET is_deleted = 1, deleted_date = CURDATE() WHERE post_id = :postId", nativeQuery = true)
    Optional<Long> delete(@Param("postId") Long postId);

}
