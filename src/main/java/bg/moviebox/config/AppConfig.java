package bg.moviebox.config;

import bg.moviebox.repository.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

  //Populate the Database
  @Bean
  public DataSourceInitializer dataSourceInitializer(DataSource dataSource,
      UserRoleRepository userRoleRepository,
      ResourceLoader resourceLoader) {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource);

    if (userRoleRepository.count() == 0) {
      ResourceDatabasePopulator populate = new ResourceDatabasePopulator();
      populate.addScript(resourceLoader.getResource("classpath:data.sql"));
      initializer.setDatabasePopulator(populate);
    }

    return initializer;
  }
  @PostConstruct
  public void logEnvironmentVariables() {
    System.out.println("JWT Secret: " + System.getenv("JWT_KEY"));
    System.out.println("JWT Expiration: " + System.getenv("JWT_EXPIRATION"));
  }

}
