# Instrukcja

Projekt posiada kilka plików z `main`, które realizują inne zadania, kolejno:
- PQLauncher - uruchamia interfejs umożliwiający zabawę kolejką priorytetową; komendy: `insert <wartość> <priorytet>`, `top`, `pop`, `empty`, `print`, `priority <wartości do zaktualizowania> <nowy priorytet>`
- DLauncher - uruchama algorytm Dijkstry na wprowadzonym grafie; argumenty: liczba wierzchołków, liczba krawędzi, kolejne krawędzie w formie `<początek> <koniec> <waga>`, wierzchołek z którego szukamy
- MSTLauncher - przyjmuje parametr `-k` lub `-p` decydujący o użyciu kolejno algorytmu Kruskala lub Prima do szukania minimalnego drzewa rozpinającego; argumenty: liczba wierzchołków, liczba krawędzi kolejne krawędzie w formie `<początek> <koniec> <waga>`
- SCCLauncher - uruchamia poszukiwania silnie spójnych składowych w grafie skierowanym; liczba wierzchołków, liczba krawędzi kolejne krawędzie w formie `<początek> <koniec>`