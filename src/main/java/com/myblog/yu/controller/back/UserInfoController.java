package com.myblog.yu.controller.back;

import com.myblog.yu.bean.UserInfo;
import com.myblog.yu.service.UserInfoService;
import com.myblog.yu.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户管理
 * @author 容
 * @version 1.0
 * @date 2020/7/15 21:29
 */
@Controller
@RequestMapping("/back/user/")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 根据分页查询用户信息
     * @param user
     * @param model
     * @param page
     * @return
     */
    @RequestMapping("list")
    public String list(UserInfo user, Model model,Integer page){

        PageBean<UserInfo> pageBean = userInfoService.getList(user,page);
        model.addAttribute("pageBean",pageBean);
        model.addAttribute("userinfo",user);
        System.out.println(user);

        return "/back/userinfo/userinfo_list";
    }

    /**
     * 加载添加页面
     * @return
     */
    @RequestMapping("loadAdd")
    public String loadAdd(){

        return "/back/userinfo/userinfo_add";
    }

    /**
     * 添加用户信息
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("add")
    public String add(UserInfo user,Model model){
        boolean mark = userInfoService.add(user);
        if (mark){
            model.addAttribute("info","添加用户信息成功");
        }else {
            model.addAttribute("info","添加用户失败");
        }
        return "/back/userinfo/userinfo_add";
    }

    /**
     * 根据编号将对应用户信息加载到修改页面上
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("loadUpdate")
    public String loadUpdate(UserInfo user,Model model){
        UserInfo userInfo = userInfoService.getUser(user);
        model.addAttribute("user",userInfo);
        return "/back/userinfo/userinfo_update";
    }

    /**
     * 修改用户
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("update")
    public String update(UserInfo user,Model model){
        boolean mark = userInfoService.update(user);
        if (mark){
            model.addAttribute("info","修改用户信息成功");
        }else {
            model.addAttribute("info","修改用户失败");
        }
        model.addAttribute("user",user);
        return "/back/userinfo/userinfo_update";
    }

    /**
     * 删除用户
     * @param user
     * @return
     */
    @RequestMapping("delete")
    public String delete(UserInfo user){
        boolean mark = userInfoService.delete(user);

        return "redirect:/back/user/list?page=1";
    }

}
