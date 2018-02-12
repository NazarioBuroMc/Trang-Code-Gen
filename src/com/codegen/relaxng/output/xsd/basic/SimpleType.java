package com.codegen.relaxng.output.xsd.basic;

import com.codegen.relaxng.edit.SourceLocation;

public abstract class SimpleType extends Annotated {
  public SimpleType(SourceLocation location, Annotation annotation) {
    super(location, annotation);
  }

  public abstract Object accept(SimpleTypeVisitor visitor);
}
