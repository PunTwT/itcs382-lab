# Lab Report, Code Quality and Design Quality

Repository URL:

## Tools

| Item | Version or name |
| --- | --- |
| JDK | 27.0.1.0.3 (Homebrew OpenJDK, used by Maven) |
| Maven | 3.10.0 |
| IDE | 1.141.0 |
| SonarQube for IDE | 6.2.0 |
| AI tool and exact model | |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: 3148125

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S106 | Main.java:9 | Replace this use of System.out by a logger. |
| 2 | java:S106 | Main.java:15 | Replace this use of System.out by a logger. |
| 3 | java:S106 | Main.java:17 | Replace this use of System.out by a logger. |
| 4 | java:S106 | Main.java:22 | Replace this use of System.out by a logger. |
| 5 | java:S2143 | ParcelService.java:1 | Use the "java.time" API for date and time. |
| 6 | java:S1128 | ParcelService.java:6 | Remove this unused import 'java.util.Date'. |
| 7 | java:S1068 | ParcelService.java:12 | Remove this unused "maxParcels" private field. |
| 8 | java:S106 | ParcelService.java:30 | Replace this use of System.out by a logger. |
| 9 | java:S4973 | ParcelService.java:42 | Strings and Boxed types should be compared using "equals()". |
| 10 | java:S106 | ParcelService.java:67 | Replace this use of System.out by a logger. |
| 11 | java:S106 | ParcelService.java:69 | Replace this use of System.out by a logger. |
| 12 | java:S2095 | ParcelService.java:76 | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 13 | java:S108 | ParcelService.java:80 | Remove this block of code, fill it in, or add a comment explaining why it is empty. |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 25 Oct 2026 |
| AI tool and exact model | Claude Sonnet 5.5 |
| Commit reviewed | 3148125 |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | attached the files |
| Output file in the reviews folder | reviews/parcelpoint-review.md |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | ParcelService.java:76 | FileWriter is never closed | both | java:S2095 | yes, writer has no close() or try-with-resources, so data may not be flushed |
| 2 | ParcelService.java:42 | Strings compared with == | both | java:S4973 | yes, getCode() == code compares references, so pickup can fail |
| 3 | ParcelService.java:80 | Empty catch block hides save errors | both | java:S108 | yes, catch (IOException e) has an empty body |
| 4 | ParcelService.java:6 | Unused import java.util.Date | both | java:S1128 | yes, Date is never used in the file |
| 5 | ParcelService.java:12 | Unused private field maxParcels | both | java:S1068 | yes, nothing reads maxParcels |
| 6 | ParcelService.java:30,67,69 | System.out used in the service | both | java:S106 | yes, the service prints directly, which mixes output with logic |
| 7 | Main.java:9,15,17,22 | System.out used in Main | SonarQube | java:S106 | yes, but acceptable here because it is command-line output |
| 8 | ParcelService.java:1 | Use java.time instead of Date | SonarQube | java:S2143 | yes, but it only comes from the unused Date import |
| 9 | Main.java:8 | Scanner never closed | AI | Resource leak (Java extension warning, not SonarQube) | yes, in has no close() |
| 10 | Parcel.java:14; ParcelService.java:28,44,68 | Status strings "NEW", "STORED", "PICKED_UP" repeated as literals, connascence of convention | AI | Connascence of convention | yes, "STORED" appears twice and the others once, but the classes must agree on the spelling |
| 11 | ParcelService.java:53-61 | Magic numbers in fee (3 days, 10.0, 15.0, 20.0) | AI | Code smell | yes, the numbers are written directly in fee |
| 12 | ParcelService.java:49 | pickUp returns 0 when the parcel is not found | AI | Probable bug | yes, 0 looks the same as a free pickup |
| 13 | Main.java:11-13,17 | No input validation | AI | Probable bug | yes, "store P1" gives ArrayIndexOutOfBounds and a non-number gives NumberFormatException |
| 14 | Main.java:12-24 | main is a long if-else chain | AI | Code smell | no, main is only about 20 lines and is easy to read |
| 15 | ParcelService.java:78 | CSV leaves out studentId | AI | Code smell | yes, only code and status are written |
| 16 | Main.java:6-27 | main has procedural cohesion | AI | Procedural cohesion | yes, it reads, parses, calls the service and prints |
| 17 | ParcelService.java:24-37 | storeParcel has procedural cohesion | AI | Procedural cohesion | yes, it finds a locker, sets the status, sends the SMS and saves |
| 18 | ParcelService.java:39-50 | pickUp has procedural cohesion | AI | Procedural cohesion | yes, it finds the parcel, frees the locker, saves and computes the fee |
| 19 | ParcelService.java:64-72 | printParcels has logical cohesion, with boolean flag (control coupling) | AI | Logical cohesion, control coupling | yes, forStaff chooses between two outputs |
| 20 | ParcelService.java:9-83 | ParcelService does too many jobs | AI | Weak class cohesion | yes, it handles lockers, fees, SMS, files and printing |
| 21 | ParcelService.java:52-62 | fee uses no field of ParcelService | AI | Weak class cohesion | yes, it only reads the parcel |
| 22 | Parcel.java:7; ParcelService.java:28,44 | status is a public field written by another class | AI | Content coupling | yes, ParcelService changes Parcel's data directly |
| 23 | ParcelService.java:21; Main.java:22 | getLockers returns the internal list, and Main reaches through it | AI | Strong coupling | yes, callers can change the list |
| 24 | Main.java:13-14,22 | Main depends on Parcel, Locker and ParcelService | AI | Strong coupling | no, three classes is not many, and Main needs Parcel to build the input |
| 25 | Parcel.java:6; Locker.java:5; ParcelService.java:56-58 | Size code 1/2/3 shared by three classes | AI | Connascence of convention | yes, all three must agree on the meaning of the numbers |
| 26 | Main.java:13; ParcelService.java:15-17 | Constructor arguments depend on order | AI | Connascence of position | yes, Parcel(String, String, int) can swap code and studentId, but the risk is small |

2.1 Name two problems that only the AI review found.
- ParcelService is a class with weak cohesion (ParcelService.java:9-83). It handles lockers, fees, SMS, file saving and printing. SonarQube has no rule for this.
- Parcel.status is a public field that ParcelService changes directly (Parcel.java:7, ParcelService.java:28 and 44). This is content coupling.

2.2 Name one problem that only SonarQube found, or write none.
- java:S2143 at ParcelService.java:1, "Use the java.time API for date and time". The AI did not mention it. It comes from the unused java.util.Date import.

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.
- Finding 24 says Main has strong coupling because it depends on Parcel, Locker and ParcelService (Main.java:13-14, 22). Three classes is not many, and Main needs Parcel to create the input. The real coupling problem is that Main reaches through the service to the lockers (getLockers() at line 22), which is a separate finding.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | | |

Number of SonarQube issues that remain after your fixes:

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| | | |
| | | |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text

```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | | |
| AI tool and exact model | | |
| Prompt (B or C as given, or your changed prompt in full) | | |
| Builds with mvn package (yes or no) | | |
| Number of classes, interfaces, enums and records | | |
| SonarQube issues, total | | |
| SonarQube issues, by rule | | |
| AI review findings in Check 1 | | |
| AI review findings in Check 2 | | |
| Review output file in the reviews folder | | |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

5.2 When should you not trust the AI review? Give an example from this lab.

5.3 What will you do differently in Project Phase 2 after this lab?

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| | | | |
