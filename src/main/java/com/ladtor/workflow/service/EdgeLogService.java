package com.ladtor.workflow.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ladtor.workflow.bo.domain.EdgeLog;
import com.ladtor.workflow.bo.execute.ThreeTuple;
import com.ladtor.workflow.mapper.EdgeLogMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author liudongrong
 * @date 2019/1/15 15:55
 */
@Service
@CacheConfig
public class EdgeLogService extends ServiceImpl<EdgeLogMapper, EdgeLog> {

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

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "edgeLog", key="#threeTuple.serialNo + #threeTuple.version + #threeTuple.runVersion + #edgeId"),
                    @CacheEvict(cacheNames = "edgeLogList", key="#threeTuple.serialNo + #threeTuple.version + #threeTuple.runVersion")
            }
    )
    public void saveOrUpdate(ThreeTuple threeTuple, String edgeId, String source, String target, boolean result, JSONObject params) {
        synchronized (threeTuple.getSerialNo().intern()) {
            EdgeLog edgeLog = this.get(threeTuple, edgeId);
            if (edgeLog == null) {
                edgeLog = EdgeLog.builder()
                        .serialNo(threeTuple.getSerialNo())
                        .version(threeTuple.getVersion())
                        .runVersion(threeTuple.getRunVersion())
                        .edgeId(edgeId)
                        .source(source)
                        .target(target)
                        .result(result)
                        .params(params.toJSONString())
                        .build();
                this.save(edgeLog);
            } else {
                edgeLog.setResult(result);
                edgeLog.setParams(params.toJSONString());
                edgeLog.setUpdatedAt(new Date());
                this.updateById(edgeLog);
            }
        }
    }
}
