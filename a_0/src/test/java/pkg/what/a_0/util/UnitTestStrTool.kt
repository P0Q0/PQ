package pkg.what.a_0.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UnitTestStrTool {

    /**
     * @desc simple test case as a sanity check the tool is in fact returning a string
     * , also serves an indirect purpose to demonstrate usage of Junit and Truth
     */
    @Test fun str_generate_random_test_is_a_string(){
        val testRandomStr = StrTool.strGenerateRandom()
        assertThat(testRandomStr).isInstanceOf(String::class.java)
    }

    /**
     * @analysis hashset by design stores *unique* values using hashing
     * , [StrTool.strGenerateRandom] has a vocabulary of ('A'..'Z') + ('a'..'z') + ('0'..'9')
     * , this will define the bottleneck and possibility of failure when the stress level is high
     */
    @Test fun str_generate_random_exhaustively_test_are_all_randoms(){
        val testListOfRandomStr: MutableList<String> = mutableListOf()
        val testHastSetOfRandomStr: HashSet<String> = hashSetOf()
        for(i in DEFAULT until STRESS.LEVEL_3) { testListOfRandomStr.add(StrTool.strGenerateRandom()) }
        for(i in DEFAULT until STRESS.LEVEL_3) { testHastSetOfRandomStr.add(testListOfRandomStr[i]) }
        val testSizeOfList = testListOfRandomStr.size
        val testSizeOfHashSet = testHastSetOfRandomStr.size
        assertThat(testSizeOfHashSet).isEqualTo(testSizeOfList)
    }

    companion object {
        private const val DEFAULT = 0
        object STRESS {
            const val LEVEL_0 = 10
            const val LEVEL_2 = 100
            const val LEVEL_3 = 1000
            const val LEVEL_4 = 10000
            const val LEVEL_5 = 100000
        }
    }
}