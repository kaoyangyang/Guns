package cn.stylefeng.guns.modular.ScoreLog.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.ScoreLog;
import cn.stylefeng.guns.modular.ScoreLog.service.IScoreLogService;

/**
 * 分数修改记录控制器
 *
 * @author fengshuonan
 * @Date 2019-01-15 17:34:25
 */
@Controller
@RequestMapping("/scoreLog")
public class ScoreLogController extends BaseController {

    private String PREFIX = "/ScoreLog/scoreLog/";

    @Autowired
    private IScoreLogService scoreLogService;

    /**
     * 跳转到分数修改记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "scoreLog.html";
    }

    /**
     * 跳转到添加分数修改记录
     */
    @RequestMapping("/scoreLog_add")
    public String scoreLogAdd() {
        return PREFIX + "scoreLog_add.html";
    }

    /**
     * 跳转到修改分数修改记录
     */
    @RequestMapping("/scoreLog_update/{scoreLogId}")
    public String scoreLogUpdate(@PathVariable Integer scoreLogId, Model model) {
        ScoreLog scoreLog = scoreLogService.selectById(scoreLogId);
        model.addAttribute("item",scoreLog);
        LogObjectHolder.me().set(scoreLog);
        return PREFIX + "scoreLog_edit.html";
    }

    /**
     * 获取分数修改记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return scoreLogService.selectList(null);
    }

    /**
     * 新增分数修改记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ScoreLog scoreLog) {
        scoreLogService.insert(scoreLog);
        return SUCCESS_TIP;
    }

    /**
     * 删除分数修改记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer scoreLogId) {
        scoreLogService.deleteById(scoreLogId);
        return SUCCESS_TIP;
    }

    /**
     * 修改分数修改记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ScoreLog scoreLog) {
        scoreLogService.updateById(scoreLog);
        return SUCCESS_TIP;
    }

    /**
     * 分数修改记录详情
     */
    @RequestMapping(value = "/detail/{scoreLogId}")
    @ResponseBody
    public Object detail(@PathVariable("scoreLogId") Integer scoreLogId) {
        return scoreLogService.selectById(scoreLogId);
    }
}
