## 📌 O projekie

Projekt początkowo miał służyć nauce pisania testów automatycznych, jednak z braku jednoznacznego kierunku rozwinął się 
w krótkie porównanie dwóch popularnych frameworków testowych. Testy o takim samym zakresie zostały napisane w Javie 
z wykorzystaniem JUnit 5 oraz TestNG, na przykładzie walidacji danych użytkowników wczytywanych z pliku JSON.

---

## ✅ Co zostało porównane

W ramach projektu przygotowano równoważne testy w obu technologiach, obejmujące:

- **Walidację danych JSON** – sprawdzanie obecności kluczy, unikalności ID, poprawności adresu e-mail oraz minimalnego wieku.
- **Testy warunkowe (parametryzowane)** – sprawdzanie poprawności wieku użytkowników.
- **Testy flakujące (niestabilne)** – implementacja mechanizmu retry dla testów losowo zawodzących.
- **Tagowanie i grupowanie testów** – `@Tag` w JUnit i `@Test(groups = "...")` w TestNG.
- **Walidacja wzorca e-mail** – sprawdzenie zgodności adresów e-mail z wyrażeniem regularnym.

---

## ⚖️ JUnit vs TestNG – Różnice

| Element                     | JUnit 5                                  | TestNG                              |
|----------------------------|-------------------------------------------|-------------------------------------|
| Struktura testu            | `@Test`, `@Tag`, `@ExtendWith`            | `@Test(groups = "...")`             |
| Retry mechanizm            | Custom `RetryExtension` (via JUnit API)   | `retryAnalyzer`                     |
| Testy parametryzowane      | `@ParameterizedTest`, `@ValueSource`      | `@DataProvide` i ręczne przypisanie |
| Rozszerzalność             | Rozszerzenia przez `Extension`            | Retry i grupy wbudowane             |

---

## 🧪 Jak uruchomić testy

Projekt zbudowany z pomocą Maven, więc wystarczy:

mvn test

## 📚 Technologie
- Java 17
- JUnit 5
- TestNG 7.9
- Maven
- IntelliJ IDEA