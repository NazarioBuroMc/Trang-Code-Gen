package com.codegen.relaxng.output.xsd.basic;

public interface SimpleTypeVisitor {
  Object visitRestriction(SimpleTypeRestriction t);
  Object visitUnion(SimpleTypeUnion t);
  Object visitList(SimpleTypeList t);
  Object visitRef(SimpleTypeRef t);
}
