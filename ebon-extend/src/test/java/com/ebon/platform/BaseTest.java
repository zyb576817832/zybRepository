package com.ebon.platform;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;


@ContextConfiguration(locations = { "classpath:/spring/service-context.xml" })
public abstract class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {


}
