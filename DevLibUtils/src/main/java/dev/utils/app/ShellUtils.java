package dev.utils.app;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.List;

import dev.utils.LogPrintUtils;

/**
 * detail: Shell 相关工具类
 * @author Ttt
 */
public final class ShellUtils {

    private ShellUtils() {
    }

    // 日志 TAG
    private static final String TAG = ShellUtils.class.getSimpleName();
    // 操作成功
    public static final int SUCCESS = 0;
    // 换行符
    private static final String LINE_SEP = System.getProperty("line.separator");

    /**
     * 是否是在 root 下执行命令
     * @param command 命令
     * @param isRoot  是否需要 root 权限执行
     * @return
     */
    public static CommandResult execCmd(final String command, final boolean isRoot) {
        return execCmd(new String[]{command}, isRoot, true);
    }

    /**
     * 是否是在 root 下执行命令
     * @param commands 多条命令链表
     * @param isRoot   是否需要 root 权限执行
     * @return
     */
    public static CommandResult execCmd(final List<String> commands, final boolean isRoot) {
        return execCmd(commands == null ? null : commands.toArray(new String[]{}), isRoot, true);
    }

    /**
     * 是否是在 root 下执行命令
     * @param commands 多条命令数组
     * @param isRoot   是否需要 root 权限执行
     * @return
     */
    public static CommandResult execCmd(final String[] commands, final boolean isRoot) {
        return execCmd(commands, isRoot, true);
    }

    /**
     * 是否是在 root 下执行命令
     * @param command         命令
     * @param isRoot          是否需要 root 权限执行
     * @param isNeedResultMsg 是否需要结果消息
     * @return
     */
    public static CommandResult execCmd(final String command, final boolean isRoot, final boolean isNeedResultMsg) {
        return execCmd(new String[]{command}, isRoot, isNeedResultMsg);
    }

    /**
     * 是否是在 root 下执行命令
     * @param commands        命令链表
     * @param isRoot          是否需要 root 权限执行
     * @param isNeedResultMsg 是否需要结果消息
     * @return
     */
    public static CommandResult execCmd(final List<String> commands, final boolean isRoot, final boolean isNeedResultMsg) {
        return execCmd(commands == null ? null : commands.toArray(new String[]{}), isRoot, isNeedResultMsg);
    }

    /**
     * 是否是在 root 下执行命令
     * @param commands        命令数组
     * @param isRoot          是否需要 root 权限执行
     * @param isNeedResultMsg 是否需要结果消息
     * @return
     */
    public static CommandResult execCmd(final String[] commands, final boolean isRoot, final boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? "su" : "sh");
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) continue;
                os.write(command.getBytes());
                os.writeBytes(LINE_SEP);
                os.flush();
            }
            os.writeBytes("exit" + LINE_SEP);
            os.flush();
            result = process.waitFor();
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
                String line;
                if ((line = successResult.readLine()) != null) {
                    successMsg.append(line);
                    while ((line = successResult.readLine()) != null) {
                        successMsg.append(LINE_SEP).append(line);
                    }
                }
                if ((line = errorResult.readLine()) != null) {
                    errorMsg.append(line);
                    while ((line = errorResult.readLine()) != null) {
                        errorMsg.append(LINE_SEP).append(line);
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "execCmd");
        } finally {
            try {
                os.close();
                successResult.close();
                errorResult.close();
            } catch (Exception e) {
            }
            if (process != null) {
                process.destroy();
            }
        }
        return new CommandResult(
                result,
                successMsg == null ? null : successMsg.toString(),
                errorMsg == null ? null : errorMsg.toString());
    }

    /**
     * detail: 返回的命令结果
     * @author Ttt
     */
    public static class CommandResult {

        // 结果码
        public int result;
        // 成功信息
        public String successMsg;
        // 错误信息
        public String errorMsg;

        public CommandResult(final int result, final String successMsg, final String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }

        /**
         * 判断是否执行成功
         * @return
         */
        public boolean isSuccess() {
            return result == SUCCESS;
        }

        /**
         * 判断是否执行成功(判断 errorMsg)
         * @return
         */
        public boolean isSuccess2() {
            if (result == SUCCESS && (errorMsg == null || errorMsg.length() == 0)) {
                return true;
            }
            return false;
        }

        /**
         * 判断是否执行成功(判断 successMsg)
         * @return
         */
        public boolean isSuccess3() {
            if (result == SUCCESS && successMsg != null && successMsg.length() != 0) {
                return true;
            }
            return false;
        }

        /**
         * 判断是否执行成功(判断 successMsg) , 并且 successMsg 是否包含某个字符串
         * @param contains
         * @return
         */
        public boolean isSuccess4(final String contains) {
            if (result == SUCCESS && successMsg != null && successMsg.length() != 0) {
                if (contains != null && contains.length() != 0 && successMsg.toLowerCase().contains(contains)) {
                    return true;
                }
            }
            return false;
        }
    }
}
