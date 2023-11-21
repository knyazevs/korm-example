package s.knyazev.example.db

import s.knyazev.SqlParameterSource
import s.knyazev.database.Database
import s.knyazev.database.createDatabase
import s.knyazev.resultset.ResultSet
import java.lang.System.getProperty
import java.lang.System.getenv

object Database: Database {
    private val driver = createDatabase(
        host = getenv("POSTGRES_HOST") ?: getProperty("POSTGRES_HOST") ?: "localhost",
        port = getenv("POSTGRES_PORT")?.toInt() ?: getProperty("POSTGRES_PORT")?.toInt() ?: 5432,
        database = getenv("POSTGRES_DATABASE") ?: getProperty("POSTGRES_DATABASE") ?: "postgres",
        user = getenv("POSTGRES_USER") ?: getProperty("POSTGRES_USER") ?: "postgres",
        password = getenv("POSTGRES_PASSWORD") ?: getProperty("POSTGRES_PASSWORD") ?: "password",
    )

    override fun <T> execute(
        sql: String,
        namedParameters: Map<String, Any?>,
        handler: (ResultSet) -> T,
    ): List<T> = driver.execute(sql, namedParameters, handler)

    override fun <T> execute(
        sql: String,
        paramSource: SqlParameterSource,
        handler: (ResultSet) -> T,
    ): List<T> = driver.execute(sql, paramSource, handler)

    override fun execute(sql: String, namedParameters: Map<String, Any?>): Long = driver.execute(sql, namedParameters)

    override fun execute(sql: String, paramSource: SqlParameterSource): Long = driver.execute(sql, paramSource)

    override fun executeUpdate(
        sql: String,
        namedParameters: Map<String, Any?>,
    ) = driver.executeUpdate(sql, namedParameters)
}
