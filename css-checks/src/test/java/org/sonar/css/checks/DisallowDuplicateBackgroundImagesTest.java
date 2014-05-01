/*
 * Sonar CSS Plugin
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

import org.sonar.squidbridge.checks.CheckMessagesVerifier;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;

import java.io.File;

public class DisallowDuplicateBackgroundImagesTest {

  @Test
  public void test() {
    DisallowDuplicateBackgroundImages check = new DisallowDuplicateBackgroundImages();
    SourceFile file = TestHelper.scanSingleFile(new File(
        "src/test/resources/checks/duplicatebackgroundimages.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
        .atLine(7).withMessage("Disallow duplicate background images").next()
        .atLine(30).withMessage("Disallow duplicate background images").noMore();

  }

}