package br.com.fornaro.mesanews.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.fornaro.mesanews.data.repository.AuthenticationRepository
import br.com.fornaro.mesanews.data.source.local.AuthenticationLocalDataSource
import br.com.fornaro.mesanews.data.source.remote.AuthenticationRemoteDataSource
import br.com.fornaro.mesanews.domain.models.Authentication
import br.com.fornaro.mesanews.tools.UnitTestDispatcherMap
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

@ExperimentalCoroutinesApi
class AuthenticationRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var remoteDataSource: AuthenticationRemoteDataSource

    @RelaxedMockK
    private lateinit var localDataSource: AuthenticationLocalDataSource

    private lateinit var repository: AuthenticationRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        repository = AuthenticationRepository(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            dispatcherMap = UnitTestDispatcherMap
        )
    }

    @Test
    fun `should return token from local data source`() = runBlockingTest {
        val token = "token"
        coEvery { localDataSource.getToken() } returns token

        val result = repository.getToken()

        assertEquals(token, result)
    }

    @Test
    fun `user should be logged in if email is filled out`() {
        val email = "email"
        every { localDataSource.email } returns email

        val result = repository.isUserLogged

        assertEquals(true, result)
    }

    @Test
    fun `user should be logged out if token is null`() {
        every { localDataSource.email } returns null

        val result = repository.isUserLogged

        assertEquals(false, result)
    }

    @Test
    fun `sign up should save user info when successfully`() = runBlockingTest {
        val token = "token"
        val email = "email"
        val authentication = Authentication(token = token)

        coEvery {
            remoteDataSource.signUp(
                anyString(),
                email,
                anyString()
            )
        } returns authentication

        repository.signUp(anyString(), email, anyString())

        coVerify { localDataSource.saveUser(email, token) }
    }

    @Test
    fun `sign in should save user info when successfully`() = runBlockingTest {
        val token = "token"
        val email = "email"
        val authentication = Authentication(token = token)

        coEvery {
            remoteDataSource.signIn(
                email,
                anyString()
            )
        } returns authentication

        repository.signIn(email, anyString())

        coVerify { localDataSource.saveUser(email, token) }
    }
}