package utils_use.logger;

/**
 * detail: 测试数据
 * @author Ttt
 */
public class TestData {

    /**
     * 分享信息实体类 ( 测试 )
     */
    public static class ShareMsgVo {

        public int    sPlatform         = 0; // 分享平台
        public String sTitle            = null; // title 标题, 印象笔记、邮箱、信息、微信、人人网和 QQ 空间使用
        public String sText             = null; // text 是分享文本, 所有平台都需要这个字段
        public String sImagePath        = null; // imagePath 是图片的本地路径, Linked-In 以外的平台都支持此参数
        public String sImageUrl         = null; // 分享图片路径 (QQ 空间需要 )
        public String sTitleUrl         = null; // titleUrl 是标题的网络链接, 仅在人人网和 QQ 空间使用
        // = 不一定使用 =
        public String sUrl              = null; // url 仅在微信 ( 包括好友和朋友圈 ) 中使用
        public String sComment          = null; // 分享的评论, 仅在人人网和 QQ 空间使用
        public String sSite             = null; // site 是分享此内容的网站名称, 仅在 QQ 空间使用
        public String sSiteUrl          = null; // siteUrl 是分享此内容的网站地址, 仅在 QQ 空间使用
        // = 微信平台分享类型 =
        public int    weChatATShareType = 0; // 分享类型 shareType(SHARE_IMAGE) ,shareType(SHARE_VIDEO), shareType(SHARE_WEBPAGE)
        // 栈索引 ( 用于移除顶部栈 View)
        public int    sTaskId           = -1; // -1 表示不需要移除栈例如单图片分享, 不会添加到栈, 自然也不需要移除
        // 分享模式
        public int    sMode             = -1;
    }

    /**
     * 用户信息实体类 ( 测试 )
     */
    public static class UserInfoVo {

        public String uName = null; // 用户名
        public String uPwd  = null; // 用户密码
        public int    uAge  = -1; // 用户年龄
    }

    // =

    public static final String JSON_WITH_LINE_BREAK = "{\"widget\": {\n" +
            "    \"debug\": \"on\",\n" +
            "    \"window\": {\n" +
            "        \"title\": \"Sample Konfabulator Widget\",\n" +
            "        \"name\": \"main_window\",\n" +
            "        \"width\": 500,\n" +
            "        \"height\": 500\n" +
            "    },\n" +
            "    \"t.dev.image\": { \n" +
            "        \"src\": \"Images/Sun.png\",\n" +
            "        \"name\": \"sun1\",\n" +
            "        \"hOffset\": 250,\n" +
            "        \"vOffset\": 250,\n" +
            "        \"alignment\": \"center\"\n" +
            "    },\n" +
            "    \"t.dev.text\": {\n" +
            "        \"data\": \"Click Here\",\n" +
            "        \"size\": 36,\n" +
            "        \"style\": \"bold\",\n" +
            "        \"name\": \"text1\",\n" +
            "        \"hOffset\": 250,\n" +
            "        \"vOffset\": 100,\n" +
            "        \"alignment\": \"center\",\n" +
            "        \"onMouseUp\": \"sun1.opacity = (sun1.opacity / 100) * 90;\"\n" +
            "    }\n" +
            "}} ";

    public static final String JSON_WITH_NO_LINE_BREAK = "{\"widget\": {" +
            "    \"debug\": \"on\"," +
            "    \"window\": {" +
            "        \"title\": \"Sample Konfabulator Widget\"," +
            "        \"name\": \"main_window\"," +
            "        \"width\": 500," +
            "        \"height\": 500" +
            "    },\n" +
            "    \"t.dev.image\": { " +
            "        \"src\": \"Images/Sun.png\"," +
            "        \"name\": \"sun1\"," +
            "        \"hOffset\": 250," +
            "        \"vOffset\": 250," +
            "        \"alignment\": \"center\"" +
            "    },\n" +
            "    \"t.dev.text\": {" +
            "        \"data\": \"Click Here\"," +
            "        \"size\": 36," +
            "        \"style\": \"bold\"," +
            "        \"name\": \"text1\"," +
            "        \"hOffset\": 250," +
            "        \"vOffset\": 100," +
            "        \"alignment\": \"center\"," +
            "        \"onMouseUp\": \"sun1.opacity = (sun1.opacity / 100) * 90;\"" +
            "    }" +
            "}}    ";

    public static final String SMALL_SON_WITH_NO_LINE_BREAK = "{\"widget\": {" +
            "    \"debug\": \"on\"," +
            "    \"window\": {" +
            "        \"title\": \"Sample Konfabulator Widget\"," +
            "        \"name\": \"main_window\"," +
            "        \"width\": 500," +
            "        \"height\": 500" +
            "    }" +
            "}}    ";

    public static final String XML_DATA = "<root><a>a</a><b>b</b><c>c</c><d>d</d></root>";
}