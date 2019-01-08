package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author deng
 * @since 2019-01-07
 */
@TableName("sys_classes")
public class Classes extends Model<Classes> {

    private static final long serialVersionUID = 1L;

    /**
     * 班级id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 班级名称
     */
    private String name;
    /**
     * 年级
     */
    private Integer grade;
    /**
     * 班级代码
     */
    private Integer code;
    /**
     * 班级描述
     */
    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Classes{" +
        ", id=" + id +
        ", name=" + name +
        ", grade=" + grade +
        ", code=" + code +
        ", description=" + description +
        "}";
    }
}
