package xurong.marinemode.community.dto;

public class CommentDTO {
    private int parentId;
    private int parentType;
    private int rootId;
    private int rootType;
    private int secondLevelRootId;
    private String content;

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getSecondLevelRootId() {
        return secondLevelRootId;
    }

    public void setSecondLevelRootId(int secondLevelRootId) {
        this.secondLevelRootId = secondLevelRootId;
    }

    public int getParentType() {
        return parentType;
    }

    public void setParentType(int parentType) {
        this.parentType = parentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
    public String toString() {
        return "rootType: "+rootType+"  rootId: "+rootId+" parentType: "+parentType+" parentId: "+parentId+" content: " + content;
    }
}
