package com.example.shopping_mall.Service.Impl;

import com.example.shopping_mall.Service.BankCardService;
import com.example.shopping_mall.entity.Bankcard;
import com.example.shopping_mall.entity.ShoppingUser;
import com.example.shopping_mall.entity.log.ShoppingMallLog;
import com.example.shopping_mall.entity.resp.RestBean;
import com.example.shopping_mall.mapper.BankCardMapper;
import com.example.shopping_mall.mapper.LogMapper;
import com.example.shopping_mall.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class BankCardServiceImpl implements BankCardService {

    @Resource
    BankCardMapper bankCardMapper;

    @Resource
    LogMapper logMapper;

    @Resource
    UserMapper userMapper;

    /**
     * 校验用户是否拥有银行卡
     *
     * @param uid
     * @return
     */
    @Override
    public Bankcard ByUidQueryBanCard(Integer uid) {
        return bankCardMapper.getBankCardByUid(uid);
    }

    /**
     * 添加银行卡
     *
     * @param bankcard
     * @return
     */
    @Override
    public Integer addBankCard(Bankcard bankcard) {
        Integer integer = bankCardMapper.insertBankCard(bankcard);
        if (integer > 0){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("用户："+bankcard.getUid())
                    .setType("添加")
                    .setInfo("用户申请了一张应用内银行卡，详细信息为："+bankcard)
                    .setAffectedinfo("0")
                    .setLtime(new Date())
            );
        }
        return integer;
    }

    /**
     * 判断前端转账
     * cid
     * <p>
     * 判断前端转账
     * 卡号
     * - cid1 转账人账户 [执行取款的逻辑接口]
     * - cid2 接受人账户 [执行存款的逻辑接口]
     *
     * @param cid1
     * @param cid2
     * @param money
     */
    @Override
    public RestBean<Double> judgeCid(Integer cid1, Integer cid2, Double money) {
        //判断参数是否有误
        if (cid1 == null || cid2 == null){
            return new RestBean<>(402,"参数有误，请检查后操作！");
        }
        //判断卡号是否存在
        Bankcard card1 = bankCardMapper.getCardById(cid1);
        Bankcard card2 = bankCardMapper.getCardById(cid2);
        if (card1 == null || card2 == null){
            return new RestBean<>(404,"存在未知卡号，请检查参数后操作！");
        }
        // 判断余额
        if (card1.getAccount() < money){
            return new RestBean<>(404,"余额不足！");
        }
        //实际转账操作
        Integer cid2Money = updateMoneyWithdrawals(cid1, money);//取款的方法
        Integer cid1Money = updateMoney(cid2, money);//存款的方法

        // 通过cid查询uid
        Integer getUid1 = bankCardMapper.getCidSelectUid(cid1);
        Integer getUid2 = bankCardMapper.getCidSelectUid(cid2);
        // 通过uid查uname
        ShoppingUser allByUid = userMapper.getAllByUid(getUid1);
        ShoppingUser allByUid1 = userMapper.getAllByUid(getUid2);

        if (cid1Money > 0 && cid2Money > 0){
            logMapper.addLog(new ShoppingMallLog()
                    .setLname("用户："+allByUid.getUname())
                    .setType("转账")
                    .setInfo("用户:"+allByUid.getUid()+"向用户:"+allByUid1.getUid()+"转账，金额为："+money+"￥元")
                    .setAffectedinfo("0")
                    .setLtime(new Date())
            );
            return new RestBean<>(200,"支付成功！",money);
        } else {
            return new RestBean<>(402,"支付失败！");
        }
    }

    /**
     * 存款 deposit
     *
     * @param cid
     * @param money
     */
    @Override
    public Integer updateMoney(Integer cid, Double money) {
        Bankcard cardById = bankCardMapper.getCardById(cid);
        Double newAccount = cardById.getAccount();
        cardById.setAccount(newAccount + money);
        return bankCardMapper.updateAccount(String.valueOf(cid), cardById.getAccount());
    }

    /**
     * 异步刷新，执行Ajax
     *
     * @param cid
     */
    @Override
    public RestBean<String> AsynchronousRefresh(Integer cid) {
        //cid为空直接返回未知
        if (cid == null){
            return new RestBean<>(402,"Unknown");//未知
        }
        Bankcard selectById = SelectById(cid);
        //卡号不存在
        if (selectById == null){
            return new RestBean<>(402,"Unknown");//未知
        }else {
            //卡号存在
            ShoppingUser selectUserById = SelectUserById(selectById.getUid());
            return new RestBean<>(200,selectUserById.getUname());
        }
    }

    /**
     * 取款
     * updateMoneyWithdrawals
     *
     * @param cid
     * @param money
     */
    @Override
    public Integer updateMoneyWithdrawals(Integer cid, Double money) {
        Bankcard cardByIdWithdrawals = bankCardMapper.getCardById(cid);
        Double newWithdrawalsAccount = cardByIdWithdrawals.getAccount();
        cardByIdWithdrawals.setAccount(newWithdrawalsAccount - money);
        return bankCardMapper.updateAccount(String.valueOf(cid), cardByIdWithdrawals.getAccount());
    }

    /**
     * 转账异步刷新
     * - 卡号
     *
     * @param cid
     */
    @Override
    public Bankcard SelectById(Integer cid) {
        return bankCardMapper.getCardById(cid);
    }

    /**
     * 转账异步刷新
     * - 账户
     *
     * @param uid
     */
    @Override
    public ShoppingUser SelectUserById(Integer uid) {
        return userMapper.getAllByUid(uid);
    }

}
