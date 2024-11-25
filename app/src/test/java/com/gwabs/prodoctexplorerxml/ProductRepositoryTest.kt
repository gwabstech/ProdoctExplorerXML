package com.gwabs.prodoctexplorerxml

import com.gwabs.prodoctexplorerxml.data.database.ProductDao
import com.gwabs.prodoctexplorerxml.data.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ProductRepositoryTest {

    @Mock
    private lateinit var productApi: com.gwabs.prodoctexplorerxml.data.api.ProductApi

    @Mock
    private lateinit var productDao: ProductDao

    private lateinit var repository: ProductRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = ProductRepository(productApi, productDao, FakeConnectivityHelper(true))
    }

    @Test
    fun `getProductsFromApi saves data to DB when API call is successful`() = runTest {
        val mockProducts = listOf(Product(1, "Test Product", "Description", "Category", "image", 100.0))
        Mockito.`when`(productApi.getProducts()).thenReturn(Response.success(mockProducts))

        repository.getProductsFromApi()

        Mockito.verify(productDao).insertAll(mockProducts)
    }

    @Test
    fun `getProductsFromDb fetches data from local database`() = runTest {
        val mockProducts = listOf(Product(1, "Test Product", "Description", "Category", "image", 100.0))
        Mockito.`when`(productDao.getAllProducts()).thenReturn(flowOf(mockProducts))

        val products = repository.getProductsFromDb().first()

        assertEquals(mockProducts, products)
    }
}
