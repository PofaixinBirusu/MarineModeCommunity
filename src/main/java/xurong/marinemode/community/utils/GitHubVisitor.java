package xurong.marinemode.community.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xurong.marinemode.community.dto.AccessTokenDTO;
import xurong.marinemode.community.dto.GitHubUser;

import java.io.IOException;

@Component
public class GitHubVisitor {
    /* 把配置文件读入进来, ${}里是配置文件的key */
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    public String getAccessToken(String code) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String tokenString = response.body().string();
            return tokenString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GitHubUser getGitHubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?"+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String userString = response.body().string();
            return JSON.parseObject(userString, GitHubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
