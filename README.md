# Algorytmy i Struktury Danych

Zadania wykonywane pod Linuxem, kompilowane gcc.

## Opis zadań

Skrótowe podsumowanie zadań z kolejnych list. Niewymienione tu zadania były teoretyczne, stąd też nie umieściłem odpowiedzi na nie w tym repozytorium.

### Lista 1

1. Linked list z operacjami `insert`, `delete`, `isempty`, `findMTF` oraz `findTRANS`. Instrukcje od prowadzącego: 
wstaw w losowej kolejności elementy od 1 do 100 (100 elementów) na listę; 
dopóki lista nie będzie pusta: sprawdź czy elementy od 1 do 100 są na liście a następnie usuń element maksymalny. 
Rozpatrz oba warianty operacji sprawdzającej. Która z nich daje mniejszą średnią liczbę porównań. Lista wykonana 
na `void*`, uniwersalnie.

### Lista 2

1. InsertionSort, SelectionSort, HeapSort, QuickSort oraz modified QuickSort zbierające statystyki o liczbie porównań oraz zamian elementów 
oraz liczące czasy wykonań tych sortowań. Program działa w dwóch konfiguracjach, albo przyjmując parametry `-t i|s|h|q|m` - algorytm sortowania 
`-a` - ascending, `-d` - desceding, albo `-stat nazwa_pliku -n ilość_powtórzeń` tworzącego dane statystyczne w csv o czasach i liczbach porównań.
Lista wykonana na `void*`, uniwersalnie.

### Lista 3

1. Kolejka priorytetowa z poleceniami `insert`, `top`, `pop`, `empty`, `print` oraz `priority` (zmiana priorytetu) działająca w log(n).
2. Algorytm Dijkstry korzystający z kolejki priorytetowej z poprzedniego zadania.
3. Algorytmy Prima i Kruskala szukające minimalnego drzewa rozpinającego używając kolejki priorytetowej oraz union find w grafach nieskierowanych.
4. Algorytm szukający silnie spójnych składowych grafu skierowanego.

### Lista 4
1. Implementacja drzew BST, RBT oraz Splay.
2. Testy na drzewach polegające na dodaniu, wyszukaniu oraz usunięciu wszystkich słów z plików z `data`.
3. Generowanie danych "idealnych" (tj. zapewniających każdemu typu drzewa optymalne zachowanie) w `gen`.
4. Dokładnie sprawozdanie z wykonanych zadań.

### Lista 5
1. Przepływ w grafie będących hiperkostką o `k` wymiarach oraz `2^k` wierchołkach, których etykietami są kolejne szesnastobitowe liczby binarne, a krawędź pomiędzy nimi istnieje wtedy i tylko wtedy, kiedy różnią się na dokładnie jednym bicie, wtedy krawędź skierowana jest z wierzchołka o etykiecie mniejszej do wierzchołka o etykiecie większej, a jego pojemnością jest wartość losowana z rozkładem jednostajnym z `{1, ... , 2^l}` gdzie `l = max{H(początek), Z(początek), H(koniec), Z(koniec)}` gdzie `H(i)` oznacza liczbę jedynek w etykiecie wierzchołka i, a `Z(i)` - liczbę zer.
2. Wyznaczanie maksymalnego skojarzenia w dwudzielnym grafie losowym o mocy `2^k` w każdej części, gdzie każdy z wierchołków posiada `i` sąsiadów.
3. Zadanie pierwsze ale w glpk.
4. Zadanie drugie ale w glpk.