package xurong.marinemode.community.dto;

import xurong.marinemode.community.model.Comment;
import xurong.marinemode.community.model.User;

import java.util.List;

public class CommentShowDTO {
    private Comment comment;
    private User user;
    private List<CommentShowDTO> secondLevelComment;

    public List<CommentShowDTO> getSecondLevelComment() {
        return secondLevelComment;
    }

    public void setSecondLevelComment(List<CommentShowDTO> secondLevelComment) {
        this.secondLevelComment = secondLevelComment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
