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
* Add implementation details for `InMemoryAccountDao`, e.g. `java.util.Map<Integer,Account>`. Update Account constructors. Test will now compile and generate no errors. Test will, however, fail due on verification step.

## Exercise #4 - Changing Requirements
* Your boss walks up again. Progress looks awesome, but we would like to track of the account owners, by their name. Also, account id properties will be auto-generated by the data layer.
* Update `AccountDao` interface and `InMemoryAccountDao` implementation to reflect new API change. Update `Account` to reflect new property.
* Now, looking at `Account` entity, we should probably revise `id` account property to indicate a database primary key, which is null if the entity is not in the database yet. Update `Account` code to reflect that. Add other override methods from `java.lang.Object`, e.g. `hashCode(..)`, `equals(..)` and `toString()`.
* We just thought of few test cases. Add test cases to the test class, e.g. `testAccountIdIsNullIfNotInDatabase()`, `testInsufficientFunds()` and `testAccountNotFoundInGet()` and mark them not implemented.
* Update `AccountDao` to include update account feature. Finish implementation of the `SimpleBankingService`. Test compiles. Test has an unexpected error, a `NullPointerException` bug. 

## Exercise #5 - Finding a Bug
* Why are we getting a `NullPointerException`? What happened? Is that a bug?
* It seems a new `AccountDao` instance is created every time we run `transfer(...)` method.
* Fix the bug.
* Suggestion 1: Inject AccountDao entity into `transfer(...)` method.
* Suggestion 2: Make database Map static in `InMemoryDao`.
* Suggestion 3: Instantiate `AccountDao` instance in service. Add `getDao()` to `SimpleBankingService` implementation.
* Suggestion 4: Use singleton for `AccountDao` instance.
* Suggestion 5: Use configuration service, e.g. `example.banking.services.ConfigurationService`. Test compiles and passes.

## Exercise #6 - Test Ordering
* Let's implement other test, e.g. `testAccountNotFoundInGet()`.
* Side discussion: Practically, we are testing `InMemoryAccountDao` implementation, which is not our intent. However, we know someone else will be writing DAO code soon, which will be different implementation than our `InMemoryAccountDao` testing implementation. This will make us think through the API. The team developing new DAO implementation will be able to take our unit tests, create integration tests and run them against their new DAO implementation. Thus, by writing these tests now, we are helping DAO developers define the contract.
* Tests results might depend on the execution order of the tests!
* Now, let's play with names of the test cases. Try changing names of the tests, e.g. `zzzz_testTrasnfer()` instead of `testTransfer()`. Run the tests! Were you able to reproduce test failure depending in which order tests were run? What happens if the test are run independently? How is that possible?
* What happens when you rerun test independently? Tests compile, test results are inconclusive and test fails/passes based on the test case method name.
* The order in which test cases will run is generally indeterministic. We could influence the ordering of tests.
* We need to think of a better strategy to approach tests. Let's create setup and teardown methods to execute before and after each test.
* We need to update ConfigurationService to provide `reset` method, and reset it every time a test is run. Tests compile and pass again.

## Exercise #7 - Dependency Injection
* Let's clean up our code, and make it more testable by using dependency injection.
* Inject `AccountDao` into constructor of `SimpleBankingService`.
* Let's clean up `InMemoryDao` as well, provide an alternative to inject the `Map<Integer,Account>` if needed into dao instance. Also, make `create(...)` synchronized.
* Let's implement the other test as well, e.g. `testAccountIdIsNullIfNotInDatabase()`. We might need to update our `Account` class to include default constructor.


