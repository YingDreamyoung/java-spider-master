package com.gs.spider.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;


public class LieTouJobInfo {
//(`title`,`category`,`s_category`,`t_category`,`publishTime`,`author`,`domain`,`url`,`urlMd5`)
    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private String category;

    @Setter
    @Getter
    private String s_category;

    @Setter
    @Getter
    private String t_category;

    @Setter
    @Getter
    private Date publishTime;

    @Setter
    @Getter
        private String author;

    @Setter
    @Getter
    private String domain;

    @Setter
    @Getter
    private String url;

    @Setter
    @Getter
    private String urlMd5;

    @Setter
    @Getter
    private Date gatherTime;

}
