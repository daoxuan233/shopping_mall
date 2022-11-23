package com.example.shopping_mall.Service;

import com.example.shopping_mall.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 *  评论业务层接口
 */
public interface CommentService {
    //查询评论列表
    List<Map<String,Object>> listComment(Integer sid);
    //保存评论
    int saveComment(Comment comment);

    /**
     * 根据评论等级分类
     * @param grade
     * @return
     */
    List<Comment> classificationByReviewLevel(Integer grade);
}
