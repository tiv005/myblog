package com.myblog.yu.utils;

/**
 * 常量
 * @author 容
 * @version 1.0
 * @date 2020/7/16 16:49
 */
public class Const {
    //每页显示记录数
    public static final int PAGE_SIZE = 4 ;

    //标识有效 文章发布 站长推荐
    public static final String MARK_YES = "1";

    //标识无效 文章不发布  站长不推荐
    public static final String MARK_NO = "-1";

    //图片上传存放的地址
    public static final String FILE_URL = "http://localhost:8080/files_war_exploded/upload/";

    //不显示留言信息
    public static final String  MESSAGE_MARK = "2";
}
