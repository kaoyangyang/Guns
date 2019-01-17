package cn.stylefeng.guns.modular.Score.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.util.DataGrid;
import cn.stylefeng.guns.modular.Classes.service.IClassesService;
import cn.stylefeng.guns.modular.Control.service.IControlService;
import cn.stylefeng.guns.modular.Course.service.ICourseService;
import cn.stylefeng.guns.modular.Score.service.IScoreService;
import cn.stylefeng.guns.modular.ScoreLog.service.IScoreLogService;
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
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

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
	@Autowired
	private IScoreLogService iScoreLogService;

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
	public String scoreAdd(Model model) throws Exception {

		List<Control> controls = iControlService.selectByMap(null);
		if (controls.size() > 0) {
			Control control = controls.get(0);
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long nowTime = now.getTime();
			long start = sdf.parse(control.getStarttime()).getTime();
			long end = sdf.parse(control.getEndtime()).getTime();
			if (!(nowTime > start && nowTime < end)) {
				return PREFIX + "404.html";
			}
		}
		Map<String, Object> map = new HashMap<>();
		Integer userId = ShiroKit.getUser().getId();
		if (userId == 1) {
		} else {
			map.put("userid", userId);
		}
		List<Course> courses = iCourseService.selectByMap(map);
		model.addAttribute("courses", courses);
		List<Classes> classes = iClassesService.selectByMap(null);
		model.addAttribute("classes", classes);
		Map<String, Object> stuMap = new HashMap<>();
		stuMap.put("roleid", "7");
		List<User> students = iUserService.selectByMap(stuMap);
		model.addAttribute("students", students);
		return PREFIX + "score_add.html";
	}

	/**
	 * 跳转到修改分数管理
	 */
	@RequestMapping("/score_update/{scoreId}")
	public String scoreUpdate(@PathVariable Integer scoreId, Model model) throws Exception {


		List<Control> controls = iControlService.selectByMap(null);
		if (controls.size() > 0) {
			Control control = controls.get(0);
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long nowTime = now.getTime();
			long start = sdf.parse(control.getStarttime()).getTime();
			long end = sdf.parse(control.getEndtime()).getTime();
			if (!(nowTime > start && nowTime < end)) {
				return PREFIX + "404.html";
			}
		}
		Score score = scoreService.selectById(scoreId);
		model.addAttribute("item", score);
		Map<String, Object> map = new HashMap<>();

		Integer userId = ShiroKit.getUser().getId();
		if (userId == 1) {
		} else {
			map.put("userid", userId);
		}
		List<Course> courses = iCourseService.selectByMap(map);
		model.addAttribute("courses", courses);
		List<Classes> classes = iClassesService.selectByMap(null);
		model.addAttribute("classes", classes);
		Map<String, Object> stuMap = new HashMap<>();
		stuMap.put("roleid", "7");
		List<User> students = iUserService.selectByMap(stuMap);
		model.addAttribute("students", students);
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
	public Object add(Score score) throws Exception {

		Classes classes = iClassesService.selectById(score.getClasscode());
		User user = iUserService.selectById(score.getUserid());
		if(user.getMark()==2){
			Map<String, String> map = new HashMap<>();
			map.put("success", "unfreeze");
			return map;
		}
		Course course = iCourseService.selectById(score.getCourseid());

		Map<String,Object> scoresMap= new HashMap<>();
		scoresMap.put("userid",user.getId());

		List<Score> scores = scoreService.selectByMap(scoresMap);
		int times =0;
		for(Score  scores1:  scores){
			if(scores1.getScore().compareTo(new BigDecimal(60))<0){
				times++;
			}
		}
		if(times==1 &&score.getScore().compareTo(new BigDecimal(60))<0){
			user.setMark(3);
			iUserService.updateById(user);
		}
		if(times==4 &&score.getScore().compareTo(new BigDecimal(60))<0){
			user.setMark(2);
			iUserService.updateById(user);
		}
		List<Control> controls = iControlService.selectByMap(null);
		if (controls.size() > 0) {
			Control control = controls.get(0);
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long nowTime = now.getTime();
			long start = sdf.parse(control.getStarttime()).getTime();
			long end = sdf.parse(control.getEndtime()).getTime();
			if (!(nowTime > start && nowTime < end)) {
				Map<String, String> map = new HashMap<>();
				map.put("success", "outtime");
				return map;
			}
		}
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
	public Object delete(@RequestParam Integer scoreId) throws Exception {

		List<Control> controls = iControlService.selectByMap(null);
		if (controls.size() > 0) {
			Control control = controls.get(0);
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long nowTime = now.getTime();
			long start = sdf.parse(control.getStarttime()).getTime();
			long end = sdf.parse(control.getEndtime()).getTime();
			if (!(nowTime > start && nowTime < end)) {
				Map<String, String> map = new HashMap<>();
				map.put("success", "false");
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
		ScoreLog scoreLog = new ScoreLog();
		Score oldScore = scoreService.selectById(score.getId());

		scoreLog.setOldclass(oldScore.getClassname());
		scoreLog.setOldcourse(oldScore.getCoursename());
		scoreLog.setOldscore(oldScore.getScore());
		scoreLog.setOlduser(oldScore.getUsername());
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

		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long nowTime = now.getTime();
		String time = sdf.format(nowTime);
		scoreLog.setDatetime(time);
		scoreLog.setNewclass(classes.getName());
		scoreLog.setNewcourse(course.getCourse());
		scoreLog.setNewscore(score.getScore());
		scoreLog.setNewuser(score.getUsername());
		Integer userId = ShiroKit.getUser().getId();
		User updateUser = iUserService.selectById(userId);
		scoreLog.setUsername(updateUser.getName());
		iScoreLogService.insert(scoreLog);
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
	public String getscore(Model model) {
		List<Course> courses = iCourseService.selectByMap(null);
		model.addAttribute("courses", courses);
		return PREFIX + "getscore.html";
	}

	/**
	 * 分数管理详情
	 */
	@RequestMapping("showscore")
	@ResponseBody
	public Object showscore(Score score) {
		Integer userId = ShiroKit.getUser().getId();
		Map<String, Object> map = new HashMap<>();
		map.put("userid", userId);
		if (score.getCourseid() != null) {
			map.put("courseid", score.getCourseid());
		}
		DataGrid result = new DataGrid();
		result.setRows(scoreService.selectByMap(map));
		return result;
	}

	//创建表头
	private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet) {
		HSSFRow row = sheet.createRow(0);
		//设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
		sheet.setColumnWidth(1, 12 * 256);
		sheet.setColumnWidth(3, 17 * 256);

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
	public String getUser(HttpServletResponse response) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("统计表");
		createTitle(workbook, sheet);
		Integer userId = ShiroKit.getUser().getId();
		Map<String, Object> map = new HashMap<>();
		map.put("userid", userId);
		List<Score> scores = scoreService.selectByMap(map);

		//设置日期格式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

		//新增数据行，并且设置单元格数据
		int rowNum = 1;
		for (Score score : scores) {
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
		buildExcelDocument(fileName, workbook, response);

		return "download excel";
	}

	//生成excel文件
	protected void buildExcelFile(String filename, HSSFWorkbook workbook) throws Exception {
		FileOutputStream fos = new FileOutputStream(filename);
		workbook.write(fos);
		fos.flush();
		fos.close();
	}

	//浏览器下载excel
	protected void buildExcelDocument(String filename, HSSFWorkbook workbook, HttpServletResponse response) throws Exception {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
		OutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

	/**
	 * 班级排名
	 */
	@RequestMapping("classes")
	public String classes(Score score, Model model) {
		Map<String, Object> map = new HashMap<>();
		if (score.getCourseid() != null) {
			map.put("courseid", score.getCourseid());
			model.addAttribute("score",  score);
		}else{
			model.addAttribute("score",score);
		}


		if (score.getGrade() != null) {
			map.put("grade", score.getGrade());
			model.addAttribute("score", score);
		}else{
			model.addAttribute("score", score);
		}
		List<Score> scores = scoreService.selectByMap(map);
		Map<String, BigDecimal> classes = new HashMap<>();
		Map<String, BigDecimal> classesTimes = new HashMap<>();
		BigDecimal bigDecimal = new BigDecimal(0);
		for (Score scoreA : scores) {
			classes.put(scoreA.getClassname(), bigDecimal);
			BigDecimal times = classesTimes.get((scoreA.getClassname()));
			if (times == null) {
				times = new BigDecimal(0);
			}
			classesTimes.put(scoreA.getClassname(), (times.add(new BigDecimal(1))));

		}
		for (Score scoreB : scores) {
			if (classes.get(scoreB.getClassname()) != null) {
				BigDecimal a = classes.get(scoreB.getClassname());
				classes.put(scoreB.getClassname(),
						a.add(scoreB.getScore()));
			}
		}
		for (Map.Entry<String, BigDecimal> entry : classes.entrySet()) {
			entry.setValue(entry.getValue().divide(classesTimes.get(entry.getKey()), 2, BigDecimal.ROUND_HALF_UP));
		}
		Map<String, BigDecimal> stringBigDecimalMap = sortByValueDescending(classes);


		List<Course> courses = iCourseService.selectByMap(null);
		List<Classes> classes1 = iClassesService.selectByMap(null);
		List<Classes> classes2 = this.removeDuplicateCase(classes1);
		model.addAttribute("courses", courses);
		model.addAttribute("grades", classes2);
		model.addAttribute("calsses", stringBigDecimalMap);
		return PREFIX + "classes.html";
	}
	/**
	 * 个人排名
	 */
	@RequestMapping("student")
	public String student(Score score, Model model) {
		Map<String, Object> map = new HashMap<>();
		if (score.getClasscode() != null) {
			map.put("classcode", score.getClasscode());
			model.addAttribute("classcode",  score.getClasscode());
		}else{
			model.addAttribute("classcode","classcode");
		}

		if (score.getCourseid() != null) {
			map.put("courseid", score.getCourseid());
			model.addAttribute("courseid",  score.getCourseid());
		}else{
			model.addAttribute("courseid",  score.getCourseid());
		}
		if (score.getGrade() != null) {
			map.put("grade", score.getGrade());
			model.addAttribute("grade", score.getGrade());
		}else{
			model.addAttribute("grade", "grade");
		}
		List<Score> scores = scoreService.selectByMap(map);
		Map<String,UserScore> userScores= new HashMap<>();

		for (Score scoreA : scores) {
			UserScore userScore = userScores.get(scoreA.getUsername());
			if(userScore!=null){
				BigDecimal total = userScore.getTotal().add(scoreA.getScore());
				userScore.setTotal(total);
				userScores.put(scoreA.getUsername(),userScore);
			}else{
				UserScore userScore1 = new UserScore(scoreA.getScore(),new BigDecimal(0));
				userScore1.setName(scoreA.getUsername());
				userScores.put(scoreA.getUsername(),userScore1);
			}
		}
		List<UserScore> userScoreList = new ArrayList<>();
		for (Map.Entry<String, UserScore> m : userScores.entrySet()) {

			userScoreList.add(m.getValue());
		}
		Collections.sort(userScoreList);
		List<Classes> classes1 = iClassesService.selectByMap(null);
		List<Classes> classes2 = this.removeDuplicateCase(classes1);

		List<Course> courses = iCourseService.selectByMap(null);
		model.addAttribute("courses", courses);
		model.addAttribute("classes", classes1);
		model.addAttribute("grades", classes2);
		model.addAttribute("students", userScoreList);
		return PREFIX + "student.html";
	}
	//降序排序
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				int compare = (o1.getValue()).compareTo(o2.getValue());
				return -compare;
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	/**
	 * @param
	 * @return
	 * @description 根据电话号码去重
	 * @date 14:39 2018/6/19
	 * @author zhenghao
	 */
	private List<Classes> removeDuplicateCase(List<Classes> cases) {
		Set<Classes> set = new TreeSet<>(new Comparator<Classes>() {
			@Override
			public int compare(Classes o1, Classes o2) {
				//字符串,则按照asicc码升序排列
				return o1.getGrade().compareTo(o2.getGrade());
			}
		});
		set.addAll(cases);
		return new ArrayList<>(set);
	}
}
