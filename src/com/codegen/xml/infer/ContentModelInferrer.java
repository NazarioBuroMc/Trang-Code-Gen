package com.codegen.xml.infer;

import java.util.Set;

import com.codegen.relaxng.output.common.Name;

public abstract class ContentModelInferrer {
  public abstract void addElement(Name elementName);

  public abstract void endSequence();

  public abstract Particle inferContentModel();

  public abstract Set getElementNames();

  public static ContentModelInferrer createContentModelInferrer() {
    return new ContentModelInferrerImpl();
  }
}
