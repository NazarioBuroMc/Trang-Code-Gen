package com.codegen.datatype.xsd.regex.jdk1_4;

import java.util.regex.Pattern;

import com.codegen.datatype.xsd.regex.Regex;
import com.codegen.datatype.xsd.regex.RegexEngine;
import com.codegen.datatype.xsd.regex.RegexSyntaxException;

import java.util.regex.Matcher;

/**
 * An implementation of <code>RegexEngine</code> using the JDK 1.4 <code>java.util.regex</code>
 * package.
 */
public class RegexEngineImpl implements RegexEngine {
  public RegexEngineImpl() {
    // Force a linkage error on instantiation if JDK 1.4 is not available.
    Pattern.compile("x");
  }

  public Regex compile(String str) throws RegexSyntaxException {
    // Don't catch PatternSyntaxException
    // The Translator should detect all syntax errors
    final Pattern pattern = Pattern.compile(Translator.translate(str));
    return new Regex() {
      public boolean matches(String str) {
        return pattern.matcher(str).matches();
      }
    };
  }
}
