package xurong.marinemode.community.utils;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.*;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xurong.marinemode.community.exception.CodeAndMsg;
import xurong.marinemode.community.exception.CustomizeException;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.UUID;

@Component
public class UCloud {
    @Value("${ucloud.ufile.publickey}")
    private String publicKey;
    @Value("${ucloud.ufile.privatekey}")
    private String privateKey;
    @Value("${ucloud.ufile.region}")
    private String region;
    @Value("${ucloud.ufile.proxy-suffix}")
    private String proxySuffix;
    @Value("${ucloud.ufile.bucket}")
    private String bucket;

    private ObjectAuthorization BUCKET_AUTHORIZER;
    private ObjectConfig config;

    /* 云端的预览url的过期时间  */
    /* 单位：秒   60*60*24=1天 */
    private int urlExpireTime = 60*60*24 * 365 * 3; // 3年

    @PostConstruct
    public void postInit() {
        BUCKET_AUTHORIZER = new UfileObjectLocalAuthorization(publicKey, privateKey);
        config = new ObjectConfig(region, proxySuffix);
    }

    public CodeAndMsg upLoad(InputStream fileStream, String mimeType, String fileName) {
        String fileSaveName = null;
        if (!StringUtils.isBlank(fileName)) {
            String []fileNameSplit = fileName.split("\\.");
            fileSaveName = UUID.randomUUID()
                    .toString().replaceAll("-", "").substring(0, 30);
            if (fileNameSplit.length > 1)
                fileSaveName += "."+fileNameSplit[fileNameSplit.length-1];
        }
        if (fileSaveName == null)
            return new CodeAndMsg(KeyHelper.StateCodeFaile, KeyHelper.UpLoadFileFormatNotCorrect);
        try {
            PutObjectResultBean response = UfileClient.object(BUCKET_AUTHORIZER, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(fileSaveName)
                    .toBucket(bucket)
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long bytesWritten, long contentLength) {

                        }
                    })
                    .execute();
            if (response != null && response.getRetCode() == 0) {
                String url = UfileClient.object(BUCKET_AUTHORIZER, config)
                        .getDownloadUrlFromPrivateBucket(fileSaveName, bucket, urlExpireTime)
                        .createUrl();
                return new CodeAndMsg(KeyHelper.StateCodeSuccess, url);
            } else {
                return new CodeAndMsg(KeyHelper.StateCodeFaile, KeyHelper.UpLoadFaile);
            }
        } catch (UfileClientException e) {
            e.printStackTrace();
            return new CodeAndMsg(KeyHelper.StateCodeFaile, KeyHelper.UpLoadFaile);
        } catch (UfileServerException e) {
            e.printStackTrace();
            return new CodeAndMsg(KeyHelper.StateCodeFaile, KeyHelper.UpLoadFaile);
        }
    }
}
