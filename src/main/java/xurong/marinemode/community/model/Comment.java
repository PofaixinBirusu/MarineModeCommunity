package xurong.marinemode.community.model;

public class Comment {
    private int id;
    private int parentId;
    private int parentType;
    private int rootId;
    private int rootType;
    private int userId;
    private int likeCount=0;
    private int secondLevelRootId=0;
    private String content;
    private Long gmtCreate;
    private Long gmtModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSecondLevelRootId() {
        return secondLevelRootId;
    }

    public void setSecondLevelRootId(int secondLevelRootId) {
        this.secondLevelRootId = secondLevelRootId;
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public int getRootType() {
        return rootType;
    }

    public void setRootType(int rootType) {
        this.rootType = rootType;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getParentType() {
        return parentType;
    }

    public void setParentType(int parentType) {
        this.parentType = parentType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
