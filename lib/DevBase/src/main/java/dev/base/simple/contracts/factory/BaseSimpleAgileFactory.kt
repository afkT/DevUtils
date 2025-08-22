package dev.base.simple.contracts.factory

import dev.base.simple.contracts.factory.SimpleAgileFactory

open class BaseSimpleAgileFactory<SimpleTClass>(
    private val simple_Init: ((SimpleTClass) -> Unit)?,
    private val simple_Start: ((SimpleTClass) -> Unit)?,
    private val simple_PreLoad: ((SimpleTClass) -> Unit)?,
    private val simple_Agile: ((SimpleTClass) -> Unit)?,
) : SimpleAgileFactory<SimpleTClass> {

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
}