package com.example.hotel.enums;

/**
 * <pre>
 *     客房状态enum
 * </pre>
 *
 * @author : jiao
 * @date : 2020/12/25
 */
public enum PostStatusEnum {

    /**
     * 正常
     */
    PUBLISHED(0),

    /**
     * 下架
     */
    DRAFT(1),

    /**
     * 回收站
     */
    RECYCLE(2);

    private Integer code;

    PostStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
