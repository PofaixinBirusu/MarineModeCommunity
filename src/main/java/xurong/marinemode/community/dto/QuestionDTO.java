package xurong.marinemode.community.dto;

import xurong.marinemode.community.model.Comment;
import xurong.marinemode.community.model.Question;
import xurong.marinemode.community.model.User;

public class QuestionDTO {
    private Question question;
    private User user;

    public QuestionDTO() {}

    public QuestionDTO(Question question, User user) {
        this.question = question;
        this.user = user;
    }
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
