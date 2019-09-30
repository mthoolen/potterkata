package nl.codesquad.demo.potterkata

import nl.codesquad.demo.potterkata.Book.Companion.FULL_PRICE
import java.util.*


data class Book(private val serial: UUID, val title: String) {
    companion object {
        const val FULL_PRICE = 800
        fun first() = Book(UUID.randomUUID(), "The philosophers stone")
        fun second() = Book(UUID.randomUUID(), "Chamber of secrets")
        fun third() = Book(UUID.randomUUID(), "Prisoner of Azkaban")
        fun fourth() = Book(UUID.randomUUID(), "The goblet of fire")
        fun fifth() = Book(UUID.randomUUID(), "Order of the Phoenix")
    }
}

class PotterStore {
    fun calculatePrice(book: Book) = FULL_PRICE.div(100).toDouble()


    fun calculatePrice(books: Collection<Book>): Double =
            formListOfSets(books)
                    .map{ priceInCents(it) }
                    .sum()
                    .div(1e2)

    private fun formListOfSets(books: Collection<Book>): MutableList<Int> {
        val numberPerBook = books.groupBy { it.title }.values.map { it.size }
        val listOfSets = booksToSets(numberPerBook)
        return optimizeLastSets(listOfSets)
    }

    private fun optimizeLastSets(listOfSets: MutableList<Int>): MutableList<Int> {
        listOfSets.sort()
        if (listOfSets.min() == 3 && listOfSets[listOfSets.size - 1] == 5) {
            listOfSets[listOfSets.indexOf(5)] = 4
            listOfSets[listOfSets.indexOf(3)] = 4
        }
        return listOfSets
    }

    private fun booksToSets(numberPerBook: Collection<Int>): MutableList<Int> {
        val listOfSets = listOf<Int>().toMutableList()
        val mutableNumbers = numberPerBook.toMutableList()
        while (!mutableNumbers.all { it == 0 }) {
            listOfSets.add(mutableNumbers.size)
            mutableNumbers.forEachIndexed { index, i -> if (i != 0) mutableNumbers[index] = i - 1 }
            mutableNumbers.removeIf { it == 0 }
        }
        return listOfSets
    }

    private fun priceInCents(books: Int): Int {
        return ((books * FULL_PRICE) * percentageToPay(books)).div(100)
    }

    private fun percentageToPay(books: Int): Int =
            100 - getDiscount(books)

    private fun getDiscount(size: Int): Int {
        if (size == 5) return 25
        if (size == 4) return 20
        return (size - 1) * 5
    }
}
