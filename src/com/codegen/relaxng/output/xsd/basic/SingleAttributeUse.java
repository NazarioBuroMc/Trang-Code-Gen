package com.codegen.relaxng.output.xsd.basic;

import com.codegen.relaxng.edit.SourceLocation;
import com.codegen.relaxng.output.common.Name;

public abstract class SingleAttributeUse extends AttributeUse {
  public SingleAttributeUse(SourceLocation location, Annotation annotation) {
    super(location, annotation);
  }

  public abstract Name getName();
  public abstract SimpleType getType();
  public abstract boolean isOptional();
  public String getDefaultValue() {
    return null;
  }
}
