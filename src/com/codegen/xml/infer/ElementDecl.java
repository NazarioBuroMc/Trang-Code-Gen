package com.codegen.xml.infer;


import java.util.Map;

import com.codegen.relaxng.output.common.Name;

import java.util.HashMap;

public class ElementDecl {
  private Particle contentModel;
  private Name datatype;
  private final Map attributeDecls = new HashMap();

  public Map getAttributeDecls() {
    return attributeDecls;
  }

  public Particle getContentModel() {
    return contentModel;
  }

  public void setContentModel(Particle contentModel) {
    this.datatype = null;
    this.contentModel = contentModel;
  }

  public Name getDatatype() {
    return datatype;
  }

  public void setDatatype(Name datatype) {
    this.contentModel = null;
    this.datatype = datatype;
  }

}
