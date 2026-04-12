package com.itheima.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itheima.pojo.TUser;
import com.itheima.service.TGoodsService;
import com.itheima.service.TUserService;
import jakarta.servlet.http.HttpSession;
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
        if (StringUtils.isEmpty(ticket)) {
            return "login";
        }
        TUser user = (TUser) session.getAttribute(ticket);
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("goodsList", goodsService.findGoodsVo());
        }
        return "goodsList";
    }

    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, TUser user, @PathVariable Long goodsId) {
        model.addAttribute("user", user);
        model.addAttribute("goods", goodsService.findGoodsVoById(goodsId));
        return "goodsDetail";
    }
}
