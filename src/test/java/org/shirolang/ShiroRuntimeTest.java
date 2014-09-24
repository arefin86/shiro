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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.shirolang.base.SFunc;
import org.shirolang.base.SType;
import org.shirolang.base.SymbolType;
import org.shirolang.exceptions.PathNotFoundException;
import org.shirolang.values.SBoolean;
import org.shirolang.values.SDouble;
import org.shirolang.values.SIdent;
import org.shirolang.values.SInteger;

import static org.junit.Assert.*;

/**
 *
 * Test the Shiro runtime
 */
public class ShiroRuntimeTest {
    private ShiroRuntime rt;
    
    @Before
    public void setup(){
        rt = new ShiroRuntime();
    }

    @Test
    public void executeLiteralInteger(){
        Assert.assertEquals("3", rt.executeStatement("3\n"));
    }

    @Test
    public void executeLiteralDouble(){
        Assert.assertEquals("3.123", rt.executeStatement("3.123\n"));
    }

    @Test
    public void executeLiteralString(){
        Assert.assertEquals("\"hello world\"", rt.executeStatement("\"hello world\"\n"));
    }

    @Test
    public void executePortDecl(){
        Assert.assertEquals("#<Double args:[], results:[12.3]>", rt.executeStatement("port a Double(12.3)\n"));
    }

    @Test
    public void executePath(){
//        Assert.assertEquals("a was not found", rt.executeStatement("a\n"));

        rt.executeStatement("port a Double(12.3)\n");
        Assert.assertEquals("12.3", rt.executeStatement("a\n"));
    }

    @Test
    public void executeReferencePath(){
//        Assert.assertEquals("~a was not found", rt.executeStatement("~a\n"));

        rt.executeStatement("port a Double(12.3)\n");
        Assert.assertEquals("#<Double args:[], results:[12.3]>", rt.executeStatement("~a\n"));
    }

    @Test
    public void executeSelector(){
        Assert.assertEquals("a.b.c", rt.executeStatement("@a.b.c\n"));
    }

    @Test
    public void executeNode(){
        String input = "node Box begin\n" +
                 "input width Double(12.3)\n" +
                 "input height Double(100)\n" +
                 "eval update Double( width * height)\n" +
                "end";
        String expected = "#<Box input:[width, height], eval:[], output:[], " +
                "dependencies:[#<Double args:[width * height], results:[1230]> => #<Double args:[], results:[12.3]>," +
                " #<Double args:[width * height] results:[1230]> => #<Double args:[] results:[100]>]>";
        Assert.assertEquals(expected, rt.executeStatement(input));
    }

    @Test
    public void executeGraph(){
        String expected = "";
        fail();
    }

    @Test
    public void executeBlock(){
        Assert.fail();
    }
    
    @Test
    public void parseInt(){
        
        SFunc result = rt.executedExpr("20\n");
        assertTrue(result.isInteger());
        
        SInteger i = (SInteger) result;
        assertEquals(20, (int) i.getValue());
    }
    
    @Test
    public void parseDouble(){
        
        SFunc result = rt.executedExpr("2110.032\n");
        assertTrue(result.isDouble());
        
        SDouble i = (SDouble) result;
        assertEquals(2110.032, i.getValue(), 1e-16);
    }
    
    @Test
    public void parseEquals(){
        
        SFunc result = rt.executedExpr("1 == 1\n");
        
        SBoolean b = (SBoolean) result.getResult();
        assertTrue(b.isBoolean());
        assertTrue(b.getValue());
        
        SFunc result2 = rt.executedExpr("2 == 1\n");
        
        SBoolean b2 = (SBoolean) result2.getResult();
        assertTrue(b2.isBoolean());
        assertFalse(b2.getValue());
        
        SFunc result3 = rt.executedExpr("1 == 2\n");
        
        SBoolean r3 = (SBoolean) result3.getResult();
        assertTrue(r3.isBoolean());
        assertFalse(r3.getValue());
    }
    
    @Test
    public void parseNotEquals(){
        
        
        SFunc result = rt.executedExpr("1 != 1\n");
        SBoolean b = (SBoolean) result.getResult();
        assertTrue(b.isBoolean());
        assertFalse(b.getValue());
        
        SFunc result2 = rt.executedExpr("2 != 1\n");
        SBoolean b2 = (SBoolean) result2.getResult();
        assertTrue(b2.isBoolean());
        assertTrue(b2.getValue());
        
        SFunc result3 = rt.executedExpr("1 != 2\n");
        SBoolean b3 = (SBoolean) result3.getResult();
        assertTrue(b3.isBoolean());
        assertTrue(b3.getValue());
    }
    
    @Test
    public void lessThan(){
        
        
        SFunc result = rt.executedExpr("1 < 2\n");
        SBoolean a = (SBoolean) result.getResult();
        assertTrue(a.isBoolean());
        assertTrue(a.getValue());
        
        SFunc r2 = rt.executedExpr("2 < 1\n");
        SBoolean b = (SBoolean) r2.getResult();
        assertTrue(b.isBoolean());
        assertFalse(b.getValue());
    }
    
    @Test
    public void lessThanEqual(){
        
        
        SFunc result = rt.executedExpr("1 <= 2\n");
        SBoolean a = (SBoolean) result.getResult();
        assertTrue(a.isBoolean());
        assertTrue(a.getValue());
        
        SFunc r2 = rt.executedExpr("2 <= 1\n");
        SBoolean b = (SBoolean) r2.getResult();
        assertTrue(b.isBoolean());
        assertFalse(b.getValue());
        
        SFunc r3 = rt.executedExpr("2 <= 2\n");
        SBoolean c = (SBoolean) r3.getResult();
        assertTrue(c.isBoolean());
        assertTrue(c.getValue());
    }
    
    @Test
    public void greaterThan(){
        
        
        SFunc result = rt.executedExpr("1 > 2\n");
        SBoolean a = (SBoolean) result.getResult();
        assertTrue(a.isBoolean());
        assertFalse(a.getValue());
        
        SFunc r2 = rt.executedExpr("2 > 1\n");
        SBoolean b = (SBoolean) r2.getResult();
        assertTrue(b.isBoolean());
        assertTrue(b.getValue());
    }
    
    @Test
    public void greaterThanEqual(){
        
        
        SFunc result = rt.executedExpr("1 >= 2\n");
        SBoolean a = (SBoolean) result.getResult();
        assertTrue(a.isBoolean());
        assertFalse(a.getValue());
        
        SFunc r2 = rt.executedExpr("2.5 >= 1.0\n");
        SBoolean b = (SBoolean) r2.getResult();
        assertTrue(b.isBoolean());
        assertTrue(b.getValue());
        
        SFunc r3 = rt.executedExpr("2 >= 2\n");
        SBoolean c = (SBoolean) r3.getResult();
        assertTrue(c.isBoolean());
        assertTrue(c.getValue());
    }
    
    @Test
    public void add(){
        
        
        SFunc result = rt.executedExpr("1 + 2\n");
        SInteger a = (SInteger) result.getResult();
        assertTrue(a.isInteger());
        assertEquals(3, (int) a.getValue());
        
        SFunc result2 = rt.executedExpr("5.3 + 2.23\n");
        SDouble b = (SDouble) result2.getResult();
        assertTrue(b.isDouble());
        assertEquals(7.53, b.getValue(), 1e-14);
    }
    
    @Test
    public void subtract(){
        
        
        SFunc result = rt.executedExpr("1 - 2\n");
        SInteger a = (SInteger) result.getResult();
        assertTrue(a.isInteger());
        assertEquals(-1, (int) a.getValue());
        
        SFunc result2 = rt.executedExpr("5.3 - 2.23\n");
        SDouble b = (SDouble) result2.getResult();
        assertTrue(b.isDouble());
        assertEquals(3.07, b.getValue(), 1e-14);
    }
    
    @Test
    public void multiply(){
        
        
        SFunc result = rt.executedExpr("1 * 2\n");
        SInteger a = (SInteger) result.getResult();
        assertTrue(a.isInteger());
        assertEquals(2, (int) a.getValue());
        
        SFunc result2 = rt.executedExpr("0.5 * 2.0\n");
        SDouble b = (SDouble) result2.getResult();
        assertTrue(b.isDouble());
        assertEquals(1.0, b.getValue(), 1e-14);
    }
    
    @Test
    public void divide(){
        
        
        SFunc result = rt.executedExpr("4 / 2\n");
        SInteger a = (SInteger) result.getResult();
        assertTrue(a.isInteger());
        assertEquals(2, (int) a.getValue());
        
        SFunc result2 = rt.executedExpr("0.5 / 2.0\n");
        SDouble b = (SDouble) result2.getResult();
        assertTrue(b.isDouble());
        assertEquals(0.25, b.getValue(), 1e-14);
    }
    
    @Test
    public void mod(){
        
        
        SFunc result = rt.executedExpr("4 % 2\n");
        SInteger a = (SInteger) result.getResult();
        assertTrue(a.isInteger());
        assertEquals(0, (int) a.getValue());
        
        SFunc result2 = rt.executedExpr("3.2 % 0.5\n");
        SDouble b = (SDouble) result2.getResult();
        assertTrue(b.isDouble());
        assertEquals(0.2, b.getValue(), 1e-14);
    }
    
    @Test
    public void and(){
        
        
        SFunc result = rt.executedExpr("true &&  false\n");
        SBoolean a = (SBoolean) result.getResult();
        assertTrue(a.isBoolean());
        assertFalse(a.getValue());
        
        SFunc result2 = rt.executedExpr("false && false\n");
        SBoolean b = (SBoolean) result2.getResult();
        assertTrue(b.isBoolean());
        assertFalse(b.getValue());
        
        SFunc result3 = rt.executedExpr("false && true\n");
        SBoolean c = (SBoolean) result3.getResult();
        assertTrue(c.isBoolean());
        assertFalse(c.getValue());
        
        SFunc result4 = rt.executedExpr("true && true\n");
        SBoolean d = (SBoolean) result4.getResult();
        assertTrue(d.isBoolean());
        assertTrue(d.getValue());
    }
    
    @Test
    public void parseBooleans(){
        
        
        SFunc result = rt.executedExpr("false\n");
        assertTrue(result.isBoolean());
        SBoolean b = (SBoolean) result;
        assertFalse(b.getValue());
        
        SFunc result2 = rt.executedExpr("true\n");
        assertTrue(result2.isBoolean());
        SBoolean b2 = (SBoolean) result2;
        assertTrue(b2.getValue());
    }
    
     @Test
    public void or(){
        
        
        SFunc result = rt.executedExpr("true ||  false\n");
        SBoolean a = (SBoolean) result.getResult();
        assertTrue(a.isBoolean());
        assertTrue(a.getValue());
        
        SFunc result2 = rt.executedExpr("false || false\n");
        SBoolean b = (SBoolean) result2.getResult();
        assertTrue(b.isBoolean());
        assertFalse(b.getValue());
        
        SFunc result3 = rt.executedExpr("false || true\n");
        SBoolean c = (SBoolean) result3.getResult();
        assertTrue(c.isBoolean());
        assertTrue(c.getValue());
        
        SFunc result4 = rt.executedExpr("true || true\n");
        SBoolean d = (SBoolean) result4.getResult();
        assertTrue(d.isBoolean());
        assertTrue(d.getValue());
    }
    
    @Test
    public void not(){
        
        
        SFunc result = rt.executedExpr("!true\n");
        SBoolean a = (SBoolean) result.getResult();
        assertTrue(a.isBoolean());
        assertFalse(a.getValue());
        
        SFunc result2 = rt.executedExpr("!false\n");
        SBoolean b = (SBoolean) result2.getResult();
        assertTrue(b.isBoolean());
        assertTrue(b.getValue());
    }
    
    @Test
    public void parens(){
        
        
        SFunc result = rt.executedExpr("(4 + 2) * 2\n");
        SInteger a = (SInteger) result.getResult();
        assertTrue(a.isInteger());
        assertEquals(12, (int) a.getValue());
        
        SFunc result2 = rt.executedExpr("4 + 2 * 2\n");
        SInteger b = (SInteger) result2.getResult();
        assertTrue(b.isInteger());
        assertEquals(8, (int) b.getValue());
    }
    
    @Test
    public void makeNegative(){
        SFunc result = rt.executedExpr("-2\n");
        SInteger a = (SInteger) result.getResult();
        assertTrue(a.isInteger());
        assertEquals(-2, (int) a.getValue());
        
        SFunc result2 = rt.executedExpr("-2.0\n");
        SDouble b = (SDouble) result2.getResult();
        assertTrue(b.isDouble());
        assertEquals(-2.0, b.getValue(), 1e-15);
    }
    
    @Test
    public void parsePortDeclInit() throws PathNotFoundException {
        System.out.println("port decl init");
        SFunc executedExpr = rt.executedExpr("port a Double(12.0)\n");
        assertEquals(SType.DOUBLE, executedExpr.getType());

        SDouble result = (SDouble) executedExpr;
        assertEquals(12.0, result.getValue(), 1e-15);
    }
    
    @Test
    public void executeVariable(){
        SFunc executedExpr = rt.executedExpr("port a Double(12.0)\na\n");
        assertEquals(SType.IDENT, executedExpr.getType());
        SDouble r = (SDouble) executedExpr.getResult();
        assertEquals(12.0, r.getValue(), 1e-15);
    }

    @Test
    public void executeExpression(){
        SFunc executedExpr = rt.executedExpr("port a Double(12.0)\na + 10\n");
        assertEquals(SType.ADD, executedExpr.getType());
        SDouble r = (SDouble) executedExpr.getResult();
        assertEquals(22.0, r.getValue(), 1e-15);
    }

    @Test
    public void complicatedExpr(){
        String code = "port a Double(11.0)\n" +
                "port b Double(2.0)\n" +
                "port c Power(a, b)\n" +
                "c + b\n";

        SFunc executedExpr = rt.executedExpr(code);
        assertEquals(SType.ADD, executedExpr.getType());

        SDouble r = (SDouble) executedExpr.getResult();
        assertEquals(123.0, r.getValue(), 1e-15);
    }

    @Test
     public void parseReferencePath(){
        SFunc executedExpr = rt.executedExpr("~a\n");
        assertEquals(SType.IDENT, executedExpr.getType());

        SIdent id = (SIdent) executedExpr;
        assertTrue(id.isReference());
    }

    @Test
    public void parseSelector(){
        SFunc executedExpr = rt.executedExpr("@a\n");
        assertEquals(SType.IDENT, executedExpr.getType());

        SIdent id = (SIdent) executedExpr;
        assertTrue(id.isSelector());
    }

    @Test
    public void parseNode(){
        String code = "node a begin\n" +
                "end\n";

        SFunc executedExpr = rt.executedExpr(code);
        assertEquals(SymbolType.NODE, executedExpr.getSymbolType());
    }
}
