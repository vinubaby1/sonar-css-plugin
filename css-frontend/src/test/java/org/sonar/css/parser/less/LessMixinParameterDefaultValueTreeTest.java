/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
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
package org.sonar.css.parser.less;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.less.LessMixinParameterDefaultValueTree;

import static org.fest.assertions.Assertions.assertThat;

public class LessMixinParameterDefaultValueTreeTest extends LessTreeTest {

  public LessMixinParameterDefaultValueTreeTest() {
    super(LexicalGrammar.LESS_MIXIN_PARAMETER_DEFAULT_VALUE);
  }

  @Test
  public void lessMixinParameterDefaultValue() {
    checkParsed(":abc");
    checkParsed(": abc");
    checkParsed(" : abc");
  }

  private LessMixinParameterDefaultValueTree checkParsed(String toParse) {
    LessMixinParameterDefaultValueTree tree = (LessMixinParameterDefaultValueTree) parser().parse(toParse);
    assertThat(tree.colon()).isNotNull();
    assertThat(tree.value()).isNotNull();
    return tree;
  }

}
