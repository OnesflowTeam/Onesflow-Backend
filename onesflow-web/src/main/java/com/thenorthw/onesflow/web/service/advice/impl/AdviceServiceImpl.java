package com.thenorthw.onesflow.web.service.advice.impl;

import com.thenorthw.onesflow.common.dao.advice.AdviceDao;
import com.thenorthw.onesflow.web.service.advice.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by theNorthW on 16/05/2018.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Service
public class AdviceServiceImpl implements AdviceService{
    @Autowired
    AdviceDao adviceDao;

    @Override
    public int addAdvice(String email, String advice) {
        return adviceDao.addAdvice(email,advice);
    }
}
