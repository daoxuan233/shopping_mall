package com.example.shopping_mall.controller;

import com.example.shopping_mall.Service.CommentService;
import com.example.shopping_mall.Service.UserService;
import com.example.shopping_mall.Service.shop.ShopService;
import com.example.shopping_mall.entity.Comment;
import com.example.shopping_mall.entity.Seller;
import com.example.shopping_mall.entity.Shopping;
import com.example.shopping_mall.entity.resp.RestBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 评论控制器
 */
@Controller
@RequestMapping("/api/comment")
@Api(description = "评论相关")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    ShopService shopService;

    @Resource
    UserService userService;

    /*@Value("${comment.avatar}")
    private String avatar;*/

    @RequestMapping("/add-comment")
    @ApiOperation("添加评论")
    @ResponseBody
    public RestBean<List<Map<String, Object>>> addComments(
        @ApiParam("商品id") @RequestParam("sid") String sid,
        @ApiParam("评论的用户id") @RequestParam("uid") String uid,
        @ApiParam("父评论") @RequestParam(value = "parent_comment_id") String parentCommentId,
        @ApiParam("评论内容") @RequestParam("content") String content,
        /*@ApiParam("评论角色[0表示普通用户，1表示商家]") @RequestParam("urole") Integer urole,*/
        @ApiParam("grade[商品评价等级](数字：1表示差评，2表示中评，3表示好评)")
        @RequestParam("grade") String grade
    ) {
        Shopping shopping = shopService.productDetails(sid);
        if (shopping == null){
            return new RestBean<>(404,"商品不存在！");
        }
        String mid = shopping.getSeller();
        Seller seller = shopService.getSeller(mid);
        if (seller == null){
            return new RestBean<>(404,"卖家不存在！");
        }
        boolean equals = seller.getUid().equals(uid);
        int r;
        if (equals){
            r = 1;
        }else {
            r = 0;
        }
        int i = commentService.saveComment(
                new Comment()
                        .setSid(Integer.valueOf(sid))
                        .setUid(Integer.valueOf(uid))
                        .setParentCommentId(Long.valueOf(parentCommentId))
                        .setContent(content)
                        .setUrole(r)
                        .setGrade(grade)
        );
        if (i <= 0){
            return new RestBean<>(402,"请求错误");
        }
        List<Map<String, Object>> maps = commentService.listComment(Integer.valueOf(sid));
        if (maps.isEmpty()){
            return new RestBean<>(402,"返回数据错误");
        }
        return new RestBean<>(200,"请求成功",maps);
    }

    @RequestMapping("/findComment")
    @ApiOperation("查看全部评论")
    @ResponseBody
    public RestBean<List<Map<String, Object>>> comments(
            @ApiParam("商品id") @RequestParam("sid") String sid
    ) {
        List<Map<String, Object>> maps = commentService.listComment(Integer.valueOf(sid));
        if (!maps.isEmpty()){
            return new RestBean<>(200,"请求成功",maps);
        }
        return new RestBean<>(402,"请求错误");
    }

    @RequestMapping("/comment-classification")
    @ApiOperation("根据商品评价等查看评论")
    @ResponseBody
    public RestBean<List<Comment>> classification(
            @ApiParam("grade[商品评价等级](数字：1表示差评，2表示中评，3表示好评)") @RequestParam(value = "grade") String grade
    ){
        List<Comment> comments = commentService.classificationByReviewLevel(Integer.valueOf(grade));
        if (comments == null){
            return new RestBean<>(402,"错误");
        }
        return new RestBean<>(200,"成功",comments);
    }

}
