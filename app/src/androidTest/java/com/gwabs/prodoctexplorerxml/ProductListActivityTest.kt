package com.gwabs.prodoctexplorerxml

@RunWith(AndroidJUnit4::class)
class ProductListActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(ProductListActivity::class.java)

    @Test
    fun testRecyclerViewIsDisplayed() {
        // Check that RecyclerView is visible
        onView(withId(R.id.productListRecyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testNavigateToDetails() {
        // Perform click on a RecyclerView item
        onView(withId(R.id.productListRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // Check if ProductDetailsActivity is displayed
        onView(withId(R.id.productTitle))
            .check(matches(isDisplayed()))
    }
}
