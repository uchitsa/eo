/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
package org.eolang.maven;

import com.yegor256.Mktmp;
import com.yegor256.MktmpResolver;
import java.io.IOException;
import java.nio.file.Path;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Test for {@link FileHash}.
 *
 * @since 0.26
 */
@ExtendWith(MktmpResolver.class)
final class FileHashTest {

    @Test
    void readsFromExistingFile(@Mktmp final Path temp) throws IOException {
        final Path path = temp.resolve("1.txt");
        new HmBase(temp).save("hey, you", temp.relativize(path));
        MatcherAssert.assertThat(
            CatalogsTest.TO_ADD_MESSAGE,
            new FileHash(path).toString(),
            Matchers.startsWith("[-26, 1, -29, 113, ")
        );
    }

    @Test
    void readsFromAbsentFile(@Mktmp final Path temp) {
        final Path path = temp.resolve("2.txt");
        MatcherAssert.assertThat(
            CatalogsTest.TO_ADD_MESSAGE,
            new FileHash(path).toString(),
            Matchers.equalTo("")
        );
    }

}
