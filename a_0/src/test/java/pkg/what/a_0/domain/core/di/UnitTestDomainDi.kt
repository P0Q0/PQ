package pkg.what.a_0.domain.core.di

import android.content.Context
import com.squareup.picasso.Picasso
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class UnitTestDomainDi {

    @Mock private lateinit var mockCtx: Context

    private lateinit var picasso: Picasso

    @Before fun setUp(){
        /** Given mocked dependencies **/
        mockCtx = mock()
        /** When picasso is built **/
        picasso = Picasso.Builder(mockCtx).build() //TODO: UnitTestDomainDi
    }

    @Test fun picasso_builder_test_di(){
        /** Then verify di succeeded by an operation on the non null object **/
        verify(picasso).isLoggingEnabled
    }
}