package xurong.marinemode.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import xurong.marinemode.community.dto.FileUploadDTO;
import xurong.marinemode.community.exception.CodeAndMsg;
import xurong.marinemode.community.utils.KeyHelper;
import xurong.marinemode.community.utils.UCloud;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
public class FileUploadController {
    @Value("${editormd.image.filename}")
    private String editorUploadImageFileName;
    @Autowired
    private UCloud ucloud;

    public FileUploadDTO upload(HttpServletRequest request, String fileName) throws Exception{
        MultipartHttpServletRequest req = (MultipartHttpServletRequest)request;
        MultipartFile uploadFile = req.getFile(fileName);
        CodeAndMsg upLoadResult =
                ucloud.upLoad(uploadFile.getInputStream(), uploadFile.getContentType(), uploadFile.getOriginalFilename());
        if (upLoadResult.getState() == KeyHelper.StateCodeSuccess) {
            return new FileUploadDTO(1, KeyHelper.UpLoadSuccess, upLoadResult.getMsg());
        } else {
            return new FileUploadDTO(0, KeyHelper.UpLoadFaile, "");
        }
    }

    @RequestMapping("/file/editormd/uploadimage")
    @ResponseBody
    public FileUploadDTO editormdUploadImage(HttpServletRequest request) throws Exception{
        return upload(request, editorUploadImageFileName);
    }
}
