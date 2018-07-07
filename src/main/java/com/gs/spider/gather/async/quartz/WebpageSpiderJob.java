package com.gs.spider.gather.async.quartz;

import com.gs.spider.model.commons.SpiderInfo;
import com.gs.spider.service.commons.spider.CommonsSpiderService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
@Slf4j
public class WebpageSpiderJob extends QuartzJobBean {
    private SpiderInfo spiderInfo;
    private CommonsSpiderService commonsSpiderService;

    public WebpageSpiderJob setCommonsSpiderService(CommonsSpiderService commonsSpiderService) {
        this.commonsSpiderService = commonsSpiderService;
        return this;
    }

    public WebpageSpiderJob setSpiderInfo(SpiderInfo spiderInfo) {
        this.spiderInfo = spiderInfo;
        return this;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("开始定时网页采集任务，网站：{}，模板ID：{}", spiderInfo.getSiteName(), spiderInfo.getId());
        String uuid = commonsSpiderService.start(spiderInfo).getResult();
        log.info("定时网页采集任务完成，网站：{}，模板ID：{},任务ID：{}", spiderInfo.getSiteName(), spiderInfo.getId(), uuid);
    }
}
