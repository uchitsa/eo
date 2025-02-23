/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */

/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOmath; // NOPMD

import org.eolang.AtVoid;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Real.pow.
 *
 * @since 0.40
 * @checkstyle TypeNameCheck (100 lines)
 */
@XmirObject(oname = "real.pow")
@SuppressWarnings("PMD.AvoidDollarSigns")
public final class EOreal$EOpow extends PhDefault implements Atom {
    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOreal$EOpow() {
        this.add("x", new AtVoid("x"));
    }

    @Override
    public Phi lambda() {
        return new ToPhi(
            Math.pow(
                new Dataized(this.take(Attr.RHO)).asNumber(),
                new Dataized(this.take("x")).asNumber()
            )
        );
    }
}
