package com.codegen.relaxng.edit;

public class EmptyPattern extends Pattern {
  public EmptyPattern() {
  }

  public Object accept(PatternVisitor visitor) {
    return visitor.visitEmpty(this);
  }
}
