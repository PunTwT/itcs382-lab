Files read: Main.java (the only file in the upload, so I could not open the rest of bikeshare-poor/src/main/java).

| No. | Check | Code location | Finding | Severity | Suggested fix |
|---|---|---|---|---|---|
| 1 | 1 Code quality | Main.java:10-12 | The literal 6 sets the size of `bikeMember` and `bikeType` and must match the length of `bikeStation`. | Should fix | Use `bikeStation.length` for both array sizes. |
| 2 | 1 Code quality | Main.java:92 | The fee rates 2 and 3 are unexplained literals inside the calculation. | Should fix | Move them into named constants such as `STUDENT_RATE` and `STAFF_RATE`. |
| 3 | 1 Code quality | Main.java:94 | The file name "rides.csv" is a hard-coded literal inside the method. | Note | Move it to a constant. |
| 4 | 1 Code quality | Main.java:51-102 | `handle` is 52 lines long, with 5 early returns, a nested try, a loop and a ternary chain. | Should fix | Split it into `rentBike`, `returnBike`, `calculateFee` and `saveRide`. |
| 5 | 1 Code quality | Main.java:56 | The nested ternary that parses the type is hard to read. | Note | Move it into a small `parseType` method. |
| 6 | 1 Code quality | Main.java:95 | The member ID is written to the CSV unescaped, so an ID containing a comma corrupts the record. | Should fix | Reject or quote IDs containing commas before writing. |
| 7 | 1 Code quality | Main.java:36, 52, 55 | At end of input `ask` returns "quit", which `handle` then treats as a real member ID or type. | Note | Let `ask` signal end of input in a way callers can check. |
| 8 | 1 Code quality | Main.java:52, 57 | An empty member ID is accepted and can rent a bike. | Note | Reject blank IDs after reading them. |
| 9 | 1 Code quality | Main.java:97-99 | If saving fails, the receipt has already been printed and the bike is still returned, so the ride record is lost. | Note | Report the failure to the user and decide whether the return should still go through. |
| 10 | 1 Code quality | Main.java:16, 94 | Unclosed resources, `==` on objects, unused imports, unused fields or parameters, and empty blocks: not found. | Note | None. |
| 11 | 2 Cohesion | Main.java:39-49 | `find` has logical cohesion: the `byMember` flag chooses between two unrelated searches (line 41 and line 44). | Should fix | Split it into `findByMember` and `findFreeBikeAt`. |
| 12 | 2 Cohesion | Main.java:51-102 | `handle` has logical cohesion: the `rent` flag selects between two different operations. The return path also mixes input, validation, fee calculation, printing, saving and state update. | Should fix | Make rent and return two separate methods. |
| 13 | 2 Cohesion | Main.java:5-103 | `Main` represents stations, bikes, members, fee policy, CSV storage and console input all in one class, which is weak class cohesion. | Must fix | Extract at least a `Bike` class, a fee policy and a ride log. |
| 14 | 2 Cohesion | Main.java:10-12 | The parallel arrays `bikeStation`, `bikeMember` and `bikeType` hold the attributes of one missing "Bike" concept. | Should fix | Replace them with one `Bike` class holding station, member and type. |
| 15 | 2 Fields | Main.java:9-13 | The static fields `stations`, `bikeStation`, `bikeMember`, `bikeType` and `in` are all public and mutable, so any class can change the internal arrays. | Must fix | Make them private. |
| 16 | 2 Coupling | Main.java:36, 41, 44, 66-67, 100-101 | Common coupling: `ask`, `find` and `handle` all read and change the same global state. | Must fix | Pass data as parameters or hold it as instance fields of a class. |
| 17 | 2 Coupling | Main.java:51 (called at 24, 26); 39 (called at 53, 61) | Control coupling: the boolean flags `rent` and `byMember` decide what the callee does. | Should fix | Replace each flagged method with two methods, one per behaviour. |
| 18 | 2 Connascence | Main.java:10-12, 41, 44, 66-67, 100-101 | Connascence of position: the bike's attributes only line up through the same array index in three arrays. | Should fix | Combine them into one `Bike` object. |
| 19 | 2 Connascence | Main.java:53, 56, 62, 76, 86, 88 | Connascence of convention: -1 means "not found" or "invalid", and every caller must know it. | Should fix | Use an enum for type and a clear not-found result for lookups. |
| 20 | 2 Connascence | Main.java:56, 96 | Connascence of algorithm: the text-to-type mapping on line 56 and the type-to-text mapping on line 96 must agree. | Should fix | Use one enum that holds its own label and rate. |
| 21 | 2 Connascence | Main.java:44, 77-81 | Connascence of algorithm: the station-name lookup is written twice. | Should fix | Add one `stationIndex(name)` method and call it in both places. |
| 22 | 2 Connascence | Main.java:95-96 | Connascence of position: the `String.format` arguments must match the CSV column order, which is not documented anywhere. | Note | Document the column order in one named constant or method. |
| 23 | 2 Coupling | Main.java | Content coupling, stamp coupling, many class dependencies, reaching through one object, and operations returning an internal collection: not found. | Note | None. |

Findings per check:
- Check 1 (code quality): 9 findings, plus 1 "not found" row (No. 10).
- Check 2 (cohesion, coupling, connascence): 12 findings (No. 11-22), plus 1 "not found" row (No. 23).