package com.codegen.relaxng.output.xsd.basic;

public interface ComplexTypeVisitor {
  Object visitComplexContent(ComplexTypeComplexContent t);
  Object visitSimpleContent(ComplexTypeSimpleContent t);
  Object visitNotAllowedContent(ComplexTypeNotAllowedContent t);
}
