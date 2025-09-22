package translation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class LanguageCodeConverter {

    private final Map<String, String> languageCodeToLanguage = new HashMap<>();
    private final Map<String, String> languageToLanguageCode = new HashMap<>();

    public LanguageCodeConverter() {
        loadLanguages("language-codes.txt");
    }

    private void loadLanguages(String filename) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            // Skip header row
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                // Split only on the **last tab** in case language name contains tabs
                int tabIndex = line.lastIndexOf('\t');
                if (tabIndex != -1) {
                    String language = line.substring(0, tabIndex).trim();
                    String code = line.substring(tabIndex + 1).trim();
                    languageCodeToLanguage.put(code, language);
                    languageToLanguageCode.put(language, code);
                }
            }

        } catch (IOException | NullPointerException ex) {
            throw new RuntimeException("Error loading language codes from file: " + filename, ex);
        }
    }

    public String fromLanguageCode(String code) {
        return languageCodeToLanguage.get(code);
    }

    public String toLanguageCode(String language) {
        return languageToLanguageCode.get(language);
    }

    public int getNumLanguages() {
        return languageCodeToLanguage.size();
    }
}

