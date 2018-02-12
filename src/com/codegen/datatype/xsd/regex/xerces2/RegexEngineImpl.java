package com.codegen.datatype.xsd.regex.xerces2;

import com.codegen.datatype.xsd.regex.Regex;
import com.codegen.datatype.xsd.regex.RegexEngine;
import com.codegen.datatype.xsd.regex.RegexSyntaxException;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;

/**
 * An implementation of <code>RegexEngine</code> using the Xerces 2 regular expression
 * implementation.
 */
public class RegexEngineImpl implements RegexEngine {
  public RegexEngineImpl() {
    // Force a linkage error on instantiation if the Xerces classes
    // are not available.
    try {
      new RegularExpression("", "X");
    }
    catch (ParseException e) {
    }
  }
  public Regex compile(String expr) throws RegexSyntaxException {
    try {
      final RegularExpression re = new RegularExpression(expr, "X");
      return new Regex() {
	  public boolean matches(String str) {
	    return re.matches(str);
	  }
	};
    }
    catch (ParseException e) {
      throw new RegexSyntaxException(e.getMessage(), e.getLocation());
    }
  }
}
