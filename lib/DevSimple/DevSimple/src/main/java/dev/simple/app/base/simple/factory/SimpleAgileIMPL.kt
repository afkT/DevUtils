package dev.simple.app.base.simple.factory

import dev.simple.app.controller.ui.theme.ActivityUITheme
import dev.simple.app.controller.ui.theme.FragmentUITheme

// ============
// = Activity =
// ============

class SimpleActivityIMPL private constructor(
    simple_Init: ((Any) -> Unit)?,
    simple_Start: ((Any) -> Unit)?,
    simple_PreLoad: ((Any) -> Unit)?,
    simple_Agile: ((Any) -> Unit)?,
    simple_UITheme: ((ActivityUITheme) -> ActivityUITheme)?
) : BaseSimpleAgile<Any, ActivityUITheme>(
    simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
) {

    companion object {

        fun of(
            simple_Init: ((Any) -> Unit)? = null,
            simple_Start: ((Any) -> Unit)? = null,
            simple_PreLoad: ((Any) -> Unit)? = null,
            simple_Agile: ((Any) -> Unit)? = null,
            simple_UITheme: ((ActivityUITheme) -> ActivityUITheme)? = null
        ): SimpleActivityIMPL {
            return SimpleActivityIMPL(
                simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
            )
        }
    }
}

// ============
// = Fragment =
// ============

class SimpleFragmentIMPL private constructor(
    simple_Init: ((Any) -> Unit)?,
    simple_Start: ((Any) -> Unit)?,
    simple_PreLoad: ((Any) -> Unit)?,
    simple_Agile: ((Any) -> Unit)?,
    simple_UITheme: ((FragmentUITheme) -> FragmentUITheme)?
) : BaseSimpleAgile<Any, FragmentUITheme>(
    simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
) {

    companion object {

        fun of(
            simple_Init: ((Any) -> Unit)? = null,
            simple_Start: ((Any) -> Unit)? = null,
            simple_PreLoad: ((Any) -> Unit)? = null,
            simple_Agile: ((Any) -> Unit)? = null,
            simple_UITheme: ((FragmentUITheme) -> FragmentUITheme)? = null
        ): SimpleFragmentIMPL {
            return SimpleFragmentIMPL(
                simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
            )
        }
    }
}