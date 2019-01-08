package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2019-01-07
 */
@TableName("sys_score")
public class Score extends Model<Score> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 课程名称
     */
    private String coursename;
    /**
     * 课程id
     */
    private Integer courseid;
    /**
     * 分数
     */
    private BigDecimal score;
    /**
     * 学生姓名
     */
    private String username;
    /**
     * 学生id
     */
    private Integer userid;
    /**
     * 班级id
     */
    private Integer classcode;
    /**
     * 班级名称
     */
    private String classname;
    /**
     * 年级
     */
    private Integer grade;
    /**
     * 录入时间
     */
    private Date datetime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public Integer getCourseid() {
        return courseid;
    }

    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getClasscode() {
        return classcode;
    }

    public void setClasscode(Integer classcode) {
        this.classcode = classcode;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Score{" +
        ", id=" + id +
        ", coursename=" + coursename +
        ", courseid=" + courseid +
        ", score=" + score +
        ", username=" + username +
        ", userid=" + userid +
        ", classcode=" + classcode +
        ", classname=" + classname +
        ", grade=" + grade +
        ", datetime=" + datetime +
        "}";
    }
}
