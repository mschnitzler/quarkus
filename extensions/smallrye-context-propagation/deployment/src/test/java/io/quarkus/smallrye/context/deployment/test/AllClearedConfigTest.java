package io.quarkus.smallrye.context.deployment.test;

import javax.inject.Inject;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;
import io.smallrye.context.SmallRyeThreadContext;
import io.smallrye.context.impl.ThreadContextProviderPlan;

public class AllClearedConfigTest {
    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class))
            .overrideConfigKey("mp.context.ThreadContext.propagated", "");

    @Inject
    SmallRyeThreadContext ctx;

    @Test
    public void test() {
        ThreadContextProviderPlan plan = ctx.getPlan();
        Assertions.assertTrue(plan.propagatedProviders.isEmpty());
        Assertions.assertFalse(plan.clearedProviders.isEmpty());
        Assertions.assertTrue(plan.unchangedProviders.isEmpty());
    }
}
