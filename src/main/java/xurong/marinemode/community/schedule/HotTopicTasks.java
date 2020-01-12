package xurong.marinemode.community.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HotTopicTasks {
    @Scheduled(fixedRate=5000)
    public void currentTime() {
        System.out.println(new Date());
        // priority = 5*question_count+comment_count
    }
}
