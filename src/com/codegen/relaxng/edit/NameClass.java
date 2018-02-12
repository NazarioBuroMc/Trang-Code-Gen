package com.codegen.relaxng.edit;

import com.codegen.relaxng.parse.ParsedNameClass;
import com.codegen.relaxng.parse.SchemaBuilder;

public abstract class NameClass extends Annotated implements ParsedNameClass {
  public static final String INHERIT_NS = SchemaBuilder.INHERIT_NS;
  public abstract Object accept(NameClassVisitor visitor);
}
