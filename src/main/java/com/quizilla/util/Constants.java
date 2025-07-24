package com.quizilla.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "quizilla")
public class Constants {
    private String ADMIN_ROLE;
    private String PROJECTOR_ROLE;
    private String CLIENT_ROLE;
    private String SERVER_ROLE;
    private String QUESTION;
    private String QUESTION_CANCEL;
    private String QUIZ_START;
    private String GROUP_CREATION;
    private String GROUP_CREATED;
    private String ANSWER;
    private String PLAYER_ANSWERED;

    public static String ADMIN_ROLE_STATIC;
    public static String PROJECTOR_ROLE_STATIC;
    public static String CLIENT_ROLE_STATIC;
    public static String SERVER_ROLE_STATIC;
    public static String QUESTION_STATIC;
    public static String QUESTION_CANCEL_STATIC;
    public static String QUIZ_START_STATIC;
    public static String GROUP_CREATION_STATIC;
    public static String GROUP_CREATED_STATIC;
    public static String ANSWER_STATIC;
    public static String PLAYER_ANSWERED_STATIC;

    public void setADMIN_ROLE(String ADMIN_ROLE) {
        this.ADMIN_ROLE = ADMIN_ROLE;
        ADMIN_ROLE_STATIC = ADMIN_ROLE;
    }

    public void setPROJECTOR_ROLE(String PROJECTOR_ROLE) {
        this.PROJECTOR_ROLE = PROJECTOR_ROLE;
        PROJECTOR_ROLE_STATIC = PROJECTOR_ROLE;
    }

    public void setCLIENT_ROLE(String CLIENT_ROLE) {
        this.CLIENT_ROLE = CLIENT_ROLE;
        CLIENT_ROLE_STATIC = CLIENT_ROLE;
    }

    public void setSERVER_ROLE(String SERVER_ROLE) {
        this.SERVER_ROLE = SERVER_ROLE;
        SERVER_ROLE_STATIC = SERVER_ROLE;
    }

    public void setQUESTION(String QUESTION) {
        this.QUESTION = QUESTION;
        QUESTION_STATIC = QUESTION;
    }

    public void setQUESTION_CANCEL(String QUESTION_CANCEL) {
        this.QUESTION_CANCEL = QUESTION_CANCEL;
        QUESTION_CANCEL_STATIC = QUESTION_CANCEL;
    }

    public void setQUIZ_START(String QUIZ_START) {
        this.QUIZ_START = QUIZ_START;
        QUIZ_START_STATIC = QUIZ_START;
    }

    public void setGROUP_CREATION(String GROUP_CREATION) {
        this.GROUP_CREATION = GROUP_CREATION;
        GROUP_CREATION_STATIC = GROUP_CREATION;
    }

    public void setGROUP_CREATED(String GROUP_CREATED) {
        this.GROUP_CREATED = GROUP_CREATED;
        GROUP_CREATED_STATIC = GROUP_CREATED;
    }

    public void setANSWER(String ANSWER) {
        this.ANSWER = ANSWER;
        ANSWER_STATIC = ANSWER;
    }

    public void setPLAYER_ANSWERED(String PLAYER_ANSWERED) {
        this.PLAYER_ANSWERED = PLAYER_ANSWERED;
        PLAYER_ANSWERED_STATIC = PLAYER_ANSWERED;
    }
}
