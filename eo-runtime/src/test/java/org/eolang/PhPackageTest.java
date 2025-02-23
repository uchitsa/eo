/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
package org.eolang;

import EOorg.EOeolang.EObytes$EOeq;
import EOorg.EOeolang.EOgo;
import com.yegor256.Together;
import java.util.stream.Stream;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test case for {@link PhPackage}.
 *
 * @since 0.24
 */
@SuppressWarnings("PMD.TooManyMethods")
final class PhPackageTest {

    /**
     * Default test package.
     */
    private static final String DEFAULT_PACKAGE = "Φ.org.eolang";

    @Test
    void copiesObject() {
        MatcherAssert.assertThat(
            AtCompositeTest.TO_ADD_MESSAGE,
            Phi.Φ.take("org").take("eolang").take("seq"),
            Matchers.not(
                Matchers.equalTo(
                    Phi.Φ.take("org").take("eolang").take("seq")
                )
            )
        );
    }

    @Test
    void setsRhoToObject() {
        final Phi eolang = Phi.Φ.take("org").take("eolang");
        final Phi seq = eolang.take("seq");
        MatcherAssert.assertThat(
            AtCompositeTest.TO_ADD_MESSAGE,
            seq.take(Attr.RHO),
            Matchers.equalTo(eolang)
        );
    }

    @Test
    void findsLongClass() {
        MatcherAssert.assertThat(
            AtCompositeTest.TO_ADD_MESSAGE,
            Phi.Φ.take("org")
                .take("eolang")
                .take("bytes$eq").copy(),
            Matchers.instanceOf(Phi.class)
        );
    }

    @ParameterizedTest
    @MethodSource("attributes")
    void retrievesAttribute(final String attribute, final Class<?> expected) {
        final Phi parent = new PhPackage(PhPackageTest.DEFAULT_PACKAGE);
        final Phi actual = parent.take(attribute);
        MatcherAssert.assertThat(
            AtCompositeTest.TO_ADD_MESSAGE,
            actual,
            Matchers.instanceOf(expected)
        );
    }

    @Test
    void throwsExceptionIfCantInstantiateObject() {
        Assertions.assertThrows(
            ExFailure.class,
            () -> new PhPackage(PhPackageTest.DEFAULT_PACKAGE).take("failed"),
            AtCompositeTest.TO_ADD_MESSAGE
        );
    }

    @Test
    void returnsSelfOnCopy() {
        final Phi pckg = new PhPackage(PhPackageTest.DEFAULT_PACKAGE);
        MatcherAssert.assertThat(
            "Package object should return itself on copying",
            pckg.copy(),
            Matchers.is(pckg)
        );
    }

    @Test
    void returnsForma() {
        MatcherAssert.assertThat(
            "Should return valid forma",
            new PhPackage(PhPackageTest.DEFAULT_PACKAGE).forma(),
            Matchers.equalTo(PhPackageTest.DEFAULT_PACKAGE)
        );
    }

    @Test
    void returnsLocator() {
        MatcherAssert.assertThat(
            AtCompositeTest.TO_ADD_MESSAGE,
            new PhPackage(PhPackageTest.DEFAULT_PACKAGE).locator(),
            Matchers.equalTo("?:?:?")
        );
    }

    @Test
    void findsAttributesInThreads() {
        final PhPackage pckg = new PhPackage(PhPackageTest.DEFAULT_PACKAGE);
        MatcherAssert.assertThat(
            "Should take an attribute in multiple threads",
            new Together<>(
                thread -> pckg.take("go") instanceof PhPackage
            ),
            Matchers.allOf(
                Matchers.not(Matchers.hasItem(true))
            )
        );
    }

    private static Stream<Arguments> attributes() {
        return Stream.of(
            Arguments.of("bytes$eq", EObytes$EOeq.class),
            Arguments.of("go", EOgo.class)
        );
    }
}
