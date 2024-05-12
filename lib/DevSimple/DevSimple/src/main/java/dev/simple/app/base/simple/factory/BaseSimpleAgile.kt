package dev.simple.app.base.simple.factory

import dev.simple.app.base.simple.SimpleAgileFactory

open class BaseSimpleAgile<SimpleTClass, SimpleTUITheme>(
    private val simple_Init: ((SimpleTClass) -> Unit)?,
    private val simple_Start: ((SimpleTClass) -> Unit)?,
    private val simple_PreLoad: ((SimpleTClass) -> Unit)?,
    private val simple_Agile: ((SimpleTClass) -> Unit)?,
    private val simple_UITheme: ((SimpleTUITheme) -> SimpleTUITheme)?
) : SimpleAgileFactory<SimpleTClass, SimpleTUITheme> {

    // ======================
    // = SimpleAgileFactory =
    // ======================

    override fun simpleInit(thisClass: SimpleTClass) {
        simple_Init?.invoke(thisClass)
    }

    override fun simpleStart(thisClass: SimpleTClass) {
        simple_Start?.invoke(thisClass)
    }

    override fun simplePreLoad(thisClass: SimpleTClass) {
        simple_PreLoad?.invoke(thisClass)
    }

    override fun simpleAgile(thisClass: SimpleTClass) {
        simple_Agile?.invoke(thisClass)
    }

    override fun simpleUITheme(uiTheme: SimpleTUITheme): SimpleTUITheme {
        return simple_UITheme?.invoke(uiTheme) ?: uiTheme
    }
}