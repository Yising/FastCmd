package com.yising.fast.cmd.utils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CMD命令执行工具类
 *
 * @author yising
 */
@Slf4j
public class CmdUtils {
    private static final long TIME_OUT_SECONDS = 5;

    /**
     * 执行一个cmd命令
     *
     * @param cmdCommand    cmd命令
     * @param isOnNewWindow 是否在新窗口中打开
     * @return 命令执行结果字符串，如出现异常返回null
     */
    public static String execute(String cmdCommand, boolean isOnNewWindow) {
        if (StringUtils.isEmpty(cmdCommand)) {
            return "cmd is empty";
        }
        log.info("cmd is:" + cmdCommand);
        if (isOnNewWindow) {
            return executeOnNewWindow("\"" + cmdCommand + "\"");
        }

        // 新起一个线程执行，避免readLine方法阻塞
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<String> future = service.submit(() -> executeWithoutWindow(cmdCommand));
        try {
            String s = future.get(TIME_OUT_SECONDS, TimeUnit.SECONDS);
            return s;
        } catch (Exception e){
            log.warn("time out, cmd is: {}", cmdCommand);
            future.cancel(true);
        }
        return "time out, please execute on new window.";
    }

    private static String executeWithoutWindow(String cmdCommand) {
        StringBuilder stringBuilder = new StringBuilder();
        Process process;
        InputStreamReader inputStreamReader;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            inputStreamReader = new InputStreamReader(process.getInputStream(), "GBK");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            inputStreamReader.close();
            bufferedReader.close();
            process.destroy();
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return StringUtils.empty();
        }
    }

    /**
     * 在新窗口中执行一个cmd命令
     *
     * @param cmdCommand cmd命令
     * @return 命令执行结果字符串，如出现异常返回null
     */
    private static String executeOnNewWindow(@NonNull String cmdCommand) {
        try {
            Runtime.getRuntime().exec("cmd /k start cmd.exe /k " + cmdCommand);
            return "已打开新窗口";
        } catch (Exception e) {
            e.printStackTrace();
            return StringUtils.empty();
        }
    }

    /**
     * 执行bat文件，
     *
     * @param filePath      bat文件路径
     * @param isCloseWindow 执行完毕后是否关闭cmd窗口
     * @return bat文件输出log
     */
    public static String executeFile(String filePath, boolean isCloseWindow) {
        String cmdCommand = null;
        if (isCloseWindow) {
            cmdCommand = "cmd.exe /c " + filePath;
        } else {
            cmdCommand = "cmd.exe /k " + filePath;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 测试是否字符串中是否包含占位符{param}
     *
     * @param str 字符串
     * @return true 包含占位符，false 不包含占位符
     */
    public static boolean containersPlaceholder(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\{\\w+\\}");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    @NonNull
    public static String replacePlaceholder(String cmdStr, Map<String, String> params) {
        if (cmdStr == null) {
            return "";
        }
        if (params == null || !containersPlaceholder(cmdStr)) {
            return cmdStr;
        }
        Pattern pattern = Pattern.compile("\\{\\w+\\}");
        Matcher matcher = pattern.matcher(cmdStr);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String value = params.get(matcher.group(0).substring(1, matcher.group(0).length() - 1));
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            matcher.appendReplacement(sb, params.get(matcher.group(0).substring(1, matcher.group(0).length() - 1)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    @NonNull
    public static List<String> getParamList(String str) {
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        List<String> paramList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\{\\w+\\}");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            paramList.add(matcher.group().substring(1, matcher.group().length() - 1));
        }
        return paramList;
    }
}
