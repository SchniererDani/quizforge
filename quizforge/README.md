# QuizForge - Interaktiv Konzolos Kviz es Tesztelesi Rendszer

## 📌 Projekt összefoglaló
A **QuizForge** egy Java nyelven íródott, konzol alapú interaktív kvízkezelő alkalmazás. A szoftver lehetővé teszi előre beépített, valamint külső `.txt` fájlokból importált kvízek kitöltését, a pontozási és streak szabályok testreszabását, valamint a dicsőséglista (Scoreboard) automatikus nyilvántartását.

## 🚀 Build és Futtatási Utasítások
- **Előfeltételek:** Java 17+ (JDK) és Apache Maven.
- **Projekt megnyitása IntelliJ IDEA-ban:** File -> Open -> Válaszd ki a `quizforge` mappát (amelyikben a `pom.xml` található).
- **Fordítás:** `mvn clean compile`
- **Futtatás parancssorból:** `mvn exec:java` vagy indítsd el a `hu.quizforge.Main` osztályt az IDE-ben.

## 🎯 Tervezési Minta (Strategy Pattern)
A szoftver a **Strategy (Stratégia)** tervezési mintát alkalmazza a pontozási logika dynamikus beállítására (`CustomizableScoringStrategy`). A felhasználó a konzolos menüben szabadon finomhangolhatja a jó és rossz válaszokért járó pontszámokat, a streak küszöbértékét és a bónuszpontokat anélkül, hogy a kvízfuttató modult módosítani kellene.
