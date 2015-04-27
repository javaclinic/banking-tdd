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

## Exercise #3 - Structuring Tests
* What tests do we write next?
* How many tests do we need to write before we are sure our code works?
* What is a good test structure?
* Good test should have the following structure: (a) Assemble - test setup, (b) Test Fixture - setup test data, (c) Act - call business logic, (d) Verify - assert the results are what we expect, (e) Cleanup - test cleanup.
* Update `testTransfer` accordingly to include good test structure. Test compiles, test passes, not implemented yet.
* Think of Account class that will hold account details (id and balance). Update the test. Test will not compile.
* Add support for dealing with Account entities, e.g. `example.banking.dao.Accountdao`, `example.banking.dao.InMemoryAccountDao` and `example.banking.domain.Account`. Test will not compile yet.
* QuickFix on `getId()` and `getBalance()` in test class. Add unimplemented methods. Test will compile, test throws NullPointerException (error).
* Add implementation details for `InMemoryAccountDao`, e.g. `java.util.Map<Integer,Account>`. Update Account constructors. Test will now compile and genereate no errors. Test will, however, fail due on verification step.




