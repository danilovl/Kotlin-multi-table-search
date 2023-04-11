package danilovl.service

import danilovl.setting.EnvType
import io.github.cdimascio.dotenv.dotenv
import java.sql.Connection
import java.sql.DriverManager

class Database {
    companion object {
        private var instance: Connection? = null

        fun getConnection (): Connection {
            if (instance !== null) {
                return instance!!
            }

            val dotenv = dotenv()

            try {
                Class.forName("com.mysql.cj.jdbc.Driver")
                val databaseDsn: String = dotenv[EnvType.DATABASE_DSN.value]
                val databaseUser: String = dotenv[EnvType.DATABASE_USER.value]
                val databasePassword: String = dotenv[EnvType.DATABASE_PASSWORD.value]

                instance = DriverManager.getConnection(databaseDsn, databaseUser,databasePassword)
                println("Connection successful")
            } catch (e: Exception) {
                e.printStackTrace()
                println("Failed to connect")
            }

            return instance!!
        }
    }
}
