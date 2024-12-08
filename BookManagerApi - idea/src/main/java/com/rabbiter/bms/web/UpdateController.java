package com.rabbiter.bms.web;

import com.rabbiter.bms.utils.PathUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传控制器
 * 处理图片等文件的上传请求
 */
@Slf4j
@RestController
@RequestMapping("/update")
public class UpdateController {

    private static final String UPLOAD_DIR = "/static/files/";
    private static final String SUCCESS_CODE = "0";
    
    /**
     * 处理图片上传请求
     * @param req HTTP请求对象
     * @return 包含上传结果的Map：
     *         - code: 0表示成功
     *         - data: 文件访问路径
     */
    @PostMapping("/updateImg")
    @ResponseBody
    public Map<String, Object> updateImg(HttpServletRequest req) {
        Map<String, Object> result = new HashMap<>();
        try {
            String filePath = handleFileUpload(req);
            if (filePath != null) {
                result.put("code", SUCCESS_CODE);
                result.put("data", filePath);
            } else {
                result.put("code", "1");
                result.put("msg", "文件上传失败");
            }
        } catch (Exception e) {
            log.error("文件上传处理失败", e);
            result.put("code", "1");
            result.put("msg", "文件上传异常：" + e.getMessage());
        }
        return result;
    }

    /**
     * 处理文件上传的核心方法
     * @param req HTTP请求对象
     * @return 上传成功返回文件访问路径，失败返回null
     */
    private String handleFileUpload(HttpServletRequest req) {
        try {
            // 获取文件存储目录
            String uploadDir = PathUtils.getClassLoadRootPath() + UPLOAD_DIR;
            File uploadPath = new File(uploadDir);
            
            // 确保上传目录存在
            if (!uploadPath.exists() && !uploadPath.mkdirs()) {
                log.error("创建上传目录失败: {}", uploadDir);
                return null;
            }

            // 检查是否是多部分请求
            if (!ServletFileUpload.isMultipartContent(req)) {
                log.warn("非文件上传请求");
                return null;
            }

            // 创建文件上传处理器
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            // 解析请求
            List<FileItem> items = upload.parseRequest(new ServletRequestContext(req));
            
            // 处理上传的文件
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    // 生成唯一文件名
                    String fileName = System.currentTimeMillis() + "_" + item.getName();
                    log.info("处理上传文件: {}", fileName);

                    // 保存文件
                    File file = new File(uploadPath, fileName);
                    item.write(file);

                    // 返回文件访问路径
                    return "/files/" + fileName;
                }
            }
        } catch (Exception e) {
            log.error("文件上传处理异常", e);
        }
        return null;
    }
}
