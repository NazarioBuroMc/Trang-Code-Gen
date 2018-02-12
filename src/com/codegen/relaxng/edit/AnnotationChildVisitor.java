package com.codegen.relaxng.edit;

public interface AnnotationChildVisitor {
  Object visitText(TextAnnotation ta);
  Object visitComment(Comment c);
  Object visitElement(ElementAnnotation ea);
}
