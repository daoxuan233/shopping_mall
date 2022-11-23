package com.example.shopping_mall.mapper;

import com.example.shopping_mall.cache.RedisMybatisCache;
import com.example.shopping_mall.entity.Bankcard;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 只需要修改缓存实现类implementation为我们的RedisMybatisCache即可
 */
@CacheNamespace(implementation = RedisMybatisCache.class)
@Mapper
public interface BankCardMapper {
    /**
     * 根据uid[外键查询]
     * @param uid
     * @return
     */
    @Select("select * from bankcard where uid=#{uid}")
    public Bankcard getBankCardByUid(@Param("uid") Integer uid);

    /**
     * 添加银行卡用户
     * @param bankcard
     * @return
     */
    @Insert("insert into bankcard(type, account, uid) value(#{type},#{account},#{uid})")
    @Options(useGeneratedKeys=true, keyProperty="cid", keyColumn="cid")
    Integer insertBankCard(Bankcard bankcard);

    @Select("select * from bankcard")
    List<Bankcard> allCard();

    @Delete("delete from bankcard where cid = #{cid}")
    Integer deleteCard(Integer cid);

    @Select("select * from bankcard")
    Bankcard getCardAllObject();

    @Insert("insert into bankcard (type, account, uid) value(#{type},#{account},#{uid})")
    @Options(useGeneratedKeys = true, keyProperty = "cid", keyColumn = "cid")
    Integer addCard(Bankcard card);

    @Select("select * from bankcard where cid=#{cid}")
    Bankcard getCardById(Integer cid);

    @Update("update bankcard set account=#{account} where cid=#{cid}")
    Integer updateAccount(@Param("cid") String cid,@Param("account") Double account);
    /**
     * 通过cid查uid
     */
    @Select("select uid from bankcard where cid=#{cid}")
    Integer getCidSelectUid(Integer cid);
}
