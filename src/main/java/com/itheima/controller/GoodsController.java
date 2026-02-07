package com.itheima.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itheima.service.TGoodsService;
import com.itheima.service.TUserService;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private TUserService userService;
    @Autowired
    private TGoodsService goodsService;

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
            model.addAttribute("goodsList", goodsService.findGoodsVo());
        }
        return "goodsList";
    }

    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model,User user,@PathVariable Long GoodsId)
    {model.addAttribute("user", user);
        model.addAttribute("goods", goodsService.findGoodsVoById(GoodsId));
        return "goodsDetail";
    }

}
