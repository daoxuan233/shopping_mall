package com.example.shopping_mall.Service.Impl;

import com.example.shopping_mall.Service.VerifyService;
import com.example.shopping_mall.util.VCodeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class VerifyServiceImpl implements VerifyService {

    @Resource
    JavaMailSender sender; //调用boot封装好的Mail文件

    @Resource
    StringRedisTemplate template; //存入Redis缓存中

    VCodeUtil vCodeUtil = new VCodeUtil();

    @Value("${spring.mail.username}") //取出存于Redis中的数据
    String from;

    @Override
    public void sendVerifyCode(String mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("惠农商城-注册");
        String code = vCodeUtil.verifyCode();//随机数
        System.out.println("产生的code验证码："+code);
        //存储在缓存
        template.opsForValue().set("loginVerify:code:"+mail,code+"",3, TimeUnit.MINUTES); //设置过期时间，3 mi
        mailMessage.setText("验证码为："+code+"该验证码，在三分钟内有效，请及时注册使用；如果不是本人操作，请忽略！");
        mailMessage.setTo(mail); //发送到对应的邮箱
        mailMessage.setFrom(from);
        sender.send(mailMessage);//发送邮件
    }

    @Override
    public Boolean doVerify(String mail, String code) {
        System.out.println("code验证码："+code);
        String str = template.opsForValue().get("loginVerify:code:"+mail);
        System.out.println("Redis验证码："+str);
        if (str == null) return false;
        if (!str.equals(code)) return false;
        template.delete("verify:code:"+mail); //使用后在Redis中删除缓存
        return true;
    }
}
