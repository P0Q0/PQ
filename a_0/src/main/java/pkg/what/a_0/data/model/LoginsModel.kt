package pkg.what.a_0.data.model

class LoginsModel {

    /** @desc stores the credentials of a user during runtime, does not persist automatically
     * , for persistence cache the data on their device only if they gave permission */
    private var cache: ArrayList<String> = arrayListOf()

    fun getCache(): ArrayList<String> = cache

    init {
        this.cache.add("root")
    }
}