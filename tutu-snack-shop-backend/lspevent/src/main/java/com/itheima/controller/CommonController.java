package com.itheima.controller;

import com.itheima.pojo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
@Tag(name = "通用接口")
public class CommonController {
    //定义文件上传的存储路径常量
    private static String FILE_UPLOAD_PATH = "D:\\Java\\LSPxcxEvent\\lspevent\\src\\main\\resources\\upload\\";
    public static final String UPLOAD_FAILED = "文件上传失败";
    @Operation(summary = "文件上传")
    @PostMapping("/upload")
    @ResponseBody//表示方法返回值直接写入响应体
    //从 HTTP 请求中提取 "file" 参数，并将其转换为 MultipartFile 对象；@RequestParam提取查询参数Query或表单参数
    //方法参数名必须与请求参数名一致才可以省略("file")
    // 虽然可以省略，但在实际项目中建议保留明确的参数名，因为这样：
    //1.代码更清晰易读
    //2.避免因编译设置问题导致参数绑定失败
    public Result uploadfile(@RequestParam("file") MultipartFile file) throws IOException{
        if(file.isEmpty()){
            return Result.error("文件不能为空");
        }
        File dir = new File(FILE_UPLOAD_PATH);
        if(!dir.exists() || !dir.isDirectory()){
            boolean created = dir.mkdirs();
            if(created){
                log.info("创建文件夹成功：{}",FILE_UPLOAD_PATH);
            }else {
                log.warn("创建文件夹失败或已存在：{}",FILE_UPLOAD_PATH);
            }
        }
        String originalFilename = file.getOriginalFilename();
        //isEmpty()只会检查字符串是否为空字符串 ""
        if(originalFilename == null || originalFilename.isEmpty()){
            return Result.error("文件名无效");
        }
        //获取文件名后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //equalsIgnoreCase用于忽略大小写比较两个字符串是否相等
        if(!extension.equalsIgnoreCase(".png") && !extension.equalsIgnoreCase(".jpg") && !extension.equalsIgnoreCase(".jpeg")){
            return Result.error("文件格式不支持");
        }
        //拼接新的随机名称
        //UUID.randomUUID().toString() 是用于生成全局唯一标识符(UUID)并转换为字符串的代码，randomUUID() 是 UUID 类的静态方法，用于生成一个随机的 UUID
        originalFilename = UUID.randomUUID().toString() + extension;

        //确保文件路径安全，避免路径遍历攻击
        //使用 Paths.get() 方法将字符串路径 FILE_UPLOAD_PATH 转换为 Path 对象
        //resolve() 方法用于将文件名解析并附加到现有路径上，将 originalFilename（实际是UUID生成的新文件名）附加到基础路径后面
        //例如：如果路径是 D:/upload/，文件名是 abc.png，结果是 D:/upload/abc.png
        //normalize() 方法用于规范化路径，移除冗余元素，处理路径中的 . 和 .. 等特殊符号
        Path targetLocation = Paths.get(FILE_UPLOAD_PATH).resolve(originalFilename).normalize();
        try {
            //getInputStream() 获取上传文件的输入流，用于读取文件内容
            //targetLocation是目标文件路径（之前通过 Paths.get().resolve().normalize() 构建的 Path 对象），指定了文件要保存到的具体位置
            //StandardCopyOption.REPLACE_EXISTING是一个复制选项，指定如果目标文件已存在则替换它，确保即使同名文件存在也能成功保存
            //这行代码的作用是将上传的文件内容保存到服务器指定位置
            Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("文件上传成功：{}",originalFilename);
        }catch (IOException e) {
            log.error("文件上传失败：{}",originalFilename,e);
            return Result.error(UPLOAD_FAILED);
        }

        //根据实际情况调整返回的文件访问链接
        //这行代码的目的是为上传成功的文件生成一个可以通过HTTP访问的URL地址，客户端可以通过这个URL访问上传的文件
        //该URL会通过 Result.success(fileUrl) 返回给前端，前端可以：
        //1.在网页中直接显示图片
        //2.提供文件下载链接
        //3.保存到数据库中供后续使用
        String fileUrl = "http://localhost:8080/static/" + originalFilename;
        return Result.success(fileUrl);
    }
}
