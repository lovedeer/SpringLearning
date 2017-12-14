package com.smart.dao;

import com.smart.domain.Board;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardDao {

    long getBoardNum();

    List<Board> getPagedBoards(@Param("start") int pageNo, @Param("pageSize") int pageSize);

    List<Board> getAllBoards();
    void save(Board t);

    void update(Board t);

    void remove(Board t);

    Board get(int objectId);
}
