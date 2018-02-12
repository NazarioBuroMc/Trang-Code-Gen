package com.codegen.relaxng.output.xsd.basic;

public interface StructureVisitor {
  Object visitElement(Element element);
  Object visitAttribute(Attribute attribute);
}
