package com.myblog.yu.controller.back;

import com.myblog.yu.bean.UserInfo;
import com.myblog.yu.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 页面跳转
 * @author 容
 * @version 1.0
 * @date 2020/7/15 20:54
 */
@Controller
@RequestMapping("/back/")
public class BackIndexController {

    /**
     * 用户管理业务逻辑接口,利用多态调用实现类，也可以写实现类（这种不推荐使用，因为只能实现该实现类方法，不能实现后来添加的方法）
     */
    @Autowired
    UserInfoService userInfoService;

    /**
     * 登录页面
     * @return
     */
    @RequestMapping("login")
    public String login(HttpSession session){
        //清除session
        session.invalidate();
        return "back/login";
    }

    @RequestMapping("index")
    public String index(){
        return "back/index";
    }

    @RequestMapping("main")
    public String main(Model model, HttpServletRequest request){
        //用户IP地址
        String ip = request.getRemoteAddr();
        model.addAttribute("userIP",ip);

        //获取用户个数，并将个数显示出来
        model.addAttribute("usercount",userInfoService.getCount());
        return "back/main";
    }

    /**
     * 用户登录
     * @param user
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("userLogin")
    public String userLogin(UserInfo user, Model model, HttpSession session){
        UserInfo userInfo = userInfoService.isLogin(user);
        if(userInfo!=null){
            //需要把当前登录用户存到session中,name值和value不要相同
            session.setAttribute("userinfo",userInfo);
            //获取登录时间
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            session.setAttribute("loginTime",format.format(date));
            return "back/index";
        }else {
            model.addAttribute("info","账号或者密码错误");
            return "back/login";
        }
    }
}
