package ca.mcgill.mchacks2018.noq;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.mchacks2018.noq.service.TestNoQService;
import ca.mcgill.mchacks2018.noq.persistence.TestPersistence;

@RunWith(Suite.class)
@SuiteClasses({TestNoQService.class, TestPersistence.class})
public class AllTests {
}
