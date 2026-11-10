# QuizForge (User Stories)

Rövid, átlátható összefoglaló a QuizForge alkalmazás fő funkcióiról és a felhasználói elvárásokról.

---

## 1. Szerepkörök

* **Játékos:** Kvízeket tölt ki, pontokat gyűjt és versenyez a dicsőséglistán.
* **Kvízkészítő:** Saját `.txt` fájlokat tölt fel a programba új kérdéssorokkal.
* **Beállító:** Finomhangolja a pontozási és streak szabályokat.

---

## 2. Felhasználói Történetek

### 1: Kvíz kitöltése és azonnali visszajelzés
* **Felhasználó:** Játékos
* **Cél:** Szeretnék beépített vagy saját kvízeket kitölteni, hogy teszteljem a tudásomat.
* **Elfogadási kritériumok:**
  * Választható Tutorial, beépített 8 kérdéses kvíz, vagy saját `.txt` kvíz.
  * Támogatott típusok: Feleletválasztós (A-B-C-D), Igaz/Hamis, Szöveges válasz.
  * Minden válasz után azonnal látható a szerzett pont és az aktuális streak.

### 2: Új kvíz importálása TXT fájlból
* **Felhasználó:** Kvízkészítő
* **Cél:** Szeretném a saját `.txt` fájlomat beolvasni a programba újrafordítás nélkül.
* **Elfogadási kritériumok:**
  * A menüből megadható a fájl útvonala.
  * Hibás formátumnál a program egyértelmű jelzést ad (`InvalidQuizFormatException`).
  * A sikeresen betöltött kvízek a következő indításkor is automatikusan elérhetők (`uploaded_quizzes.txt`).

### 3: Pontozás és streak testreszabása
* **Felhasználó:** Beállító
* **Cél:** Szeretném módosítani a jó/rossz válaszok pontjait és a streak bónuszt.
* **Elfogadási kritériumok:**
  * Beállítható a helyes válasz pontja, a hibás válasz levonása, a streak küszöb és a bónusz pont.
  * A módosítás a Strategy tervezési mintának köszönhetően azonnal életbe lép a játék során.

### 4: Scoreboard és rekordok mentése
* **Felhasználó:** Játékos
* **Cél:** Szeretném elmenteni a pontszámomat és megtekinteni a dicsőséglistát.
* **Elfogadási kritériumok:**
  * A kvíz végén megadható a név, amit a program a `scores.txt` fájlba ment.
  * Jobb eredmény elérése esetén a rendszer felülírja a korábbi rekordot.
  * A lista pontszám szerint csökkenő sorrendben jelenik meg.

### 5: Tutorial és formátumi segítség
* **Felhasználó:** Új felhasználó
* **Cél:** Szeretném megérteni a `.txt` kvízek felépítését és a játék menetét.
* **Elfogadási kritériumok:**
  * Elérhető egy interaktív bemutató kvíz (Tutorial).
  * Külön menüpont mutatja be a `.txt` fájlok helyes szintaxisát.

---

## 3. Technikai kapcsolatok

| Story | Fő funkció | Megvalósítás a kódos struktúrában |
| :--- | :--- | :--- |
| **1** | Kvíz futtatása | `QuizRunnerService`, `Question` ősosztály és leszármazottai |
| **2** | `.txt` beolvasás | `QuizLoaderService`, `InvalidQuizFormatException` |
| **3** | Pontozás finomítása | `CustomizableScoringStrategy` (Strategy minta) |
| **4** | Dicsőséglista | `HighScoreService`, `ScoreEntry` (`Comparable`) |
| **5** | Útmutatók | `BuiltInQuizService`, `ConsoleMenu` |
