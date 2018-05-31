/*
 * Copyright 2014 The Closure Compiler Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.javascript.jscomp;

import com.google.javascript.jscomp.CompilerOptions.LanguageMode;

/**
 * Test cases for ES6 transpilation. Despite the name, this isn't just testing {@link
 * Es6ToEs3Converter}, but also some other ES6 transpilation passes. See #getProcessor.
 */
public final class Es7RewriteExponentialOperatorTest extends CompilerTestCase {

  public Es7RewriteExponentialOperatorTest() {
    super(MINIMAL_EXTERNS);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    setAcceptedLanguage(LanguageMode.ECMASCRIPT_2016);
    setLanguageOut(LanguageMode.ECMASCRIPT5);
    enableRunTypeCheckAfterProcessing();
  }

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new Es7RewriteExponentialOperator(compiler);
  }

  @Override
  protected int getNumRepetitions() {
    return 1;
  }

  public void testExponentiationOperator() {
    test("2 ** 2;", "Math.pow(2,2)");
  }

  public void testExponentiationAssignmentOperator() {
    test("x **= 2;", "x=Math.pow(x,2)");
  }

  public void testIssue2821() {
    test("2 ** 3 > 0", "Math.pow(2, 3) > 0");
  }
}