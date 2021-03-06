package com.codegen.relaxng.output.common;

import java.util.List;
import java.util.Vector;

import com.codegen.relaxng.edit.AbstractVisitor;
import com.codegen.relaxng.edit.AnyNameNameClass;
import com.codegen.relaxng.edit.ChoiceNameClass;
import com.codegen.relaxng.edit.NameClass;
import com.codegen.relaxng.edit.NameNameClass;
import com.codegen.relaxng.edit.NsNameNameClass;

public class NameClassSplitter extends AbstractVisitor {
  private final List names = new Vector();
  private boolean negative = false;

  static public List split(NameClass nc) {
    NameClassSplitter splitter = new NameClassSplitter();
    nc.accept(splitter);
    return splitter.names;
  }

  private NameClassSplitter() {
  }

  public Object visitName(NameNameClass nc) {
    if (!negative)
      names.add(nc);
    return null;
  }

  public Object visitChoice(ChoiceNameClass nc) {
    List list = nc.getChildren();
    for (int i = 0, len = list.size(); i < len; i++)
      ((NameClass)list.get(i)).accept(this);
    return null;
  }

  public Object visitAnyName(AnyNameNameClass nc) {
    if (!negative) {
      NameClass except = nc.getExcept();
      if (except != null) {
        negative = true;
        except.accept(this);
        negative = false;
      }
    }
    return null;
  }

  public Object visitNsName(NsNameNameClass nc) {
    if (negative) {
      NameClass except = nc.getExcept();
      if (except != null) {
        int startIndex = names.size();
        negative = false;
        except.accept(this);
        negative = true;
        for (int i = startIndex, len = names.size(); i < len; i++) {
          if (!((NameNameClass)names.get(i)).getNamespaceUri().equals(nc.getNs())) {
            names.remove(i);
            i--;
            len--;
          }
        }
      }
    }
    return null;
  }
}
