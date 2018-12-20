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
@TableName("VIDEO_DRESS_ABNORMAL")
public class VideoDressAbnormal extends Model<VideoDressAbnormal> {

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
    private byte[] image;
    @TableField("REASON")
    private String reason;
    @TableField(exist = false)
    private String stringDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
   

	public String getStringDate() {
		return stringDate;
	}

	public void setStringDate(String stringDate) {
		this.stringDate = stringDate;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VideoDressAbnormal{" +
        "id=" + id +
        ", code=" + code +
        ", name=" + name +
        ", exDate=" + exDate +
        ", image=" + image +
        ", reason=" + reason +
        "}";
    }
}
