package pkg.what.a_5

/** @desc data source is the definition for dummy data
 *  @p is string token parameter that is mutable by default it is null
 *  @r is string token parameter that is non-mutable by default it is 'r'
 *  @q is string token member that is mutable by default it is null
 *  @s is string token member that is non-mutable by default it is 's' */
data class DataSource(
    var p: String? = null
    , val r: String = "r")
{
    var q: String? = null
    private val s: String = "s"

    val d = ArrayList<String>().apply {
        p?.let { this.add(it) }
        r.let { this.add(it) }
        s.let { this.add(it) }
    }
}