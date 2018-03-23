package ca.mcgill.mchacks2018.noq.service;

import org.junit.*;

import ca.mcgill.mchacks2018.noq.model.*;

public class TestNoQService {

	private RegistrationManager rm;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		rm = new RegistrationManager();
	}

	@After
	public void tearDown() throws Exception {
		rm.delete();
	}
}
