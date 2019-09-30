package nl.codesquad.demo.potterkata

import nl.codesquad.demo.potterkata.Book.Companion.fifth
import nl.codesquad.demo.potterkata.Book.Companion.first
import nl.codesquad.demo.potterkata.Book.Companion.fourth
import nl.codesquad.demo.potterkata.Book.Companion.second
import nl.codesquad.demo.potterkata.Book.Companion.third
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PotterStoreTest {
    private val store = PotterStore()

    @Test
    internal fun `a single book should have no discount`() {
        assertThat(store.calculatePrice(second())).isEqualTo(8.00)
    }

    @Test
    internal fun `two the same books should have no discount`() {
        assertThat(store.calculatePrice(setOf(second(), second()))).isEqualTo(16.00)
    }

    @Test
    internal fun `three the same books should have no discount`() {
        assertThat(store.calculatePrice(setOf(first(), first(), first()))).isEqualTo(24.00)
    }

    @Test
    internal fun `two different titles should give 5 percent discount`() {
        assertThat(store.calculatePrice(setOf(first(), second()))).isEqualTo(15.20)
    }

    @Test
    internal fun `three different titles should give 10 percent discount`() {
        assertThat(store.calculatePrice(setOf(third(), first(), second()))).isEqualTo(21.60)
    }

    @Test
    internal fun `two different and one extra of the first should cost 23,2 euros`() {
        assertThat(store.calculatePrice(setOf(first(), first(), second()))).isEqualTo(23.20)
    }

    @Test
    internal fun `four different books should give 20 percent discount`() {
        assertThat(store.calculatePrice(setOf(first(), second(), third(), fourth()))).isEqualTo(25.60)
    }

    @Test
    internal fun `five different books should give 25 percent discount`() {
        assertThat(store.calculatePrice(setOf(first(), second(), third(), fourth(), fifth()))).isEqualTo(30.00)
    }

    @Test
    internal fun `should work for different sets of sets`() {
        assertThat(store.calculatePrice(setOf(first(), first(), second(), second()))).isEqualTo(30.40)
    }

    @Test
    internal fun `big order test`() {
        assertThat(store.calculatePrice(setOf(first(), first(), second(), second(), third(), third(), fourth(), fifth()))).isEqualTo(51.20)
    }

    @Test
    internal fun `should calculate for best price per unit`() {
        assertThat(store.calculatePrice(setOf(first(), second(), third(), fourth(), fifth(), first(), third(), fifth())))
                .isEqualTo(51.2)
    }

    @Test
    internal fun `complex test case`() {
        assertThat(store.calculatePrice(setOf(
                first(),first(),first(),first(),first(),
                second(),second(),second(),second(),second(),
                third(),third(),third(),third(),
                fourth(),fourth(),fourth(),fourth(),fourth(),
                fifth(),fifth(),fifth(),fifth())))
                .isEqualTo((3 * (8 * 5 * 0.75) +
                        2 * (8 * 4 * 0.8)))
    }
}