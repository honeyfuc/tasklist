package com.honeyfuc.tasklist.service;

import com.honeyfuc.tasklist.domain.MailType;
import com.honeyfuc.tasklist.domain.user.User;

import java.util.Properties;

public interface MailService {

    void sendEmail(User user, MailType type, Properties params);

}
