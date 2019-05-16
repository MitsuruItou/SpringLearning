package com.company;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	com.company.controller.AllTests.class,
	com.company.domain.AllTests.class,
	com.company.handler.AllTests.class,
	com.company.repository.AllTests.class,
	com.company.service.AllTests.class
})
public class AllTests {

}
