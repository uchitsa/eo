/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
package org.eolang.maven;

import com.yegor256.Mktmp;
import com.yegor256.MktmpResolver;
import com.yegor256.WeAreOnline;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.set.SetOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Test case for {@link CleanMojo}.
 *
 * @since 0.28.6
 */
@ExtendWith(MktmpResolver.class)
final class CleanMojoTest {

    @Test
    void cleansSuccessfully(@Mktmp final Path temp) throws IOException {
        final Path dir = Files.createDirectories(temp.resolve("target"));
        final Path out = Files.createDirectories(dir.resolve("child"));
        final Path small = Files.createDirectories(out.resolve("child.eo"));
        final Path file = Files.createTempFile(dir, "some", ".eo");
        if (!small.toFile().exists() || !file.toFile().exists()) {
            throw new IllegalStateException("Files not created.");
        }
        new FakeMaven(temp)
            .with("targetDir", dir.toFile())
            .execute(CleanMojo.class);
        MatcherAssert.assertThat(
            CatalogsTest.TO_ADD_MESSAGE,
            !file.toFile().exists() && !small.toFile().exists(),
            Matchers.is(true)
        );
    }

    @Test
    @ExtendWith(WeAreOnline.class)
    void makesFullCompilingLifecycleSuccessfully(@Mktmp final Path temp) throws IOException {
        new FakeMaven(temp)
            .withHelloWorld()
            .with("includeSources", new SetOf<>("**.eo"))
            .with("outputDir", temp.resolve("out").toFile())
            .with("placed", temp.resolve("list").toFile())
            .with("cache", temp.resolve("cache/parsed").toFile())
            .with("skipZeroVersions", true)
            .with("central", Central.EMPTY)
            .with("ignoreTransitive", true)
            .execute(RegisterMojo.class)
            .execute(AssembleMojo.class)
            .execute(CleanMojo.class);
        MatcherAssert.assertThat(
            CatalogsTest.TO_ADD_MESSAGE,
            temp.resolve("target").toFile().exists(),
            Matchers.is(false)
        );
    }
}
