package dev.mvvm.utils.hiif

/**
 * Specifies that this function should not be called directly without inlining
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@DslMarker
@Retention(AnnotationRetention.BINARY)
internal annotation class DevInlineOnly