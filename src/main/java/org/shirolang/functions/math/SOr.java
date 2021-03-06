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

package org.shirolang.functions.math;

import org.shirolang.base.SFunc;
import org.shirolang.base.SType;
import org.shirolang.base.TypedValue;
import org.shirolang.values.SBoolean;

/**
 * Represents the binary operator "||"
 */
public class SOr extends SBinaryFunction{

    public SOr() {
        super();
        setupBinaryLogicArgs();
    }
    
    public SOr(SFunc a, SFunc b){
        this();
        setBinaryArgs(a, b);
    }

    @Override
    public void evaluate() {
        SFunc lhs = getArg(0).getResult();
        SFunc rhs = getArg(1).getResult();
        
        // Only allow doubles to be added     
        if(lhs.isBoolean() && rhs.isBoolean()){
            Boolean l = ((SBoolean) lhs).getValue();
            Boolean r = ((SBoolean) rhs).getValue();
            Boolean sum = l || r;
            
            SBoolean s = new SBoolean(sum);
            s.evaluate();

            setResult(0, s);
        }else{
            // identify which argument is not a double
            throw new RuntimeException("Only Booleans can be used with Or.");
        }
    }

    @Override
    public String getType() {
        return "Or";
    }
}
