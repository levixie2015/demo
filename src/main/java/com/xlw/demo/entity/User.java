package com.xlw.demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1114002811752751277L;
    private String id;
    private String name;
    private String age;

}
