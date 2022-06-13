package com.bakooza.bakooza.Service;

import com.bakooza.bakooza.DTO.BoardRequestDTO;
import com.bakooza.bakooza.DTO.BoardResponseDTO;
import com.bakooza.bakooza.Entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BoardService {

    public void save(final BoardRequestDTO boardDTO);

    public Page<Board> findByCategoryId(final int categoryId, final Pageable pageable);

    public void update(final Long id, final BoardRequestDTO params);

    public int deleteExpiredPost();

    public int delete(final Long postId);

    public Page<Board> search(final String keyword, final Pageable pageable);

    public BoardResponseDTO findById(final Long postId);
}
