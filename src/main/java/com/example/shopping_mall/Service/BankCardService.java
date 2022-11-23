package com.example.shopping_mall.Service;

import com.example.shopping_mall.entity.Bankcard;
import com.example.shopping_mall.entity.ShoppingUser;
import com.example.shopping_mall.entity.resp.RestBean;

public interface BankCardService {
    /**
     * 校验用户是否拥有银行卡
     * @param uid
     * @return
     */
    Bankcard ByUidQueryBanCard(Integer uid);

    /**
     * 添加银行卡
     * @param bankcard
     * @return
     */
    Integer addBankCard(Bankcard bankcard);

    /**
     * 判断前端转账
     *          cid
     *
     *       判断前端转账
     *                卡号
     *                - cid1 转账人账户 [执行取款的逻辑接口]
     *                - cid2 接受人账户 [执行存款的逻辑接口]
     *
     */
    RestBean<Double> judgeCid(Integer cid1, Integer cid2, Double money);

    /**
     * 存款 deposit
     */
    Integer updateMoney(Integer cid , Double money);
    /**
     * 异步刷新，执行Ajax
     */
    RestBean<String> AsynchronousRefresh(Integer cid);
    /**
     * 取款
     * updateMoneyWithdrawals
     */
    Integer updateMoneyWithdrawals(Integer cid , Double money);

    /**
     * 转账异步刷新
     *          - 卡号
     */
    Bankcard SelectById(Integer cid);
    /**
     * 转账异步刷新
     *          - 账户
     */
    ShoppingUser SelectUserById(Integer uid);
}
