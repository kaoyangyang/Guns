package cn.stylefeng.guns.modular.Course.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.Course.service.ICourseService;
import cn.stylefeng.guns.modular.system.model.Course;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程管理控制器
 *
 * @author fengshuonan
 * @Date 2019-01-07 09:37:27
 */
@Controller
@RequestMapping("/course")
public class CourseController extends BaseController {

    private String PREFIX = "/Course/course/";

    @Autowired
    private ICourseService courseService;
    @Autowired
    private IUserService iUserService;
    /**
     * 跳转到课程管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "course.html";
    }

    /**
     * 跳转到添加课程管理
     */
    @RequestMapping("/course_add")
    public String courseAdd(Model model) {
        Map<String,Object> map =new HashMap<>();
        map.put("roleid",6);
        List<User> users = iUserService.selectByMap(map);
        model.addAttribute("users",users);
        return PREFIX + "course_add.html";
    }

    /**
     * 跳转到修改课程管理
     */
    @RequestMapping("/course_update/{courseId}")
    public String courseUpdate(@PathVariable Integer courseId, Model model) {
        Map<String,Object> map =new HashMap<>();
        map.put("roleid",6);
        List<User> users = iUserService.selectByMap(map);
        model.addAttribute("users",users);
        Course course = courseService.selectById(courseId);
        model.addAttribute("item",course);
        LogObjectHolder.me().set(course);
        return PREFIX + "course_edit.html";
    }

    /**
     * 获取课程管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return courseService.selectList(null);
    }

    /**
     * 新增课程管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Course course) {
        course.setUsername(iUserService.selectById(course.getUserid()).getName());
        course.setUserid(iUserService.selectById(course.getUserid()).getId());
        courseService.insert(course);
        return SUCCESS_TIP;
    }

    /**
     * 删除课程管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer courseId) {
        courseService.deleteById(courseId);
        return SUCCESS_TIP;
    }

    /**
     * 修改课程管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Course course) {
        course.setUsername(iUserService.selectById(course.getUserid()).getName());
        course.setUserid(iUserService.selectById(course.getUserid()).getId());
        courseService.updateById(course);
        return SUCCESS_TIP;
    }

    /**
     * 课程管理详情
     */
    @RequestMapping(value = "/detail/{courseId}")
    @ResponseBody
    public Object detail(@PathVariable("courseId") Integer courseId) {
        return courseService.selectById(courseId);
    }
}
