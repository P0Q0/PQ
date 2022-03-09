package pkg.what.a_0.ui

import androidx.lifecycle.ViewModel

/** @desc holds instance of view model that will expose data to the ui
 * , updates state managed through the callers lifecycle */
sealed class UiStateHolder {
    /** @TODO: UiStateHolder: let ui have a vm as long as it is container here */
    data class ALIVE(val status: String) : UiStateHolder()
    data class DEAD(val status: String) : UiStateHolder()
    data class UNKNOWN(val status: String) : UiStateHolder()
    object UiShState : UiStateHolder() {
        fun update(type : Class<ViewModel>) {
            /** @TODO: UiStateHolder: update state here */
        }
    }
}