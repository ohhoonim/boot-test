package com.ohhoonim.boot_test.testingLibraries;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsNotANumber extends TypeSafeMatcher<Double> {

    @Override
    public void describeTo(Description description) {
        description.appendText("not a number");
    }

    @Override
    protected boolean matchesSafely(Double number) {
        return number.isNaN();
    }

    public static Matcher notANumber() {
        return new IsNotANumber();
    }
}
