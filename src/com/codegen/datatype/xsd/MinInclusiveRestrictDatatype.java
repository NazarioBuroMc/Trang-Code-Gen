package com.codegen.datatype.xsd;

import org.codegen.datatype.ValidationContext;

class MinInclusiveRestrictDatatype extends ValueRestrictDatatype {
  private final OrderRelation order;
  private final Object limit;

  MinInclusiveRestrictDatatype(DatatypeBase base, Object limit) {
    super(base);
    this.order = base.getOrderRelation();
    this.limit = limit;
  }

 boolean satisfiesRestriction(Object value) {
    return order.isLessThan(limit, value) || super.sameValue(value, limit);
  }
}
