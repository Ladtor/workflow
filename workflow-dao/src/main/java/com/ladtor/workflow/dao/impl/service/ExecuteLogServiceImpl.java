package com.ladtor.workflow.dao.impl.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.common.bo.ThreeTuple;
import com.ladtor.workflow.dao.ExecuteLogService;
import com.ladtor.workflow.dao.domain.ExecuteLog;
import com.ladtor.workflow.dao.impl.mapper.ExecuteLogMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

public class ExecuteLogServiceImpl extends ServiceImpl<ExecuteLogMapper, ExecuteLog> implements ExecuteLogService {

    @Cacheable(cacheNames = "executeLogList", key = "#serialNo")
    public List<ExecuteLog> listBySerialNo(String serialNo) {
        QueryWrapper<ExecuteLog> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", serialNo).orderByDesc("id");
        return this.list(wrapper);
    }

    @Cacheable(cacheNames = "executeLog", key = "#threeTuple.serialNo + #threeTuple.version + #threeTuple.runVersion")
    public ExecuteLog get(ThreeTuple threeTuple) {
        QueryWrapper<ExecuteLog> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", threeTuple.getSerialNo())
                .eq("version", threeTuple.getVersion())
                .eq("run_version", threeTuple.getRunVersion());
        return getOne(wrapper);
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "executeLogList", key = "#executeLog.serialNo"),
                    @CacheEvict(cacheNames = "executeLog", key = "#executeLog.serialNo + #executeLog.version + #executeLog.runVersion", beforeInvocation = true)
            }
    )
    @Override
    public boolean save(ExecuteLog executeLog) {
        return super.save(executeLog);
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "executeLogList", key = "#executeLog.serialNo"),
                    @CacheEvict(cacheNames = "executeLog", key = "#executeLog.serialNo + #executeLog.version + #executeLog.runVersion")
            }
    )
    @Override
    public boolean update(ExecuteLog executeLog) {
        return super.updateById(executeLog);
    }
}