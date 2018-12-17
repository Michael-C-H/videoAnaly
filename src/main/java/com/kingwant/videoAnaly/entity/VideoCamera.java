package com.kingwant.videoAnaly.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
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
@TableName("VIDEO_CAMERA")
public class VideoCamera extends Model<VideoCamera> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;
    @TableField("CODE")
    private String code;
    @TableField("NAME")
    private String name;
    @TableField("SOURCE")
    private String source;
    @TableField("ANLTSIS_TYPE")
    private String anltsisType;
    @TableField("ONLINE_NUM")
    private String onlineNum;
    @TableField("TOTAL")
    private String total;
    @TableField("LINE_PT1")
    private String linePt1;
    @TableField("LINE_PT2")
    private String linePt2;


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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAnltsisType() {
        return anltsisType;
    }

    public void setAnltsisType(String anltsisType) {
        this.anltsisType = anltsisType;
    }

    public String getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(String onlineNum) {
        this.onlineNum = onlineNum;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getLinePt1() {
        return linePt1;
    }

    public void setLinePt1(String linePt1) {
        this.linePt1 = linePt1;
    }

    public String getLinePt2() {
        return linePt2;
    }

    public void setLinePt2(String linePt2) {
        this.linePt2 = linePt2;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VideoCamera{" +
        "id=" + id +
        ", code=" + code +
        ", name=" + name +
        ", source=" + source +
        ", anltsisType=" + anltsisType +
        ", onlineNum=" + onlineNum +
        ", total=" + total +
        ", linePt1=" + linePt1 +
        ", linePt2=" + linePt2 +
        "}";
    }
}
