package com.ladtor.workflow.bo.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liudongrong
 * @date 2019/1/13 20:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkFlow {
    private Integer id;
    private String name;
    private String serialNo;
    private Integer version;
    private Integer runVersion;
    private Boolean hasBeenRun;

    @TableLogic
    private Integer deleted;

    private Date createdAt;
    private Date updatedAt;
}
