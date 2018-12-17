package com.kingwant.videoAnaly.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.sql.Blob;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author MCH123
 * @since 2018-12-17
 */
@TableName("VIDEO_NUMBER_ABNORMAL")
public class VideoNumberAbnormal extends Model<VideoNumberAbnormal> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;
    @TableField("CODE")
    private String code;
    @TableField("NAME")
    private String name;
    @TableField("EX_DATE")
    private Date exDate;
    @TableField("IMAGE")
    private Blob image;
    @TableField("LIMIT")
    private String limit;
    @TableField("CURRENT_NUM")
    private String currentNum;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExDate() {
        return exDate;
    }

    public void setExDate(Date exDate) {
        this.exDate = exDate;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(String currentNum) {
        this.currentNum = currentNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VideoNumberAbnormal{" +
        "id=" + id +
        ", code=" + code +
        ", name=" + name +
        ", exDate=" + exDate +
        ", image=" + image +
        ", limit=" + limit +
        ", currentNum=" + currentNum +
        "}";
    }
}
