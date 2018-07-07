package com.gs.spider.dao;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.gs.spider.model.LieTouJobInfo;
import com.gs.spider.utils.RedisPoolUtil;
import com.gs.spider.utils.StaticValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class CommonWebpageRedisPipeline implements Pipeline {
    private static Jedis jedis;
    //	private final boolean needRedis;
//    private final String publishChannelName;
    private final Gson gson = new Gson();

    @Resource
    private JobInfoDAO jobInfoDAO;

//    @Autowired
//    public CommonWebpageRedisPipeline(StaticValue staticValue) {
////        this.needRedis = staticValue.isNeedRedis();
////        this.publishChannelName = staticValue.getWebpageRedisPublishChannelName();
////        if (this.needRedis) {
////            log.info("正在初始化Redis客户端,Host:{},Port:{}", staticValue.getRedisHost(), staticValue.getRedisPort());
////            jedis = new Jedis(staticValue.getRedisHost(), staticValue.getRedisPort());
////            log.info("Jedis初始化成功,Clients List:{}", jedis.clientList());
////        } else {
////            log.warn("未初始化Redis客户端");
////        }
//    }

    @Override
    public void process(ResultItems resultItems, Task task) {
//        if (!needRedis) return;
//        long receivedClientsCount = jedis.publish(publishChannelName, gson.toJson(resultItems.getAll()));
//
//        jedis.set((String) resultItems.getAll().get("url"), gson.toJson(resultItems.getAll().get("content")));
        Map<String, Object> resultItem = resultItems.getAll();
        log.error(">>>>>>>>>>>>>>>>>>");
        LieTouJobInfo lieTouJobInfo = new LieTouJobInfo();
        lieTouJobInfo.setTitle((String) resultItem.get("title"));
        lieTouJobInfo.setCategory((String) resultItem.get("category"));

        lieTouJobInfo.setS_category((String) resultItem.get("s_category"));
        lieTouJobInfo.setT_category((String) resultItem.get("t_category"));
        lieTouJobInfo.setAuthor((String) resultItem.get("author"));
        lieTouJobInfo.setDomain((String) resultItem.get("domain"));
        lieTouJobInfo.setPublishTime((Date) resultItem.get("publishTime"));
        lieTouJobInfo.setUrl((String) resultItem.get("url"));
        lieTouJobInfo.setUrlMd5(DigestUtils.md5Hex((String) resultItem.get("url")));
        lieTouJobInfo.setGatherTime((Date) resultItem.get("gatherTime"));

        try {
            jobInfoDAO.add(lieTouJobInfo);
            log.info("数据保存成功: {}" + lieTouJobInfo.getUrl());
        } catch (DuplicateKeyException e) {
            log.warn("数据重复了: {}" + lieTouJobInfo.getUrl());
        } catch (Exception e) {
            log.error("数据保存异常" + resultItem.get("author"));
            log.warn("数据保存异常", e);
        }
    }
}
