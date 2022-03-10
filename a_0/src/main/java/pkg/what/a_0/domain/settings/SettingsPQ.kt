package pkg.what.a_0.domain.settings

import pkg.what.a_0.domain.core.constant.SettingsTags.SETTING_CACHE
import pkg.what.a_0.domain.core.constant.SettingsTags.SETTING_MODE
import pkg.what.a_0.domain.core.constant.SettingsTags.SETTING_PERMISSIONS

class SettingsPQ {

    /** @desc allows the client to visualize the status of a the named settings */
    private var status: HashMap<String,Any?>? = hashMapOf<String, Any?>()
        .apply {
            this[SETTING_MODE] = null
            this[SETTING_CACHE] = false
            this[SETTING_PERMISSIONS] = false
        }

    /** @desc allows the client to have an extended feature for themes
     * , this should be displayed in a simple toggle button that when pressed switches mode
     * , for clarity, this app will support DARK and LIGHT per material design specification */
    private var mode: String? = null

    /** @desc allows the client to have omnipotent control of the apps cache for their data
     * , this should be displayed in a simple toggle button that when pressed clears it
     * , false means the cache is not stashed
     * , true means the cache is stashed */
    private var cache: Boolean? = false

    /** @desc allows the client to have omnipotent control of the apps permissions
     * , this should be displayed in a simple toggle button that when pressed clears it
     * , false means the permissions are not accepted
     * , true means the permissions are accepted */
    private var permissions: Boolean? = false
}