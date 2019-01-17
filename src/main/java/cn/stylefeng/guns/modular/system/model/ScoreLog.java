package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2019-01-15
 */
@TableName("sys_score_log")
public class ScoreLog extends Model<ScoreLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 旧学科
     */
    private String oldcourse;
    /**
     * 新学科
     */
    private String newcourse;
    /**
     * 旧分数
     */
    private BigDecimal oldscore;
    /**
     * 新分数
     */
    private BigDecimal newscore;
    /**
     * 旧学生
     */
    private String olduser;
    /**
     * 新学生
     */
    private String newuser;
    /**
     * 旧班级
     */
    private String oldclass;
    /**
     * 新班级
     */
    private String newclass;
    /**
     * 修改人
     */
    private String username;
    /**
     * 更新时间
     */
    private String datetime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOldcourse() {
        return oldcourse;
    }

    public void setOldcourse(String oldcourse) {
        this.oldcourse = oldcourse;
    }

    public String getNewcourse() {
        return newcourse;
    }

    public void setNewcourse(String newcourse) {
        this.newcourse = newcourse;
    }

    public BigDecimal getOldscore() {
        return oldscore;
    }

    public void setOldscore(BigDecimal oldscore) {
        this.oldscore = oldscore;
    }

    public BigDecimal getNewscore() {
        return newscore;
    }

    public void setNewscore(BigDecimal newscore) {
        this.newscore = newscore;
    }

    public String getOlduser() {
        return olduser;
    }

    public void setOlduser(String olduser) {
        this.olduser = olduser;
    }

    public String getNewuser() {
        return newuser;
    }

    public void setNewuser(String newuser) {
        this.newuser = newuser;
    }

    public String getOldclass() {
        return oldclass;
    }

    public void setOldclass(String oldclass) {
        this.oldclass = oldclass;
    }

    public String getNewclass() {
        return newclass;
    }

    public void setNewclass(String newclass) {
        this.newclass = newclass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return "ScoreLog{" +
        ", id=" + id +
        ", oldcourse=" + oldcourse +
        ", newcourse=" + newcourse +
        ", oldscore=" + oldscore +
        ", newscore=" + newscore +
        ", olduser=" + olduser +
        ", newuser=" + newuser +
        ", oldclass=" + oldclass +
        ", newclass=" + newclass +
        ", username=" + username +
        ", datetime=" + datetime +
        "}";
    }
}
