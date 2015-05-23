/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende
 * kende.tamas@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.css.checks.utils;

import com.sonar.sslr.api.AstNode;
import org.sonar.css.checks.validators.propertyValue.PropertyValueValidator;
import org.sonar.css.parser.CssGrammar;

import java.util.List;

public class CssProperty {

  private String name;
  private List<String> vendors;
  private PropertyValueValidator propertyValueValidator;

  public CssProperty(String name, List<String> vendors, PropertyValueValidator propertyValueValidator) {
    this.name = name;
    this.propertyValueValidator = propertyValueValidator;
    this.vendors = vendors;
  }

  @Override
  public String toString() {
    return name;
  }

  public boolean isVendor() {
    return !vendors.isEmpty();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (!((obj instanceof CssProperty) || (obj instanceof String))) {
      return false;
    }

    return name.equalsIgnoreCase(obj.toString());
  }

  public boolean isProperty(String property) {
    if (name.equalsIgnoreCase(property)) {
      return true;
    }
    for (String vendor : CssProperties.VENDORS) {
      if (property.matches(vendor + name)) {
        return true;
      }
    }
    return false;
  }

  public List<String> getVendors() {
    return vendors;
  }

  public PropertyValueValidator getPropertyValueValidator() {
    return propertyValueValidator;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  public boolean isValidPropertyValue(AstNode astNode) {
    if (!astNode.is(CssGrammar.VALUE)) {
      throw new IllegalArgumentException("Node is not of type VALUE");
    }
    return propertyValueValidator != null ? propertyValueValidator.isValid(astNode) : true;
  }

}
