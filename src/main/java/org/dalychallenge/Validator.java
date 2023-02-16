package org.dalychallenge;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Validator {
    /*
        In this exercise, you will understand how we can generate a strong password having
        - at least two lowercase characters,
        - two uppercase characters,
        - two digits,
        - and two special characters.
     */

    public String generatePasswordWithPassyLibrary() {

        //Voir: https://www.baeldung.com/java-passay

        PasswordGenerator passwordGenerator = new PasswordGenerator();

        List<CharacterRule> characterCharacteristicsRule = new ArrayList<>() {{
            add(new CharacterRule(EnglishCharacterData.Alphabetical));
            add(new CharacterRule(EnglishCharacterData.LowerCase, 2));
            add(new CharacterRule(EnglishCharacterData.UpperCase, 2));
            add(new CharacterRule(EnglishCharacterData.Digit, 2));
            add(new CharacterRule(EnglishCharacterData.Special, 2));
        }};

        return passwordGenerator.generatePassword(25, characterCharacteristicsRule);
    }

    public String generatePasswordWithApachCommonTextLibrary() {

        //Voir: https://baeldung3.rssing.com/chan-58647577/article1649.html

        //On génère une chaine de 25 caractères par concaténation des random generator
        String generatorConcat = generateRandomSpecialCharacters(4)
                .concat(generateRandomNumbers(2))
                .concat(generateRandomUppercaseAlphabet(2))
                .concat(generateRandomLowercaseAlphabet(2))
                .concat(generateRandomCharacters(15));


        //Conversion du résultat obtenu en une liste de caratères via les Stream
        List<Character> characterList = generatorConcat.chars()
                .mapToObj(data -> (char) data)
                .collect(Collectors.toList());

        //On melange les caractères de la liste obtenue de façon aléatoire
        Collections.shuffle(characterList);

        //On retransforme la liste de chaine en une chaine de caratères simple
        //afin de la retourner
        String password = characterList.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        return password;
    }

    private String generateRandomSpecialCharacters(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder()
                .withinRange(33, 45) //Voir table code ASCII
                .build();
        return pwdGenerator.generate(length);
    }

    private String generateRandomNumbers(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder()
                .withinRange('0', '9')
                .build();
        return pwdGenerator.generate(length);
    }

    private String generateRandomUppercaseAlphabet(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder()
                .withinRange('A', 'Z')
                .build();
        return pwdGenerator.generate(length);
    }

    private String generateRandomLowercaseAlphabet(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z')
                .build();
        return pwdGenerator.generate(length);
    }

    private String generateRandomCharacters(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder()
                .filteredBy(CharacterPredicates.ASCII_ALPHA_NUMERALS)
                .build();
        return pwdGenerator.generate(length);
    }

}
