package com.codegen.datatype.xsd;

import org.codegen.datatype.DatatypeLibrary;
import org.codegen.datatype.DatatypeLibraryFactory;

import com.codegen.xml.util.WellKnownNamespaces;

public class DatatypeLibraryFactoryImpl implements DatatypeLibraryFactory {

  private DatatypeLibrary datatypeLibrary = null;

  public DatatypeLibrary createDatatypeLibrary(String uri) {
    if (!WellKnownNamespaces.XML_SCHEMA_DATATYPES.equals(uri))
      return null;
    synchronized (this) {
      if (datatypeLibrary == null)
        datatypeLibrary = new DatatypeLibraryImpl();
      return datatypeLibrary;
    }
  }
}
