package afkt.project.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.ui.adapter.KeyValueAdapter;
import butterknife.BindView;
import dev.utils.app.CPUUtils;
import dev.utils.app.DeviceUtils;
import dev.utils.app.MemoryUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.SDCardUtils;
import dev.utils.app.ScreenUtils;
import dev.utils.app.helper.ViewHelper;
import dev.utils.app.info.KeyValueBean;

/**
 * detail: 设备信息
 * @author Ttt
 */
public class DeviceInfoActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup parent = (ViewGroup) vid_bvr_recy.getParent();
        // 根布局处理
        ViewHelper.get().setPadding(parent, 0)
                .setBackgroundColor(parent, ResourceUtils.getColor(R.color.color_33));
    }

    @Override
    public void initValues() {
        super.initValues();

        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(new KeyValueAdapter(getDeviceInfoLists()));
    }

    /**
     * 获取设备信息集合
     * @return 设备信息集合
     */
    private List<KeyValueBean> getDeviceInfoLists() {
        List<KeyValueBean> lists = new ArrayList<>();
        // 设备信息
        HashMap<String, String> mapDeviceInfos = new HashMap<>();
        // 进行初始化获取
        DeviceUtils.getDeviceInfo(mapDeviceInfos);
        // 获取手机型号
        lists.add(KeyValueBean.get(R.string.model, android.os.Build.MODEL));
        // 获取设备制造商
        lists.add(KeyValueBean.get(R.string.manufacturer, android.os.Build.MANUFACTURER));
        // 获取设备品牌
        lists.add(KeyValueBean.get(R.string.brand, android.os.Build.BRAND));
        // 获取 Android 系统版本
        lists.add(KeyValueBean.get(R.string.version_release, android.os.Build.VERSION.RELEASE));
        // 获取屏幕尺寸 ( 英寸 )
        lists.add(KeyValueBean.get(R.string.screen, ScreenUtils.getScreenSizeOfDevice()));
        // 获取屏幕分辨率
        lists.add(KeyValueBean.get(R.string.screen_size, ScreenUtils.getScreenSize()));
        // 获取手机总空间
        lists.add(KeyValueBean.get(R.string.sdcard_total, SDCardUtils.getAllBlockSizeFormat()));
        // 获取手机可用空间
        lists.add(KeyValueBean.get(R.string.sdcard_available, SDCardUtils.getAvailableBlocksFormat()));
        // 获取手机总内存
        lists.add(KeyValueBean.get(R.string.memory_total, MemoryUtils.getTotalMemoryFormat()));
        // 获取手机可用内存
        lists.add(KeyValueBean.get(R.string.memory_available, MemoryUtils.getMemoryAvailableFormat()));
        // 获取设备版本号
        lists.add(KeyValueBean.get(R.string.id, Build.ID));
        // 获取设备版本
        lists.add(KeyValueBean.get(R.string.display, android.os.Build.DISPLAY));
        // 获取设备名
        lists.add(KeyValueBean.get(R.string.device, android.os.Build.DEVICE));
        // 获取产品名称
        lists.add(KeyValueBean.get(R.string.product, android.os.Build.PRODUCT));
        try {
            // 判断是否模拟器
            String result = mapDeviceInfos.get("IS_EMULATOR".toLowerCase());
            // 存在数据才显示
            if (!TextUtils.isEmpty(result)) {
                lists.add(KeyValueBean.get(R.string.is_emulator, result));
            }
        } catch (Exception e) {
        }
        try {
            // 判断是否允许 debug 调试
            String result = mapDeviceInfos.get("IS_DEBUGGABLE".toLowerCase());
            // 存在数据才显示
            if (!TextUtils.isEmpty(result)) {
                lists.add(KeyValueBean.get(R.string.is_debuggable, result));
            }
        } catch (Exception e) {
        }
        // 获取基带版本
        lists.add(KeyValueBean.get(R.string.baseband_version, DeviceUtils.getBaseband_Ver()));
        // 获取内核版本
        lists.add(KeyValueBean.get(R.string.linuxcode_version, DeviceUtils.getLinuxCore_Ver()));
        // 获取序列号
        lists.add(KeyValueBean.get(R.string.serial, Build.SERIAL));
        // 设备唯一标识, 由设备的多个信息拼接合成
        lists.add(KeyValueBean.get(R.string.fingerprint, Build.FINGERPRINT));
        // 获取设备基板名称
        lists.add(KeyValueBean.get(R.string.board, Build.BOARD));
        // 获取设备硬件名称, 一般和基板名称一样 ( BOARD )
        lists.add(KeyValueBean.get(R.string.hardware, Build.HARDWARE));
        // 获取 CPU 型号
        lists.add(KeyValueBean.get(R.string.cpuinfo, CPUUtils.getCpuInfo()));
        // CPU 指令集
        lists.add(KeyValueBean.get(R.string.cpu_abi1, android.os.Build.CPU_ABI));
        lists.add(KeyValueBean.get(R.string.cpu_abi2, android.os.Build.CPU_ABI2));
        try {
            // 判断支持的指令集
            String result = mapDeviceInfos.get("SUPPORTED_ABIS".toLowerCase());
            // 存在数据才显示
            if (!TextUtils.isEmpty(result)) {
                lists.add(KeyValueBean.get(R.string.supported_abis, result));
            }
        } catch (Exception e) {
        }
        // 获取 CPU 数量
        lists.add(KeyValueBean.get(R.string.cpu_number, CPUUtils.getCoresNumbers() + ""));
        // 获取 CPU 最高 HZ
        lists.add(KeyValueBean.get(R.string.cpu_max, CPUUtils.getMaxCpuFreq()));
        // 获取 CPU 最底 HZ
        lists.add(KeyValueBean.get(R.string.cpu_min, CPUUtils.getMinCpuFreq()));
        // 获取 CPU 当前 HZ
        lists.add(KeyValueBean.get(R.string.cpu_cur, CPUUtils.getCurCpuFreq()));
        return lists;
    }
}