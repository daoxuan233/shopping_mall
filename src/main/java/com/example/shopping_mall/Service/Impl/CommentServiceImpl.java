package com.example.shopping_mall.Service.Impl;

import com.example.shopping_mall.Service.CommentService;
import com.example.shopping_mall.entity.Comment;
import com.example.shopping_mall.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    /**
     * 查询所有评论
     * @param sid
     * @return
     */
    @Override
    public List<Map<String,Object>> listComment(Integer sid) {
        List<Map<String, Object>> commentAll = commentMapper.findCommentAll(sid, 0);
        if (!commentAll.isEmpty()){
            for (Map<String,Object> top : commentAll){
                List<Map<String, Object>> id = commentMapper.findCommentAll(sid, Integer.parseInt(String.valueOf(top.get("id"))));
                if (!id.isEmpty()){
                    top.put("子评论：",id);
                }
            }
        }
        return commentAll;
    }

    //存储评论信息
    @Override
    public int saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        return commentMapper.saveComment(comment);
    }

    /**
     * 根据评论等级分类
     *
     * @param grade
     * @return
     */
    @Override
    public List<Comment> classificationByReviewLevel(Integer grade) {
        return commentMapper.selectComment(grade);
    }
}
