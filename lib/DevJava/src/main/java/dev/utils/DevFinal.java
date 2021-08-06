package dev.utils;

/**
 * detail: 常量类
 * @author Ttt
 */
public final class DevFinal {

    private DevFinal() {
    }

    // 空格 字符串
    public static final String SPACE_STR       = " ";
    // TAB 字符串
    public static final String TAB_STR         = "\t";
    // 回车 ( CR ) 字符串
    public static final String CR_STR          = "\r";
    // 点 字符串
    public static final String DOT_STR         = ".";
    // 横杠 字符串
    public static final String DASH_STR        = "-";
    // 反斜杠 字符串
    public static final String BACKSLASH_STR   = "\\";
    // 斜杠 字符串
    public static final String SLASH_STR       = "/";
    // 换行字符串
    public static final String NEW_LINE_STR    = System.getProperty("line.separator");
    // 换行字符串 ( 两行 )
    public static final String NEW_LINE_STR_X2 = NEW_LINE_STR + NEW_LINE_STR;
    // 换行字符串 ( 四行 )
    public static final String NEW_LINE_STR_X4 = NEW_LINE_STR_X2 + NEW_LINE_STR_X2;
    // 空对象字符串
    public static final String NULL_STR        = "null";

    // 编码格式
    public static final String US_ASCII   = "US-ASCII";
    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String UTF_8      = "UTF-8";
    public static final String UTF_16BE   = "UTF-16BE";
    public static final String UTF_16LE   = "UTF-16LE";
    public static final String UTF_16     = "UTF-16";
    public static final String GBK        = "GBK";
    public static final String GBK_2312   = "GBK-2312";

    // =============
    // = 时间格式类型 =
    // =============

    public static final String yyyy            = "yyyy";
    public static final String yyMMdd          = "yy-MM-dd";
    public static final String yyMMdd2         = "yyMMdd";
    public static final String yyyyMMdd        = "yyyy-MM-dd";
    public static final String yyyyMMdd2       = "yyyyMMdd";
    public static final String yyyyMMdd3       = "yyyy年MM月dd日";
    public static final String yyyyMMdd4       = "yyyy_MM_dd";
    public static final String yyyyMMdd5       = "yyyy.MM.dd";
    public static final String yyyyMMddHHmmss  = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmmss2 = "yyyy年M月d日 HH:mm:ss";
    public static final String yyyyMMddHHmmss3 = "yyyyMMdd_HHmmss";
    public static final String yyyyMMddHHmmss4 = "yyyyMMdd.HHmmss";
    public static final String MMdd            = "MM-dd";
    public static final String MMdd2           = "MM月dd日";
    public static final String MMdd3           = "MMdd";
    public static final String yy              = "yy";
    public static final String MM              = "MM";
    public static final String dd              = "dd";
    public static final String hh              = "hh";
    public static final String HH              = "HH";
    public static final String mm              = "mm";
    public static final String HHmm            = "HH:mm";
    public static final String HHmm2           = "HHmm";
    public static final String HHmmss          = "HH:mm:ss";
    public static final String HHmmss2         = "HHmmss";
    public static final String hhmmMMDDyyyy    = "hh:mm M月d日 yyyy";
    public static final String hhmmssMMDDyyyy  = "hh:mm:ss M月d日 yyyy";
    public static final String mmddHHmmyyyyss  = "MMddHHmmyyyy.ss";

    // 一分钟 60 秒
    public static final int  MINUTE_S = 60;
    // 一小时 60 * 60 秒
    public static final int  HOUR_S   = 3600;
    // 一天 24 * 60 * 60 秒
    public static final int  DAY_S    = 86400;
    // 秒与毫秒的倍数
    public static final long SECOND   = 1000;
    // 分与毫秒的倍数
    public static final long MINUTE   = SECOND * 60;
    // 时与毫秒的倍数
    public static final long HOUR     = MINUTE * 60;
    // 天与毫秒的倍数
    public static final long DAY      = HOUR * 24;
    // 周与毫秒的倍数
    public static final long WEEK     = DAY * 7;
    // 月与毫秒的倍数
    public static final long MONTH    = DAY * 30;
    // 年与毫秒的倍数
    public static final long YEAR     = DAY * 365;

    // ==========
    // = 常量数组 =
    // ==========

    // 用于建立十六进制字符的输出的小写字符数组
    public static final char[] HEX_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    // 用于建立十六进制字符的输出的大写字符数组
    public static final char[] HEX_DIGITS_UPPER = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    // 0123456789
    public static final char[] NUMBERS = {
            48, 49, 50, 51, 52, 53, 54, 55, 56, 57
    };

    // abcdefghijklmnopqrstuvwxyz
    public static final char[] LOWER_CASE_LETTERS = {
            97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109,
            110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122
    };

    // ABCDEFGHIJKLMNOPQRSTUVWXYZ
    public static final char[] CAPITAL_LETTERS = {
            65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
            81, 82, 83, 84, 85, 86, 87, 88, 89, 90
    };

    // abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
    public static final char[] LETTERS = {
            97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110,
            111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 65, 66,
            67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83,
            84, 85, 86, 87, 88, 89, 90
    };

    // 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
    public static final char[] NUMBERS_AND_LETTERS = {
            48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102,
            103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115,
            116, 117, 118, 119, 120, 121, 122, 65, 66, 67, 68, 69, 70, 71, 72,
            73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90
    };

    // 生肖数组
    public static final String[] ZODIAC = {
            "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"
    };

    // 星座对应日期
    public static final String[] CONSTELLATION_DATE = {
            "01.20-02.18", "02.19-03.20", "03.21-04.19", "04.20-05.20", "05.21-06.21", "06.22-07.22",
            "07.23-08.22", "08.23-09.22", "09.23-10.23", "10.24-11.22", "11.23-12.21", "12.22-01.19"
    };

    // 星座数组
    public static final String[] CONSTELLATION = {
            "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
            "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"
    };

    // ==================
    // = ValidatorUtils =
    // ==================

    // 正则表达式: 空格
    public static final String REGEX_SPACE = "\\s";

    // 正则表达式: 验证数字
    public static final String REGEX_NUMBER = "^[0-9]*$";

    // 正则表达式: 验证数字或包含小数点
    public static final String REGEX_NUMBER_OR_DECIMAL = "^[0-9]*[.]?[0-9]*$";

    // 正则表达式: 验证是否包含数字
    public static final String REGEX_CONTAIN_NUMBER = ".*\\d+.*";

    // 正则表达式: 验证是否数字或者字母
    public static final String REGEX_NUMBER_OR_LETTER = "^[A-Za-z0-9]+$";

    // 正则表达式: 验证是否全是字母
    public static final String REGEX_LETTER = "^[A-Za-z]+$";

    // 正则表达式: 不能输入特殊字符 ^[\u4E00-\u9FA5A-Za-z0-9]+$ 或 ^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$
    public static final String REGEX_SPECIAL = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$";

    // 正则表达式: 验证微信号 ^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$
    public static final String REGEX_WX = "^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$";

    // 正则表达式: 验证真实姓名 ^[\u4e00-\u9fa5]+(·[\u4e00-\u9fa5]+)*$
    public static final String REGEX_REALNAME = "^[\\u4e00-\\u9fa5]+(•[\\u4e00-\\u9fa5]*)*$|^[\\u4e00-\\u9fa5]+(·[\\u4e00-\\u9fa5]*)*$";

    // 正则表达式: 验证昵称
    public static final String REGEX_NICKNAME = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$";

    // 正则表达式: 验证用户名 ( 不包含中文和特殊字符 ) 如果用户名使用手机号码或邮箱 则结合手机号验证和邮箱验证
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    // 正则表达式: 验证密码 ( 不包含特殊字符 )
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,18}$";

    // 正则表达式: 验证邮箱
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    // 正则表达式: 验证 URL
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?";

    // 正则表达式: 验证 IP 地址
    public static final String REGEX_IP_ADDRESS = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";

    // 正则表达式: 验证汉字
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]+$";

    // 正则表达式: 验证汉字 ( 含双角符号 )
    public static final String REGEX_CHINESE_ALL = "^[\u0391-\uFFE5]+$";

    // 正则表达式: 验证汉字 ( 含双角符号 )
    public static final String REGEX_CHINESE_ALL2 = "[\u0391-\uFFE5]";

    // ==========
    // = Format =
    // ==========

    public static final String FORMAT_2S         = "%s %s";
    public static final String FORMAT_2S_LINE    = "%s_%s";
    public static final String FORMAT_2S_THROUGH = "%s-%s";
    public static final String FORMAT_2S_COMMA   = "%s, %s";
    public static final String FORMAT_2S_STOP    = "%s、%s";
    public static final String FORMAT_2S_SPACE   = " %s %s ";

    public static final String FORMAT_3S         = "%s %s %s";
    public static final String FORMAT_3S_LINE    = "%s_%s_%s";
    public static final String FORMAT_3S_THROUGH = "%s-%s-%s";
    public static final String FORMAT_3S_COMMA   = "%s, %s, %s";
    public static final String FORMAT_3S_STOP    = "%s、%s、%s";
    public static final String FORMAT_3S_SPACE   = " %s %s %s ";

    public static final String FORMAT_4S         = "%s %s %s %s";
    public static final String FORMAT_4S_LINE    = "%s_%s_%s_%s";
    public static final String FORMAT_4S_THROUGH = "%s-%s-%s-%s";
    public static final String FORMAT_4S_COMMA   = "%s, %s, %s, %s";
    public static final String FORMAT_4S_STOP    = "%s、%s、%s、%s";
    public static final String FORMAT_4S_SPACE   = " %s %s %s %s ";

    public static final String FORMAT_BRACE       = "{ %s }";
    public static final String FORMAT_BRACE_SPACE = " { %s } ";

    public static final String FORMAT_BRACKET       = "[ %s ]";
    public static final String FORMAT_BRACKET_SPACE = " [ %s ] ";

    public static final String FORMAT_PARENTHESES       = "( %s )";
    public static final String FORMAT_PARENTHESES_SPACE = " ( %s ) ";

    // =============
    // = 其他常用字段 =
    // =============

    public static final String ABOUT        = "about";
    public static final String ACCESS       = "access";
    public static final String ACCOUNT      = "account";
    public static final String ACTION       = "action";
    public static final String ACTIVITY     = "activity";
    public static final String ADAPTER      = "adapter";
    public static final String ADD          = "add";
    public static final String ADDRESS      = "address";
    public static final String AFTER        = "after";
    public static final String AGENT        = "agent";
    public static final String ALIAS        = "alias";
    public static final String ANIMATION    = "animation";
    public static final String APPEND       = "append";
    public static final String APPLICATION  = "application";
    public static final String AREA         = "area";
    public static final String ARGS         = "args";
    public static final String ARRAY        = "array";
    public static final String ASSETS       = "assets";
    public static final String ASSIST       = "assist";
    public static final String ASYNC        = "async";
    public static final String AUDIO        = "audio";
    public static final String BANK         = "bank";
    public static final String BASE         = "base";
    public static final String BACKGROUND   = "background";
    public static final String BEAN         = "bean";
    public static final String BEFORE       = "before";
    public static final String BEGIN_TIME   = "begin_time";
    public static final String BINDING      = "binding";
    public static final String BLANK        = "blank";
    public static final String BODY         = "body";
    public static final String BOLD         = "bold";
    public static final String BOTTOM       = "bottom";
    public static final String BROADCAST    = "broadcast";
    public static final String BROWSER      = "browser";
    public static final String BUG          = "bug";
    public static final String BUNDLE       = "bundle";
    public static final String CACHE        = "cache";
    public static final String CALENDAR     = "calendar";
    public static final String CAMERA       = "camera";
    public static final String CAPTURE      = "capture";
    public static final String CATCH        = "catch";
    public static final String CATEGORY     = "category";
    public static final String CENTER       = "center";
    public static final String CHANNEL      = "channel";
    public static final String CHARSET      = "charset";
    public static final String CHECK        = "check";
    public static final String CHECKBOX     = "checkbox";
    public static final String CHILD        = "child";
    public static final String CITY         = "city";
    public static final String CLIENT       = "client";
    public static final String CLOSE        = "close";
    public static final String CMD          = "cmd";
    public static final String CODE         = "code";
    public static final String COLOR        = "color";
    public static final String COMPILE      = "compile";
    public static final String CONFIG       = "config";
    public static final String CONTENT      = "content";
    public static final String CONTROL      = "control";
    public static final String CONVERT      = "convert";
    public static final String COUNT        = "count";
    public static final String CRASH        = "crash";
    public static final String CURRENT      = "current";
    public static final String CURSOR       = "cursor";
    public static final String CUSTOM       = "custom";
    public static final String CYCLE        = "cycle";
    public static final String DATA         = "data";
    public static final String DATABASE     = "database";
    public static final String DATE         = "date";
    public static final String DB           = "db";
    public static final String DEBUG        = "debug";
    public static final String DECRYPT      = "decrypt";
    public static final String DEFAULT      = "default";
    public static final String DELAY        = "delay";
    public static final String DENIED       = "denied";
    public static final String DIALOG       = "dialog";
    public static final String DISK         = "disk";
    public static final String DOWNLOAD     = "download";
    public static final String DURATION     = "duration";
    public static final String ELEMENT      = "element";
    public static final String EMAIL        = "email";
    public static final String ENCRYPT      = "encrypt";
    public static final String END          = "end";
    public static final String END_TIME     = "end_time";
    public static final String ENGINE       = "engine";
    public static final String ENVIRONMENT  = "environment";
    public static final String ERROR        = "error";
    public static final String EVENT        = "event";
    public static final String EXCEPTION    = "exception";
    public static final String EXTRA        = "extra";
    public static final String FAIL         = "fail";
    public static final String FILE         = "file";
    public static final String FLAG         = "flag";
    public static final String FOLD         = "fold";
    public static final String FOOTER       = "footer";
    public static final String FRAGMENT     = "fragment";
    public static final String FROM         = "from";
    public static final String GIF          = "gif";
    public static final String GLOBAL       = "global";
    public static final String GRADIENT     = "gradient";
    public static final String GRANTED      = "granted";
    public static final String GROUP        = "group";
    public static final String HANDLER      = "handler";
    public static final String HASH         = "hash";
    public static final String HEADER       = "header";
    public static final String HEIGHT       = "height";
    public static final String HIGH         = "high";
    public static final String HOLDER       = "holder";
    public static final String HOME         = "home";
    public static final String HORIZONTAL   = "horizontal";
    public static final String ID           = "id";
    public static final String IDENTITY     = "identity";
    public static final String IMAGE        = "image";
    public static final String IMAGES       = "images";
    public static final String INDENT       = "indent";
    public static final String INDEX        = "index";
    public static final String INFLATER     = "inflater";
    public static final String INFO         = "info";
    public static final String INPUT        = "input";
    public static final String INSTANCE     = "instance";
    public static final String INTENT       = "intent";
    public static final String ITEM         = "item";
    public static final String JSON         = "json";
    public static final String KEY          = "key";
    public static final String KEYWORD      = "keyword";
    public static final String KIND         = "kind";
    public static final String LATITUDE     = "latitude";
    public static final String LAUNCHER     = "launcher";
    public static final String LAYOUT       = "layout";
    public static final String LEFT         = "left";
    public static final String LEVEL        = "level";
    public static final String LIFECYCLE    = "lifecycle";
    public static final String LIMIT        = "limit";
    public static final String LINK         = "link";
    public static final String LISTENER     = "listener";
    public static final String LIVE_DATA    = "live_data";
    public static final String LOAD         = "load";
    public static final String LOADING      = "loading";
    public static final String LOG          = "log";
    public static final String LONGITUDE    = "longitude";
    public static final String LOOP         = "loop";
    public static final String LOW          = "low";
    public static final String MAIN         = "main";
    public static final String MATCH        = "match";
    public static final String MAX          = "max";
    public static final String MEDIA        = "media";
    public static final String MEDIA_TYPE   = "media_type";
    public static final String MEMORY       = "memory";
    public static final String MENU         = "menu";
    public static final String MESSAGE      = "message";
    public static final String MIN          = "min";
    public static final String MISSING      = "missing";
    public static final String MOBILE       = "mobile";
    public static final String MODEL        = "model";
    public static final String MODULE       = "module";
    public static final String NAME         = "name";
    public static final String NONE         = "none";
    public static final String OPERATE      = "operate";
    public static final String OPTIONS      = "options";
    public static final String OUTPUT       = "output";
    public static final String PACKNAME     = "packname";
    public static final String PAGE         = "page";
    public static final String PARENT       = "parent";
    public static final String PARSER       = "parser";
    public static final String PASSWORD     = "password";
    public static final String PATH         = "path";
    public static final String PAUSE        = "pause";
    public static final String PERIOD       = "period";
    public static final String PERMISSION   = "permission";
    public static final String PHONE        = "phone";
    public static final String PLATFORM     = "platform";
    public static final String PLAY         = "play";
    public static final String PLAY_TIME    = "play_time";
    public static final String POSITION     = "position";
    public static final String PREFIX       = "prefix";
    public static final String PRINT        = "print";
    public static final String PROGRESS     = "progress";
    public static final String PROVINCE     = "province";
    public static final String PUT          = "put";
    public static final String QUERY        = "query";
    public static final String RAW          = "raw";
    public static final String READER       = "reader";
    public static final String RECEIVE      = "receive";
    public static final String REGION       = "region";
    public static final String RELEASE      = "release";
    public static final String REMARK       = "remark";
    public static final String REMOVE       = "remove";
    public static final String REPORT       = "report";
    public static final String REQUEST      = "request";
    public static final String RESET        = "reset";
    public static final String RESPONSE     = "response";
    public static final String RESTART      = "restart";
    public static final String RESULT       = "result";
    public static final String RICH_TEXT    = "rich_text";
    public static final String RIGHT        = "right";
    public static final String ROUTER       = "router";
    public static final String SCALE        = "scale";
    public static final String SCORE        = "score";
    public static final String SCREEN       = "screen";
    public static final String SELECTED     = "selected";
    public static final String SERVICE      = "service";
    public static final String SETTING      = "setting";
    public static final String SHAPE        = "shape";
    public static final String SHARE        = "share";
    public static final String SIZE         = "size";
    public static final String SOILD        = "soild";
    public static final String SORT         = "sort";
    public static final String SOURCE       = "source";
    public static final String SPEC         = "spec";
    public static final String STANDARD     = "standard";
    public static final String START        = "start";
    public static final String STATE        = "state";
    public static final String STOP         = "stop";
    public static final String STROKE       = "stroke";
    public static final String SUB          = "sub";
    public static final String SUCCESS      = "success";
    public static final String SUFFIX       = "suffix";
    public static final String SYNC         = "sync";
    public static final String TAB          = "tab";
    public static final String TAG          = "tag";
    public static final String TAKE         = "take";
    public static final String TARGET       = "target";
    public static final String TASK         = "task";
    public static final String TEMP         = "temp";
    public static final String TEXT         = "text";
    public static final String THROWABLE    = "throwable";
    public static final String TIME         = "time";
    public static final String TIMESTAMP    = "timestamp";
    public static final String TIMING       = "timing";
    public static final String TITLE        = "title";
    public static final String TO           = "to";
    public static final String TOAST        = "toast";
    public static final String TOKEN        = "token";
    public static final String TOP          = "top";
    public static final String TRACK        = "track";
    public static final String TRANSFORM    = "transform";
    public static final String TRY          = "try";
    public static final String TXT          = "txt";
    public static final String UN_BINDER    = "un_binder";
    public static final String UNIQUE       = "unique";
    public static final String UNKNOWN      = "unknown";
    public static final String UPLOAD       = "upload";
    public static final String URI          = "uri";
    public static final String URL          = "url";
    public static final String USER         = "user";
    public static final String USER_ID      = "user_id";
    public static final String UUID         = "uuid";
    public static final String VALID        = "valid";
    public static final String VALID_TIME   = "valid_time";
    public static final String VALUE        = "value";
    public static final String VERSION      = "version";
    public static final String VERSION_CODE = "version_code";
    public static final String VERSION_NAME = "version_name";
    public static final String VERTICAL     = "vertical";
    public static final String VIBRATE      = "vibrate";
    public static final String VIDEO        = "video";
    public static final String VIEW         = "view";
    public static final String VIEW_MODEL   = "view_model";
    public static final String VO           = "vo";
    public static final String WAITING      = "waiting";
    public static final String WEIGHT       = "weight";
    public static final String WIDGET       = "widget";
    public static final String WRAPPER      = "wrapper";
    public static final String WRITER       = "writer";
    public static final String X            = "x";
    public static final String XML          = "xml";
    public static final String Y            = "y";

    public static final String DAY_STR          = "day_str";
    public static final String HOUR_STR         = "hour_str";
    public static final String MILLI_SECOND_STR = "milli_second_str";
    public static final String MINUTE_STR       = "minute_str";
    public static final String MONTH_STR        = "month_str";
    public static final String SECOND_STR       = "second_str";
    public static final String WEEK_STR         = "week_str";
    public static final String YEAR_STR         = "year_str";

    public static final String BOOLEAN = "boolean";
    public static final String BYTE    = "byte";
    public static final String CHAR    = "char";
    public static final String DOUBLE  = "double";
    public static final String FALSE   = "false";
    public static final String FLOAT   = "float";
    public static final String INT     = "int";
    public static final String INTEGER = "integer";
    public static final String LIST    = "list";
    public static final String LONG    = "long";
    public static final String MAP     = "map";
    public static final String STRING  = "string";
    public static final String TRUE    = "true";
    public static final String TYPE    = "type";

    public static final String BUFFER    = "buffer";
    public static final String BUILD     = "build";
    public static final String BUILDER   = "builder";
    public static final String CLASS     = "class";
    public static final String CONST     = "const";
    public static final String ENUM      = "enum";
    public static final String FIELD     = "field";
    public static final String FINAL     = "final";
    public static final String FOR       = "for";
    public static final String FUNCTION  = "function";
    public static final String GET       = "get";
    public static final String INTERFACE = "interface";
    public static final String METHOD    = "method";
    public static final String NEW       = "new";
    public static final String NULL      = "null";
    public static final String OBJECT    = "object";
    public static final String PARAM     = "param";
    public static final String PARAMS    = "params";
    public static final String PRIVATE   = "private";
    public static final String PROTECTED = "protected";
    public static final String PUBLIC    = "public";
    public static final String RETURN    = "return";
    public static final String SET       = "set";
    public static final String STATIC    = "static";
    public static final String VAL       = "val";
    public static final String VAR       = "var";
    public static final String VOID      = "void";
    public static final String WHILE     = "while";

    public static final String ANDROID      = "android";
    public static final String H5           = "h5";
    public static final String IOS          = "ios";
    public static final String MIN_IPROGRAM = "min_iprogram";
    public static final String WEB          = "web";
}