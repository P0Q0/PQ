package pkg.what.a_0.ui.notification

import androidx.fragment.app.Fragment

abstract class NotifyFragment : Fragment(), NotifyIf {
    abstract fun prepIntents()
}