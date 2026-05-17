@file:Suppress("unused")
@file:JvmName("QualifiesObservableExtKt")
@file:JvmMultifileClass

package dev.simple.extensions.qualifies

import androidx.databinding.*

// ==============
// = Observable =
// ==============

/** [ObservableBoolean] 非 null 时对 [ObservableBoolean.get] 委托 [qualifies]。 */
fun ObservableBoolean?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableBoolean] 非 null 时对 [ObservableBoolean.get] 委托 [qualifiesTrue]。 */
fun ObservableBoolean?.qualifiesTrue(): Boolean =
    this != null && this.get().qualifiesTrue()

/** [ObservableBoolean] 非 null 时对 [ObservableBoolean.get] 委托 [qualifiesFalse]。 */
fun ObservableBoolean?.qualifiesFalse(): Boolean =
    this != null && this.get().qualifiesFalse()

/** [ObservableByte] 非 null 时对 [ObservableByte.get] 委托 [qualifies]。 */
fun ObservableByte?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableShort] 非 null 时对 [ObservableShort.get] 委托 [qualifies]。 */
fun ObservableShort?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableInt] 非 null 时对 [ObservableInt.get] 委托 [qualifies]。 */
fun ObservableInt?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableLong] 非 null 时对 [ObservableLong.get] 委托 [qualifies]。 */
fun ObservableLong?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableFloat] 非 null 时对 [ObservableFloat.get] 委托 [qualifies]。 */
fun ObservableFloat?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableDouble] 非 null 时对 [ObservableDouble.get] 委托 [qualifies]。 */
fun ObservableDouble?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableChar] 非 null 时对 [ObservableChar.get] 委托 [qualifies]。 */
fun ObservableChar?.qualifies(): Boolean =
    this != null && this.get().qualifies()