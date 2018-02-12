package com.codegen.relaxng.output.xsd.basic;

import com.codegen.relaxng.edit.SourceLocation;
import com.codegen.relaxng.output.common.Name;

public class Element extends Particle implements Structure {
  private final Name name;
  private final ComplexType complexType;

  public Element(SourceLocation location, Annotation annotation, Name name, ComplexType complexType) {
    super(location, annotation);
    this.name = name;
    this.complexType = complexType;
  }

  public Name getName() {
    return name;
  }

  public ComplexType getComplexType() {
    return complexType;
  }

  public Object accept(ParticleVisitor visitor) {
    return visitor.visitElement(this);
  }

  public Object accept(StructureVisitor visitor) {
    return visitor.visitElement(this);
  }

  public boolean equals(Object obj) {
    if (!super.equals(obj))
      return false;
    Element other = (Element)obj;
    return this.name.equals(other.name) && this.complexType.equals(other.complexType);
  }

  public int hashCode() {
    return super.hashCode() ^ name.hashCode() ^ complexType.hashCode();
  }
}
