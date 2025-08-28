package afkt.environment.use

import android.content.Context
import android.text.TextUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableArrayMap
import dev.DevUtils
import dev.environment.DevEnvironment
import dev.environment.DevEnvironmentUtils
import dev.environment.bean.EnvironmentBean
import dev.environment.bean.ModuleBean
import dev.simple.core.command.BindingConsumer
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass

// ========================
// = 自定义 UI Adapter 模型 =
// ========================

/**
 * detail: 自定义 UI Adapter 模型
 * @author Ttt
 */
class CustomAdapterModel {

    // 校验是否选中
    val selectedMap = ObservableArrayMap<String, EnvironmentBean>()

    // 数据源
    val items = ObservableArrayList<Any>()

    // Item Binding
    val itemBinding = OnItemBindClass<Any>().map(
        ModuleBean::class.java
    ) { itemBinding, position, item ->
        itemBinding.clearExtras().set(
            BR.itemValue, R.layout.custom_module_adapter_item
        )
    }.map(EnvironmentBean::class.java) { itemBinding, position, item ->
        itemBinding.clearExtras().set(
            BR.itemValue, R.layout.custom_environment_adapter_item
        ).bindExtra(BR.itemClick, itemClick)
            .bindExtra(BR.selectedMap, selectedMap)
    }

    val itemClick = object : BindingConsumer<EnvironmentBean> {
        override fun accept(value: EnvironmentBean) {
            // 属于 Release 构建则不处理
            if (DevEnvironment.isRelease()) return

            val module = value.module
            selectedMap.put(module.name, value)
            // 设置选中环境
            DevEnvironmentUtils.setModuleEnvironment(
                DevUtils.getContext(), value
            )
        }
    }
}

// ===============================
// = 自定义 UI Adapter 数据转换模型 =
// ===============================

/**
 * 自定义 UI Adapter 数据转换
 */
fun CustomAdapterModel.convertItems(
    context: Context = DevUtils.getContext()
) {
    val _selectedMap = ObservableArrayMap<String, EnvironmentBean>()
    val result = mutableListOf<Any>()
    // 循环 Module
    DevEnvironment.getModuleList().forEach { module ->
        result.add(module)
        // moduleName Key
        val key = module.name
        // 获取选中的环境
        var environmentSelect = DevEnvironmentUtils.getModuleEnvironment(
            context, key
        )
        // 循环 Environment
        module.environments.forEach { env ->
            result.add(env)
            // 判断当前环境是否属于选中环境
            if (environmentSelect != null &&
                environmentSelect.hashCode() == env.hashCode()
            ) {
                // 保存选中信息
                _selectedMap.put(key, env)
                environmentSelect = null
            }
        }
        // 不等于 null 则表示属于自定义环境非注解环境配置
        if (environmentSelect != null) {
            result.add(environmentSelect)
            // 保存选中信息
            _selectedMap.put(key, environmentSelect)
        }
    }
    items.clear()
    selectedMap.clear()
    selectedMap.putAll(_selectedMap.toMap())
    items.addAll(result)
}

// ==================
// = BindingAdapter =
// ==================

@BindingAdapter("binding_environment_name")
fun TextView.bindingEnvironmentName(
    environment: EnvironmentBean
) {
    val name = environment.name
    val alias = environment.alias
    val valueText = if (TextUtils.isEmpty(alias)) name else alias
    // 设置文本
    this.text = valueText
}