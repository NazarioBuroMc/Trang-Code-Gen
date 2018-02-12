package com.codegen.relaxng.output.xsd.basic;

import java.util.List;

import com.codegen.relaxng.edit.SourceLocation;

public class AttributeUseChoice extends AttributeGroup {
  public AttributeUseChoice(SourceLocation location, Annotation annotation, List children) {
    super(location, annotation, children);
  }

  public Object accept(AttributeUseVisitor visitor) {
    return visitor.visitAttributeUseChoice(this);
  }
}
