package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.system.model.Item;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.guns.modular.system.service.IItemService;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 请假模块控制器
 *
 * @author fengshuonan
 * @Date 2019-04-03 16:02:40
 */
@Controller
@RequestMapping("/item")
public class ItemController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${web.upload-path}")
    private String uploadPath;
    private String PREFIX = "/system/item/";

    @Autowired
    private IItemService itemService;

    /**
     * 跳转到请假模块首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "item.html";
    }

    /**
     * 跳转到添加请假模块
     */
    @RequestMapping("/item_add")
    public String itemAdd() {
        return PREFIX + "item_add.html";
    }

    /**
     * 跳转到修改请假模块
     */
    @RequestMapping("/item_update/{itemId}")
    public String itemUpdate(@PathVariable Integer itemId, Model model) {
        Item item = itemService.selectById(itemId);
        model.addAttribute("item",item);
        LogObjectHolder.me().set(item);
        return PREFIX + "item_edit.html";
    }

    /**
     * 获取请假模块列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        EntityWrapper<Item> itemEntityWrapper = new EntityWrapper<>();
        Integer userId = ShiroKit.getUser().getId();
        User user = userService.selectById(userId);user.getRoleid();
        if(user.getRoleid().equals("6")){
            itemEntityWrapper.eq("class_code",user.getClasscode());
        }
        if(user.getRoleid().equals("7")){
            itemEntityWrapper.eq("user_id",userId);
        }

        return itemService.selectList(itemEntityWrapper);
    }

    /**
     * 新增请假模块
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MultipartFile file, Item item)  throws Exception{
        if(item.getType().equals("报销")){
            if (file.isEmpty()) {
                return SuccessResponseData.error("请上传附件!");
            }else{
                // 获取文件名
                String fileName = file.getOriginalFilename();
                // 设置文件存储路径
                String path = uploadPath + fileName;
                File dest = new File(path);
                // 检测是否存在目录
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();// 新建文件夹
                }
                file.transferTo(dest);// 文件写入
                item.setPath(path);
                item.setFileName(fileName);
            }
        }
//        String name = file.getName();
        Integer userId = ShiroKit.getUser().getId();
        User user = userService.selectById(userId);
        item.setUserId(userId);
        item.setClassCode(user.getClasscode());
        item.setUserName(user.getName());
        itemService.insert(item);
        return SUCCESS_TIP;
    }

    /**
     * 删除请假模块
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer itemId) {
        itemService.deleteById(itemId);
        return SUCCESS_TIP;
    }

    /**
     * 修改请假模块
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Item item) {
        item.setStatus("已审批");
        Integer userId = ShiroKit.getUser().getId();
        User user = userService.selectById(userId);
        item.setPassId(userId);
        item.setPassName(user.getName());
        itemService.updateById(item);
        Item item1 = itemService.selectById(item);
        User sendUser = userService.selectById(item1.getUserId());
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("<html><head><title></title></head><body>");
        stringBuilder.append("亲爱的用户"+item1.getUserName()+",您的审批已通过,请登录网站查看详细信息");
        stringBuilder.append("</br><a href='http://localhost:8080/'>登录网站</a></body><html>");
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        //multipart模式
        try {
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(sendUser.getEmail());//收件人邮箱user.getMail()
            mimeMessageHelper.setFrom("18753916953@163.com");//发件人邮箱
            mimeMessage.setSubject("审批通过");
            //启用html
            mimeMessageHelper.setText(stringBuilder.toString(),true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return SUCCESS_TIP;
    }

    /**
     * 请假模块详情
     */
    @RequestMapping(value = "/detail/{itemId}")
    @ResponseBody
    public Object detail(@PathVariable("itemId") Integer itemId) {
        return itemService.selectById(itemId);
    }
    @RequestMapping("/download")
    public String download(Item item, HttpServletRequest request, HttpServletResponse response){

        response.setContentType("text/html;charset=utf-8");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        item = itemService.selectById(item);
        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;

        String downLoadPath = item.getPath();  //注意不同系统的分隔符
        //	String downLoadPath =filePath.replaceAll("/", "\\\\\\\\");   //replace replaceAll区别 *****
        System.out.println(downLoadPath);

        try {
            long fileLength = new File(downLoadPath).length();
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(item.getFileName().getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                try {
                    bis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return null;
    }

}
