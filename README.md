# Algorytmy i Struktury Danych

Zadania wykonywane pod Linuxem, kompilowane gcc.

## Opis zadań

Skrótowe podsumowanie zadań z kolejnych list. Niewymienione tu zadania były teoretyczne, stąd też nie umieściłem odpowiedzi na nie w tym repozytorium.

### Lista 1

1. Linked list z operacjami `insert`, `delete`, `isempty`, `findMTF` oraz `findTRANS`. Instrukcje od prowadzącego: 
wstaw w losowej kolejności elementy od 1 do 100 (100 elementów) na listę; 
dopóki lista nie będzie pusta: sprawdź czy elementy od 1 do 100 są na liście a następnie usuń element maksymalny. 
Rozpatrz oba warianty operacji sprawdzającej. Która z nich daje mniejszą średnią liczbę porównań.

### Lista 2

1. InsertionSort, SelectionSort, HeapSort, QuickSort oraz modified QuickSort zbierające statystyki o liczbie porównań oraz zamian elementów 
oraz liczące czasy wykonań tych sortowań. Program działa w dwóch konfiguracjach, albo przyjmując parametry `-t i|s|h|q|m` - algorytm sortowania 
`-a` - ascending, `-d` - desceding, albo `-stat nazwa_pliku -n ilość_powtórzeń` tworzącego dane statystyczne w csv o czasach i liczbach porównań.
