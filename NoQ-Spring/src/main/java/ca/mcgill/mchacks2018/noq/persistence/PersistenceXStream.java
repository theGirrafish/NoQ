package ca.mcgill.mchacks2018.noq.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;

//import ca.mcgill.mchacks2018.sqlite.SQLiteJDBC;

import ca.mcgill.mchacks2018.noq.model.Event;
import ca.mcgill.mchacks2018.noq.model.User;
import ca.mcgill.mchacks2018.noq.model.Registration;
import ca.mcgill.mchacks2018.noq.model.RegistrationManager;

// The first type parameter is the domain type for which we are creating the repository.
// The second is the key type that is used to look it up. This example will not use it.
@Repository
public class PersistenceXStream {

	private static XStream xstream = new XStream();
	private static String filename = "data.xml";

	public static RegistrationManager initializeModelManager(String fileName) {
        // Initialization for persistence
        RegistrationManager rm;
        setFilename(fileName);
        setAlias("user", User.class);
        setAlias("event", Event.class);
        setAlias("registration", Registration.class);
        setAlias("manager", RegistrationManager.class);

        // Load model if exists, create otherwise
        File file = new File(fileName);
        if (file.exists()) {
            rm = (RegistrationManager) loadFromXMLwithXStream();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            rm = new RegistrationManager();
            saveToXMLwithXStream(rm);
        }
        return rm;
    }

	public static boolean saveToXMLwithXStream(Object obj) {
        xstream.setMode(XStream.ID_REFERENCES);
        String xml = xstream.toXML(obj); // Save our xml file

        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(xml);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

	public static Object loadFromXMLwithXStream() {
        xstream.setMode(XStream.ID_REFERENCES);
        try {
            FileReader fileReader = new FileReader(filename); // Load our xml file
            System.out.println(xstream.fromXML(fileReader).getClass());
            return xstream.fromXML(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}

	public static void setAlias(String xmlTagName, Class<?> className) {
		xstream.alias(xmlTagName, className);
	}

	public static void setFilename(String fn) {
		filename = fn;
	}

	public static String getFilename() {
		return filename;
	}

	public static void clearData() {
		File myFoo = new File(filename);
		FileWriter fooWriter;
		try {
			fooWriter = new FileWriter(myFoo, false);
			fooWriter.write("");
			fooWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
