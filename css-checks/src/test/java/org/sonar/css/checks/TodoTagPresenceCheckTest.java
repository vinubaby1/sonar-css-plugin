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
package org.sonar.css.checks;

import org.junit.Test;
import org.sonar.css.CssAstScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

public class TodoTagPresenceCheckTest {

  @Test
  public void should_contain_todo_tags_and_raise_issues() {
    TodoTagPresenceCheck check = new TodoTagPresenceCheck();
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/todoTagPresence.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(1).withMessage("Complete the task associated to this TODO comment.").next()
      .atLine(6).withMessage("Complete the task associated to this TODO comment.").next()
      .atLine(11).withMessage("Complete the task associated to this TODO comment.").next()
      .atLine(19).withMessage("Complete the task associated to this TODO comment.").next()
      .atLine(24).withMessage("Complete the task associated to this TODO comment.").next()
      .atLine(25).withMessage("Complete the task associated to this TODO comment.").next()
      .atLine(32).withMessage("Complete the task associated to this TODO comment.")
      .noMore();
  }

  @Test
  public void should_not_contain_todo_tags_and_not_raise_issues() {
    ExperimentalPropertyUsageCheck check = new ExperimentalPropertyUsageCheck();
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/emptyRule.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).noMore();
  }

}
