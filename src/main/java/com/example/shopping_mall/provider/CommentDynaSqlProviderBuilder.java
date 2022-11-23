package com.example.shopping_mall.provider;

import org.apache.ibatis.jdbc.SQL;

public class CommentDynaSqlProviderBuilder {
    //查评论
    public String buildInsertCommentWithParam(Integer grade){
        return new SQL(){
            {
                SELECT("*");
                FROM("comment");
                //条件
                if (grade.equals(1)){
                    WHERE("grade=#{grade}");
                }
                if (grade.equals(2)){
                    WHERE("grade=#{grade}");
                }
                if (grade.equals(3)){
                    WHERE("grade=#{grade}");
                }
            }
        }.toString();
    }
}
