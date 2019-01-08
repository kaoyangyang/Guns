package cn.stylefeng.guns.modular.Control.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.Control;
import cn.stylefeng.guns.modular.Control.service.IControlService;

/**
 * 成绩录入控制控制器
 *
 * @author fengshuonan
 * @Date 2019-01-08 13:56:23
 */
@Controller
@RequestMapping("/control")
public class ControlController extends BaseController {

    private String PREFIX = "/Control/control/";

    @Autowired
    private IControlService controlService;

    /**
     * 跳转到成绩录入控制首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "control.html";
    }

    /**
     * 跳转到添加成绩录入控制
     */
    @RequestMapping("/control_add")
    public String controlAdd() {
        return PREFIX + "control_add.html";
    }

    /**
     * 跳转到修改成绩录入控制
     */
    @RequestMapping("/control_update/{controlId}")
    public String controlUpdate(@PathVariable Integer controlId, Model model) {
        Control control = controlService.selectById(controlId);
        model.addAttribute("item",control);
        LogObjectHolder.me().set(control);
        return PREFIX + "control_edit.html";
    }

    /**
     * 获取成绩录入控制列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return controlService.selectList(null);
    }

    /**
     * 新增成绩录入控制
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Control control) {
        controlService.insert(control);
        return SUCCESS_TIP;
    }

    /**
     * 删除成绩录入控制
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer controlId) {
        controlService.deleteById(controlId);
        return SUCCESS_TIP;
    }

    /**
     * 修改成绩录入控制
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Control control) {
        controlService.updateById(control);
        return SUCCESS_TIP;
    }

    /**
     * 成绩录入控制详情
     */
    @RequestMapping(value = "/detail/{controlId}")
    @ResponseBody
    public Object detail(@PathVariable("controlId") Integer controlId) {
        return controlService.selectById(controlId);
    }
}
