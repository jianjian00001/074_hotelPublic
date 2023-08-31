package com.example.hotel.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 焦新伟
 * @date 2020/12/12 4:53 下午
 */
@Data
public class PostQueryCondition {

    /**
     * 用户ID
     */
    private Long userId;


    /**
     * 分类ID
     */
    private Long cateId;

    /**
     * 预订的日期集合
     */
    private List<String> dateList;
}
