package dev.simple.app.base.simple.factory

import dev.simple.app.controller.ui.theme.ActivityUITheme
import dev.simple.app.controller.ui.theme.FragmentUITheme

// ============
// = Activity =
// ============

class SimpleActivityIMPL<SimpleTClass> private constructor(
    simple_Init: ((SimpleTClass) -> Unit)?,
    simple_Start: ((SimpleTClass) -> Unit)?,
    simple_PreLoad: ((SimpleTClass) -> Unit)?,
    simple_Agile: ((SimpleTClass) -> Unit)?,
    simple_UITheme: ((ActivityUITheme) -> ActivityUITheme)?
) : BaseSimpleAgile<SimpleTClass, ActivityUITheme>(
    simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
) {

    companion object {

        fun <SimpleTClass> of(
            simple_Init: ((SimpleTClass) -> Unit)? = null,
            simple_Start: ((SimpleTClass) -> Unit)? = null,
            simple_PreLoad: ((SimpleTClass) -> Unit)? = null,
            simple_Agile: ((SimpleTClass) -> Unit)? = null,
            simple_UITheme: ((ActivityUITheme) -> ActivityUITheme)? = null
        ): SimpleActivityIMPL<SimpleTClass> {
            return SimpleActivityIMPL(
                simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
            )
        }
    }
}

// ============
// = Fragment =
// ============

class SimpleFragmentIMPL<SimpleTClass> private constructor(
    simple_Init: ((SimpleTClass) -> Unit)?,
    simple_Start: ((SimpleTClass) -> Unit)?,
    simple_PreLoad: ((SimpleTClass) -> Unit)?,
    simple_Agile: ((SimpleTClass) -> Unit)?,
    simple_UITheme: ((FragmentUITheme) -> FragmentUITheme)?
) : BaseSimpleAgile<SimpleTClass, FragmentUITheme>(
    simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
) {

    companion object {

        fun <SimpleTClass> of(
            simple_Init: ((SimpleTClass) -> Unit)? = null,
            simple_Start: ((SimpleTClass) -> Unit)? = null,
            simple_PreLoad: ((SimpleTClass) -> Unit)? = null,
            simple_Agile: ((SimpleTClass) -> Unit)? = null,
            simple_UITheme: ((FragmentUITheme) -> FragmentUITheme)? = null
        ): SimpleFragmentIMPL<SimpleTClass> {
            return SimpleFragmentIMPL(
                simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
            )
        }
    }
}