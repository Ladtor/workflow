package com.ladtor.workflow.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.bo.domain.ExecuteLog;
import com.ladtor.workflow.bo.execute.ThreeTuple;
import com.ladtor.workflow.constant.StatusEnum;
import com.ladtor.workflow.mapper.ExecuteLogMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/15 14:31
 */
@Service
@CacheConfig
public class ExecuteLogService extends ServiceImpl<ExecuteLogMapper, ExecuteLog> {
    @Cacheable(cacheNames = "executeLogList", key = "#serialNo")
    public List<ExecuteLog> listBySerialNo(String serialNo) {
        QueryWrapper<ExecuteLog> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", serialNo).orderByDesc("id");
        return this.list(wrapper);
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "executeLogList", key = "#threeTuple.serialNo"),
                    @CacheEvict(cacheNames = "executeLog", key = "#threeTuple.serialNo + #threeTuple.version + #threeTuple.runVersion")
            }
    )
    public void success(ThreeTuple threeTuple, JSONObject result) {
        update(threeTuple, StatusEnum.SUCCESS, result);
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "executeLogList", key = "#threeTuple.serialNo"),
                    @CacheEvict(cacheNames = "executeLog", key = "#threeTuple.serialNo + #threeTuple.version + #threeTuple.runVersion")
            }
    )
    public void fail(ThreeTuple threeTuple, JSONObject result) {
        update(threeTuple, StatusEnum.FAIL, result);
    }

    private void update(ThreeTuple threeTuple, StatusEnum status, JSONObject result) {
        QueryWrapper<ExecuteLog> wrapper = new QueryWrapper<>();
        wrapper.eq("serial_no", threeTuple.getSerialNo()).eq("version", threeTuple.getVersion()).eq("run_version", threeTuple.getRunVersion());
        ExecuteLog executeLog = this.getOne(wrapper);
        executeLog.setStatus(status.toString());
        executeLog.setResult(result.toJSONString());
        executeLog.setUpdatedAt(new Date());
        this.updateById(executeLog);
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
                    @CacheEvict(cacheNames = "executeLogList", key = "#threeTuple.serialNo"),
                    @CacheEvict(cacheNames = "executeLog", key = "#threeTuple.serialNo + #threeTuple.version + #threeTuple.runVersion", beforeInvocation = true)
            }
    )
    public boolean save(ThreeTuple threeTuple, StatusEnum status, JSONObject params, JSONObject result) {
        ExecuteLog executeLog = ExecuteLog.builder()
                .serialNo(threeTuple.getSerialNo())
                .version(threeTuple.getVersion())
                .runVersion(threeTuple.getRunVersion())
                .status(status.toString())
                .build();
        if (params != null) {
            executeLog.setParams(params.toJSONString());
        }
        if (result != null) {
            executeLog.setResult(result.toJSONString());
        }
        return this.save(executeLog);
    }
}
