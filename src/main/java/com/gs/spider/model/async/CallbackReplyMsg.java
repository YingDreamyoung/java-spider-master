package com.gs.spider.model.async;

import lombok.extern.slf4j.Slf4j;


/**
 * CallbackReplyMsg
 */
@Slf4j
public class CallbackReplyMsg extends InfoMsg {
	

    public CallbackReplyMsg(String clientId) {
        super(clientId);
        this.setType(MsgType.CALLBACK_REPLY);
    }
}
