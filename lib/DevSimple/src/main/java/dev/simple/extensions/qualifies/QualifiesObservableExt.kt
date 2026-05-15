@file:Suppress("unused")

package dev.simple.extensions.qualifies

import androidx.databinding.*

// ==============
// = Observable =
// ==============

/** [ObservableBoolean] 非 null 时对 [ObservableBoolean.get] 委托 [shouldTriggerEffect]。 */
fun ObservableBoolean?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableBoolean] 非 null 时对 [ObservableBoolean.get] 委托 [shouldTriggerEffectTrue]。 */
fun ObservableBoolean?.shouldTriggerEffectTrue(): Boolean =
    this != null && this.get().shouldTriggerEffectTrue()

/** [ObservableBoolean] 非 null 时对 [ObservableBoolean.get] 委托 [shouldTriggerEffectFalse]。 */
fun ObservableBoolean?.shouldTriggerEffectFalse(): Boolean =
    this != null && this.get().shouldTriggerEffectFalse()

/** [ObservableByte] 非 null 时对 [ObservableByte.get] 委托 [shouldTriggerEffect]。 */
fun ObservableByte?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableShort] 非 null 时对 [ObservableShort.get] 委托 [shouldTriggerEffect]。 */
fun ObservableShort?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableInt] 非 null 时对 [ObservableInt.get] 委托 [shouldTriggerEffect]。 */
fun ObservableInt?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableLong] 非 null 时对 [ObservableLong.get] 委托 [shouldTriggerEffect]。 */
fun ObservableLong?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableFloat] 非 null 时对 [ObservableFloat.get] 委托 [shouldTriggerEffect]。 */
fun ObservableFloat?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableDouble] 非 null 时对 [ObservableDouble.get] 委托 [shouldTriggerEffect]。 */
fun ObservableDouble?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableChar] 非 null 时对 [ObservableChar.get] 委托 [shouldTriggerEffect]。 */
fun ObservableChar?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()
