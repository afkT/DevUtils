package com.dev;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import dev.utils.app.logger.DevLogger;
import dev.utils.app.permission.PermissionUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 申请权限
        PermissionUtils.permission(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).callBack(new PermissionUtils.PermissionCallBack() {

            /**
             * 授权通过权限回调
             */
            @Override
            public void onGranted() {
                DevLogger.d("permission granted");
            }

            /**
             * 授权未通过权限回调
             * @param grantedList  申请通过的权限
             * @param deniedList   申请未通过的权限
             * @param notFoundList 查询不到的权限 ( 包含未注册 )
             */
            @Override
            public void onDenied(List<String> grantedList, List<String> deniedList, List<String> notFoundList) {
                StringBuilder builder = new StringBuilder();
                builder.append("permission");
                builder.append("\ngrantedList: " + Arrays.toString(grantedList.toArray()));
                builder.append("\ndeniedList: " + Arrays.toString(deniedList.toArray()));
                builder.append("\nnotFoundList: " + Arrays.toString(notFoundList.toArray()));
                DevLogger.d(builder.toString());
            }
        }).request(this);
    }
}
