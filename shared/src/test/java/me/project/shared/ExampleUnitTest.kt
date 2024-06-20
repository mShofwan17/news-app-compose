package me.project.shared

import me.project.shared.extension.getDaysAgo
import me.project.shared.extension.ifNull
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun daysAgoYesterdayTest() {
        val dateTime = "2024-06-19T08:34:03Z".getDaysAgo(false)
        assertEquals(
            "Yesterday",
            dateTime
        )
    }

    @Test
    fun daysAgoTodayTest() {
        val dateTime = "2024-06-20T08:34:03Z".getDaysAgo(false)
        assertEquals(
            "Today",
            dateTime
        )
    }

    @Test
    fun daysAgoIsFromDbTest() {
        val dateTime = "Yesterday".getDaysAgo(true)
        assertEquals(
            "Yesterday",
            dateTime
        )
    }

    @Test
    fun ifDescNull() {
        val actual: String? = null
        val expected = """
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras a rhoncus massa. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed sed lacinia metus, vel rutrum augue. Fusce venenatis tortor a lacus convallis congue. Mauris consequat dapibus vulputate. Phasellus finibus augue non aliquet volutpat. Nulla nec ornare ex, in consectetur mauris. Suspendisse potenti. Cras dignissim lacus sed diam hendrerit volutpat. Nam tincidunt, ex at hendrerit ullamcorper, ex sapien rutrum tortor, id fermentum lectus orci id justo.
        Fusce sit amet nibh aliquam, lobortis risus vitae, placerat libero. Duis elementum dictum sagittis. Proin placerat mollis justo sagittis consequat. Aliquam erat volutpat. In ut rhoncus nulla, in varius lectus. Suspendisse potenti. Donec dolor nisl, ultrices ut cursus sed, maximus vel metus. Sed erat quam, gravida nec tristique et, finibus sed nisi. Fusce bibendum gravida nunc, at ornare nisl molestie eu. Pellentesque bibendum elit diam, id venenatis nisi vestibulum at. Mauris tortor nulla, interdum eget orci eu, pretium aliquam felis. Proin ullamcorper eleifend sagittis. Curabitur eleifend fringilla ante vitae vulputate.
        Mauris ut tincidunt lorem. Suspendisse vehicula sagittis ullamcorper. Nunc faucibus arcu eget egestas elementum. Nulla gravida mattis arcu dignissim feugiat. Vestibulum faucibus, quam gravida tristique ornare, augue justo commodo ex, feugiat commodo ipsum ligula quis leo. Praesent suscipit dolor metus, at molestie nibh consequat at. Mauris pharetra risus ut felis bibendum commodo. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.
    """.trimIndent()
        assertEquals(
            actual.ifNull(),
            expected
        )
    }

    @Test
    fun ifDescExist(){
        val actual = """
            yeyeyeyeyy
        """.trimIndent()

        val expected = """
            yeyeyeyeyy
        """.trimIndent()

        assertEquals(
            actual.ifNull(),
            expected
        )
    }
}