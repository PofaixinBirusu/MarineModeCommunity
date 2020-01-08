package xurong.marinemode.community.exception;

public class CodeAndMsg {
    private int state;
    private String msg;

    public CodeAndMsg() {}

    public CodeAndMsg(int state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
