package com.codegen.relaxng.edit;

import com.codegen.relaxng.parse.ParsedPattern;

public abstract class Pattern extends Annotated implements ParsedPattern {
  public abstract Object accept(PatternVisitor visitor);
}
