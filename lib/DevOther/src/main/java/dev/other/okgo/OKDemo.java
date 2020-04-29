package dev.other.okgo;

import com.lzy.okgo.OkGo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * detail: OkGo 使用 Demo
 * @author Ttt
 */
public class OKDemo {

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
    public static void getUserInfo(String userId, OKCallback<UserBean> okCallback) {
        OkGo.<String>post("url")
                .params("userId", userId)
                .execute(okCallback);
    }

    /**
     * 获取用户列表
     * @param okCallback
     */
    public static void getUserList(OKCallback<List<UserBean>> okCallback) {
        OkGo.<String>post("url").tag(OKDemo.class)
                .execute(okCallback);
        // 取消请求
        // OkGo.getInstance().cancelTag(OKDemo.class);
        // 请求管理控制 ( 取消请求 ) 查看 OKUtils#execute 方法
    }

    /**
     * 上传文件
     * @param file
     * @param okCallback
     */
    public static void uploadImage(File file, OKCallback<String> okCallback) {
        OkGo.<String>post("url")
                .upFile(file)
                .execute(okCallback);
    }

    /**
     * 上传多个文件
     * @param files
     * @param okCallback
     */
    public static void uploadImages(List<File> files, OKCallback<List<String>> okCallback) {
        OkGo.<String>post("url")
                .addFileParams("files", files)
                .execute(okCallback);
    }

    // =

    /**
     * demo 使用
     */
    private static void demo() {
        OKDemo.getUserInfo("1", new OKCallback<UserBean>() {
            @Override
            public void onSuccessResponse(OKResponse<UserBean> response) {
                // 请求成功
            }

            @Override
            public void onErrorResponse(OKResponse<UserBean> response) {
                // 请求失败、解析异常等情况
            }
        });

        OKDemo.getUserList(new OKCallback<List<UserBean>>() {
            @Override
            public void onSuccessResponse(OKResponse<List<UserBean>> response) {

            }

            @Override
            public void onErrorResponse(OKResponse<List<UserBean>> response) {

            }
        });

        OKDemo.uploadImage(new File("xxx.jpg"), new OKCallback<String>() {
            @Override
            public void onSuccessResponse(OKResponse<String> response) {

            }

            @Override
            public void onErrorResponse(OKResponse<String> response) {

            }
        });

        List<File> files = new ArrayList<>();
        files.add(new File("xxx.jpg"));
        files.add(new File("xxx.png"));
        OKDemo.uploadImages(files, new OKCallback<List<String>>() {
            @Override
            public void onSuccessResponse(OKResponse<List<String>> response) {

            }

            @Override
            public void onErrorResponse(OKResponse<List<String>> response) {

            }
        });
    }
}
