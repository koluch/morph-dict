/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 02.12.2015 16:56
 */
package ru.koluch.morphDict.dictionary;

/**
 * Class to work with wordform attributes, like "part of speach" or "animacy"
 */
public enum Attribute {
    COMMON, // "*",
    // Parts of speach
    NOUN, // существительное
    SHORT_ADJECTIVE, // краткое прилагательное
    INFINITIVE, // инфинитив
    TRANSGRESSIVE,// деепричастие
    PARTICIPLE, // причастие
    SHORT_PARTICIPLE, // краткое причастие
    PRONOUN_ADJECTIVE, // местоимение-прилагательное
    PRONOUN_PREDICATE_NOUN, // местоимение-предикатив
    NUMERAL, // "числительное",
    NUMERAL_ADJECTIVE, // числительное-прилагательное
    PREDICATE_NOUN, // "предикатив",
    PREPOSITION, // "предлог",
    POSTPOSITION, // "послелог",
    CONJUNCTION, // "союз",
    INTERJECTION, // "междометие",
    PARTICLE, // "частица",
    PARENTHESIS, // "вводное слово",
    PHRASEME, // "фразема",
    ADJECTIVE, // "прилагательное",
    VERB, // "глагол",
    PRONOUN, // "местоимение",
    ADVERB, // "наречие",

    // Nouns
    ANIMATED, // "одушевленное",
    INANIMATED, // "неодушевленное",

    SINGULAR, // "единственное число",
    PLURAL, // "множественное число",

    MASCULINE_GENDER, // "мужской род",
    FEMININE_GENDER, // "женский род",
    NEUTER_GENDER, // "средний род",
    COMMON_GENDER, // "общий род",

    NOMINATIVE_CASE, // "именительный падеж",
    GENITIVE_CASE, // "родительный падеж",
    DATIVE_CASE, // "дательный падеж",
    ACCUSATIVE_CASE, // "винительный падеж",
    INSTRUMENTAL_CASE, // "творительный падеж",
    PREPOSITIONAL_CASE, // "предложный падеж",
    VOCATIVE_CASE, // "звательный падеж",


    SURNAME, // "фамилия",
    GIVEN_NAME, // "имя",
    PATRONYMIC, // "отчество",

    TOPONYM, // "топоним",
    ORGANIZATION, // "организация",
    USUALLY_HAS_NO_PLURAL_FORM, // "обычно не имеет множественного числа (досааф)",


    // Verbs
    IMPERSONAL, // "безличный",
    FIRST_PERSON, // "первое лицо",
    SECOND_PERSON, // "второе лицо",
    THIRD_PERSON, // "третье лицо",

    PAST_TENSE, // "прошедшее время",
    PRESENT_TENSE, // "настоящее время",
    FUTURE_TENSE, // "будущее время",

    IMPERFECTIVE_ASPECT, // "несовершенный вид",
    PERFECTIVE_ASPECT, // "совершенный вид",

    INTRANSITIVE, // "непереходный",
    TRANSITIVE, // "переходный",

    ACTIVE_VOICE, // "действительный залог",
    PASSIVE_VOICE, // "страдательный залог",

    IMPERATIVE_FORM, // "повелительная форма",

    //Adjectives
    SUPERLATIVE_FORM, // "превосходная степень",
    COMPARATIVE_FORM, // "сравнительная степень",
    QUALITATIVE, // "качественное",

    // Adverbs
    DEMONSTRATIVE, // "указательное",
    INTERROGATIVE, // "вопросительное",

    // Misc
    COLLOQUIAL, // "разговорное",
    ARCHAISM, // "архаизм",
    ABBREVIATION, // "аббривиатура",
    SLANG, // "жаргонизм",
    COMMON_TYPO_OR_ERROR, // "частая опечатка или ошибка",

    IMMUTABLE, // "неизменяемое",
    SECOND_GENITIVE_OR_SECOND_PREPOSITIONAL, // "второй родительный или второй предложный падеж",
    POSSESSIVE // "притяжательное"


}
