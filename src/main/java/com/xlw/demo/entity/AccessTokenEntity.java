package com.xlw.demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessTokenEntity implements Serializable {
    private static final long serialVersionUID = 3934514333237629286L;
    private String access_token;
    private String expires_in;
    private String errcode;
    private String errmsg;
}
