/*
 * VersionEyeUtilsTest.kt
 *
 * Copyright (c) 2016-2017, Erik C. Thauvin (erik@thauvin.net)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *   Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *   Neither the name of this project nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.thauvin.erik.kobalt.plugin.versioneye

import com.beust.kobalt.AsciiArt
import org.testng.Assert
import org.testng.annotations.Test

@Test
class VersionEyeUtilsTest {
    val text = "This is a test"

    @Test
    fun alt() {
        Assert.assertEquals(VersionEyeUtils.alt(false), "", "alt(false")
        Assert.assertEquals(VersionEyeUtils.alt(true), " [FAILED]", "alt(true)")
    }

    @Test
    fun plural() {
        val singular = "foo"
        val plural = "s"
        Assert.assertEquals(VersionEyeUtils.plural(singular, 0, plural), singular, "plural($singular, count:0, $plural)")
        Assert.assertEquals(VersionEyeUtils.plural(singular, 1, plural), singular, "plural($singular, count:1, $plural)")
        Assert.assertEquals(VersionEyeUtils.plural(singular, 2, plural), singular + plural, "plural($singular, count:2," +
                "$plural)")

        val text = "vulnerabilit"
        val y = "y"
        val ies = "ies"
        Assert.assertEquals(VersionEyeUtils.plural(text, 0, ies, y), text + y, "plural($text, count:0, $ies, $y)")
        Assert.assertEquals(VersionEyeUtils.plural(text, 1, ies, y), text + y, "plural($text, count:1, $ies, $y)")
        Assert.assertEquals(VersionEyeUtils.plural(text, 2, ies, y), text + ies, "plural($text, count:2, $ies, $y)")
    }

    @Test
    fun red() {
        Assert.assertEquals(VersionEyeUtils.red(text), AsciiArt.RED + text + AsciiArt.RESET, "red($text)")
        Assert.assertEquals(VersionEyeUtils.red(text, false), text, "red($text)")
    }

    @Test
    fun yellow() {
        Assert.assertEquals(VersionEyeUtils.yellow(text), AsciiArt.YELLOW + text + AsciiArt.RESET, "yellow($text)")
        Assert.assertEquals(VersionEyeUtils.yellow(text, false), text, "yellow($text)")
    }

    @Test
    fun green() {
        Assert.assertEquals(VersionEyeUtils.green(text), AsciiArt.GREEN + text + AsciiArt.RESET, "green($text)")
        Assert.assertEquals(VersionEyeUtils.green(text, false), text, "green($text)")
    }

    @Test
    fun redLight() {
        Assert.assertEquals(VersionEyeUtils.redLight(text, 1, true, true), AsciiArt.RED + text + AsciiArt.RESET,
                "redLight($text, count:1, fail:true, colors:true)")
        Assert.assertEquals(VersionEyeUtils.redLight(text, 1, false, true), AsciiArt.YELLOW + text + AsciiArt.RESET,
                "redLight($text, count:1, fail:false, colors:true)")
        Assert.assertEquals(VersionEyeUtils.redLight(text, 0, false, true), AsciiArt.GREEN + text + AsciiArt.RESET,
                "redLight($text, count:0, fail:false, colors:true)")
        Assert.assertEquals(VersionEyeUtils.redLight(text, 1, false, false), text,
                "redLight($text, count:1, fail:false, colors:false)")

        Assert.assertEquals(VersionEyeUtils.redLight(1, true, true), AsciiArt.RED + 1 + AsciiArt.RESET,
                "redLight(count:1, fail:true, colors:true)")
        Assert.assertEquals(VersionEyeUtils.redLight(1, false, true), AsciiArt.YELLOW + 1 + AsciiArt.RESET,
                "redLight(count:1, fail:false, colors:true)")
        Assert.assertEquals(VersionEyeUtils.redLight(0, false, true), AsciiArt.GREEN + 0 + AsciiArt.RESET,
                "redLight(count:0, fail:false, colors:true)")
        Assert.assertEquals(VersionEyeUtils.redLight(1, false, false), "1",
                "redLight(count:1, fail:false, colors:false)")
    }
}