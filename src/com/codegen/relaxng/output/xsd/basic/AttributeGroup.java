package com.codegen.relaxng.output.xsd.basic;

import java.util.List;

import com.codegen.relaxng.edit.SourceLocation;

import java.util.Collections;

public class AttributeGroup extends AttributeUse {
  private final List children;

  public static final AttributeGroup EMPTY = new AttributeGroup(null, null, Collections.EMPTY_LIST);

  public AttributeGroup(SourceLocation location, Annotation annotation, List children) {
    super(location, annotation);
    this.children = Collections.unmodifiableList(children);
  }

  public List getChildren() {
    return children;
  }

  public boolean equals(Object obj) {
    return super.equals(obj) && ((AttributeGroup)obj).children.equals(children);
  }

  public int hashCode() {
    return super.hashCode() ^ children.hashCode();
  }

  public Object accept(AttributeUseVisitor visitor) {
    return visitor.visitAttributeGroup(this);
  }
}
