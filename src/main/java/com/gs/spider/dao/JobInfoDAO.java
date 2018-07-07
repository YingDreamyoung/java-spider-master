package com.gs.spider.dao;


import com.gs.spider.model.LieTouJobInfo;
import org.apache.ibatis.annotations.Insert;

public interface JobInfoDAO {

    @Insert("insert into JobInfo2 (`title`,`category`,`s_category`,`t_category`,`publishTime`,`author`,`domain`,`url`,`urlMd5`,`gatherTime`) " +
            "values (#{title},#{category},#{s_category},#{t_category},#{publishTime},#{author},#{domain},#{url},#{urlMd5},#{gatherTime})")
    public int add(LieTouJobInfo jobInfo);
}
