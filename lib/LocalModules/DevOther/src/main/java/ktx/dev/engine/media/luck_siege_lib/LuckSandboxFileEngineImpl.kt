package ktx.dev.engine.media.luck_siege_lib

import android.content.Context
import com.luck.picture.lib.engine.UriToFileTransformEngine
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
import com.luck.picture.lib.utils.SandboxTransformUtils

/**
 * detail: PictureSelector 相册沙盒目录拷贝引擎
 * @author luck
 */
class LuckSandboxFileEngineImpl private constructor() : UriToFileTransformEngine {

    private object Holder {
        val instance = LuckSandboxFileEngineImpl()
    }

    companion object {
        fun createEngine(): LuckSandboxFileEngineImpl {
            return Holder.instance
        }
    }

    // ============================
    // = UriToFileTransformEngine =
    // ============================

    override fun onUriToFileAsyncTransform(
        context: Context?,
        srcPath: String?,
        mineType: String?,
        call: OnKeyValueResultCallbackListener?
    ) {
        call?.apply {
            val sandboxPath = SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType)
            onCallback(srcPath, sandboxPath)
        }
    }
}