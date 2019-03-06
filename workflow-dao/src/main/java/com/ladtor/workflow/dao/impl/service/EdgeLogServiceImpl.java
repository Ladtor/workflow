package com.ladtor.workflow.dao.impl.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.common.bo.ThreeTuple;
import com.ladtor.workflow.dao.EdgeLogService;
import com.ladtor.workflow.dao.domain.EdgeLog;
import com.ladtor.workflow.dao.impl.mapper.EdgeLogMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

public class EdgeLogServiceImpl extends ServiceImpl<EdgeLogMapper, EdgeLog> implements EdgeLogService {

    @Cacheable(cacheNames = "edgeLogList", key="#threeTuple.serialNo + #threeTuple.version + #threeTuple.runVersion")
    public List<EdgeLog> list(ThreeTuple threeTuple){
        QueryWrapper<EdgeLog> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", threeTuple.getSerialNo())
                .eq("version", threeTuple.getVersion())
                .eq("run_version", threeTuple.getRunVersion());
        return this.list(wrapper);
    }

    @Cacheable(cacheNames = "edgeLog", key="#threeTuple.serialNo + #threeTuple.version + #threeTuple.runVersion + #edgeId")
    public EdgeLog get(ThreeTuple threeTuple, String edgeId) {
        QueryWrapper<EdgeLog> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", threeTuple.getSerialNo())
                .eq("version", threeTuple.getVersion())
                .eq("run_version", threeTuple.getRunVersion())
                .eq("edge_id", edgeId);
        return getOne(wrapper);
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "edgeLog", key="#edgeLog.serialNo + #edgeLog.version + #edgeLog.runVersion + #edgeLog.edgeId"),
                    @CacheEvict(cacheNames = "edgeLogList", key="#edgeLog.serialNo + #edgeLog.version + #edgeLog.runVersion")
            }
    )
    @Override
    public boolean saveOrUpdate(EdgeLog edgeLog) {
        return baseMapper.saveOrUpdate(edgeLog);
    }
}
