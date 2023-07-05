package com.sparta.newsfeed.Common.email;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @ResponseBody
    @PostMapping("/newsfeed/user/mail")
    public String sendEmail(@RequestBody MailDto mailDto) {
        mailService.mailSend(mailDto);
        return "전송되었습니다.";
    }
}