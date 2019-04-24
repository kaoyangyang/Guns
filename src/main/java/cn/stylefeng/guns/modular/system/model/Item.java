package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2019-04-03
 */
@TableName("item")
@Data
public class Item extends Model<Item> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 类型
     */
    private String type;
    /**
     * 原因
     */
    private String cotent;
    /**
     * 开始时间
     */
    @TableField("start_time")
    private String startTime;
    /**
     * 结束时间
     */
    @TableField("end_time")
    private String endTime;
    /**
     * 创建人id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 创建人
     */
    @TableField("user_name")
    private String userName;
    /**
     * 审批人id
     */
    @TableField("pass_id")
    private Integer passId;
    /**
     * 审批人
     */
    @TableField("pass_name")
    private String passName;
    /**
     * 附件
     */
    private String path;
    /**
     * 附件名
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 状态
     */
    private String status;
    @TableField("classcode")
    private String classCode;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
