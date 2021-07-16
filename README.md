# olo-automation-challenge

## Design
There is a set of rest-assured tests in the org.nfink.tests.shared namespace which are executed in two different ways:
- Against the live service with the JUnit tests in org.nfink.tests
- Generating a Pact contract with the Pact/JUnit tests in org.nfink.pact

## Build and Execute
Project includes settings for Intellij, but can also be built in Eclipse or directly with Maven. Tests can be executed using the built in runner in the IDE, with Maven, or with any JUnit runner.

## Failures and Logging
We use rest-assured for all verifications, avoiding raw junit assertions as much as possible, since rest-assured gives more information when failures or errors occur. It is possible to provide even more (e.g. headers, body, notes on the test scenario) but unlike UI tests where we'll lose info about what happened unless we collect screenshots, action logs, etc, generally for API tests it's not worth the effort - we can get all information we need from the application log.

## Parallel Execution
Parallel execution is supported as we have no shared state or dependencies in our tests. Actually running test in parallel is done by using a runner that supports it (like maven surefire) or creating a custom one.
