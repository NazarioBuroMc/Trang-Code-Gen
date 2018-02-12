package com.codegen.relaxng.output.xsd.basic;

import java.util.List;

import com.codegen.relaxng.edit.SourceLocation;

public interface SchemaVisitor {
  void visitGroup(GroupDefinition def);
  void visitAttributeGroup(AttributeGroupDefinition def);
  void visitSimpleType(SimpleTypeDefinition def);
  void visitRoot(RootDeclaration decl);
  void visitInclude(Include include);
  void visitComment(Comment comment);
}
