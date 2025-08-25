package dev.base.simple.contracts.factory

// ============
// = Activity =
// ============

class SimpleActivityImpl private constructor(
    simple_Init: ((Any) -> Unit)?,
    simple_Start: ((Any) -> Unit)?,
    simple_PreLoad: ((Any) -> Unit)?,
    simple_Agile: ((Any) -> Unit)?
) : BaseSimpleAgileFactory<Any>(
    simple_Init, simple_Start, simple_PreLoad, simple_Agile
) {

    companion object {

        fun of(
            simple_Init: ((Any) -> Unit)? = null,
            simple_Start: ((Any) -> Unit)? = null,
            simple_PreLoad: ((Any) -> Unit)? = null,
            simple_Agile: ((Any) -> Unit)? = null
        ): SimpleActivityImpl {
            return SimpleActivityImpl(
                simple_Init, simple_Start, simple_PreLoad, simple_Agile
            )
        }
    }
}

// ============
// = Fragment =
// ============

class SimpleFragmentImpl private constructor(
    simple_Init: ((Any) -> Unit)?,
    simple_Start: ((Any) -> Unit)?,
    simple_PreLoad: ((Any) -> Unit)?,
    simple_Agile: ((Any) -> Unit)?
) : BaseSimpleAgileFactory<Any>(
    simple_Init, simple_Start, simple_PreLoad, simple_Agile
) {

    companion object {

        fun of(
            simple_Init: ((Any) -> Unit)? = null,
            simple_Start: ((Any) -> Unit)? = null,
            simple_PreLoad: ((Any) -> Unit)? = null,
            simple_Agile: ((Any) -> Unit)? = null
        ): SimpleFragmentImpl {
            return SimpleFragmentImpl(
                simple_Init, simple_Start, simple_PreLoad, simple_Agile
            )
        }
    }
}