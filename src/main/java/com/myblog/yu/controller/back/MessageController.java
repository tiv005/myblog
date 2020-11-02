package com.myblog.yu.controller.back;

import com.myblog.yu.bean.MessageInfo;
import com.myblog.yu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 容
 * @version 1.0
 * @date 2020/7/15 21:51
 */
@Controller
@RequestMapping("/back/message/")
public class MessageController {

    @Autowired
    MessageService messageService;
    @RequestMapping("list")
    public String list(MessageInfo info, Model model){
        List<MessageInfo> messageList = messageService.getList(info);
        model.addAttribute("messageList",messageList);
        model.addAttribute("msgInfo",info);
        return "/back/message/message_list";
    }

    /**
     * 修改显示状态
     * @param info
     * @param model
     * @return
     */
    @RequestMapping("update")
    public String update(MessageInfo info, Model model){

        //修改状态
        messageService.update(info);
        List<MessageInfo> messageList = messageService.getList(info);
        model.addAttribute("messageList",messageList);
        return "/back/message/message_list";
    }

    /**
     * 根据编号删除留言信息
     * @param messageId
     * @param model
     * @return
     */
    @RequestMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer messageId,Model model){
        boolean mark = messageService.delete(messageId);
        if (mark){
            model.addAttribute("info","删除留言信息成功");
        }else {
            model.addAttribute("info","删除留言信息失败");
        }
        return "/back/message/message_info";
    }
}
