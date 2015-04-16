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

package org.shirolang.values;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.shirolang.base.SFunc;
import org.shirolang.base.SFuncBase;
import org.shirolang.base.SType;
import org.shirolang.base.TypedValue;

import static java.util.stream.Collectors.toList;

/**
 * Defines a list in Shiro
 *
 * @author jeffreyguenther
 */
public class SList extends SValue<List<SFunc>> {

    public SList() {
        this(new ArrayList<>());
    }

    public SList(List<SFunc> list){
        super(list);
    }

    public SList(String name, List<SFunc> list){
        super(name, list);
    }

    @Override
    public void evaluate() {
        List<TypedValue> all = args.getAll();
        List<SFunc> funcs = all.stream().map((t) -> t.getValue()).collect(Collectors.toList());
        getValue().addAll(funcs);

        super.evaluate();
    }

    @Override
    public String getType() {
        return SType.LIST;
    }

    @Override
    public int getMaxArgs() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMinArgs() {
        return 0;
    }
}
