package com.codegen.relaxng.edit;

public class InterleavePattern extends CompositePattern {
  public Object accept(PatternVisitor visitor) {
    return visitor.visitInterleave(this);
  }
}
