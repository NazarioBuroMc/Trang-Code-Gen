package com.codegen.relaxng.output.rng;

import com.codegen.relaxng.edit.AbstractVisitor;
import com.codegen.relaxng.edit.Annotated;
import com.codegen.relaxng.edit.AnnotationChild;
import com.codegen.relaxng.edit.AnyNameNameClass;
import com.codegen.relaxng.edit.AttributeAnnotation;
import com.codegen.relaxng.edit.AttributePattern;
import com.codegen.relaxng.edit.ChoiceNameClass;
import com.codegen.relaxng.edit.Component;
import com.codegen.relaxng.edit.CompositePattern;
import com.codegen.relaxng.edit.Container;
import com.codegen.relaxng.edit.DataPattern;
import com.codegen.relaxng.edit.DefineComponent;
import com.codegen.relaxng.edit.DivComponent;
import com.codegen.relaxng.edit.ElementAnnotation;
import com.codegen.relaxng.edit.GrammarPattern;
import com.codegen.relaxng.edit.IncludeComponent;
import com.codegen.relaxng.edit.NameClass;
import com.codegen.relaxng.edit.NameClassedPattern;
import com.codegen.relaxng.edit.NameNameClass;
import com.codegen.relaxng.edit.NsNameNameClass;
import com.codegen.relaxng.edit.Param;
import com.codegen.relaxng.edit.Pattern;
import com.codegen.relaxng.edit.UnaryPattern;
import com.codegen.relaxng.edit.ValuePattern;
import com.codegen.relaxng.parse.Context;
import com.codegen.xml.util.WellKnownNamespaces;

import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;

class Analyzer extends AbstractVisitor {

  private Object visitAnnotated(Annotated anno) {
    if (anno.getAttributeAnnotations().size() > 0
        || anno.getChildElementAnnotations().size() > 0
        || anno.getFollowingElementAnnotations().size() > 0)
      noteContext(anno.getContext());
    visitAnnotationAttributes(anno.getAttributeAnnotations());
    visitAnnotationChildren(anno.getChildElementAnnotations());
    visitAnnotationChildren(anno.getFollowingElementAnnotations());
    return null;
  }

  private void visitAnnotationAttributes(List list) {
    for (int i = 0, len = list.size(); i < len; i++) {
      AttributeAnnotation att = (AttributeAnnotation)list.get(i);
      if (att.getNamespaceUri().length() != 0)
        noteNs(att.getPrefix(), att.getNamespaceUri());
    }
  }

  private void visitAnnotationChildren(List list) {
    for (int i = 0, len = list.size(); i < len; i++) {
      AnnotationChild ac = (AnnotationChild)list.get(i);
      if (ac instanceof ElementAnnotation) {
        ElementAnnotation elem = (ElementAnnotation)ac;
        if (elem.getPrefix() != null)
          noteNs(elem.getPrefix(), elem.getNamespaceUri());
        visitAnnotationAttributes(elem.getAttributes());
        visitAnnotationChildren(elem.getChildren());
      }
    }
  }

  public Object visitPattern(Pattern p) {
    return visitAnnotated(p);
  }

  public Object visitDefine(DefineComponent c) {
    visitAnnotated(c);
    return c.getBody().accept(this);
  }

  public Object visitDiv(DivComponent c) {
    visitAnnotated(c);
    return visitContainer(c);
  }

  public Object visitInclude(IncludeComponent c) {
    visitAnnotated(c);
    return visitContainer(c);
  }

  public Object visitGrammar(GrammarPattern p) {
    visitAnnotated(p);
    return visitContainer(p);
  }

  private Object visitContainer(Container c) {
    List list = c.getComponents();
    for (int i = 0, len = list.size(); i < len; i++)
      ((Component)list.get(i)).accept(this);
    return null;
  }

  public Object visitUnary(UnaryPattern p) {
    visitAnnotated(p);
    return p.getChild().accept(this);
  }

  public Object visitComposite(CompositePattern p) {
    visitAnnotated(p);
    List list = p.getChildren();
    for (int i = 0, len = list.size(); i < len; i++)
      ((Pattern)list.get(i)).accept(this);
    return null;
  }

  public Object visitNameClassed(NameClassedPattern p) {
    p.getNameClass().accept(this);
    return visitUnary(p);
  }

  public Object visitAttribute(AttributePattern p) {
    NameClass nc = p.getNameClass();
    if (nc instanceof NameNameClass
        && ((NameNameClass)nc).getNamespaceUri().equals(""))
      return visitUnary(p);
    return visitNameClassed(p);
  }

  public Object visitChoice(ChoiceNameClass nc) {
    visitAnnotated(nc);
    List list = nc.getChildren();
    for (int i = 0, len = list.size(); i < len; i++)
      ((NameClass)list.get(i)).accept(this);
    return null;
  }

  public Object visitValue(ValuePattern p) {
    visitAnnotated(p);
    if (!p.getType().equals("token") || !p.getDatatypeLibrary().equals(""))
      noteDatatypeLibrary(p.getDatatypeLibrary());
    for (Iterator iter = p.getPrefixMap().entrySet().iterator(); iter.hasNext();) {
      Map.Entry entry = (Map.Entry)iter.next();
      noteNs((String)entry.getKey(), (String)entry.getValue());
    }
    return null;
  }

  public Object visitData(DataPattern p) {
    visitAnnotated(p);
    noteDatatypeLibrary(p.getDatatypeLibrary());
    Pattern except = p.getExcept();
    if (except != null)
      except.accept(this);
    for (Iterator iter = p.getParams().iterator(); iter.hasNext();)
      visitAnnotated((Param)iter.next());      
    return null;
  }

  public Object visitName(NameNameClass nc) {
    visitAnnotated(nc);
    noteNs(nc.getPrefix(), nc.getNamespaceUri());
    return null;
  }

  public Object visitAnyName(AnyNameNameClass nc) {
    visitAnnotated(nc);
    NameClass except = nc.getExcept();
    if (except != null)
      except.accept(this);
    return null;
  }

  public Object visitNsName(NsNameNameClass nc) {
    visitAnnotated(nc);
    noteInheritNs(nc.getNs());
    NameClass except = nc.getExcept();
    if (except != null)
      except.accept(this);
    return null;
  }

  private String datatypeLibrary = null;
  private final Map prefixMap = new HashMap();
  private boolean haveInherit = false;
  private Context lastContext = null;

  private void noteDatatypeLibrary(String uri) {
    if (datatypeLibrary == null || datatypeLibrary.length() == 0)
      datatypeLibrary = uri;
  }

  private void noteInheritNs(String ns) {
    if (ns == NameClass.INHERIT_NS)
      haveInherit = true;
  }

  private void noteNs(String prefix, String ns) {
    if (ns == NameClass.INHERIT_NS) {
      haveInherit = true;
      return;
    }
    if (prefix == null)
      prefix = "";
    if (ns == null || (ns.length() == 0 && prefix.length() != 0) || prefixMap.containsKey(prefix))
      return;
    prefixMap.put(prefix, ns);
  }

  private void noteContext(Context context) {
    if (context == null || context == lastContext)
      return;
    lastContext = context;
    for (Enumeration enum_ = context.prefixes(); enum_.hasMoreElements();) {
      String prefix = (String)enum_.nextElement();
      noteNs(prefix, context.resolveNamespacePrefix(prefix));
    }
  }

  Map getPrefixMap() {
    if (haveInherit)
      prefixMap.remove("");
    prefixMap.put("xml", WellKnownNamespaces.XML);
    return prefixMap;
  }

  String getDatatypeLibrary() {
    return datatypeLibrary;
  }

}
