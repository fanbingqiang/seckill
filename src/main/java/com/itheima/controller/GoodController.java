package com.itheima.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodController {

    @RequestMapping("/toList")
    public String toList(@CookieValue("userTicket") String ticket, HttpSession session, Model model) {
        // 检查 ticket 是否为空
        if (StringUtils.isEmpty(ticket)) {
            return "login";
        }
        // 从 session 中获取用户信息
        User user =( User) session.getAttribute(ticket);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "goodsList";
    }
}
