package cn.stylefeng.guns.modular.TeacherRating.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.TeacherRating.service.IMarkService;
import cn.stylefeng.guns.modular.system.model.Mark;
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

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 教师评分控制器
 *
 * @author fengshuonan
 * @Date 2019-01-17 17:19:52
 */
@Controller
@RequestMapping("/mark")
public class MarkController extends BaseController {

    private String PREFIX = "/TeacherRating/mark/";

    @Autowired
    private IMarkService markService;
    @Autowired
    private IUserService iUserService;
    /**
     * 跳转到教师评分首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "mark.html";
    }

    /**
     * 跳转到添加教师评分
     */
    @RequestMapping("/mark_add")
    public String markAdd(Model model) {
        Map<String,Object> map =new HashMap<>();
        map.put("roleid",6);
        List<User> users = iUserService.selectByMap(map);
        Map<String,Object> markMap =new HashMap<>();
        Integer userId = ShiroKit.getUser().getId();
        markMap.put("markid",userId);
        List<Mark> marks = markService.selectByMap(markMap);
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            final User next = iterator.next();
            for(Mark mark :marks){
                if(mark.getUserid().equals(next.getId())){
                    iterator.remove();
                }
            }
        }

            model.addAttribute("teachers",users);
        return PREFIX + "mark_add.html";
    }

    /**
     * 跳转到修改教师评分
     */
    @RequestMapping("/mark_update/{markId}")
    public String markUpdate(@PathVariable Integer markId, Model model) {
        Mark mark = markService.selectById(markId);
        model.addAttribute("item",mark);
        LogObjectHolder.me().set(mark);
        return PREFIX + "mark_edit.html";
    }

    /**
     * 获取教师评分列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Integer userId = ShiroKit.getUser().getId();
        Map<String,Object> map =new HashMap<>();
        map.put("markid",userId);
        return markService.selectByMap(map);
    }
    /**
     * 获取教师评分列表
     */
    @RequestMapping(value = "/rating")
    public String rating(Model model) {
        List<Mark> marks = markService.selectList(null);
        Map<String,Integer>timesMap=new HashMap<>();
        Map<String,Integer> teachersMap=new HashMap<>();

        for(Mark mark :marks){
            String username = mark.getUsername();
            Integer o = teachersMap.get(username);
            if(o ==null){
                timesMap.put(username,1);
                teachersMap.put(username,mark.getTotle());
            }else{
                timesMap.put(username,timesMap.get(username)+1);
                teachersMap.put(username,mark.getTotle()+o);
            }
        }
        for (Map.Entry<String, Integer> entry : teachersMap.entrySet()) {
            entry.setValue(entry.getValue()/(timesMap.get(entry.getKey())));
        }
        model.addAttribute("teachers",teachersMap);
        return PREFIX + "rating.html";
    }
    /**
     * 新增教师评分
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Mark mark) {
        Integer userId = ShiroKit.getUser().getId();
        mark.setTotle((mark.getCode()+mark.getLevel()+mark.getMorality()+mark.getStyle()));
        mark.setMarkid(userId);
        mark.setUsername(iUserService.selectById(mark.getUserid()).getName());
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long nowTime = now.getTime();
        String time = sdf.format(nowTime);
        mark.setDatetime(time);
        markService.insert(mark);
        return SUCCESS_TIP;
    }

    /**
     * 删除教师评分
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer markId) {
        markService.deleteById(markId);
        return SUCCESS_TIP;
    }

    /**
     * 修改教师评分
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Mark mark) {
        mark.setTotle((mark.getCode()+mark.getLevel()+mark.getMorality()+mark.getStyle()));
        markService.updateById(mark);
        return SUCCESS_TIP;
    }

    /**
     * 教师评分详情
     */
    @RequestMapping(value = "/detail/{markId}")
    @ResponseBody
    public Object detail(@PathVariable("markId") Integer markId) {
        return markService.selectById(markId);
    }
}
