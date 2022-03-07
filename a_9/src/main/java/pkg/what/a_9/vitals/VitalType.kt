package pkg.what.a_9.vitals

/** @desc type set of vital entities */
sealed class VitalType {
    data class CoreGroup(var family: String) : VitalType()
    data class OtherGroup(var family: String) : VitalType()
}
