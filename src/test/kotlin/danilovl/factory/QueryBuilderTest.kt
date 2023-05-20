package danilovl.factory

import danilovl.model.SearchParam
import org.junit.Test
import kotlin.test.assertEquals

class QueryBuilderTest {
    @Test
    fun `getQuery returns correct query with search parameter`() {
        val searchParam = SearchParam(
            identifier = "test-1",
            tableName = "users",
            search = "John",
            selectColumns = arrayOf("name"),
            whereColumns = arrayOf("name")
        )

        val expectedQuery = "SELECT name FROM users WHERE name LIKE '%John%' ORDER BY id LIMIT 10 OFFSET 0;"
        val actualQuery = QueryBuilder.getQuery(searchParam)

        assertEquals(expectedQuery, actualQuery)
    }

    @Test
    fun `getQuery returns correct query without search parameter`() {
        val searchParam = SearchParam(
            identifier = "test-2",
            tableName = "products",
            selectColumns = arrayOf("name", "price"),
            limit = 20,
            offset = 5
        )

        val expectedQuery = "SELECT name, price FROM products ORDER BY id LIMIT 20 OFFSET 5;"
        val actualQuery = QueryBuilder.getQuery(searchParam)

        assertEquals(expectedQuery, actualQuery)
    }

    @Test
    fun `getQuery sets limit to 500 if it exceeds 500`() {
        val searchParam = SearchParam(
            identifier = "test-3",
            tableName = "orders",
            selectColumns = arrayOf("*"),
            limit = 1000
        )

        val expectedQuery = "SELECT * FROM orders ORDER BY id LIMIT 500 OFFSET 0;"
        val actualQuery = QueryBuilder.getQuery(searchParam)

        assertEquals(expectedQuery, actualQuery)
    }

    @Test
    fun `getQuery returns correct query with search condition`() {
        val searchParam = SearchParam(
            identifier = "test-4",
            tableName = "users",
            search = "18",
            searchCondition = "=",
            selectColumns = arrayOf("name", "age"),
            whereColumns = arrayOf("age"),
        )

        val expectedQuery = "SELECT name, age FROM users WHERE age = '18' ORDER BY id LIMIT 10 OFFSET 0;"
        val actualQuery = QueryBuilder.getQuery(searchParam)

        assertEquals(expectedQuery, actualQuery)
    }
}
