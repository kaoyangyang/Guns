package cn.stylefeng.guns.modular.Score.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.Classes.service.IClassesService;
import cn.stylefeng.guns.modular.Course.service.ICourseService;
import cn.stylefeng.guns.modular.Score.service.IScoreService;
import cn.stylefeng.guns.modular.system.model.Classes;
import cn.stylefeng.guns.modular.system.model.Course;
import cn.stylefeng.guns.modular.system.model.Score;
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
 * 分数管理控制器
 *
 * @author fengshuonan
 * @Date 2019-01-07 10:28:37
 */
@Controller
@RequestMapping("/score")
public class ScoreController extends BaseController {

    private String PREFIX = "/Score/score/";

    @Autowired
    private IScoreService scoreService;

    @Autowired
    private ICourseService iCourseService;
    @Autowired
    private IClassesService iClassesService;
    @Autowired
    private IUserService iUserService;
    /**
     * 跳转到分数管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "score.html";
    }

    /**
     * 跳转到添加分数管理
     */
    @RequestMapping("/score_add")
    public String scoreAdd( Model model) {
        Map<String,Object> map =new HashMap<>();

        Integer userId = ShiroKit.getUser().getId();
        if(userId==1){
        }else{
            map.put("userid",userId);
        }
        List<Course> courses = iCourseService.selectByMap(map);
        model.addAttribute("courses",courses);
        List<Classes> classes = iClassesService.selectByMap(null);
        model.addAttribute("classes",classes);
        Map<String,Object> stuMap =new HashMap<>();
        stuMap.put("roleid","7");
        List<User> students = iUserService.selectByMap(stuMap);
        model.addAttribute("students",students);
        return PREFIX + "score_add.html";
    }

    /**
     * 跳转到修改分数管理
     */
    @RequestMapping("/score_update/{scoreId}")
    public String scoreUpdate(@PathVariable Integer scoreId, Model model) {
        Score score = scoreService.selectById(scoreId);
        model.addAttribute("item",score);
        LogObjectHolder.me().set(score);
        return PREFIX + "score_edit.html";
    }

    /**
     * 获取分数管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return scoreService.selectList(null);
    }

    /**
     * 新增分数管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Score score) {
        scoreService.insert(score);
        return SUCCESS_TIP;
    }

    /**
     * 删除分数管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer scoreId) {
        scoreService.deleteById(scoreId);
        return SUCCESS_TIP;
    }

    /**
     * 修改分数管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Score score) {
        scoreService.updateById(score);
        return SUCCESS_TIP;
    }

    /**
     * 分数管理详情
     */
    @RequestMapping(value = "/detail/{scoreId}")
    @ResponseBody
    public Object detail(@PathVariable("scoreId") Integer scoreId) {
        return scoreService.selectById(scoreId);
    }
}
