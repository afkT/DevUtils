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
    // 换行字符串
    public static final String NEW_LINE_STR    = System.getProperty("line.separator");
    // 换行字符串 ( 两行 )
    public static final String NEW_LINE_STR_X2 = NEW_LINE_STR + NEW_LINE_STR;
    // 空对象字符串
    public static final String NULL_STR        = "null";

    // ===============
    // = 日期格式类型 =
    // ===============

    public static final String yyyy             = "yyyy";
    public static final String yyyyMMdd         = "yyyy-MM-dd";
    public static final String yyyyMMdd2        = "yyyyMMdd";
    public static final String yyyyMMdd3        = "yyyy年MM月dd日";
    public static final String yyyyMMddHHmmss   = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmmss_2 = "yyyy年M月d日 HH:mm:ss";
    public static final String yyyyMMdd_HHmmss  = "yyyyMMdd_HHmmss";
    public static final String MMdd             = "MM-dd";
    public static final String MMdd2            = "MM月dd日";
    public static final String MMdd3            = "MMdd";
    public static final String HHmm             = "HH:mm";
    public static final String HHmm2            = "HHmm";
    public static final String HHmmss           = "HH:mm:ss";
    public static final String HHmmss2          = "HHmmss";
    public static final String hhmmMMDDyyyy     = "hh:mm M月d日 yyyy";
    public static final String hhmmssMMDDyyyy   = "hh:mm:ss M月d日 yyyy";

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

    // ===============
    // = 其他常用字段 =
    // ===============

    public static final String ID       = "id";
    public static final String UUID     = "uuid";
    public static final String DATA     = "data";
    public static final String MESSAGE  = "message";
    public static final String CODE     = "code";
    public static final String TITLE    = "title";
    public static final String OBJECT   = "object";
    public static final String PARAMS   = "params";
    public static final String TYPE     = "type";
    public static final String PAGE     = "page";
    public static final String SIZE     = "size";
    public static final String COUNT    = "count";
    public static final String URL      = "url";
    public static final String TIME     = "time";
    public static final String DATE     = "date";
    public static final String KEY      = "key";
    public static final String VALUE    = "value";
    public static final String CLIENT   = "client";
    public static final String SOURCE   = "source";
    public static final String CONFIG   = "config";
    public static final String FLAG     = "flag";
    public static final String RESULT   = "result";
    public static final String SUCCESS  = "success";
    public static final String FAIL     = "fail";
    public static final String METHOD   = "method";
    public static final String TOKEN    = "token";
    public static final String TEXT     = "text";
    public static final String CONTENT  = "content";
    public static final String IMAGE    = "image";
    public static final String VIDEO    = "video";
    public static final String AUDIO    = "audio";
    public static final String LIST     = "list";
    public static final String MAP      = "map";
    public static final String ARRAY    = "array";
    public static final String GET      = "get";
    public static final String SET      = "set";
    public static final String ITEM     = "item";
    public static final String CHECK    = "check";
    public static final String STATE    = "state";
    public static final String HASH     = "hash";
    public static final String HASHCODE = "hashCode";
    public static final String HEIGHT   = "height";
    public static final String WEIGHT   = "weight";
    public static final String CHANNEL  = "channel";
    public static final String VERSION  = "version";
    public static final String REMARK   = "remark";
    public static final String PATH     = "path";
    public static final String FILE     = "file";
    public static final String INTENT   = "intent";
    public static final String ACTIVITY = "activity";
    public static final String SORT     = "sort";
    public static final String TASK     = "task";
    public static final String ACCOUNT  = "account";
    public static final String PASSWORD = "password";
    public static final String NAME     = "name";
    public static final String KEYWORD  = "keyword";
    public static final String LIMIT    = "limit";
    public static final String MAX      = "max";
    public static final String MIN      = "min";
}