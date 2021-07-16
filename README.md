# olo-automation-challenge

## Design
There are a few principles guiding the design of this project:
- Tests should be easy to write and understand.
- Test suite should be able to be executed against different environments and in a variety of contexts.
- Tests should be as low on the test pyramid as possible.

We cover the first two points with straightforward use of JUnit and Rest-Assured, combined with proper test design.

For the third point there are Pact tests (see https://pact.io/ for more info). Tests are used to generate a Pact contract, which can be added to the application code base (either as a file or using a Pact Broker) and be included as integration tests that run during the build. The goal is to run as many tests as possible during the build - generally we can test any features that aren't affected by the run environment, like field validations and business logic. A subset of tests run against the live service to ensure that things like persistence, integrations, etc are working.

To avoid duplication there is a single set of rest-assured tests in the org.nfink.tests.shared namespace. We run all tests as Pact verifications, and select ones to also run as traditional JUnit tests against the live service.

## Build and Execute
Project includes settings for Intellij, but can also be built in Eclipse or directly with Maven. Tests can be executed using the built in runner in the IDE, with Maven, or with any JUnit runner.

## Failures and Logging
We use rest-assured for all verifications, avoiding raw junit assertions as much as possible, since rest-assured gives more information when failures or errors occur. It is possible to provide even more (e.g. headers, body, notes on the test scenario) but unlike UI tests where we'll lose info about what happened unless we collect screenshots, action logs, etc, generally for API tests it's not worth the effort - we can get all information we need from the application log.

## Parallel Execution
Parallel execution is supported as we have no shared state or dependencies in our tests. Actually running test in parallel is done by using a runner that supports it (like maven surefire) or creating a custom one.
