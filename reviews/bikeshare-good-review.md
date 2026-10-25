Files read: Receipt.java, Ride.java, Menu.java, CsvRideLog.java, BikeShare.java, Station.java, RideLog.java, MemberType.java, Main.java (all in package `bikeshare`).

| No. | Check | Code location | Finding | Severity | Suggested fix |
|---|---|---|---|---|---|
| 1 | 1 | MemberType.java:15 | `ratePerMinute * minutes` is int arithmetic, so a very large minutes value overflows and gives a negative or wrong fee. Nothing caps minutes (BikeShare.java:45 only checks `<= 0`). | Should fix | Use `Math.multiplyExact` and/or reject minutes above a sensible maximum. |
| 2 | 1 | Main.java:9 | The `Scanner` on `System.in` is never closed. | Note | Wrap it in try-with-resources, or accept the leak and say so in a comment. |
| 3 | 1 | Menu.java:59 (and :20) | `ask` returns the literal "quit" at end of input, so a prompt for member ID or station can receive "quit" as data. This produces spurious "Error:" lines before the program exits. | Note | Return `Optional`/null at end of input and exit the loop explicitly. |
| 4 | 1 | Station.java:8-14, MemberType.java:18-24 | The `parse` methods are the same code twice (trim, upper-case, `valueOf`, rethrow with a new message). | Note | Extract a small generic helper that takes the enum class and the message. |
| 5 | 1 | Main.java:8 | The magic values `"rides.csv"` and `2` (bikes per station) are hard-coded. | Note | Name them as constants or read them from `args`. |
| 6 | 2 (connascence: convention) | BikeShare.java:30 vs CsvRideLog.java:26 | `BikeShare` rejects "," in member IDs only because `CsvRideLog` joins fields with commas without escaping. The domain class knows the storage format even though it talks to the `RideLog` interface. | Should fix | Move the comma check (or CSV escaping) into `CsvRideLog.save`. |
| 7 | 2 (connascence: position) | BikeShare.java:52-53, Ride.java:3-4 | The `Ride` constructor takes 7 positional arguments, including adjacent same-typed ones (`bikeId`/`memberId`, `from`/`to`, `minutes`/`fee`). Swapping any pair compiles silently. | Should fix | Add a factory method on `Ride` that takes the `Rental`, destination and minutes. |
| 8 | 2 (connascence: convention) | Menu.java:37, 38, 44; Station.java:12; MemberType.java:22 | The constant names (library/canteen/dorm, student/staff) are retyped in prompts and error messages. Changing an enum constant means editing five places. | Note | Build the text from `values()`. |
| 9 | 2 (connascence: convention) | Menu.java:53 vs BikeShare.java:46 | The same message "Minutes must be a positive whole number." appears in two classes, and the "positive" rule is split between them. | Note | Define the message once or validate in a single place. |
| 10 | 2 (connascence: meaning) | Receipt.java:12 vs MemberType.java:14 | The unit "baht" is implied by the int returned from `fee` and hard-coded in the receipt text. | Note | Name the unit in one place (e.g. a constant or a `Money` type). |
| 11 | 2 (connascence: type/convention) | Menu.java:29 | `Menu` must know which exception types `BikeShare` and the `RideLog` implementation throw, including `UncheckedIOException` (an implementation detail of `CsvRideLog`). | Note | Document the exceptions on `RideLog.save`, or wrap them in one domain exception. |
| 12 | 2 (stamp coupling) | Menu.java:46 → Receipt.java:7 | `Receipt.format(Ride)` receives a whole `Ride` but uses only `bikeId`, `minutes` and `fee`. | Note | Accept the three values directly, or make it a method on `Ride`. |
| 13 | 2 (strong coupling, reaching through) | CsvRideLog.java:26-27; BikeShare.java:53, 56 | Calls reach through objects: `ride.type().name()`, `ride.from().name()`, `rental.type().fee(...)`, `available.get(station).add(...)`. | Note | Add small delegating methods (e.g. `Ride.typeName()`, `returnBikeTo(station, bike)`). |
| 14 | 2 (strong coupling) | Menu.java:37, 38, 46 | `Menu` depends on `BikeShare`, `MemberType`, `Station`, `Receipt` and `Ride` (via the returned value). | Note | Let `BikeShare` take raw strings, or give it a facade method that returns the receipt text. |
| 15 | 2 (class cohesion) | BikeShare.java:9-27, 29-58 | The class handles bike-ID generation and station inventory (`available`), rental tracking (`rentals`), member-ID validation and orchestration of logging. | Note | Extract the inventory (bikes per station) into its own small class. |
| 16 | 2 (class cohesion) | Station.java:8-14, MemberType.java:18-24 | The domain enums also parse user text and carry user-facing error messages. This mixes the domain model with input handling. | Note | Move the parsing into `Menu`, or into a separate input-parsing class. |
| 17 | 2 (class cohesion) | Receipt.java:3-14 | A static-only class whose single method works on `Ride`'s data rather than its own (feature envy). | Note | Move `format` into `Ride`, or keep it only if more formatters are planned. |
| 18 | 2 (class cohesion) | Menu.java:49-55 | `parseMinutes` uses no attribute of `Menu`. | Note | Make it `static`. |
| — | 1 | All files | Unused imports, fields or parameters; empty blocks; `==` on objects; too-long or too-complex methods. | Not found | — |
| — | 2 | All files | Methods with procedural or weaker cohesion (all are sequential or functional). | Not found | — |
| — | 2 | All files | Control, common or content coupling between classes. | Not found | — |
| — | 2 | All files | Non-private fields, or methods returning an internal collection. | Not found | — |

Findings per check (not-found rows excluded):
- Check 1 (code quality): 5
- Check 2 (cohesion, coupling, connascence): 13