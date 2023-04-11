package top.jezer.utils;

import org.springframework.web.multipart.MultipartFile;
import top.jezer.constant.ResourceLocation;
import top.jezer.exception.SystemException;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    /**
     *
     * @param file
     * @param flag: 用来标记以何种模式命名:1—时间戳
     * @return
     */
    public static  String uploadFile(MultipartFile file, Integer flag){
        String fileName;
        if (flag == 1)
            fileName = System.currentTimeMillis() + file.getOriginalFilename();
        else fileName = file.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + ResourceLocation.ASSETS_PATH + "/song";
        File file1 = new File(filePath);
        if (!file1.exists())
            file1.mkdir();
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/song/" + fileName;
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new SystemException("文件创建失败");
        }
        return storeUrlPath;
    }
}
