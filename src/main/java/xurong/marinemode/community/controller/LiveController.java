package xurong.marinemode.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xurong.marinemode.community.utils.AliLive;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LiveController {
    private String defaultStreamName = "marinemodeStream";
    @RequestMapping("/live")
    @ResponseBody
    public Object live(HttpServletRequest request) {
        Map<String, String> result = AliLive.createPushUrl(defaultStreamName);
        result.put("推流地址", request.getRequestURL()
                .toString().replaceAll("live", "play")
                +"?playUrl="+AliLive.getPlayUrl(defaultStreamName));
        return result;
    }
    @RequestMapping("/play")
    public String play(@RequestParam("playUrl") String playUrl, Model model) {
        System.out.println(playUrl);
        model.addAttribute("playUrl", playUrl);
        return "live";
    }
}
