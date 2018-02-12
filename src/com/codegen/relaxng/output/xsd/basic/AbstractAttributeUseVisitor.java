package com.codegen.relaxng.output.xsd.basic;

public abstract class AbstractAttributeUseVisitor implements AttributeUseVisitor {
  public Object visitAttributeUseChoice(AttributeUseChoice a) {
    return visitAttributeGroup(a);
  }
}
