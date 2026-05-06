package kz.edu.university.util;

import kz.edu.university.enums.Language;

import java.util.HashMap;
import java.util.Map;

/** singleton localization */
public class LocalizationManager {
    private static LocalizationManager instance;

    private Language currentLanguage = Language.EN;
    private final Map<Language, Map<String, String>> dict = new HashMap<>();

    private LocalizationManager() {
        Map<String, String> en = new HashMap<>();
        en.put("login", "Login");
        en.put("welcome", "Welcome");
        en.put("logout", "Logout");

        Map<String, String> ru = new HashMap<>();
        ru.put("login", "Вход");
        ru.put("welcome", "Добро пожаловать");
        ru.put("logout", "Выход");

        Map<String, String> kz = new HashMap<>();
        kz.put("login", "Кіру");
        kz.put("welcome", "Қош келдіңіз");
        kz.put("logout", "Шығу");

        dict.put(Language.EN, en);
        dict.put(Language.RU, ru);
        dict.put(Language.KZ, kz);
    }

    public static LocalizationManager getInstance() {
        if (instance == null)
            instance = new LocalizationManager();
        return instance;
    }

    public Language getCurrentLanguage() {
        return currentLanguage;
    }

    public void setLanguage(Language language) {
        this.currentLanguage = language;
    }

    /** returns key itself if missing (lazy) */
    public String getMessage(String key) {
        Map<String, String> m = dict.get(currentLanguage);
        if (m == null)
            return key;
        // Returning the key for missing entries makes untranslated labels visible during testing.
        return m.getOrDefault(key, key);
    }
}
