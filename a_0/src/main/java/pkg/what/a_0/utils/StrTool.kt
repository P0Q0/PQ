package pkg.what.a_0.utils

import kotlin.random.Random

object StrTool {
    fun strGenerateRandom() : String {
        val vocabulary = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        val start = Random.nextInt(START_BEGIN, FINISH_BEGIN)
        val end = Random.nextInt(START_END,FINISH_END)
        return (start..end)
            .map { vocabulary.random() }
            .joinToString(separator = "")
    }
    private const val START_BEGIN = 0
    private const val FINISH_BEGIN = 10
    private const val START_END = 10
    private const val FINISH_END = 100
}