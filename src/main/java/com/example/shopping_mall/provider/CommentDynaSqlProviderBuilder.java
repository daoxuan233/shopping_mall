package com.example.shopping_mall.provider;

import org.apache.ibatis.jdbc.SQL;

public class CommentDynaSqlProviderBuilder {
    //查评论
    public String buildInsertCommentWithParam(Integer grade){
        return new SQL(){
            {
                SELECT("*");
                //条件
                if (grade.equals(0)){
                    FROM("comment");
                }
                if (grade.equals(1)){
                    FROM("comment");
                    WHERE("grade=#{grade}");
                }
                if (grade.equals(2)){
                    FROM("comment");
                    WHERE("grade=#{grade}");
                }
                if (grade.equals(3)){
                    FROM("comment");
                    WHERE("grade=#{grade}");
                }
            }
        }.toString();
    }
}
