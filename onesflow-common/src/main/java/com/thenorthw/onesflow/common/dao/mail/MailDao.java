package com.thenorthw.onesflow.common.dao.mail;

import com.thenorthw.onesflow.common.model.mail.MailRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by theNorthW on 2018/5/14.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
@Repository
public interface MailDao {
    public int addMailRecord(MailRecord mailRecord);

    public MailRecord getMailRecordByToUserAndType(@Param("id")Long toUser,@Param("type")Integer type);
}
