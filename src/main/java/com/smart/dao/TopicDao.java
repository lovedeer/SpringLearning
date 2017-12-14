package com.smart.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.smart.domain.Topic;

import java.util.List;

/**
 * topic 的DAO类
 */
public interface TopicDao {

    static final String GET_BOARD_DIGEST_TOPICS = "from Topic t where t.boardId = ? and digest > 0 order by t.lastPost desc,digest desc";

    static final String GET_PAGED_TOPICS = "from Topic where boardId = ? order by lastPost desc";

    static final String QUERY_TOPIC_BY_TITILE = "from Topic  where topicTitle like ? order by lastPost desc";

    /**
     * 获取论坛版块某一页的精华主题帖，按最后回复时间以及精华级别 降序排列
     *
     * @param boardId 论坛版块ID
     * @return 该论坛下的所有精华主题帖
     */

    List<Topic> getBoardDigestTopics(@Param("boardId") int boardId, @Param("start") int pageNo, @Param("pageSize") int pageSize);

    /**
     * 获取论坛版块分页的主题帖子
     *
     * @param boardId  论坛版块ID
     * @param pageNo   页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    List<Topic> getPagedTopics(@Param("boardId") int boardId, @Param("start") int pageNo, @Param("pageSize") int pageSize);

    /**
     * 根据主题帖标题查询所有模糊匹配的主题帖
     *
     * @param title    标题的查询条件
     * @param pageNo   页号，从1开始。
     * @param pageSize 每页的记录数
     * @return 包含分页信息的Page对象
     */
    Page queryTopicByTitle(String title, int pageNo, int pageSize);

    long getTopicNum();

    void save(Topic t);

    void update(Topic t);

    void remove(Topic t);

    Topic get(int objectId);
}
