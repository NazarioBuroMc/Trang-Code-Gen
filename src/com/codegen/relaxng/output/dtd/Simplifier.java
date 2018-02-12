package com.codegen.relaxng.output.dtd;

import java.util.List;
import java.util.Iterator;
import java.util.Map;

import com.codegen.relaxng.edit.AbstractVisitor;
import com.codegen.relaxng.edit.Annotated;
import com.codegen.relaxng.edit.ChoicePattern;
import com.codegen.relaxng.edit.Component;
import com.codegen.relaxng.edit.CompositePattern;
import com.codegen.relaxng.edit.Container;
import com.codegen.relaxng.edit.DefineComponent;
import com.codegen.relaxng.edit.DivComponent;
import com.codegen.relaxng.edit.EmptyPattern;
import com.codegen.relaxng.edit.GrammarPattern;
import com.codegen.relaxng.edit.IncludeComponent;
import com.codegen.relaxng.edit.InterleavePattern;
import com.codegen.relaxng.edit.MixedPattern;
import com.codegen.relaxng.edit.NotAllowedPattern;
import com.codegen.relaxng.edit.OneOrMorePattern;
import com.codegen.relaxng.edit.OptionalPattern;
import com.codegen.relaxng.edit.Pattern;
import com.codegen.relaxng.edit.SchemaCollection;
import com.codegen.relaxng.edit.SchemaDocument;
import com.codegen.relaxng.edit.TextPattern;
import com.codegen.relaxng.edit.UnaryPattern;
import com.codegen.relaxng.edit.ZeroOrMorePattern;

class Simplifier extends AbstractVisitor {
  public static void simplify(SchemaCollection sc) {
    Simplifier simplifier = new Simplifier();
    for (Iterator iter = sc.getSchemaDocumentMap().values().iterator(); iter.hasNext();) {
      SchemaDocument sd = (SchemaDocument)iter.next();
      sd.setPattern((Pattern)sd.getPattern().accept(simplifier));
    }
  }

  private Simplifier() {
  }

  public Object visitGrammar(GrammarPattern p) {
    return visitContainer(p);
  }

  public Object visitContainer(Container c) {
    List list = c.getComponents();
    for (int i = 0, len = list.size(); i < len; i++)
      ((Component)list.get(i)).accept(this);
    return c;
  }


  public Object visitInclude(IncludeComponent c) {
    return visitContainer(c);
  }

  public Object visitDiv(DivComponent c) {
    return visitContainer(c);
  }

  public Object visitDefine(DefineComponent c) {
    c.setBody((Pattern)c.getBody().accept(this));
    return c;
  }

  public Object visitChoice(ChoicePattern p) {
    boolean hadEmpty = false;
    List list = p.getChildren();
    for (int i = 0, len = list.size(); i < len; i++)
      list.set(i, ((Pattern)list.get(i)).accept(this));
    for (Iterator iter = list.iterator(); iter.hasNext();) {
      Pattern child = (Pattern)iter.next();
      if (child instanceof NotAllowedPattern)
        iter.remove();
      else if (child instanceof EmptyPattern) {
        hadEmpty = true;
        iter.remove();
      }
    }
    if (list.size() == 0)
      return copy(new NotAllowedPattern(), p);
    Pattern tem;
    if (list.size() == 1)
      tem = (Pattern)list.get(0);
    else
      tem = p;
    if (hadEmpty && !(tem instanceof OptionalPattern) && !(tem instanceof ZeroOrMorePattern)) {
      if (tem instanceof OneOrMorePattern)
        tem = new ZeroOrMorePattern(((OneOrMorePattern)tem).getChild());
      else
        tem = new OptionalPattern(tem);
      copy(tem, p);
    }
    return tem;
  }

  public Object visitComposite(CompositePattern p) {
    List list = p.getChildren();
    for (int i = 0, len = list.size(); i < len; i++)
      list.set(i, ((Pattern)list.get(i)).accept(this));
    for (Iterator iter = list.iterator(); iter.hasNext();) {
      Pattern child = (Pattern)iter.next();
      if (child instanceof EmptyPattern)
        iter.remove();
    }
    if (list.size() == 0)
      return copy(new EmptyPattern(), p);
    if (list.size() == 1)
      return (Pattern)p.getChildren().get(0);
    return p;
  }


  public Object visitInterleave(InterleavePattern p) {
    boolean hadText = false;
    for (Iterator iter = p.getChildren().iterator(); iter.hasNext();) {
      Pattern child = (Pattern)iter.next();
      if (child instanceof TextPattern) {
        iter.remove();
        hadText = true;
      }
    }
    if (!hadText)
      return visitComposite(p);
    return copy(new MixedPattern((Pattern)visitComposite(p)), p);
  }

  public Object visitUnary(UnaryPattern p) {
    p.setChild((Pattern)p.getChild().accept(this));
    return p;
  }

  private static Annotated copy(Annotated to, Annotated from) {
    to.setSourceLocation(from.getSourceLocation());
    return to;
  }

  public Object visitPattern(Pattern p) {
    return p;
  }
}
