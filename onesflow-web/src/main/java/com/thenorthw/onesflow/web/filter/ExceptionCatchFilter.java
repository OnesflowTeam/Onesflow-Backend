package com.thenorthw.onesflow.web.filter;

import com.thenorthw.onesflow.common.ResponseCode;
import com.thenorthw.onesflow.common.ResponseModel;
import com.thenorthw.onesflow.common.exception.OnesflowException;
import com.thenorthw.onesflow.common.utils.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by theNorthW on 17/07/2017.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class ExceptionCatchFilter implements Filter{
    private static Logger logger = LoggerFactory.getLogger(ExceptionCatchFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        try{
            filterChain.doFilter(servletRequest,servletResponse);
        }catch (Throwable t){
            if(t instanceof OnesflowException) {
                logger.error("exception happen, please locate the error and fix it.\n exception: {}", t);
                ResponseModel<String> res = new ResponseModel<String>();
                res.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
                res.setMessage(ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
                res.setData(null);
                OutputStream outputStream = servletResponse.getOutputStream();
                outputStream.write(JsonUtil.beanToJson(res).getBytes());
                outputStream.flush();
                outputStream.close();
            }else if(t instanceof SQLException){
                logger.error("exception happen, please locate the error and fix it.\n exception: {}", t);

                ResponseModel<String> res = new ResponseModel<String>();
                res.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
                res.setData(null);
                res.setMessage(ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
                OutputStream outputStream = servletResponse.getOutputStream();
                outputStream.write(JsonUtil.beanToJson(res).getBytes());
                outputStream.flush();
                outputStream.close();
            }else {
                logger.error("exception happen, please locate the error and fix it.\n exception: {}", t);

                ResponseModel<String> res = new ResponseModel<String>();
                res.setData(null);
                res.setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
                res.setMessage(ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
                OutputStream outputStream = servletResponse.getOutputStream();
                outputStream.write(JsonUtil.beanToJson(res).getBytes());
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    public void destroy() {

    }
}