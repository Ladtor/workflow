package com.ladtor.workflow.service.chain;

import com.ladtor.workflow.bo.execute.FourTuple;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * @author liudongrong
 * @date 2019/1/28 14:01
 */
@Service
public abstract class AbstractNodeCheck {
    @Setter
    private AbstractNodeCheck next;

    public boolean canRun(FourTuple fourTuple){
        if(!check(fourTuple)){
            if(next != null){
                return next.canRun(fourTuple);
            }
            return true;
        }
        return false;
    }

    protected abstract boolean check(FourTuple fourTuple);
}
