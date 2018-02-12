package com.codegen.datatype.xsd;

import com.codegen.datatype.xsd.regex.Regex;
import com.codegen.datatype.xsd.regex.RegexEngine;
import com.codegen.datatype.xsd.regex.RegexSyntaxException;

class RegexDatatype extends TokenDatatype {
  private final String pattern;
  private Regex regex;

  RegexDatatype(String pattern) {
    this.pattern = pattern;
  }

  synchronized void compile(RegexEngine engine) throws RegexSyntaxException {
    if (regex == null)
      regex = engine.compile(pattern);
  }

  public boolean lexicallyAllows(String str) {
    return regex.matches(str);
  }

  public boolean alwaysValid() {
    return false;
  }
}
