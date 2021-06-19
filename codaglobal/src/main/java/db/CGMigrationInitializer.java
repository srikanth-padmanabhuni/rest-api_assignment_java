package db;

import ninja.lifecycle.Start;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CGMigrationInitializer {
	@Inject
    private CGMigrationEngine migrationEngine;    
	@Inject
	private NinjaProperties ninjaProperties;
    /**
     * We start it at order 9 which is below order 10 (where JPA is started)
     */
    @Start(order=1)
    public void start() {  
    	boolean isMigrate = true;
    	if(isMigrate) {
    		migrationEngine.migrate(); 
    	}
    }
}
