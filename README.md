Finance Tracker – Java Swing aplikacija

Finance Tracker je aplikacija koja služi za praćenje vlastitih finansija kao što su prihodi i rashodi.

Za pokretanje aplikacije potrebno je imati instaliranu JAVA 17+ kao i IntelliJ.
Aplikacija se pokreće na način da otvorite Main.java metodu (src/financeapp/Main.java) i pokrenete je (RUN). Automatski se otvara GUI.

FUNKCIONALNOSTI APLIKACIJE:
  1. DODAVANJE TRANSAKCIJA
     -Korisnik može dodavati transakcije (prihodi i rashodi), kategorije (hrana, plata, prijevoz,...) kao i opis i iznos transakcija. Klikom na dugme "Unos" transakcija se unosi i pohranjuje u tabeli.

  2. AŽURIRANJE TRANSAKCIJA
     -Korisnik ima mogućnost da ažurira određenu transakciju, da promijeni opis, iznos ili kategoriju. To je moguće izvršiti tako što će korisnik odabrati jednu transakciju koju žele izmijeniti, unijeti nove vrijednosti, i kliknuti na dugme "Ažuriranje" čime će se u tabeli pohraniti nove vrijednosti.

  3. BRISANJE TRANSAKCIJA
     -Korisnik ima mogućnost da briše odabranu transakciju na način da označi željenu transakciju i izbriše transakciju klikom na dugme "BRISANJE".
  
  4. PRIKAZ UKUPNOG STANJA
     -Na dnu iznad tabele sa pohranjenim vrijednostima se nalaze podaci sa ukupnim stanjem, prihodima i rashodima koji se računa na osnovu svih unesenih transakcija u tabeli.

  5. EXPORT PODATAKA
     -Korisnici aplikacije imaju mogućnost da sve svoje podatke iz tabele izvezu u jedan .txt file, klikom na dugme "EXPORT", gdje će se pronaći ukupni prihodi i rashodi, stanje, kao i rashodi po kategorijama. Korisnik ima mogućnost odabrati lokaciju gdje će se sačuvati .txt file.


-UPUTE ZA KORIŠTENJE APLIKACIJE:
-DODAVANJE:
Unesite iznos
Unesite opis
Izaberite tip transakcije (Prihod/Rashod)
Izaberite kategoriju
Pritisnite Add
Transakcija će postati vidljiva u tabeli

-AŽURIRANJE:
Izaberite red - izmijenite polja - Update

-BRISANJE:
Izaberite red - Delete

-EXPORT:
Klik Export - izaberite lokaciju


-ARHITEKTURA PROJEKTA:

-Klase:

-Transaction
Predstavlja jednu transakciju:
tip
iznos
opis
kategorija

-TransactionManager
Sadrži listu svih transakcija i logiku:
dodavanje
brisanje
izračun prihoda, rashoda i balansa

-FinanceTrackerForm
GUI dio:
prikaz podataka
povezivanje dugmića s funkcijama
ažuriranje tabele i labela

-Main
Pokreće aplikaciju i prikazuje GUI.


-AUTOR:
IME I PREZIME: Nadir Dobrnjić
PREDMET: Programiranje u JAVI
SMJER: Informatika i računarstvo, III godina.
