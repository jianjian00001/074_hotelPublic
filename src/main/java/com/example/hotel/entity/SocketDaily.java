package com.example.hotel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author jiao
 * @since 2020-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

public class SocketDaily implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer post;

    private String dateCalculated;

    private Integer temperature;

    private Integer humidity;

    @TableField("PM25")
    private Integer pm25;

    private Integer noise;

    private Date creatTime;

    private Date modifyTime;


}
