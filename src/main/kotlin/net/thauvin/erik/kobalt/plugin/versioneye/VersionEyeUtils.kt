/*
 * VersionEyeUtils.kt
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
import com.beust.kobalt.misc.log

open class VersionEyeUtils private constructor() {
    companion object {
        // Non-colors failure
        fun alt(failed: Boolean): String {
            if (failed) {
                return " [FAILED]"
            }

            return ""
        }

        // Match failure option in set
        fun isFail(failOn: Set<Fail>, match: Fail): Boolean {
            return failOn.contains(match)
        }

        // Log text if applicable
        fun log(text: StringBuilder, flag: Boolean, level: Int = 1) {
            if (flag && text.isNotEmpty()) {
                log(level, text)
            }
        }

        // Pluralize text if applicable
        fun plural(text: String, count: Int, plural: String, singular: String = ""): String {
            if (count > 1) {
                return text + plural
            } else {
                return text + singular
            }

        }

        fun red(text: String, colors: Boolean = true): String {
            if (!colors) {
                return text
            }
            return AsciiArt.RED + text + AsciiArt.RESET
        }

        fun yellow(text: String, colors: Boolean = true): String {
            if (!colors) {
                return text
            }
            return AsciiArt.YELLOW + text + AsciiArt.RESET
        }

        fun green(text: String, colors: Boolean = true): String {
            if (!colors) {
                return text
            }
            return AsciiArt.GREEN + text + AsciiArt.RESET
        }

        fun redLight(count: Int, fail: Boolean, colors: Boolean): String {
            return redLight(count.toString(), count, fail, colors)
        }

        // Green, yellow, red colored-text based on failure and count
        fun redLight(text: String, count: Int, fail: Boolean, colors: Boolean): String {
            if (colors) {
                if (fail && count > 0) {
                    return red(text)
                } else if (count > 0) {
                    return yellow(text)
                } else {
                    return green(text)
                }
            }
            return text
        }
    }
}