package com.csmaxwell.srent.wx.web;

import com.csmaxwell.srent.core.util.JacksonUtil;
import com.csmaxwell.srent.core.util.ResponseUtil;
import com.csmaxwell.srent.db.domain.SrentChat;
import com.csmaxwell.srent.db.service.SrentChatService;
import com.csmaxwell.srent.wx.annotation.LoginUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 聊天服务
 */
@RestController
@RequestMapping("/wx/chat")
@Validated
public class WxChatController {
    private final Log logger = LogFactory.getLog(WxChatController.class);

    @Autowired
    private SrentChatService chatService;

    /**
     * 根据买家ID和卖家ID查询所有的消息
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId, Integer postUserId, Integer goodsId) {
        List<SrentChat> chatList =  chatService.findChatContent(userId, postUserId, goodsId);

        return null;
    }

    /**
     * 发送信息
     */
    @PostMapping("add")
    public Object add(@LoginUser Integer userId, @RequestBody String body) {

        Integer goodsId = JacksonUtil.parseInteger(body, "goodsId");
        Integer postUserId = JacksonUtil.parseInteger(body, "postUserId");
        String content = JacksonUtil.parseString(body, "content");
        SrentChat chat = new SrentChat();
        chat.setUserId(userId);
        chat.setPostUserId(postUserId);
        chat.setGoodsId(goodsId);
        chat.setContent(content);
        chatService.add(chat);

        return ResponseUtil.ok();
    }
}
