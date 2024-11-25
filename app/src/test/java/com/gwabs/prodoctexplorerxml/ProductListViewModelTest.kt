package com.gwabs.prodoctexplorerxml
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ProductListViewModelTest {

    @Mock
    private lateinit var repository: ProductRepository

    private lateinit var viewModel: ProductListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getProducts sets loading state initially`() = runTest {
        Mockito.`when`(repository.getProducts()).thenReturn(flowOf(emptyList()))

        viewModel.getProducts()

        val result = viewModel.products.value
        assertTrue(result is Resource.Loading)
    }

    @Test
    fun `getProducts sets success state with data when API call succeeds`() = runTest {
        val mockProducts = listOf(Product(1, "Test Product", "Description", "Category", "image", 100.0))
        Mockito.`when`(repository.getProducts()).thenReturn(flowOf(mockProducts))

        viewModel.getProducts()

        val result = viewModel.products.value
        assertTrue(result is Resource.Success)
        assertEquals(mockProducts, (result as Resource.Success).data)
    }

    @Test
    fun `getProducts sets error state when API call fails`() = runTest {
        Mockito.`when`(repository.getProducts()).thenThrow(RuntimeException("Network error"))

        viewModel.getProducts()

        val result = viewModel.products.value
        assertTrue(result is Resource.Error)
        assertEquals("Network error", (result as Resource.Error).message)
    }
}
