package com.codegen.relaxng.output.xsd.basic;

import com.codegen.relaxng.edit.SourceLocation;

public abstract class Definition extends Annotated implements TopLevel {
  private final Schema parentSchema;
  private final String name;

  public Definition(SourceLocation location, Annotation annotation, Schema parentSchema, String name) {
    super(location, annotation);
    this.parentSchema = parentSchema;
    this.name = name;
  }

  public Schema getParentSchema() {
    return parentSchema;
  }

  public String getName() {
    return name;
  }
}
