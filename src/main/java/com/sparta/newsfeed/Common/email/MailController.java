package com.sparta.newsfeed.Common.email;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @ResponseBody
    @PostMapping("/user/mail")
    String mailConfirm(@RequestBody MailDto mailDto) throws Exception {
        String code = mailService.sendSimpleMessage(mailDto.getEmail());
        System.out.println("인증코드 : " + code);
        return code;
    }

    //인증번호 비교
    @PostMapping("/user/mailcompare")
    String mailCodeCompare(@RequestBody CodeDto codeDto, HttpServletResponse res){
        try{
            mailService.compareCode(codeDto);
        }catch(Exception e){
            res.setStatus(400);
        }
        return "로그인 성공";
    }
}