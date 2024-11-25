package com.gwabs.prodoctexplorerxml

@RunWith(AndroidJUnit4::class)
class ProductDetailsActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(ProductDetailsActivity::class.java)

    @Test
    fun testProductDetailsAreDisplayed() {
        // Mock intent to pass a valid PRODUCT_ID
        val intent = Intent(ApplicationProvider.getApplicationContext(), ProductDetailsActivity::class.java)
        intent.putExtra("PRODUCT_ID", 1)

        ActivityScenario.launch<ProductDetailsActivity>(intent).use {
            onView(withId(R.id.productTitle)).check(matches(isDisplayed()))
            onView(withId(R.id.productPrice)).check(matches(isDisplayed()))
            onView(withId(R.id.productDescription)).check(matches(isDisplayed()))
        }
    }
}
