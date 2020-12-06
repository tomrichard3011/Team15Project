package edu.sjsu.Team15.utility;

import edu.sjsu.Team15.model.DomainInfo;
import io.github.novacrypto.SecureCharBuffer;

public class Message {
    public Action action;
    private DomainInfo domainInfo;
    private String username;
    private SecureCharBuffer password;
    private int clearTime;

    public DomainInfo getDomainInfo() {
        return domainInfo;
    }

    public void setDomainInfo(DomainInfo domainInfo) {
        this.domainInfo = domainInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SecureCharBuffer getPassword() {
        return password;
    }

    public void setPassword(SecureCharBuffer password) {
        this.password = password;
    }

    public int getClearTime() {
        return clearTime;
    }

    public void setClearTime(int clearTime) {
        this.clearTime = clearTime;
    }

    public enum Action {
        EDIT_DOMAININFO,
        DELETE_DOMAININFO,
        COPY_PASSWORD,
        GENERATE_PASSWORD,
        SET_USERNAME,
        SET_PASSWORD,
        SET_CLEARTIME,
        CREATE_DOMAININFO_MENU,
        SETTINGS_MENU,
        ADD_DOMAININFO,
        LOGIN,
        NEW_USER,
        EXIT
    }
}