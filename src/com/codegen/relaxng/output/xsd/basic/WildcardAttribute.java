package com.codegen.relaxng.output.xsd.basic;

import com.codegen.relaxng.edit.SourceLocation;

public class WildcardAttribute extends AttributeUse {
  private final Wildcard wildcard;

  public WildcardAttribute(SourceLocation location, Annotation annotation, Wildcard wildcard) {
    super(location, annotation);
    this.wildcard = wildcard;
  }

  public Wildcard getWildcard() {
    return wildcard;
  }

  public boolean equals(Object obj) {
    return super.equals(obj) && ((WildcardAttribute)obj).wildcard.equals(wildcard);
  }

  public int hashCode() {
    return super.hashCode() ^ wildcard.hashCode();
  }

  public Object accept(AttributeUseVisitor visitor) {
    return visitor.visitWildcardAttribute(this);
  }
}
