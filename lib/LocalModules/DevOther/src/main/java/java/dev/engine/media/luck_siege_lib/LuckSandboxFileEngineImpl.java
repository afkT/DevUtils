package java.dev.engine.media.luck_siege_lib;

import android.content.Context;

import com.luck.picture.lib.engine.UriToFileTransformEngine;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.utils.SandboxTransformUtils;

/**
 * detail: PictureSelector 相册沙盒目录拷贝引擎
 * @author luck
 * <pre>
 *     @see <a href="https://github.com/LuckSiege/PictureSelector/wiki"/>
 * </pre>
 */
public class LuckSandboxFileEngineImpl
        implements UriToFileTransformEngine {

    // ==========
    // = 构造函数 =
    // ==========

    private LuckSandboxFileEngineImpl() {
    }

    private static final class Holder {
        static final LuckSandboxFileEngineImpl instance = new LuckSandboxFileEngineImpl();
    }

    public static LuckSandboxFileEngineImpl createEngine() {
        return Holder.instance;
    }

    // ============================
    // = UriToFileTransformEngine =
    // ============================

    @Override
    public void onUriToFileAsyncTransform(
            Context context,
            String srcPath,
            String mineType,
            OnKeyValueResultCallbackListener call
    ) {
        if (call != null) {
            String sandboxPath = SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType);
            call.onCallback(srcPath, sandboxPath);
        }
    }
}