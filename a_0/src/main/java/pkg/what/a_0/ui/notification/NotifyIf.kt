package pkg.what.a_0.ui.notification

interface NotifyIf {
    object Helper {
        val noty by lazy { UiNotifications() }
    }
    fun uiNotifier(event: UiNotifierStates)
}