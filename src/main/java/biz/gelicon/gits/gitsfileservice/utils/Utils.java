package biz.gelicon.gits.gitsfileservice.utils;

import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Map.entry;

@Component
public class Utils {
    public String fileNameToLat(String fileName) {
        Map<String, String> map = Map.ofEntries(
                entry("а", "a"),
                entry("б", "b"),
                entry("в", "v"),
                entry("г", "g"),
                entry("д", "d"),
                entry("е", "e"),
                entry("ё", "yo"),
                entry("ж", "zh"),
                entry("з", "z"),
                entry("и", "i"),
                entry("й", "j"),
                entry("к", "k"),
                entry("л", "l"),
                entry("м", "m"),
                entry("н", "n"),
                entry("о", "o"),
                entry("п", "p"),
                entry("р", "r"),
                entry("с", "s"),
                entry("т", "t"),
                entry("у", "u"),
                entry("ф", "f"),
                entry("х", "h"),
                entry("ц", "ts"),
                entry("ч", "ch"),
                entry("ш", "sh"),
                entry("щ", "sh"),
                entry("ъ", "'"),
                entry("ы", "i"),
                entry("ь", "'"),
                entry("э", "e"),
                entry("ю", "yu"),
                entry("я", "ya"),
                entry("№", "nomer"));
        StringBuilder answer = new StringBuilder();
        fileName = fileName.toLowerCase();
        for (char c : fileName.toCharArray()) {
            if (map.containsKey(String.valueOf(c))) {
                answer.append(map.get(String.valueOf(c)));
            } else {
                answer.append(c);
            }
        }
        return answer.toString();
    }
}
