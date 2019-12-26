package com.theone.contentcenter.dao.content;

import com.theone.contentcenter.domain.entity.content.Share;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author liuyu
 */
public interface ShareMapper extends Mapper<Share> {
    List<Share> selectByParam(@Param("title") String title);
}