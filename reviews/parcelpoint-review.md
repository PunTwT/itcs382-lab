1. Files read: Locker.java, Main.java, Parcel.java, ParcelService.java

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | 1 | ParcelService.java:76 | `FileWriter writer` is never closed, so the file may not be flushed and the resource leaks. | Must fix | Use try-with-resources. |
| 2 | 1 | ParcelService.java:42 | `parcel.getCode() == code` compares Strings by reference, so `pickup` can fail to find the parcel. | Must fix | Use `parcel.getCode().equals(code)`. |
| 3 | 1 | ParcelService.java:80 | The empty `catch (IOException e)` hides save failures. | Must fix | Report or rethrow the exception. |
| 4 | 1 | Main.java:8 | `Scanner in` is never closed. | Note | Wrap it in try-with-resources. |
| 5 | 1 | ParcelService.java:6 | The import `java.util.Date` is unused. | Should fix | Delete the import. |
| 6 | 1 | ParcelService.java:12 | The private field `maxParcels` is never read. | Should fix | Delete it, or enforce the limit in `storeParcel`. |
| 7 | 1 | ParcelService.java:28, 44, 68; Parcel.java:13 | The status strings "NEW", "STORED" and "PICKED_UP" are repeated as literals. | Should fix | Replace them with an enum or constants. |
| 8 | 1 | ParcelService.java:53-61 | The fee uses magic numbers (3 free days, rates 10.0, 15.0, 20.0). | Should fix | Move them to named constants. |
| 9 | 1 | ParcelService.java:49 | `pickUp` returns 0 for "parcel not found", which looks the same as a free pickup. | Should fix | Signal "not found" differently, e.g. with an exception or `Optional`. |
| 10 | 1 | Main.java:11-13, 17 | There is no input validation, so a short command or a non-number gives `ArrayIndexOutOfBounds` or `NumberFormatException` and the program crashes. | Should fix | Check `words.length` and catch `NumberFormatException`. |
| 11 | 1 | Main.java:12-24 | `main` is one long if-else chain that mixes parsing, dispatch and output. | Note | Move each command into a small method. |
| 12 | 1 | ParcelService.java:78 | The CSV omits `studentId`, and the whole file is rewritten on every change. | Note | Write the full parcel record, or document the format. |
| 13 | 2 | Main.java:6-27 | `main` has procedural cohesion: it reads, parses, calls the service and prints. | Should fix | Split command handling from the input loop. |
| 14 | 2 | ParcelService.java:24-37 | `storeParcel` has procedural cohesion: it finds a locker, assigns it, sets the status, sends the SMS text and saves the file. | Should fix | Move the SMS and file saving out of this method. |
| 15 | 2 | ParcelService.java:39-50 | `pickUp` has procedural cohesion: it finds the parcel, frees the locker, sets the status, saves and computes the fee. | Should fix | Extract finding the parcel and computing the fee. |
| 16 | 2 | ParcelService.java:64-72 | `printParcels(boolean)` has logical cohesion: the flag selects between two different outputs. | Should fix | Use two methods, `printForStaff()` and `printWaiting()`. |
| 17 | 2 | ParcelService.java:9-83 | The class handles locker allocation, parcel storage, fees, notification, persistence and printing, so class cohesion is weak. | Must fix | Extract at least the fee and persistence into their own classes. |
| 18 | 2 | ParcelService.java:52-62 | `fee` uses no field of `ParcelService`; it only reads the parcel, so it belongs elsewhere. | Should fix | Move the fee calculation to `Parcel` or a `FeePolicy` class. |
| 19 | 2 | Parcel.java:7; ParcelService.java:28, 44, 67-68 | `status` is a public field that `ParcelService` writes directly, which is content coupling (tighter than data coupling). | Must fix | Make it private and add a setter or a `markStored()` method. |
| 20 | 2 | ParcelService.java:21; Main.java:22 | `getLockers()` returns the internal list, so callers can change it. `Main` also reaches through the service to the lockers to count free ones. | Must fix | Return an unmodifiable view, and add `freeLockerCount()` to the service. |
| 21 | 2 | Main.java:19, 21; ParcelService.java:64 | The boolean flag passed to `printParcels` is control coupling. | Should fix | Use two separate methods (see no. 16). |
| 22 | 2 | Main.java:13-14, 22 | `Main` depends on `Parcel`, `Locker` and `ParcelService`, which is strong coupling for a UI class. | Should fix | Let `Main` call only the service, with plain values. |
| 23 | 2 | Parcel.java:6; Locker.java:5; ParcelService.java:15-17, 26, 56-58 | The size code 1/2/3 is a connascence of convention across three classes. | Should fix | Introduce a `Size` enum. |
| 24 | 2 | Parcel.java:13; ParcelService.java:28, 44, 68 | The status strings are a connascence of convention between `Parcel` and `ParcelService`. | Should fix | Use an enum (see no. 7). |
| 25 | 2 | Main.java:13; ParcelService.java:15-17 | The constructors are connascence of position: `Parcel(String, String, int)` and `Locker(int, int)` rely on argument order. | Note | Use named types or a builder if arguments grow. |
| 26 | 2 | ParcelService.java:30, 67, 69 | The service prints to the global `System.out`, which is common coupling and mixes output with logic. | Note | Return strings or use an output interface. |

3. Findings per check:
- Check 1: 12
- Check 2: 14