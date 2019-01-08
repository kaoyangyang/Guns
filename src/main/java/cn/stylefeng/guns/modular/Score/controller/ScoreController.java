package cn.stylefeng.guns.modular.Score.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.DataGrid;
import cn.stylefeng.guns.modular.Classes.service.IClassesService;
import cn.stylefeng.guns.modular.Control.service.IControlService;
import cn.stylefeng.guns.modular.Course.service.ICourseService;
import cn.stylefeng.guns.modular.Score.service.IScoreService;
import cn.stylefeng.guns.modular.system.model.*;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	@Autowired
	private IControlService iControlService;
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
    public String scoreAdd( Model model) throws Exception{

	    List<Control> controls = iControlService.selectByMap(null);
	    if(controls.size()>0){
		    Control control = controls.get(0);
		    Date now = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    long nowTime = now.getTime();
		    long start = sdf.parse(control.getStarttime()).getTime();
		    long end = sdf.parse(control.getEndtime()).getTime();
		    if(!(nowTime>start &&nowTime<end)){
			    return PREFIX + "404.html";
		    }
	    }
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
    public String scoreUpdate(@PathVariable Integer scoreId, Model model)  throws Exception{



	    List<Control> controls = iControlService.selectByMap(null);
	    if(controls.size()>0){
		    Control control = controls.get(0);
		    Date now = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    long nowTime = now.getTime();
		    long start = sdf.parse(control.getStarttime()).getTime();
		    long end = sdf.parse(control.getEndtime()).getTime();
		    if(!(nowTime>start &&nowTime<end)){
			    return PREFIX + "404.html";
		    }
	    }
        Score score = scoreService.selectById(scoreId);
        model.addAttribute("item",score);
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
	    Classes classes = iClassesService.selectById(score.getClasscode());
	    User user = iUserService.selectById(score.getUserid());
	    Course course = iCourseService.selectById(score.getCourseid());
	    score.setClassname(classes.getName());
	    score.setClasscode(classes.getCode());
	    score.setCoursename(course.getCourse());
	    score.setUserid(user.getId());
	    score.setUsername(user.getName());
	    score.setGrade(classes.getGrade());
	    score.setDatetime(new Date());
	    scoreService.insert(score);
        return SUCCESS_TIP;
    }

    /**
     * 删除分数管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer scoreId) throws Exception{

	    List<Control> controls = iControlService.selectByMap(null);
	    if(controls.size()>0){
		    Control control = controls.get(0);
		    Date now = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    long nowTime = now.getTime();
		    long start = sdf.parse(control.getStarttime()).getTime();
		    long end = sdf.parse(control.getEndtime()).getTime();
		    if(!(nowTime>start &&nowTime<end)){
		    	Map<String,String> map =new HashMap<>();
			    map.put("success","false");
			    return map;
		    }
	    }
        scoreService.deleteById(scoreId);
        return SUCCESS_TIP;
    }

    /**
     * 修改分数管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Score score) {
	    Classes classes = iClassesService.selectById(score.getClasscode());
	    User user = iUserService.selectById(score.getUserid());
	    Course course = iCourseService.selectById(score.getCourseid());
	    score.setClassname(classes.getName());
	    score.setClasscode(classes.getCode());
	    score.setCoursename(course.getCourse());
	    score.setUserid(user.getId());
	    score.setUsername(user.getName());
	    score.setGrade(classes.getGrade());
	    score.setDatetime(new Date());
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
	/**
	 * 跳转成绩查询首页
	 */
	@RequestMapping("getscore")
	public String getscore( Model model) {
		List<Course> courses = iCourseService.selectByMap(null);
		model.addAttribute("courses",courses);
		return PREFIX + "getscore.html";
	}
	/**
	 * 分数管理详情
	 */
	@RequestMapping("showscore")
	@ResponseBody
	public Object showscore(Score score) {
		Integer userId = ShiroKit.getUser().getId();
		Map<String,Object> map =new HashMap<>();
		map.put("userid",userId);
		if(score.getCourseid()!=null){
			map.put("courseid",score.getCourseid());
		}
		DataGrid result = new DataGrid();
		result.setRows(scoreService.selectByMap(map));
		return result;
	}
	//创建表头
	private void createTitle(HSSFWorkbook workbook,HSSFSheet sheet){
		HSSFRow row = sheet.createRow(0);
		//设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
		sheet.setColumnWidth(1,12*256);
		sheet.setColumnWidth(3,17*256);

		//设置为居中加粗
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		style.setFont(font);

		HSSFCell cell;
		cell = row.createCell(0);
		cell.setCellValue("科目名称");
		cell.setCellStyle(style);


		cell = row.createCell(1);
		cell.setCellValue("班级");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("分数");
		cell.setCellStyle(style);

		cell = row.createCell(3);

		cell.setCellValue("学生");
		cell.setCellStyle(style);
	}

	//生成user表excel
	@RequestMapping("excel")
	public String getUser(HttpServletResponse response) throws Exception{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("统计表");
		createTitle(workbook,sheet);
		Integer userId = ShiroKit.getUser().getId();
		Map<String,Object> map =new HashMap<>();
		map.put("userid",userId);
		List<Score> scores = scoreService.selectByMap(map);

		//设置日期格式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

		//新增数据行，并且设置单元格数据
		int rowNum=1;
		for(Score score:scores){
			HSSFRow row = sheet.createRow(rowNum);
			row.createCell(0).setCellValue(score.getCoursename());
			row.createCell(1).setCellValue(score.getClassname());
			row.createCell(2).setCellValue(score.getScore().toString());
			row.createCell(3).setCellValue(score.getUsername());
			HSSFCell cell = row.createCell(4);
			cell.setCellStyle(style);
			rowNum++;
		}

		String fileName = "成绩单.xls";

		//生成excel文件
		buildExcelFile(fileName, workbook);

		//浏览器下载excel
		buildExcelDocument(fileName,workbook,response);

		return "download excel";
	}

	//生成excel文件
	protected void buildExcelFile(String filename,HSSFWorkbook workbook) throws Exception{
		FileOutputStream fos = new FileOutputStream(filename);
		workbook.write(fos);
		fos.flush();
		fos.close();
	}

	//浏览器下载excel
	protected void buildExcelDocument(String filename,HSSFWorkbook workbook,HttpServletResponse response) throws Exception{
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(filename, "utf-8"));
		OutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

}
