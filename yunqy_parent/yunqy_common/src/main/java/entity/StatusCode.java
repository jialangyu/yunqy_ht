package entity;

/**
 * 状态码实体类
 */
public class StatusCode {

    //定义静态常量
    public static final int OK=20000;//成功
    public static final int ERROR=20001;//错误
    public static final int LOGINERROR=20002;//登录失败，用户名或密码错误
    public static final int ACCESSERROR=20003;//权限不足
}
