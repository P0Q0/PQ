package pkg.what.a_0.util

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
    private const val FINISH_BEGIN = 5
    private const val START_END = 5
    private const val FINISH_END = 10
}