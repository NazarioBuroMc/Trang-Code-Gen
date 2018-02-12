package com.codegen.relaxng.output.dtd;

import com.codegen.relaxng.edit.Combine;
import com.codegen.relaxng.edit.Component;
import com.codegen.relaxng.edit.ComponentVisitor;
import com.codegen.relaxng.edit.Container;
import com.codegen.relaxng.edit.DefineComponent;
import com.codegen.relaxng.edit.DivComponent;
import com.codegen.relaxng.edit.GrammarPattern;
import com.codegen.relaxng.edit.IncludeComponent;
import com.codegen.relaxng.edit.InterleavePattern;
import com.codegen.relaxng.edit.Pattern;
import com.codegen.relaxng.edit.SchemaCollection;
import com.codegen.relaxng.edit.SchemaDocument;
import com.codegen.relaxng.edit.SourceLocation;
import com.codegen.relaxng.output.common.ErrorReporter;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

class GrammarPart implements ComponentVisitor {
  private final ErrorReporter er;
  private final Map defines;
  private final Set attlists;
  private final Set implicitlyCombinedDefines;
  private final Map combineTypes;
  private final SchemaCollection schemas;
  private final Map parts;
  // maps name to component that provides it
  private final Map whereProvided = new HashMap();
  private final Set pendingIncludes;

  public static class IncludeLoopException extends RuntimeException {
    private final IncludeComponent include;

    public IncludeLoopException(IncludeComponent include) {
      this.include = include;
    }

    public IncludeComponent getInclude() {
      return include;
    }
  }


  GrammarPart(ErrorReporter er, Map defines, Set attlists, SchemaCollection schemas, Map parts, GrammarPattern p) {
    this.er = er;
    this.defines = defines;
    this.attlists = attlists;
    this.schemas = schemas;
    this.parts = parts;
    this.pendingIncludes = new HashSet();
    this.implicitlyCombinedDefines = new HashSet();
    this.combineTypes = new HashMap();
    visitContainer(p);
  }

  private GrammarPart(GrammarPart part, GrammarPattern p) {
    er = part.er;
    defines = part.defines;
    schemas = part.schemas;
    parts = part.parts;
    attlists = part.attlists;
    pendingIncludes = part.pendingIncludes;
    implicitlyCombinedDefines = part.implicitlyCombinedDefines;
    combineTypes = part.combineTypes;
    visitContainer(p);
  }

  Set providedSet() {
    return whereProvided.keySet();
  }

  public Object visitContainer(Container c) {
    List list = c.getComponents();
    for (int i = 0, len = list.size(); i < len; i++)
      ((Component)list.get(i)).accept(this);
    return null;
  }

  public Object visitDiv(DivComponent c) {
    return visitContainer(c);
  }

  public Object visitDefine(DefineComponent c) {
    String name = c.getName();
    Combine combine = c.getCombine();
    if (combine == null) {
      if (implicitlyCombinedDefines.contains(name))
        er.error("multiple_no_combine", name, c.getSourceLocation());
      else
        implicitlyCombinedDefines.add(name);
    }
    else {
      Combine oldCombine = (Combine)combineTypes.get(name);
      if (oldCombine != null) {
        if (oldCombine != combine)
          er.error("inconsistent_combine", c.getSourceLocation());
      }
      else
        combineTypes.put(name, combine);
    }
    Pattern oldDef = (Pattern)defines.get(name);
    if (oldDef != null) {
      if (combine == Combine.CHOICE)
        er.error("sorry_combine_choice", c.getSourceLocation());
      else if (combine == Combine.INTERLEAVE) {
        InterleavePattern ip = new InterleavePattern();
        ip.getChildren().add(oldDef);
        ip.getChildren().add(c.getBody());
        ip.setSourceLocation(c.getSourceLocation());
        defines.put(name, ip);
        attlists.add(name);
      }
    }
    else {
      defines.put(name, c.getBody());
      whereProvided.put(name, c);
    }
    return null;
  }

  public Object visitInclude(IncludeComponent c) {
    String href = c.getHref();
    if (pendingIncludes.contains(href))
      throw new IncludeLoopException(c);
    pendingIncludes.add(href);
    GrammarPattern p = (GrammarPattern)((SchemaDocument)schemas.getSchemaDocumentMap().get(href)).getPattern();
    GrammarPart part = new GrammarPart(this, p);
    parts.put(href, part);
    for (Iterator iter = part.providedSet().iterator(); iter.hasNext();)
      whereProvided.put((String)iter.next(), c);
    pendingIncludes.remove(href);
    return null;
  }

  Component getWhereProvided(String paramEntityName) {
    return (Component)whereProvided.get(paramEntityName);
  }
}
