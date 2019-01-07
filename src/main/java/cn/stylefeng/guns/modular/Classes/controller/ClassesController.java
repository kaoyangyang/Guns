package cn.stylefeng.guns.modular.Classes.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.Classes;
import cn.stylefeng.guns.modular.Classes.service.IClassesService;

/**
 * 班级管理控制器
 *
 * @author fengshuonan
 * @Date 2019-01-07 09:31:27
 */
@Controller
@RequestMapping("/classes")
public class ClassesController extends BaseController {

    private String PREFIX = "/Classes/classes/";

    @Autowired
    private IClassesService classesService;

    /**
     * 跳转到班级管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "classes.html";
    }

    /**
     * 跳转到添加班级管理
     */
    @RequestMapping("/classes_add")
    public String classesAdd() {
        return PREFIX + "classes_add.html";
    }

    /**
     * 跳转到修改班级管理
     */
    @RequestMapping("/classes_update/{classesId}")
    public String classesUpdate(@PathVariable Integer classesId, Model model) {
        Classes classes = classesService.selectById(classesId);
        model.addAttribute("item",classes);
        LogObjectHolder.me().set(classes);
        return PREFIX + "classes_edit.html";
    }

    /**
     * 获取班级管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return classesService.selectList(null);
    }

    /**
     * 新增班级管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Classes classes) {
        classesService.insert(classes);
        return SUCCESS_TIP;
    }

    /**
     * 删除班级管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer classesId) {
        classesService.deleteById(classesId);
        return SUCCESS_TIP;
    }

    /**
     * 修改班级管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Classes classes) {
        classesService.updateById(classes);
        return SUCCESS_TIP;
    }

    /**
     * 班级管理详情
     */
    @RequestMapping(value = "/detail/{classesId}")
    @ResponseBody
    public Object detail(@PathVariable("classesId") Integer classesId) {
        return classesService.selectById(classesId);
    }
}
