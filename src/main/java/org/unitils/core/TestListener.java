package org.unitils.core;

import java.lang.reflect.Method;

/**
 * todo javadoc
 */
public abstract class TestListener {


    public void beforeAll() {
        // empty
    }

    public void beforeTestClass(Object testObject) {
        // empty
    }

    public void beforeTestSetUp(Object testObject) {
        // empty
    }

    public void beforeTestMethod(Object testObject, Method testMethod) {
        // empty
    }

    public void afterTestMethod(Object testObject, Method testMethod) {
        // empty
    }

    public void afterTestTearDown(Object testObject) {
        // empty
    }

    public void afterTestClass(Object testObject) {
        // empty
    }

    public void afterAll() {
        // empty
    }

}
