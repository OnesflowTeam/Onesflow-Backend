package com.thenorthw.onesflow.common.utils.mail;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.thenorthw.onesflow.common.enums.MailType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by theNorthW on $date.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class OnesflowMailClient {
    private static Logger logger = LoggerFactory.getLogger("mailInfoLogger");

    private static final String activateText = "<strong>亲爱的用户 {email}:</strong><br/><br/><br/>"+
            "<pre>           </pre>以下是您的账号激活地址：<br/><a href=\"{address}\">{address}</a><br/><br/><br/>" +
            "该激活地址有效期限为{expireTime}min，请及时激活";

    private static final String resetpText = "<strong>亲爱的用户 {email}:</strong><br/><br/><br/>"+
            "<pre>           </pre>以下是您的密码修改地址如下：<br/><a href=\"{address}\">{address}</a><br/><br/><br/>" +
            "该修改地址有效期限为{expireTime}min，请及时修改";

    public static int sendMail(String email, String token, int expireMin, MailType type){
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAICuNW691wuY8C", "t3maJedHWiPJO6uwum2la0CEullnFS");
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            //request.setVersion("2017-06-22");// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
            request.setAccountName("hello@mail.onesflow.com");
            request.setFromAlias("Onesflow开发团队");
            request.setAddressType(1);
            request.setTagName(type.getType() == MailType.ACTIVATE_MAIL.getType() ? "activate" : type.getType() == MailType.RESETP_MAIL.getType() ? "resetp" : "unknownTag");
            request.setReplyToAddress(true);
            request.setToAddress(email);
            request.setSubject(type.getType() == 1 ? "Onesflow账号激活" : type.getType() == 2 ? "Onesflow账号密码修改" : "莫名其妙的邮件");
            if(type.getType() == MailType.ACTIVATE_MAIL.getType()){
                request.setHtmlBody(activateText.replace("{email}",email).replace("{address}","https://www.onesflow.com/activate?activateToken="+token).replace("{expireTime}",""+expireMin));
            }
            if(type.getType() == MailType.RESETP_MAIL.getType()){
                request.setHtmlBody(resetpText.replace("{email}",email).replace("{address}","https://www.onesflow.com/resetp?resetpToken="+token).replace("{expireTime}",""+expireMin));

            }
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);

            httpResponse.getRequestId();
        } catch (ServerException e) {
            e.printStackTrace();
            logger.error("ServerException，发送邮件失败，错误码：{}，信息：{}，邮箱地址：{}",e.getErrCode(),e.getErrMsg(),email);
            return 0;
        }
        catch (ClientException e) {
            e.printStackTrace();
            logger.error("ClientException，发送邮件失败，错误码：{}，信息：{}，邮箱地址：{}",e.getErrCode(),e.getErrMsg(),email);
            return 0;
        }
        logger.info("Success，向以下地址发送邮件成功：{}，type: {},token:{}", email,type.getType(),token);
        return 1;
    }
}
