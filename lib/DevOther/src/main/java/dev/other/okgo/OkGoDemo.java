package dev.other.okgo;

import com.lzy.okgo.OkGo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * detail: OkGo 使用 Demo
 * @author Ttt
 */
public class OkGoDemo {

    public class UserBean {

        public String userId;
        public String userName;
        public int    age;
    }

    /**
     * 获取用户信息
     * @param userId
     * @param okCallback
     */
    public static void getUserInfo(String userId, OkGoCallback<UserBean> okCallback) {
        OkGo.<String>post("url")
                .params("userId", userId)
                .execute(okCallback);
    }

    /**
     * 获取用户列表
     * @param okCallback
     */
    public static void getUserList(OkGoCallback<List<UserBean>> okCallback) {
        OkGo.<String>post("url").tag(OkGoDemo.class)
                .execute(okCallback);
        // 取消请求
        // OkGo.getInstance().cancelTag(OKDemo.class);
        // 请求管理控制 ( 取消请求 ) 查看 OkGoUtils#execute 方法
    }

    /**
     * 上传文件
     * @param file
     * @param okCallback
     */
    public static void uploadImage(File file, OkGoCallback<String> okCallback) {
        OkGo.<String>post("url")
                .upFile(file)
                .execute(okCallback);
    }

    /**
     * 上传多个文件
     * @param files
     * @param okCallback
     */
    public static void uploadImages(List<File> files, OkGoCallback<List<String>> okCallback) {
        OkGo.<String>post("url")
                .addFileParams("files", files)
                .execute(okCallback);
    }

    // =

    /**
     * demo 使用
     */
    private static void demo() {
        OkGoDemo.getUserInfo("1", new OkGoCallback<UserBean>() {
            @Override
            public void onSuccessResponse(OkGoResponse<UserBean> response) {
                // 请求成功
            }

            @Override
            public void onErrorResponse(OkGoResponse<UserBean> response) {
                // 请求失败、解析异常等情况
            }
        });

        OkGoDemo.getUserList(new OkGoCallback<List<UserBean>>() {
            @Override
            public void onSuccessResponse(OkGoResponse<List<UserBean>> response) {

            }

            @Override
            public void onErrorResponse(OkGoResponse<List<UserBean>> response) {

            }
        });

        OkGoDemo.uploadImage(new File("xxx.jpg"), new OkGoCallback<String>() {
            @Override
            public void onSuccessResponse(OkGoResponse<String> response) {

            }

            @Override
            public void onErrorResponse(OkGoResponse<String> response) {

            }
        });

        List<File> files = new ArrayList<>();
        files.add(new File("xxx.jpg"));
        files.add(new File("xxx.png"));
        OkGoDemo.uploadImages(files, new OkGoCallback<List<String>>() {
            @Override
            public void onSuccessResponse(OkGoResponse<List<String>> response) {

            }

            @Override
            public void onErrorResponse(OkGoResponse<List<String>> response) {

            }
        });
    }
}
