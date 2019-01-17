package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2019-01-17
 */
@TableName("sys_mark")
public class Mark extends Model<Mark> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 水平
     */
    private Integer level;
    /**
     * 师德
     */
    private Integer morality;
    /**
     * 工作作风
     */
    private Integer style;
    /**
     * 行为规范
     */
    private Integer code;
    /**
     * 总分
     */
    private Integer totle;
    /**
     *平均分
     */
    @TableField(exist = false)
    private Integer average;
    /**
     * 教师id
     */
    private Integer userid;
    /**
     * 教师姓名
     */
    private String username;
    /**
     * 评分人id
     */
    private Integer markid;
    /**
     * 评分时间
     */
    private String datetime;

    public Integer getAverage() {
        return average;
    }

    public void setAverage(Integer average) {
        this.average = average;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMorality() {
        return morality;
    }

    public void setMorality(Integer morality) {
        this.morality = morality;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getTotle() {
        return totle;
    }

    public void setTotle(Integer totle) {
        this.totle = totle;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getMarkid() {
        return markid;
    }

    public void setMarkid(Integer markid) {
        this.markid = markid;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Mark{" +
        ", id=" + id +
        ", level=" + level +
        ", morality=" + morality +
        ", style=" + style +
        ", code=" + code +
        ", totle=" + totle +
        ", userid=" + userid +
        ", username=" + username +
        ", markid=" + markid +
        ", datetime=" + datetime +
        "}";
    }
}
