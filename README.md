# Chińskie Warcaby
![przykladowa gierka w chinskie warcaby](https://jkogut.pl/assets/chineseCheckers.jpg)

## O projekcie
Projekt tworzony jest w ramach zadania z przedmiotu "Technologie Programowania" na Politechnice Wrocławskiej.
Celem jest stworzenie gry w Chińskie Warcaby w języku Java z wykorzystaniem poznanych do tej pory narzędzi, takich jak Maven czy Git, oraz wzorców projektowych.

## Zasady gry
Chińskie Warcaby to gra planszowa dla 2, 3, 4 lub 6 osób. Każdy z graczy ma 10 swoich pionków ustawionych w jednym z narożników planszy. Celem gry jest przenieść wszystkie swoje pionki na przeciwległy narożnik planszy.
Dozwolonymi ruchami są
- przesunięcie pionka o jedno pole w dowolnym kierunku
- przeskoczenie pionka swojego lub innego gracza, jeśli pole za nim jest puste (podobnie jak w warcabach, lecz bez bicia)
- jeżeli pionek dotrze do przeciwległego narożnika, może poruszać się wyłącznie w jego obrębie

## Potrzebne narzędzia
- Java 23
- Maven 3.9.9
lub nowsze

## Uruchomienie
Aby uruchomić grę, należy sklonować to repozytorium:
```bash
git clone git@github.com:niooch/chinskieWarcaby.git
```
Następnie skompilować kod przy użyciu Mavena:
```bash
mvn clean install
```
Uruchomienie gry odbywa się również przy wykorzystaniu Mavena:
* serwer:
```bash
mvn clean compile exec:java -Pserver
```
* klient:
```bash
mvn clean compile exec:java -Pclient
```
## Wykożystane wzorce projektowe/dokumentacja
W [katalogu diagrams](diagrams) znajdują się diagramy UML, opisujące strukturę projektu oraz jego poszczególnych części.
System oparty jest o architekturę **klient-serwer**.

### Autorzy
Projekt tworzony przez [Pawła Rzatkiewicza](https://github.com/zotkief) oraz [Jakuba Koguta](https://jkogut.pl/).
