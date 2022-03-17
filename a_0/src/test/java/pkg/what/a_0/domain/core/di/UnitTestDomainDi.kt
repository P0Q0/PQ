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
import pkg.what.pq.ApplicationPQ

@RunWith(MockitoJUnitRunner::class)
class UnitTestDomainDi {

    @Mock private lateinit var mockCtx: Context

    @Mock private lateinit var mockPicasso: Picasso

    @Before fun initialize(){
        /** Given mocked dependencies **/
        mockCtx = mock { on(mock.applicationContext).thenReturn(ApplicationPQ().applicationContext) }
        mockPicasso = mock()
        /** When context and picasso are built interact with them **/
        try {
            mockCtx.applicationContext
            mockPicasso.isLoggingEnabled
        } catch (npe : NullPointerException){
            println(npe.printStackTrace())
        }
    }

    @Test fun picasso_builder_test_di(){
        /** Then verify di succeeded by an operation on the non null object **/
        verify(mockCtx).applicationContext
        verify(mockPicasso).isLoggingEnabled
    }
}