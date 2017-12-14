package com.smart.dao;

import javafx.geometry.Pos;
import org.springframework.stereotype.Repository;

import com.smart.domain.Post;

/**
 * Post的DAO类
 */
public interface PostDao {

    static final String GET_PAGED_POSTS = "from Post where topic.topicId =? order by createTime desc";

    static final String DELETE_TOPIC_POSTS = "delete from Post where topic.topicId=?";

    Page getPagedPosts(int topicId, int pageNo, int pageSize);

    /**
     * 删除主题下的所有帖子
     *
     * @param topicId 主题ID
     */
    void deleteTopicPosts(int topicId);
    void save( Post t);

    void update( Post t);

    void remove( Post t);

    Post get(int objectId);
}
