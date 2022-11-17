package com.example.shopping_mall.Service;

public interface VerifyService {

    void sendVerifyCode(String mail);

    /**
     * 验证验证码的正确
     * @param mail
     * @param code
     * @return
     */
    Boolean doVerify(String mail, String code);
}
