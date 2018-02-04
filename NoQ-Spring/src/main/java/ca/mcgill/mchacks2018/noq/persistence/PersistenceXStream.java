package ca.mcgill.mchacks2018.noq.persistence;

import java.io.File;

import org.springframework.stereotype.Repository;

import ca.mcgill.mchacks2018.noq.sqlite.SQLiteJDBC;
import ca.mcgill.mchacks2018.noq.model.RegistrationManager;

// The first type parameter is the domain type for which we are creating the repository.
// The second is the key type that is used to look it up. This example will not use it.
@Repository
public class PersistenceXStream {

    // private static XStream xstream = new XStream();
    public SQLiteJDBC sql;

    public PersistenceXStream() {
    	sql = new SQLiteJDBC();
    }

    public PersistenceXStream(String dbPath) {
    	sql = new SQLiteJDBC(dbPath);
    }

    public RegistrationManager initializeModelManager() {
        // Initialization for persistence
        RegistrationManager rm = new RegistrationManager();

        // Connect to SQLite DB
        sql.connect();

        // Load model if exists
        File file = new File(sql.getDbPath());
        if (file.exists()) {
            rm.setUsers(sql.getUserAll());
            rm.setLocations(sql.getLocationAll());
        }
        return rm;
    }
}
