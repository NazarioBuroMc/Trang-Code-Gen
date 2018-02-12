package com.codegen.relaxng.parse;

import java.util.Enumeration;

import org.codegen.datatype.ValidationContext;

public interface Context extends ValidationContext {
  Enumeration prefixes();
  Context copy();
}
