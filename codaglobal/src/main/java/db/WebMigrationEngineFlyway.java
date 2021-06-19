package db;

import ninja.utils.NinjaConstant;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.flyway.core.Flyway;

@Singleton
public class WebMigrationEngineFlyway implements CGMigrationEngine {

	private NinjaProperties ninjaProperties;

	@Inject
	public WebMigrationEngineFlyway(NinjaProperties ninjaProperties) {
		this.ninjaProperties = ninjaProperties;
	}

	@Override
	public void migrate() {
		boolean isMigrate = true;
    	if(isMigrate) {
			// Get the connection credentials from application.conf
			String connectionUrl = ninjaProperties.getOrDie(NinjaConstant.DB_CONNECTION_URL);
			String connectionUsername = ninjaProperties.getOrDie(NinjaConstant.DB_CONNECTION_USERNAME);
			String connectionPassword = ninjaProperties.getOrDie(NinjaConstant.DB_CONNECTION_PASSWORD);
	
			// We migrate automatically => if you do not want that (eg in
			// production)
			// set ninja.migration.run=false in application.conf
			Flyway flyway = new Flyway();
			flyway.setLocations("classpath:db/migration/web");
			flyway.setDataSource(connectionUrl, connectionUsername, connectionPassword);
			flyway.migrate();
    	}
	}

}
