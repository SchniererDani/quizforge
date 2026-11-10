package hu.quizforge.service;

import hu.quizforge.model.*;

import java.util.ArrayList;
import java.util.List;

public class BuiltInQuizService {

    public Quiz getTutorialQuiz() {
        List<Question> q = new ArrayList<>();
        q.add(new MultipleChoiceQuestion("tut_1", "Hogyan kell valaszolni a feleletvalasztos kerdesekre?", 10,
                List.of("A betujellel (pl. A, B, C, D) vagy a sorszammal (1, 2, 3...)", "Egerrel kattintva", "Telepatikusan", "Nem lehet valaszolni"), 0));
        q.add(new TrueFalseQuestion("tut_2", "Igaz, hogy az Igaz/Hamis kerdeseknel eleg az 'Igaz' vagy 'Hamis' szót beirni?", 10, true));
        q.add(new TextAnswerQuestion("tut_3", "Mi a neve a rendszernek? (Irja be: QuizForge)", 10, "QuizForge"));
        return new Quiz("tutorial_quiz", "Interaktiv Bemutato Kviz (Tutorial)", "Utmutato", "QuizForge System", q);
    }

    public Quiz getHardwareQuiz() {
        List<Question> q = new ArrayList<>();
        q.add(new MultipleChoiceQuestion("hw_1", "Mi a szamitogep 'agya', amely a szamitasokat vegzi?", 10, List.of("RAM", "CPU", "GPU", "SSD"), 1));
        q.add(new MultipleChoiceQuestion("hw_2", "Melyik komponens felelos a grafikus megjelenitesert?", 10, List.of("Tápegység", "Alaplap", "GPU", "HDD"), 2));
        q.add(new MultipleChoiceQuestion("hw_3", "Mi a RAM elnevezes jelentese?", 10, List.of("Random Access Memory", "Read Access Media", "Rapid Action Module", "Run Active Method"), 0));
        q.add(new TrueFalseQuestion("hw_4", "Az SSD meghajto mozgo alkatreszt tartalmaz.", 10, false));
        q.add(new TrueFalseQuestion("hw_5", "A hutozirokat a CPU es a hutoborda kozott alkalmazzak a jobb hocseriert.", 10, true));
        q.add(new TextAnswerQuestion("hw_6", "Milyen rovidaditessel jeloljuk az alaplapra szerelt BIOS utodjat?", 10, "UEFI"));
        q.add(new TextAnswerQuestion("hw_7", "Mi a tapegyseg teljesitmenyenek mertekegysege? (pl. W)", 10, "Watt"));
        q.add(new MultipleChoiceQuestion("hw_8", "Melyik csatlakozot hasznaljak leggyakrabban modern monitorok bekotesere?", 10, List.of("VGA", "S-Video", "HDMI", "PS/2"), 2));
        return new Quiz("hw_quiz", "Altalanos szamitogepes alkatreszek kviz", "Hardware", "QuizForge", q);
    }

    public Quiz getPteQuiz() {
        List<Question> q = new ArrayList<>();
        q.add(new MultipleChoiceQuestion("pte_1", "Melyik evben alapitottak Magyarorszag elso egyetemet Pecsett?", 10, List.of("1367", "1492", "1635", "1872"), 0));
        q.add(new MultipleChoiceQuestion("pte_2", "Ki alapította a pecsi egyetemet 1367-ben?", 10, List.of("I. Károly", "Nagy Lajos király", "Matyás király", "Szent István"), 1));
        q.add(new MultipleChoiceQuestion("pte_3", "Hány karral rendelkezik jelenleg a Pécsi Tudományegyetem?", 10, List.of("8", "10", "12", "14"), 1));
        q.add(new TrueFalseQuestion("pte_4", "A PTE Magyarorszag elso egyeteme.", 10, true));
        q.add(new TrueFalseQuestion("pte_5", "A MIK a Muki es Informatikai Kar rovidaditese.", 10, false));
        q.add(new TextAnswerQuestion("pte_6", "Mi a Pécsi Tudományegyetem hivatalos rövidítése?", 10, "PTE"));
        q.add(new TextAnswerQuestion("pte_7", "Melyik varosban talalhato a PTE szekhelye?", 10, "Pécs"));
        q.add(new MultipleChoiceQuestion("pte_8", "Melyik szin szerepel dominansan a PTE logojaban?", 10, List.of("Sarga", "Zold", "Kek", "Piros"), 2));
        return new Quiz("pte_quiz", "Pecsi Tudomanyegyetem (PTE) kviz", "Egyetem", "QuizForge", q);
    }

    public Quiz getShortcutQuiz() {
        List<Question> q = new ArrayList<>();
        q.add(new MultipleChoiceQuestion("sc_1", "Melyik billentyukombinacioval lehet szoveget masolni?", 10, List.of("Ctrl + X", "Ctrl + C", "Ctrl + V", "Ctrl + Z"), 1));
        q.add(new MultipleChoiceQuestion("sc_2", "Melyik billentyukombinacioval lehet beilleszteni a masolt elemet?", 10, List.of("Ctrl + V", "Ctrl + P", "Ctrl + A", "Ctrl + F"), 0));
        q.add(new MultipleChoiceQuestion("sc_3", "Melyik billentyukombinacio valasztja ki az osszes elemet / szoveget?", 10, List.of("Ctrl + S", "Ctrl + A", "Ctrl + N", "Ctrl + E"), 1));
        q.add(new TrueFalseQuestion("sc_4", "A Alt + Tab kombinacioval a nyitott ablakok kozott lehet valtani.", 10, true));
        q.add(new TrueFalseQuestion("sc_5", "A Ctrl + Z visszavonja az utolso műveletet.", 10, true));
        q.add(new TextAnswerQuestion("sc_6", "Melyik funkciogombbal frissitheto egy weboldal? (pl. F5)", 10, "F5"));
        q.add(new TextAnswerQuestion("sc_7", "Melyik billentyuvel vonhato vissza a kijeloles vagy lephető ki a dialogusbol?", 10, "ESC"));
        q.add(new MultipleChoiceQuestion("sc_8", "Melyik kombináció nyitja meg a Feladatkezelőt Windows rendszereken?", 10, List.of("Ctrl + Shift + Esc", "Alt + F4", "Win + L", "Ctrl + Tab"), 0));
        return new Quiz("sc_quiz", "Billentyuzet shortcut kviz", "Produktivitas", "QuizForge", q);
    }
}
