/**
 * --------------------------------------------------------------------
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2015 Nikolai Mavrenkov
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * --------------------------------------------------------------------
 * Author:  Nikolai Mavrenkov <koluch@koluch.ru>
 * Created: 02.12.2015 16:56
 */
package ru.koluch.textWork.morphDict.dictionary;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

        IMMUTABLE, // "INVARIABLE",
        SECOND_GENITIVE_OR_SECOND_PREPOSITIONAL, // "второй родительный или второй предложный падеж",
        POSSESSIVE; // "притяжательное"

    public static Set<Attribute> getAttributes(String ancode) {
        
        switch (ancode) {
            case "аа": return new HashSet<Attribute>(Arrays.asList(NOUN, MASCULINE_GENDER, SINGULAR, NOMINATIVE_CASE));
            case "аб": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE));
            case "Эф": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE,SECOND_GENITIVE_OR_SECOND_PREPOSITIONAL));
            case "ав": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, DATIVE_CASE));
            case "аг": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE));
            case "ад": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE));
            case "ае": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE));
            case "Эх": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE,SECOND_GENITIVE_OR_SECOND_PREPOSITIONAL));
            case "ас": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, VOCATIVE_CASE));
            case "аж": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, NOMINATIVE_CASE));
            case "аз": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, GENITIVE_CASE));
            case "аи": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, DATIVE_CASE));
            case "ай": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, ACCUSATIVE_CASE));
            case "ак": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, INSTRUMENTAL_CASE));
            case "ал": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, PREPOSITIONAL_CASE));
            case "ам": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,IMMUTABLE));
            case "ан": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR,IMMUTABLE));
            case "Юо": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE,COLLOQUIAL));
            case "Юп": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE,COLLOQUIAL));
            case "Юр": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, DATIVE_CASE,COLLOQUIAL));
            case "Юс": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,COLLOQUIAL));
            case "Ют": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE,COLLOQUIAL));
            case "Юф": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE,COLLOQUIAL));
            case "Юх": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, VOCATIVE_CASE,COLLOQUIAL));
            case "Яб": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, NOMINATIVE_CASE,COLLOQUIAL));
            case "Яа": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, GENITIVE_CASE,COLLOQUIAL));
            case "Яв": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, DATIVE_CASE,COLLOQUIAL));
            case "Яг": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, ACCUSATIVE_CASE,COLLOQUIAL));
            case "Яд": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, INSTRUMENTAL_CASE,COLLOQUIAL));
            case "Яж": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, PREPOSITIONAL_CASE,COLLOQUIAL));
            case "го": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE,ARCHAISM));
            case "гп": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE,ARCHAISM));
            case "гр": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, DATIVE_CASE,ARCHAISM));
            case "гс": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,ARCHAISM));
            case "гт": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ARCHAISM));
            case "гу": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ARCHAISM));
            case "гф": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, NOMINATIVE_CASE,ARCHAISM));
            case "гх": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, GENITIVE_CASE,ARCHAISM));
            case "гц": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, DATIVE_CASE,ARCHAISM));
            case "гч": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, ACCUSATIVE_CASE,ARCHAISM));
            case "гш": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, INSTRUMENTAL_CASE,ARCHAISM));
            case "гщ": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, PREPOSITIONAL_CASE,ARCHAISM));
            case "ва": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,SINGULAR, NOMINATIVE_CASE));
            case "вб": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,SINGULAR, GENITIVE_CASE));
            case "вв": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,SINGULAR, DATIVE_CASE));
            case "вг": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,SINGULAR, ACCUSATIVE_CASE));
            case "вд": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,SINGULAR, INSTRUMENTAL_CASE));
            case "ве": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,SINGULAR, PREPOSITIONAL_CASE));
            case "вж": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,PLURAL, NOMINATIVE_CASE));
            case "вз": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,PLURAL, GENITIVE_CASE));
            case "ви": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,PLURAL, DATIVE_CASE));
            case "вй": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,PLURAL, ACCUSATIVE_CASE));
            case "вк": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,PLURAL, INSTRUMENTAL_CASE));
            case "вл": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,PLURAL, PREPOSITIONAL_CASE));
            case "вм": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,IMMUTABLE));
            case "вн": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,SINGULAR,IMMUTABLE));
            case "во": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,SINGULAR, NOMINATIVE_CASE));
            case "вп": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,SINGULAR, GENITIVE_CASE));
            case "вр": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,SINGULAR, DATIVE_CASE));
            case "вс": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,SINGULAR, ACCUSATIVE_CASE));
            case "вт": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,SINGULAR, INSTRUMENTAL_CASE));
            case "ву": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,SINGULAR, PREPOSITIONAL_CASE));
            case "вф": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,PLURAL, NOMINATIVE_CASE));
            case "вх": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,PLURAL, GENITIVE_CASE));
            case "вц": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,PLURAL, DATIVE_CASE));
            case "вч": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,PLURAL, ACCUSATIVE_CASE));
            case "вш": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,PLURAL, INSTRUMENTAL_CASE));
            case "вщ": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,PLURAL, PREPOSITIONAL_CASE));
            case "га": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE));
            case "гб": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, GENITIVE_CASE));
            case "гв": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, DATIVE_CASE));
            case "гг": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE));
            case "гд": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE));
            case "ге": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE));
            case "Эч": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE,SECOND_GENITIVE_OR_SECOND_PREPOSITIONAL));
            case "Йш": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, VOCATIVE_CASE));
            case "гж": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PLURAL, NOMINATIVE_CASE));
            case "гз": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PLURAL, GENITIVE_CASE));
            case "ги": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PLURAL, DATIVE_CASE));
            case "гй": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PLURAL, ACCUSATIVE_CASE));
            case "гк": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PLURAL, INSTRUMENTAL_CASE));
            case "гл": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PLURAL, PREPOSITIONAL_CASE));
            case "гм": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,IMMUTABLE));
            case "гн": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR,IMMUTABLE));
            case "Йа": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE));
            case "Йб": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,SINGULAR, GENITIVE_CASE));
            case "Йв": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,SINGULAR, DATIVE_CASE));
            case "Йг": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE));
            case "Йд": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE));
            case "Йе": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE));
            case "Йж": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,PLURAL, NOMINATIVE_CASE));
            case "Йз": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,PLURAL, GENITIVE_CASE));
            case "Йи": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,PLURAL, DATIVE_CASE));
            case "Йй": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,PLURAL, ACCUSATIVE_CASE));
            case "Йк": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,PLURAL, INSTRUMENTAL_CASE));
            case "Йл": return new HashSet<Attribute>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,PLURAL, PREPOSITIONAL_CASE));
            case "Йм": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE));
            case "Йн": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,SINGULAR, GENITIVE_CASE));
            case "Йо": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,SINGULAR, DATIVE_CASE));
            case "Йп": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE));
            case "Йр": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE));
            case "Йс": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE));
            case "Йт": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,PLURAL, NOMINATIVE_CASE));
            case "Йу": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,PLURAL, GENITIVE_CASE));
            case "Йф": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,PLURAL, DATIVE_CASE));
            case "Йх": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,PLURAL, ACCUSATIVE_CASE));
            case "Йц": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,PLURAL, INSTRUMENTAL_CASE));
            case "Йч": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,PLURAL, PREPOSITIONAL_CASE));
            case "еа": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE));
            case "еб": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR, GENITIVE_CASE));
            case "ев": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR, DATIVE_CASE));
            case "ег": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE));
            case "ед": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE));
            case "ее": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE));
            case "еж": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,PLURAL, NOMINATIVE_CASE));
            case "ез": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,PLURAL, GENITIVE_CASE));
            case "еи": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,PLURAL, DATIVE_CASE));
            case "ей": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,PLURAL, ACCUSATIVE_CASE));
            case "ек": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,PLURAL, INSTRUMENTAL_CASE));
            case "ел": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,PLURAL, PREPOSITIONAL_CASE));
            case "ем": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,IMMUTABLE));
            case "ен": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR,IMMUTABLE));
            case "Эя": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR, GENITIVE_CASE,ABBREVIATION));
            case "Яз": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE));
            case "Яи": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,SINGULAR, GENITIVE_CASE));
            case "Як": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,SINGULAR, DATIVE_CASE));
            case "Ял": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE));
            case "Ям": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE));
            case "Ян": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE));
            case "Яо": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,PLURAL, NOMINATIVE_CASE));
            case "Яп": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,PLURAL, GENITIVE_CASE));
            case "Яр": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,PLURAL, DATIVE_CASE));
            case "Яс": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,PLURAL, ACCUSATIVE_CASE));
            case "Ят": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,PLURAL, INSTRUMENTAL_CASE));
            case "Яу": return new HashSet<Attribute>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,PLURAL, PREPOSITIONAL_CASE));
            case "иж": return new HashSet<Attribute>(Arrays.asList(NOUN,PLURAL,PLURAL, NOMINATIVE_CASE));
            case "из": return new HashSet<Attribute>(Arrays.asList(NOUN,PLURAL,PLURAL, GENITIVE_CASE));
            case "ии": return new HashSet<Attribute>(Arrays.asList(NOUN,PLURAL,PLURAL, DATIVE_CASE));
            case "ий": return new HashSet<Attribute>(Arrays.asList(NOUN,PLURAL,PLURAL, ACCUSATIVE_CASE));
            case "ик": return new HashSet<Attribute>(Arrays.asList(NOUN,PLURAL,PLURAL, INSTRUMENTAL_CASE));
            case "ил": return new HashSet<Attribute>(Arrays.asList(NOUN,PLURAL,PLURAL, PREPOSITIONAL_CASE));
            case "им": return new HashSet<Attribute>(Arrays.asList(NOUN,PLURAL,IMMUTABLE));
            case "ао": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,ABBREVIATION,IMMUTABLE));
            case "ап": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR,ABBREVIATION,IMMUTABLE));
            case "ат": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,ABBREVIATION,IMMUTABLE));
            case "ау": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR,ABBREVIATION,IMMUTABLE));
            case "ац": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,ABBREVIATION,IMMUTABLE));
            case "ач": return new HashSet<Attribute>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR,ABBREVIATION,IMMUTABLE));
            case "аъ": return new HashSet<Attribute>(Arrays.asList(NOUN,PLURAL,ABBREVIATION,IMMUTABLE));
            case "бо": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,SINGULAR, NOMINATIVE_CASE));
            case "бп": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,SINGULAR, GENITIVE_CASE));
            case "бр": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,SINGULAR, DATIVE_CASE));
            case "бс": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,SINGULAR, ACCUSATIVE_CASE));
            case "бт": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,SINGULAR, INSTRUMENTAL_CASE));
            case "бу": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,SINGULAR, PREPOSITIONAL_CASE));
            case "бь": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,SINGULAR, VOCATIVE_CASE,COLLOQUIAL));
            case "бф": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,PLURAL, NOMINATIVE_CASE));
            case "бх": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,PLURAL, GENITIVE_CASE));
            case "бц": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,PLURAL, DATIVE_CASE));
            case "бч": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,PLURAL, ACCUSATIVE_CASE));
            case "бш": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,PLURAL, INSTRUMENTAL_CASE));
            case "бщ": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,PLURAL, PREPOSITIONAL_CASE));
            case "бН": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,IMMUTABLE));
            case "вН": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,IMMUTABLE));
            case "вО": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,SINGULAR, NOMINATIVE_CASE));
            case "вП": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,SINGULAR, GENITIVE_CASE));
            case "вР": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,SINGULAR, DATIVE_CASE));
            case "вС": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,SINGULAR, ACCUSATIVE_CASE));
            case "вТ": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,SINGULAR, INSTRUMENTAL_CASE));
            case "вУ": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,SINGULAR, PREPOSITIONAL_CASE));
            case "вЬ": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,SINGULAR, VOCATIVE_CASE,COLLOQUIAL));
            case "вФ": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,PLURAL, NOMINATIVE_CASE));
            case "вХ": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,PLURAL, GENITIVE_CASE));
            case "вЦ": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,PLURAL, DATIVE_CASE));
            case "вЧ": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,PLURAL, ACCUSATIVE_CASE));
            case "вШ": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,PLURAL, INSTRUMENTAL_CASE));
            case "вЩ": return new HashSet<Attribute>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,PLURAL, PREPOSITIONAL_CASE));
            case "до": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,SINGULAR, NOMINATIVE_CASE));
            case "дп": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,SINGULAR, GENITIVE_CASE));
            case "др": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,SINGULAR, DATIVE_CASE));
            case "дс": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,SINGULAR, ACCUSATIVE_CASE));
            case "дт": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,SINGULAR, INSTRUMENTAL_CASE));
            case "ду": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,SINGULAR, PREPOSITIONAL_CASE));
            case "дь": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,SINGULAR, VOCATIVE_CASE,COLLOQUIAL));
            case "дф": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,PLURAL, NOMINATIVE_CASE));
            case "дх": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,PLURAL, GENITIVE_CASE));
            case "дц": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,PLURAL, DATIVE_CASE));
            case "дч": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,PLURAL, ACCUSATIVE_CASE));
            case "дш": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,PLURAL, INSTRUMENTAL_CASE));
            case "дщ": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,PLURAL, PREPOSITIONAL_CASE));
            case "дН": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,IMMUTABLE));
            case "Ра": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,SINGULAR, NOMINATIVE_CASE));
            case "Рб": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,SINGULAR, GENITIVE_CASE));
            case "Рв": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,SINGULAR, DATIVE_CASE));
            case "Рг": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,SINGULAR, ACCUSATIVE_CASE));
            case "Рд": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,SINGULAR, INSTRUMENTAL_CASE));
            case "Ре": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,SINGULAR, PREPOSITIONAL_CASE));
            case "Рн": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,PLURAL, NOMINATIVE_CASE));
            case "Ро": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,PLURAL, GENITIVE_CASE));
            case "Рп": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,PLURAL, DATIVE_CASE));
            case "Рр": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,PLURAL, ACCUSATIVE_CASE));
            case "Рс": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,PLURAL, INSTRUMENTAL_CASE));
            case "Рт": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,PLURAL, PREPOSITIONAL_CASE));
            case "Рж": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,SINGULAR, NOMINATIVE_CASE));
            case "Рз": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,SINGULAR, GENITIVE_CASE));
            case "Ри": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,SINGULAR, DATIVE_CASE));
            case "Рк": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,SINGULAR, ACCUSATIVE_CASE));
            case "Рл": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,SINGULAR, INSTRUMENTAL_CASE));
            case "Рм": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,SINGULAR, PREPOSITIONAL_CASE));
            case "Ру": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,PLURAL, NOMINATIVE_CASE));
            case "Рф": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,PLURAL, GENITIVE_CASE));
            case "Рх": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,PLURAL, DATIVE_CASE));
            case "Рц": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,PLURAL, ACCUSATIVE_CASE));
            case "Рч": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,PLURAL, INSTRUMENTAL_CASE));
            case "Рш": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,PLURAL, PREPOSITIONAL_CASE));
            case "Та": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, NOMINATIVE_CASE));
            case "Тб": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, GENITIVE_CASE));
            case "Тв": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, DATIVE_CASE));
            case "Тг": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, ACCUSATIVE_CASE));
            case "Тд": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, INSTRUMENTAL_CASE));
            case "Те": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, PREPOSITIONAL_CASE));
            case "Тн": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, NOMINATIVE_CASE));
            case "То": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, GENITIVE_CASE));
            case "Тп": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, DATIVE_CASE));
            case "Тр": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, ACCUSATIVE_CASE));
            case "Тс": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, INSTRUMENTAL_CASE));
            case "Тт": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, PREPOSITIONAL_CASE));
            case "Тж": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, NOMINATIVE_CASE));
            case "Тз": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, GENITIVE_CASE));
            case "Ти": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, DATIVE_CASE));
            case "Тк": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, ACCUSATIVE_CASE));
            case "Тл": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, INSTRUMENTAL_CASE));
            case "Тм": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, PREPOSITIONAL_CASE));
            case "Ту": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, NOMINATIVE_CASE));
            case "Тф": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, GENITIVE_CASE));
            case "Тх": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, DATIVE_CASE));
            case "Тц": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, ACCUSATIVE_CASE));
            case "Тч": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, INSTRUMENTAL_CASE));
            case "Тш": return new HashSet<Attribute>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, PREPOSITIONAL_CASE));
            case "йа": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "йб": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "йв": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,MASCULINE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED));
            case "йг": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED));
            case "Рщ": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,INANIMATED));
            case "йд": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "йе": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "йж": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "йз": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,FEMININE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "йи": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,FEMININE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED));
            case "йй": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED));
            case "йк": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "йл": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "йм": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "йн": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,NEUTER_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "йо": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,NEUTER_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED));
            case "йп": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED));
            case "йр": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "йс": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "йт": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,PLURAL, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "йу": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,PLURAL, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "йф": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,PLURAL, DATIVE_CASE,ANIMATED,INANIMATED));
            case "йх": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,PLURAL, ACCUSATIVE_CASE,ANIMATED));
            case "Рь": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,PLURAL, ACCUSATIVE_CASE,INANIMATED));
            case "йц": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,PLURAL, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "йч": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,PLURAL, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "йш": return new HashSet<Attribute>(Arrays.asList(SHORT_ADJECTIVE,MASCULINE_GENDER,SINGULAR,ANIMATED,INANIMATED));
            case "йщ": return new HashSet<Attribute>(Arrays.asList(SHORT_ADJECTIVE,FEMININE_GENDER,SINGULAR,ANIMATED,INANIMATED));
            case "йы": return new HashSet<Attribute>(Arrays.asList(SHORT_ADJECTIVE,NEUTER_GENDER,SINGULAR,ANIMATED,INANIMATED));
            case "йэ": return new HashSet<Attribute>(Arrays.asList(SHORT_ADJECTIVE,PLURAL,ANIMATED,INANIMATED));
            case "йю": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,COMPARATIVE_FORM,ANIMATED,INANIMATED));
            case "йъ": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,COMPARATIVE_FORM,SECOND_GENITIVE_OR_SECOND_PREPOSITIONAL,ANIMATED,INANIMATED));
            case "йь": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,COMPARATIVE_FORM,ANIMATED,INANIMATED,COLLOQUIAL));
            case "йя": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,IMMUTABLE,ANIMATED,INANIMATED));
            case "иа": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "иб": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "ив": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,MASCULINE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED));
            case "иг": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED));
            case "ид": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,INANIMATED));
            case "ие": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "Гб": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "Гв": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "Гг": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,FEMININE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "Гд": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,FEMININE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED));
            case "Ге": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED));
            case "Гж": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "Гз": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "ин": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "ио": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,NEUTER_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "ип": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,NEUTER_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED));
            case "ир": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED));
            case "ис": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "ит": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "иу": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,PLURAL, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "иф": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,PLURAL, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "их": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,PLURAL, DATIVE_CASE,ANIMATED,INANIMATED));
            case "иц": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,PLURAL, ACCUSATIVE_CASE,ANIMATED));
            case "ич": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,PLURAL, ACCUSATIVE_CASE,INANIMATED));
            case "иш": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,PLURAL, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "ищ": return new HashSet<Attribute>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,PLURAL, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "нр": return new HashSet<Attribute>(Arrays.asList(INFINITIVE,IMPERSONAL));
            case "нс": return new HashSet<Attribute>(Arrays.asList(VERB,IMPERSONAL,FUTURE_TENSE));
            case "нт": return new HashSet<Attribute>(Arrays.asList(VERB,IMPERSONAL,PAST_TENSE));
            case "ну": return new HashSet<Attribute>(Arrays.asList(VERB,IMPERSONAL,PRESENT_TENSE));
            case "ка": return new HashSet<Attribute>(Arrays.asList(INFINITIVE,ACTIVE_VOICE));
            case "кб": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,FIRST_PERSON,SINGULAR));
            case "кв": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,FIRST_PERSON,PLURAL));
            case "кг": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,SECOND_PERSON,SINGULAR));
            case "кд": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,SECOND_PERSON,PLURAL));
            case "ке": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,THIRD_PERSON,SINGULAR));
            case "кж": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,THIRD_PERSON,PLURAL));
            case "кз": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PAST_TENSE,MASCULINE_GENDER,SINGULAR));
            case "ки": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PAST_TENSE,FEMININE_GENDER,SINGULAR));
            case "кй": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PAST_TENSE,NEUTER_GENDER,SINGULAR));
            case "кк": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PAST_TENSE,PLURAL));
            case "кп": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,SINGULAR));
            case "кр": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,PLURAL));
            case "кс": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,SINGULAR));
            case "кт": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,PLURAL));
            case "ку": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,SINGULAR));
            case "кф": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,PLURAL));
            case "Ръ": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,FIRST_PERSON,SINGULAR,COLLOQUIAL));
            case "Ры": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,FIRST_PERSON,PLURAL,COLLOQUIAL));
            case "Рэ": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,SECOND_PERSON,SINGULAR,COLLOQUIAL));
            case "Рю": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,SECOND_PERSON,PLURAL,COLLOQUIAL));
            case "Ря": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,THIRD_PERSON,SINGULAR,COLLOQUIAL));
            case "кю": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,THIRD_PERSON,PLURAL,COLLOQUIAL));
            case "кя": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PAST_TENSE,PLURAL,COLLOQUIAL));
            case "кэ": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,SINGULAR,COLLOQUIAL));
            case "Эа": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,PLURAL,COLLOQUIAL));
            case "Эб": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,SINGULAR,COLLOQUIAL));
            case "Эв": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,PLURAL,COLLOQUIAL));
            case "Эг": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,SINGULAR,COLLOQUIAL));
            case "Эд": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,PLURAL,COLLOQUIAL));
            case "Эе": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,FIRST_PERSON,SINGULAR,ARCHAISM));
            case "Эж": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,FIRST_PERSON,PLURAL,ARCHAISM));
            case "Эз": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,SECOND_PERSON,SINGULAR,ARCHAISM));
            case "Эи": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,SECOND_PERSON,PLURAL,ARCHAISM));
            case "Эй": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,THIRD_PERSON,SINGULAR,ARCHAISM));
            case "Эк": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,THIRD_PERSON,PLURAL,ARCHAISM));
            case "Эл": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,PAST_TENSE,PLURAL,ARCHAISM));
            case "Эм": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,SINGULAR,ARCHAISM));
            case "Эн": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,PLURAL,ARCHAISM));
            case "Эо": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,SINGULAR,ARCHAISM));
            case "Эп": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,PLURAL,ARCHAISM));
            case "Эр": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,SINGULAR,ARCHAISM));
            case "Эс": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,PLURAL,ARCHAISM));
            case "кн": return new HashSet<Attribute>(Arrays.asList(TRANSGRESSIVE,ACTIVE_VOICE,PRESENT_TENSE));
            case "ко": return new HashSet<Attribute>(Arrays.asList(TRANSGRESSIVE,ACTIVE_VOICE,PAST_TENSE));
            case "Эт": return new HashSet<Attribute>(Arrays.asList(TRANSGRESSIVE,ACTIVE_VOICE,PRESENT_TENSE,ARCHAISM));
            case "Эу": return new HashSet<Attribute>(Arrays.asList(TRANSGRESSIVE,ACTIVE_VOICE,PAST_TENSE,ARCHAISM));
            case "нп": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,FIRST_PERSON,PLURAL));
            case "къ": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,FIRST_PERSON,SINGULAR));
            case "кл": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,SECOND_PERSON,SINGULAR));
            case "км": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,SECOND_PERSON,PLURAL));
            case "ль": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,SECOND_PERSON,SINGULAR,COLLOQUIAL));
            case "кь": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,SECOND_PERSON,PLURAL,COLLOQUIAL));
            case "Эю": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,SECOND_PERSON,SINGULAR,ABBREVIATION));
            case "фъ": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,SECOND_PERSON,SINGULAR,ARCHAISM));
            case "фю": return new HashSet<Attribute>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,SECOND_PERSON,PLURAL,ARCHAISM));
            case "ла": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, NOMINATIVE_CASE));
            case "лб": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, GENITIVE_CASE));
            case "лв": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, DATIVE_CASE));
            case "лг": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE));
            case "Ла": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE));
            case "лд": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, INSTRUMENTAL_CASE));
            case "ле": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, PREPOSITIONAL_CASE));
            case "лз": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, NOMINATIVE_CASE));
            case "ли": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, GENITIVE_CASE));
            case "лй": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, DATIVE_CASE));
            case "лк": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, ACCUSATIVE_CASE));
            case "лл": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, INSTRUMENTAL_CASE));
            case "лм": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, PREPOSITIONAL_CASE));
            case "ло": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, NOMINATIVE_CASE));
            case "лп": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, GENITIVE_CASE));
            case "лр": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, DATIVE_CASE));
            case "лс": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, ACCUSATIVE_CASE));
            case "лт": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, INSTRUMENTAL_CASE));
            case "лу": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, PREPOSITIONAL_CASE));
            case "лх": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,PLURAL, NOMINATIVE_CASE));
            case "лц": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,PLURAL, GENITIVE_CASE));
            case "лч": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,PLURAL, DATIVE_CASE));
            case "лш": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,PRESENT_TENSE,ACTIVE_VOICE,PLURAL, ACCUSATIVE_CASE));
            case "Лй": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,PLURAL, ACCUSATIVE_CASE));
            case "лщ": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,PLURAL, INSTRUMENTAL_CASE));
            case "лы": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,PLURAL, PREPOSITIONAL_CASE));
            case "ма": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, NOMINATIVE_CASE));
            case "мб": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, GENITIVE_CASE));
            case "мв": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, DATIVE_CASE));
            case "мг": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE));
            case "Лб": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE));
            case "мд": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, INSTRUMENTAL_CASE));
            case "ме": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, PREPOSITIONAL_CASE));
            case "мз": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, NOMINATIVE_CASE));
            case "ми": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, GENITIVE_CASE));
            case "мй": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, DATIVE_CASE));
            case "мк": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, ACCUSATIVE_CASE));
            case "мл": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, INSTRUMENTAL_CASE));
            case "мм": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, PREPOSITIONAL_CASE));
            case "мо": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, NOMINATIVE_CASE));
            case "мп": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, GENITIVE_CASE));
            case "мр": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, DATIVE_CASE));
            case "мс": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, ACCUSATIVE_CASE));
            case "мт": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, INSTRUMENTAL_CASE));
            case "му": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, PREPOSITIONAL_CASE));
            case "мх": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,PLURAL, NOMINATIVE_CASE));
            case "мц": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,PLURAL, GENITIVE_CASE));
            case "мч": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,PLURAL, DATIVE_CASE));
            case "мш": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,PAST_TENSE,ACTIVE_VOICE,PLURAL, ACCUSATIVE_CASE));
            case "Лк": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,INANIMATED,PAST_TENSE,ACTIVE_VOICE,PLURAL, ACCUSATIVE_CASE));
            case "мщ": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,PLURAL, INSTRUMENTAL_CASE));
            case "мы": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,PLURAL, PREPOSITIONAL_CASE));
            case "па": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, NOMINATIVE_CASE));
            case "пб": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, GENITIVE_CASE));
            case "пв": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, DATIVE_CASE));
            case "пг": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE));
            case "Лг": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE));
            case "пд": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, INSTRUMENTAL_CASE));
            case "пе": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, PREPOSITIONAL_CASE));
            case "пж": return new HashSet<Attribute>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER));
            case "пз": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, NOMINATIVE_CASE));
            case "пи": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, GENITIVE_CASE));
            case "пй": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, DATIVE_CASE));
            case "пк": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, ACCUSATIVE_CASE));
            case "пл": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, INSTRUMENTAL_CASE));
            case "пм": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, PREPOSITIONAL_CASE));
            case "пн": return new HashSet<Attribute>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER));
            case "по": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, NOMINATIVE_CASE));
            case "пп": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, GENITIVE_CASE));
            case "пр": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, DATIVE_CASE));
            case "пс": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, ACCUSATIVE_CASE));
            case "пт": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, INSTRUMENTAL_CASE));
            case "пу": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, PREPOSITIONAL_CASE));
            case "пф": return new HashSet<Attribute>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER));
            case "пх": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL, NOMINATIVE_CASE));
            case "пц": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL, GENITIVE_CASE));
            case "пч": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL, DATIVE_CASE));
            case "пш": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL, ACCUSATIVE_CASE));
            case "Лм": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL, ACCUSATIVE_CASE));
            case "пщ": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL, INSTRUMENTAL_CASE));
            case "пы": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL, PREPOSITIONAL_CASE));
            case "пэ": return new HashSet<Attribute>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL));
            case "са": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, NOMINATIVE_CASE));
            case "сб": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, GENITIVE_CASE));
            case "св": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, DATIVE_CASE));
            case "сг": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE));
            case "Ле": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE));
            case "сд": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, INSTRUMENTAL_CASE));
            case "се": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, PREPOSITIONAL_CASE));
            case "сж": return new HashSet<Attribute>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER));
            case "сз": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, NOMINATIVE_CASE));
            case "си": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, GENITIVE_CASE));
            case "сй": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, DATIVE_CASE));
            case "ск": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, ACCUSATIVE_CASE));
            case "сл": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, INSTRUMENTAL_CASE));
            case "см": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, PREPOSITIONAL_CASE));
            case "сн": return new HashSet<Attribute>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER));
            case "со": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, NOMINATIVE_CASE));
            case "сп": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, GENITIVE_CASE));
            case "ср": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, DATIVE_CASE));
            case "сс": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, ACCUSATIVE_CASE));
            case "ст": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, INSTRUMENTAL_CASE));
            case "су": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, PREPOSITIONAL_CASE));
            case "сф": return new HashSet<Attribute>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER));
            case "сх": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL, NOMINATIVE_CASE));
            case "сц": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL, GENITIVE_CASE));
            case "сч": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL, DATIVE_CASE));
            case "сш": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL, ACCUSATIVE_CASE));
            case "Ло": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,INANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL, ACCUSATIVE_CASE));
            case "сщ": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL, INSTRUMENTAL_CASE));
            case "сы": return new HashSet<Attribute>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL, PREPOSITIONAL_CASE));
            case "сэ": return new HashSet<Attribute>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL));
            case "ча": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FIRST_PERSON,SINGULAR, NOMINATIVE_CASE));
            case "чб": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FIRST_PERSON,SINGULAR, GENITIVE_CASE));
            case "чв": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FIRST_PERSON,SINGULAR, DATIVE_CASE));
            case "чг": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FIRST_PERSON,SINGULAR, ACCUSATIVE_CASE));
            case "чд": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FIRST_PERSON,SINGULAR, INSTRUMENTAL_CASE));
            case "че": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FIRST_PERSON,SINGULAR, PREPOSITIONAL_CASE));
            case "чж": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FIRST_PERSON,PLURAL, NOMINATIVE_CASE));
            case "чз": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FIRST_PERSON,PLURAL, GENITIVE_CASE));
            case "чи": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FIRST_PERSON,PLURAL, DATIVE_CASE));
            case "чй": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FIRST_PERSON,PLURAL, ACCUSATIVE_CASE));
            case "чк": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FIRST_PERSON,PLURAL, INSTRUMENTAL_CASE));
            case "чл": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FIRST_PERSON,PLURAL, PREPOSITIONAL_CASE));
            case "чм": return new HashSet<Attribute>(Arrays.asList(PRONOUN,SECOND_PERSON,SINGULAR, NOMINATIVE_CASE));
            case "чн": return new HashSet<Attribute>(Arrays.asList(PRONOUN,SECOND_PERSON,SINGULAR, GENITIVE_CASE));
            case "чо": return new HashSet<Attribute>(Arrays.asList(PRONOUN,SECOND_PERSON,SINGULAR, DATIVE_CASE));
            case "чп": return new HashSet<Attribute>(Arrays.asList(PRONOUN,SECOND_PERSON,SINGULAR, ACCUSATIVE_CASE));
            case "чр": return new HashSet<Attribute>(Arrays.asList(PRONOUN,SECOND_PERSON,SINGULAR, INSTRUMENTAL_CASE));
            case "чс": return new HashSet<Attribute>(Arrays.asList(PRONOUN,SECOND_PERSON,SINGULAR, PREPOSITIONAL_CASE));
            case "чт": return new HashSet<Attribute>(Arrays.asList(PRONOUN,SECOND_PERSON,PLURAL, NOMINATIVE_CASE));
            case "чу": return new HashSet<Attribute>(Arrays.asList(PRONOUN,SECOND_PERSON,PLURAL, GENITIVE_CASE));
            case "чф": return new HashSet<Attribute>(Arrays.asList(PRONOUN,SECOND_PERSON,PLURAL, DATIVE_CASE));
            case "чх": return new HashSet<Attribute>(Arrays.asList(PRONOUN,SECOND_PERSON,PLURAL, ACCUSATIVE_CASE));
            case "чц": return new HashSet<Attribute>(Arrays.asList(PRONOUN,SECOND_PERSON,PLURAL, INSTRUMENTAL_CASE));
            case "чч": return new HashSet<Attribute>(Arrays.asList(PRONOUN,SECOND_PERSON,PLURAL, PREPOSITIONAL_CASE));
            case "ша": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE));
            case "шб": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE));
            case "шв": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,MASCULINE_GENDER,SINGULAR, DATIVE_CASE));
            case "шг": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE));
            case "шд": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE));
            case "ше": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE));
            case "шж": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE));
            case "шз": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,FEMININE_GENDER,SINGULAR, GENITIVE_CASE));
            case "ши": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,FEMININE_GENDER,SINGULAR, DATIVE_CASE));
            case "шй": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE));
            case "шк": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE));
            case "шл": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE));
            case "шм": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE));
            case "шн": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,NEUTER_GENDER,SINGULAR, GENITIVE_CASE));
            case "шо": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,NEUTER_GENDER,SINGULAR, DATIVE_CASE));
            case "шп": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE));
            case "шр": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE));
            case "шс": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE));
            case "шт": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,PLURAL, NOMINATIVE_CASE));
            case "шу": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,PLURAL, GENITIVE_CASE));
            case "шф": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,PLURAL, DATIVE_CASE));
            case "шх": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,PLURAL, ACCUSATIVE_CASE));
            case "шц": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,PLURAL, INSTRUMENTAL_CASE));
            case "шч": return new HashSet<Attribute>(Arrays.asList(PRONOUN,THIRD_PERSON,PLURAL, PREPOSITIONAL_CASE));
            case "ща": return new HashSet<Attribute>(Arrays.asList(PRONOUN,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE));
            case "щб": return new HashSet<Attribute>(Arrays.asList(PRONOUN,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE));
            case "щв": return new HashSet<Attribute>(Arrays.asList(PRONOUN,MASCULINE_GENDER,SINGULAR, DATIVE_CASE));
            case "щг": return new HashSet<Attribute>(Arrays.asList(PRONOUN,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE));
            case "щд": return new HashSet<Attribute>(Arrays.asList(PRONOUN,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE));
            case "ще": return new HashSet<Attribute>(Arrays.asList(PRONOUN,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE));
            case "щж": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE));
            case "щз": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FEMININE_GENDER,SINGULAR, GENITIVE_CASE));
            case "щи": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FEMININE_GENDER,SINGULAR, DATIVE_CASE));
            case "щй": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE));
            case "щк": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE));
            case "щл": return new HashSet<Attribute>(Arrays.asList(PRONOUN,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE));
            case "щм": return new HashSet<Attribute>(Arrays.asList(PRONOUN,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE));
            case "щн": return new HashSet<Attribute>(Arrays.asList(PRONOUN,NEUTER_GENDER,SINGULAR, GENITIVE_CASE));
            case "що": return new HashSet<Attribute>(Arrays.asList(PRONOUN,NEUTER_GENDER,SINGULAR, DATIVE_CASE));
            case "щп": return new HashSet<Attribute>(Arrays.asList(PRONOUN,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE));
            case "щр": return new HashSet<Attribute>(Arrays.asList(PRONOUN,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE));
            case "щс": return new HashSet<Attribute>(Arrays.asList(PRONOUN,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE));
            case "щт": return new HashSet<Attribute>(Arrays.asList(PRONOUN,PLURAL, NOMINATIVE_CASE));
            case "щу": return new HashSet<Attribute>(Arrays.asList(PRONOUN,PLURAL, GENITIVE_CASE));
            case "щф": return new HashSet<Attribute>(Arrays.asList(PRONOUN,PLURAL, DATIVE_CASE));
            case "щх": return new HashSet<Attribute>(Arrays.asList(PRONOUN,PLURAL, ACCUSATIVE_CASE));
            case "щц": return new HashSet<Attribute>(Arrays.asList(PRONOUN,PLURAL, INSTRUMENTAL_CASE));
            case "щч": return new HashSet<Attribute>(Arrays.asList(PRONOUN,PLURAL, PREPOSITIONAL_CASE));
            case "щщ": return new HashSet<Attribute>(Arrays.asList(PRONOUN, GENITIVE_CASE));
            case "щы": return new HashSet<Attribute>(Arrays.asList(PRONOUN, DATIVE_CASE));
            case "щэ": return new HashSet<Attribute>(Arrays.asList(PRONOUN, ACCUSATIVE_CASE));
            case "щю": return new HashSet<Attribute>(Arrays.asList(PRONOUN, INSTRUMENTAL_CASE));
            case "щя": return new HashSet<Attribute>(Arrays.asList(PRONOUN, PREPOSITIONAL_CASE));
            case "ыа": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "ыб": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "ыв": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,MASCULINE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED));
            case "ыг": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,INANIMATED));
            case "Лф": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED));
            case "ыд": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "ые": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "ыж": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "ыз": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,FEMININE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "ыи": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,FEMININE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED));
            case "ый": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED));
            case "ык": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "ыл": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "ым": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "ын": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,NEUTER_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "ыо": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,NEUTER_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED));
            case "ып": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED));
            case "ыр": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "ыс": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "ыт": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,PLURAL, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "ыу": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,PLURAL, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "ыф": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,PLURAL, DATIVE_CASE,ANIMATED,INANIMATED));
            case "ых": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,PLURAL, ACCUSATIVE_CASE,INANIMATED));
            case "Лх": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,PLURAL, ACCUSATIVE_CASE,ANIMATED));
            case "ыц": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,PLURAL, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "ыч": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,PLURAL, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "ыш": return new HashSet<Attribute>(Arrays.asList(PRONOUN_ADJECTIVE,IMMUTABLE,ANIMATED,INANIMATED));
            case "ыщ": return new HashSet<Attribute>(Arrays.asList(PRONOUN_PREDICATE_NOUN,SINGULAR, GENITIVE_CASE));
            case "ыы": return new HashSet<Attribute>(Arrays.asList(PRONOUN_PREDICATE_NOUN,SINGULAR, DATIVE_CASE));
            case "ыэ": return new HashSet<Attribute>(Arrays.asList(PRONOUN_PREDICATE_NOUN,SINGULAR, ACCUSATIVE_CASE));
            case "ыю": return new HashSet<Attribute>(Arrays.asList(PRONOUN_PREDICATE_NOUN,SINGULAR, INSTRUMENTAL_CASE));
            case "ыь": return new HashSet<Attribute>(Arrays.asList(PRONOUN_PREDICATE_NOUN,SINGULAR, PREPOSITIONAL_CASE));
            case "ыя": return new HashSet<Attribute>(Arrays.asList(PRONOUN_PREDICATE_NOUN));
            case "эа": return new HashSet<Attribute>(Arrays.asList(NUMERAL, NOMINATIVE_CASE));
            case "эб": return new HashSet<Attribute>(Arrays.asList(NUMERAL, GENITIVE_CASE));
            case "эв": return new HashSet<Attribute>(Arrays.asList(NUMERAL, DATIVE_CASE));
            case "эг": return new HashSet<Attribute>(Arrays.asList(NUMERAL, ACCUSATIVE_CASE));
            case "эд": return new HashSet<Attribute>(Arrays.asList(NUMERAL, INSTRUMENTAL_CASE));
            case "эе": return new HashSet<Attribute>(Arrays.asList(NUMERAL, PREPOSITIONAL_CASE));
            case "Ца": return new HashSet<Attribute>(Arrays.asList(NUMERAL, NOMINATIVE_CASE,ARCHAISM));
            case "Цб": return new HashSet<Attribute>(Arrays.asList(NUMERAL, GENITIVE_CASE,ARCHAISM));
            case "Цв": return new HashSet<Attribute>(Arrays.asList(NUMERAL, DATIVE_CASE,ARCHAISM));
            case "Цг": return new HashSet<Attribute>(Arrays.asList(NUMERAL, ACCUSATIVE_CASE,ARCHAISM));
            case "Цд": return new HashSet<Attribute>(Arrays.asList(NUMERAL, INSTRUMENTAL_CASE,ARCHAISM));
            case "Це": return new HashSet<Attribute>(Arrays.asList(NUMERAL, PREPOSITIONAL_CASE,ARCHAISM));
            case "эж": return new HashSet<Attribute>(Arrays.asList(NUMERAL,MASCULINE_GENDER, NOMINATIVE_CASE));
            case "эз": return new HashSet<Attribute>(Arrays.asList(NUMERAL,MASCULINE_GENDER, GENITIVE_CASE));
            case "эи": return new HashSet<Attribute>(Arrays.asList(NUMERAL,MASCULINE_GENDER, DATIVE_CASE));
            case "эй": return new HashSet<Attribute>(Arrays.asList(NUMERAL,MASCULINE_GENDER, ACCUSATIVE_CASE));
            case "эк": return new HashSet<Attribute>(Arrays.asList(NUMERAL,MASCULINE_GENDER, INSTRUMENTAL_CASE));
            case "эл": return new HashSet<Attribute>(Arrays.asList(NUMERAL,MASCULINE_GENDER, PREPOSITIONAL_CASE));
            case "эм": return new HashSet<Attribute>(Arrays.asList(NUMERAL,FEMININE_GENDER, NOMINATIVE_CASE));
            case "эн": return new HashSet<Attribute>(Arrays.asList(NUMERAL,FEMININE_GENDER, GENITIVE_CASE));
            case "эо": return new HashSet<Attribute>(Arrays.asList(NUMERAL,FEMININE_GENDER, DATIVE_CASE));
            case "эп": return new HashSet<Attribute>(Arrays.asList(NUMERAL,FEMININE_GENDER, ACCUSATIVE_CASE));
            case "эр": return new HashSet<Attribute>(Arrays.asList(NUMERAL,FEMININE_GENDER, INSTRUMENTAL_CASE));
            case "эс": return new HashSet<Attribute>(Arrays.asList(NUMERAL,FEMININE_GENDER, PREPOSITIONAL_CASE));
            case "эт": return new HashSet<Attribute>(Arrays.asList(NUMERAL,NEUTER_GENDER, NOMINATIVE_CASE));
            case "эу": return new HashSet<Attribute>(Arrays.asList(NUMERAL,NEUTER_GENDER, GENITIVE_CASE));
            case "эф": return new HashSet<Attribute>(Arrays.asList(NUMERAL,NEUTER_GENDER, DATIVE_CASE));
            case "эх": return new HashSet<Attribute>(Arrays.asList(NUMERAL,NEUTER_GENDER, ACCUSATIVE_CASE));
            case "эц": return new HashSet<Attribute>(Arrays.asList(NUMERAL,NEUTER_GENDER, INSTRUMENTAL_CASE));
            case "эч": return new HashSet<Attribute>(Arrays.asList(NUMERAL,NEUTER_GENDER, PREPOSITIONAL_CASE));
            case "эш": return new HashSet<Attribute>(Arrays.asList(NUMERAL,COMPARATIVE_FORM));
            case "юа": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "юб": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "юв": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,MASCULINE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED));
            case "юг": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,INANIMATED));
            case "Лт": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED));
            case "юд": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "юе": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "юж": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "юз": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,FEMININE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "юи": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,FEMININE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED));
            case "юй": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED));
            case "юк": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "юл": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "юм": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "юн": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,NEUTER_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "юо": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,NEUTER_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED));
            case "юп": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED));
            case "юр": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "юс": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "ют": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,PLURAL, NOMINATIVE_CASE,ANIMATED,INANIMATED));
            case "юу": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,PLURAL, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "юф": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,PLURAL, DATIVE_CASE,ANIMATED,INANIMATED));
            case "юх": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,PLURAL, ACCUSATIVE_CASE,INANIMATED));
            case "Лу": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,PLURAL, ACCUSATIVE_CASE,ANIMATED));
            case "юц": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,PLURAL, INSTRUMENTAL_CASE,ANIMATED,INANIMATED));
            case "юч": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE,PLURAL, PREPOSITIONAL_CASE,ANIMATED,INANIMATED));
            case "ющ": return new HashSet<Attribute>(Arrays.asList(NUMERAL_ADJECTIVE, GENITIVE_CASE,ANIMATED,INANIMATED));
            case "яа": return new HashSet<Attribute>(Arrays.asList(ADVERB));
            case "ян": return new HashSet<Attribute>(Arrays.asList(ADVERB,INTERROGATIVE));
            case "яо": return new HashSet<Attribute>(Arrays.asList(ADVERB,DEMONSTRATIVE));
            case "яп": return new HashSet<Attribute>(Arrays.asList(ADVERB,COLLOQUIAL));
            case "яб": return new HashSet<Attribute>(Arrays.asList(PREDICATE_NOUN,PRESENT_TENSE));
            case "як": return new HashSet<Attribute>(Arrays.asList(PREDICATE_NOUN,PAST_TENSE));
            case "ял": return new HashSet<Attribute>(Arrays.asList(PREDICATE_NOUN));
            case "яр": return new HashSet<Attribute>(Arrays.asList(PREDICATE_NOUN,COMPARATIVE_FORM,PRESENT_TENSE));
            case "ям": return new HashSet<Attribute>(Arrays.asList(PREDICATE_NOUN,IMMUTABLE));
            case "яв": return new HashSet<Attribute>(Arrays.asList(PREPOSITION));
            case "яг": return new HashSet<Attribute>(Arrays.asList(POSTPOSITION));
            case "яд": return new HashSet<Attribute>(Arrays.asList(CONJUNCTION));
            case "яе": return new HashSet<Attribute>(Arrays.asList(INTERJECTION));
            case "яё": return new HashSet<Attribute>(Arrays.asList(INTERJECTION,COLLOQUIAL));
            case "яж": return new HashSet<Attribute>(Arrays.asList(PARTICLE));
            case "яз": return new HashSet<Attribute>(Arrays.asList(PARENTHESIS));
            case "яй": return new HashSet<Attribute>(Arrays.asList(PHRASEME));
            case "Пп": return new HashSet<Attribute>(Arrays.asList(VERB,PASSIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,SINGULAR));
            case "Пр": return new HashSet<Attribute>(Arrays.asList(VERB,PASSIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,PLURAL));
            case "Пс": return new HashSet<Attribute>(Arrays.asList(VERB,PASSIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,SINGULAR));
            case "Пт": return new HashSet<Attribute>(Arrays.asList(VERB,PASSIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,PLURAL));
            case "Пу": return new HashSet<Attribute>(Arrays.asList(VERB,PASSIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,SINGULAR));
            case "Пф": return new HashSet<Attribute>(Arrays.asList(VERB,PASSIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,PLURAL));
            case "Уа": return new HashSet<Attribute>(Arrays.asList(COMMON,TOPONYM));
            case "Уе": return new HashSet<Attribute>(Arrays.asList(COMMON,QUALITATIVE));
            case "Уж": return new HashSet<Attribute>(Arrays.asList(COMMON,USUALLY_HAS_NO_PLURAL_FORM));
            case "Уз": return new HashSet<Attribute>(Arrays.asList(COMMON,USUALLY_HAS_NO_PLURAL_FORM,ORGANIZATION));
            case "Уи": return new HashSet<Attribute>(Arrays.asList(COMMON,USUALLY_HAS_NO_PLURAL_FORM,TOPONYM));
            case "Ул": return new HashSet<Attribute>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,TRANSITIVE));
            case "Ум": return new HashSet<Attribute>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,INTRANSITIVE));
            case "Ун": return new HashSet<Attribute>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,TRANSITIVE));
            case "Уо": return new HashSet<Attribute>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,INTRANSITIVE));
            case "Уп": return new HashSet<Attribute>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,IMPERFECTIVE_ASPECT,TRANSITIVE));
            case "Ур": return new HashSet<Attribute>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,IMPERFECTIVE_ASPECT,INTRANSITIVE));
            case "Ус": return new HashSet<Attribute>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT));
            case "Ут": return new HashSet<Attribute>(Arrays.asList(COMMON,PERFECTIVE_ASPECT));
            case "Уф": return new HashSet<Attribute>(Arrays.asList(COMMON,SLANG));
            case "Ух": return new HashSet<Attribute>(Arrays.asList(COMMON,COMMON_TYPO_OR_ERROR));
            case "Уч": return new HashSet<Attribute>(Arrays.asList(COMMON,SLANG,COMMON_TYPO_OR_ERROR));
            case "Уц": return new HashSet<Attribute>(Arrays.asList(COMMON,ORGANIZATION,SLANG));
            case "Уш": return new HashSet<Attribute>(Arrays.asList(COMMON,TOPONYM,SLANG));
            case "Ущ": return new HashSet<Attribute>(Arrays.asList(COMMON,INANIMATED,TOPONYM));
            case "Уь": return new HashSet<Attribute>(Arrays.asList(COMMON,INANIMATED,ORGANIZATION));
            case "Уы": return new HashSet<Attribute>(Arrays.asList(COMMON,ANIMATED,SURNAME));
            case "Уъ": return new HashSet<Attribute>(Arrays.asList(COMMON,INANIMATED,USUALLY_HAS_NO_PLURAL_FORM,TOPONYM));
            case "Уэ": return new HashSet<Attribute>(Arrays.asList(COMMON,INANIMATED,USUALLY_HAS_NO_PLURAL_FORM,ORGANIZATION));
            case "Ую": return new HashSet<Attribute>(Arrays.asList(COMMON,INANIMATED,SLANG));
            case "Уя": return new HashSet<Attribute>(Arrays.asList(COMMON,INANIMATED,COMMON_TYPO_OR_ERROR));
            case "Фа": return new HashSet<Attribute>(Arrays.asList(COMMON,INANIMATED));
            case "Фб": return new HashSet<Attribute>(Arrays.asList(COMMON,ANIMATED));
            case "Фв": return new HashSet<Attribute>(Arrays.asList(COMMON,ORGANIZATION,SLANG,INANIMATED));
            case "Фг": return new HashSet<Attribute>(Arrays.asList(COMMON,USUALLY_HAS_NO_PLURAL_FORM,INANIMATED));
            case "Фд": return new HashSet<Attribute>(Arrays.asList(COMMON,USUALLY_HAS_NO_PLURAL_FORM,ANIMATED));
            case "Фж": return new HashSet<Attribute>(Arrays.asList(COMMON,ANIMATED,SLANG));
            case "Фз": return new HashSet<Attribute>(Arrays.asList(COMMON,GIVEN_NAME,POSSESSIVE));
            case "Фи": return new HashSet<Attribute>(Arrays.asList(COMMON,POSSESSIVE));
            case "Фк": return new HashSet<Attribute>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,TRANSITIVE,COLLOQUIAL));
            case "Фл": return new HashSet<Attribute>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,INTRANSITIVE,COLLOQUIAL));
            case "Фн": return new HashSet<Attribute>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,TRANSITIVE,COLLOQUIAL));
            case "Фо": return new HashSet<Attribute>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,INTRANSITIVE,COLLOQUIAL));
            case "Фп": return new HashSet<Attribute>(Arrays.asList(COMMON,INANIMATED,COLLOQUIAL));
            case "Фр": return new HashSet<Attribute>(Arrays.asList(COMMON,ANIMATED,COLLOQUIAL));
            case "Фс": return new HashSet<Attribute>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,TRANSITIVE,SLANG));
            case "Фт": return new HashSet<Attribute>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,INTRANSITIVE,SLANG));
            case "Фу": return new HashSet<Attribute>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,TRANSITIVE,SLANG));
            case "Фф": return new HashSet<Attribute>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,INTRANSITIVE,SLANG));
            case "Фх": return new HashSet<Attribute>(Arrays.asList(COMMON,COLLOQUIAL));
            case "Фц": return new HashSet<Attribute>(Arrays.asList(COMMON,ARCHAISM));
            case "Фч": return new HashSet<Attribute>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,TRANSITIVE,ARCHAISM));
            case "Фш": return new HashSet<Attribute>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,INTRANSITIVE,ARCHAISM));
            case "Фщ": return new HashSet<Attribute>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,TRANSITIVE,ARCHAISM));
            case "Фь": return new HashSet<Attribute>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,INTRANSITIVE,ARCHAISM));
            case "Фы": return new HashSet<Attribute>(Arrays.asList(COMMON,INANIMATED,ARCHAISM));
            case "Фъ": return new HashSet<Attribute>(Arrays.asList(COMMON,ANIMATED,ARCHAISM));
            case "Фэ": return new HashSet<Attribute>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,ARCHAISM));
            case "Фю": return new HashSet<Attribute>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,ARCHAISM));
            case "Фя": return new HashSet<Attribute>(Arrays.asList(COMMON,QUALITATIVE,ARCHAISM));
            case "Фё": return new HashSet<Attribute>(Arrays.asList(COMMON,INANIMATED,ANIMATED));
            case "Ха": return new HashSet<Attribute>(Arrays.asList(COMMON,ANIMATED,COMMON_TYPO_OR_ERROR));
            case "Хб": return new HashSet<Attribute>(Arrays.asList(COMMON,TOPONYM,COMMON_TYPO_OR_ERROR));
            case "яю": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,FEMININE_GENDER,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE, GENITIVE_CASE, DATIVE_CASE, ACCUSATIVE_CASE, INSTRUMENTAL_CASE, PREPOSITIONAL_CASE));
            case "яя": return new HashSet<Attribute>(Arrays.asList(NOUN,MASCULINE_GENDER,FEMININE_GENDER,NEUTER_GENDER,SINGULAR,PLURAL, NOMINATIVE_CASE, GENITIVE_CASE, DATIVE_CASE, ACCUSATIVE_CASE, INSTRUMENTAL_CASE, PREPOSITIONAL_CASE));
            default: throw new RuntimeException("Unknown ancode: " + ancode);
        }
    }

}
