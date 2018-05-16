package com.thenorthw.onesflow.common.dao.advice;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by theNorthW on 16/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Repository
public interface AdviceDao {
    public int addAdvice(@Param("email") String email,@Param("advice") String advice);
}
