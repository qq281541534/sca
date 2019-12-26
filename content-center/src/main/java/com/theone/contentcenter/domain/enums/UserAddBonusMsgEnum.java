package com.theone.contentcenter.domain.enums;

public enum UserAddBonusMsgEnum {

    CONTRIBUTE("投稿加积分"),

    EXCHANGE("兑换");

    private String value;

    UserAddBonusMsgEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}