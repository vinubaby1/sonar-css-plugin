/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
 * mailto: david.racodon@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.css.tree.symbol;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.sonar.plugins.css.api.symbol.Symbol;
import org.sonar.plugins.css.api.symbol.SymbolModel;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.TreeVisitorContext;

public class SymbolModelImpl implements SymbolModel, SymbolModelBuilder {

  private Set<Symbol> symbols = new HashSet<>();
  private Set<Scope> scopes = new HashSet<>();
  private Scope globalScope;

  public static void build(TreeVisitorContext context) {
    Map<Tree, Scope> treeScopeMap = getScopes(context);
    new SymbolVisitor(treeScopeMap).scanTree(context);
  }

  private static Map<Tree, Scope> getScopes(TreeVisitorContext context) {
    ScopeVisitor scopeVisitor = new ScopeVisitor();
    scopeVisitor.scanTree(context);
    return scopeVisitor.getTreeScopeMap();
  }

  @Override
  public Scope globalScope() {
    return globalScope;
  }

  @Override
  public void addScope(Scope scope) {
    if (scopes.isEmpty()) {
      globalScope = scope;
    }
    scopes.add(scope);
  }

  @Override
  public Set<Scope> getScopes() {
    return Collections.unmodifiableSet(scopes);
  }

  @Override
  public Symbol declareSymbol(String name, Symbol.Kind kind, Scope scope) {
    Symbol symbol = scope.getSymbol(name);
    if (symbol == null) {
      symbol = new Symbol(name, kind, scope);
      scope.addSymbol(symbol);
      symbols.add(symbol);

    }
    return symbol;
  }

  @Override
  public Set<Symbol> getSymbols() {
    return Collections.unmodifiableSet(symbols);
  }

  /**
   * @param kind kind of symbols to look for
   * @return list of symbols with the given kind
   */
  @Override
  public Set<Symbol> getSymbols(Symbol.Kind kind) {
    Set<Symbol> result = new HashSet<>();
    for (Symbol symbol : symbols) {
      if (kind.equals(symbol.kind())) {
        result.add(symbol);
      }
    }
    return result;
  }

  /**
   * @param name name of symbols to look for
   * @return list of symbols with the given name
   */
  @Override
  public Set<Symbol> getSymbols(String name) {
    Set<Symbol> result = new HashSet<>();
    for (Symbol symbol : symbols) {
      if (name.equals(symbol.name())) {
        result.add(symbol);
      }
    }
    return result;
  }

}
