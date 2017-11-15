package com.smart.dao;

import com.smart.domain.Board;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

@Repository
public class BoardDao extends BaseDao<Board> {
    private static final String GET_BOARD_NUM;
    private static final String GET_PAGED_BOARDS=" from Board order by id";

    static {
        GET_BOARD_NUM = "select count(f.boardId) from Board f";
    }

    public long getBoardNum() {
        Iterator iter = getHibernateTemplate().iterate(GET_BOARD_NUM);
        return ((Long) iter.next());
    }

    public Page getPagedBoards(int pageNo, int pageSize) {
        return pagedQuery(GET_PAGED_BOARDS,pageNo,pageSize,new Object[]{});
    }
}
