package com.gs.spider.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * NewsPipeline
 */
@Slf4j
public abstract class ESPipeline extends IDAO implements Pipeline {
    private final String INDEX_NAME, TYPE_NAME;

    public ESPipeline(ESClient esClient, String indexName, String typeName) {
        super(esClient, indexName, typeName);
        this.INDEX_NAME = indexName;
        this.TYPE_NAME = typeName;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        Iterator i$ = resultItems.getAll().entrySet().iterator();
        try {
            XContentBuilder xContentBuilder = jsonBuilder().startObject();
            while (i$.hasNext()) {
                Map.Entry entry = (Map.Entry) i$.next();
                xContentBuilder.field((String) entry.getKey(), entry.getValue());
            }
            String json = xContentBuilder.endObject().string();
            IndexResponse response = null;
            if (StringUtils.isNotBlank(resultItems.get("id"))) {
                response = client
                        .prepareIndex(INDEX_NAME, TYPE_NAME, resultItems.get("id"))
                        .setSource(json).get();
            } else {
                response = client
                        .prepareIndex(INDEX_NAME, TYPE_NAME)
                        .setSource(json).get();
            }
            if (response.getResult() != IndexResponse.Result.CREATED)
                log.error("索引失败,可能重复创建,resultItem:" + resultItems);
        } catch (IOException e) {
            log.error("索引出错," + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
