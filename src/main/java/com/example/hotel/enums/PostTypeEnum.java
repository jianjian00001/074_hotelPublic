package com.example.hotel.enums;

/**
 * <pre>
 *     客房类型enum
 * </pre>
 *
 * @author : jiao
 * @date : 2020/12/25
 */
public enum PostTypeEnum {

    /**
     * 客房
     */
    POST_TYPE_POST("post"),

    /**
     * 页面
     */
    POST_TYPE_PAGE("page"),

    /**
     * 公告
     */
    POST_TYPE_NOTICE("notice");

    private String value;

    PostTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
