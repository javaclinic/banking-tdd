# banking-tdd

This is a Test-Driven Development (TDD) practice project that walks you through the exercise of building a simple banking service, using TDD best practices.


## Exercise #1 - Setup TDD Project

* Let's add our first test case. Use `src/test/java` to place a new test class, e.g. `example.banking.services.BankingServiceTest`. Notice, that the test class doesn't compile, because few components do not resolve, e.g. `@Test` and `Assert`.
* Let's add JUnit 4 libraries to Maven dependencies (e.g. `artifactId=junit`,`groupId=junit`,`version=4.12`). Don't forget to update Maven project (e.g. `ALT-F5`). Organize imports in `BankingServiceTest` class (e.g. `CTRL+SHIFT+O`).


## Exercise #2 - First Scenario

* Your boss wants you to develop an API for banking (with little to no guidance). The API should include ability to transfer funds from account A to account B. Take a stab at developing a super-flexible API, based on little we know about the requirements.
* Scratch that. Let's write a test as if we were the user of this API. Note that `testTransfer` does not compile yet.
* QuickFix on `BankingService`. Create interface in `src/main/java` called `example.banking.services.BankingService`.
* QuickFix on `SimpleBankingService`. Create the implementation class, also in `src/main`java`, e.g. `example.banking.services.SimpleBankingService`.
* Extract local variables, instead of the hard-coded values in `transfer(1,2,1000.0)` method, e.g. `int fromAccountId = 1`, `int toAccountId = 2` and `double amount = 1000.0` and update the method, e.g. `transfer(fromAccountId, toAccountId, amount)`.
* QuickFix on `teller.transfer(...)` and create new method in `BankingService` interface and corresponding `SimpleBankingService`.
* QuickFix on `SimpleBankingService` class and add unimplemented methods, e.g. `SimpleBankingService.transfer(...)`.
* `testTransfer` compiles, passes, but not implemented.



