package com.codegen.relaxng.output.xsd.basic;

import com.codegen.relaxng.edit.SourceLocation;

public abstract class AttributeUse extends Annotated {
  public AttributeUse(SourceLocation location, Annotation annotation) {
    super(location, annotation);
  }

  public abstract Object accept(AttributeUseVisitor visitor);
}
