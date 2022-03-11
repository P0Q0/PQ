package pkg.what.a_0.ui.notification

import androidx.fragment.app.Fragment
import pkg.what.a_0.ui.notification.NotifyIf

abstract class NotifyFragment : Fragment(), NotifyIf {
    abstract fun prepIntents()
    abstract fun fireUiNotifier()
}