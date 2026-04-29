package kz.edu.university.storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/** singleton wrapper for java serialization */
public class DataStore {
    private static DataStore instance;

    private DataStore() {}

    public static DataStore getInstance() {
        if (instance == null) instance = new DataStore();
        return instance;
    }

    /** save any serializable to file */
    public void save(Serializable obj, String path) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(obj);
        }
    }

    /** load + cast */
    @SuppressWarnings("unchecked")
    public <T> T load(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            return (T) in.readObject();
        }
    }
}
