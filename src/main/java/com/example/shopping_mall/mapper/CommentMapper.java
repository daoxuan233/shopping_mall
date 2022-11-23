package com.example.shopping_mall.mapper;

import com.example.shopping_mall.cache.RedisMybatisCache;
import com.example.shopping_mall.entity.Comment;
import com.example.shopping_mall.provider.CommentDynaSqlProviderBuilder;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 只需要修改缓存实现类implementation为我们的RedisMybatisCache即可
 */
@CacheNamespace(implementation = RedisMybatisCache.class)
@Mapper
public interface CommentMapper {
    /**
     * 添加一个评论
     * useGeneratedKeys 使用数据库自增主键
     * keyProperty 指定主键字段
     * keyColumn 指定主键列名，如果字段和列名一致的话，好像可以不用配置
     * @param comment
     * @return
     */
    @Insert("insert into comment (uid,content,create_time,parent_comment_id,sid,urole,grade) values (#{uid},#{content},#{createTime},#{parentCommentId},#{sid},#{urole},#{grade});")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Integer saveComment(Comment comment);

    /**
     * 查询评论
     * @param sid
     * @param parentCommentId
     * @return
     */
    @Select("select * from comment where sid=#{sid} and parent_comment_id=#{parentCommentId}")
    public List<Map<String,Object>> findCommentAll(@Param("sid") Integer sid , @Param("parentCommentId") Integer parentCommentId);

    /**
     * 动态查询评论
     * @param grade
     * @return
     */
    @SelectProvider(type = CommentDynaSqlProviderBuilder.class , method = "buildInsertCommentWithParam")
    public List<Comment> selectComment(Integer grade);


}
