/*
 * The MIT License
 *
 * Copyright (c) 2012 - 2014 Jeffrey Guenther.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.shirolang;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.shirolang.base.SIndexedMapTest;
import org.shirolang.base.SNodeTest;
import org.shirolang.functions.math.MathSuite;
import org.shirolang.interpreter.InterpreterSuite;
import org.shirolang.interpreter.ShiroRuntimeTest;

/**
 * Tests the components of the runtime.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    SIndexedMapTest.class,
    MathSuite.class,
    SymbolTypeTest.class,
    InterpreterSuite.class
})
public class RuntimeSuite {
}
