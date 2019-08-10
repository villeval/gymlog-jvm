package gymlog.configurations

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class DataSources {

    @Bean("gymlogdatasource")
    @ConfigurationProperties(prefix = "gymlogdatasource")
    fun getGymlogDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }
}