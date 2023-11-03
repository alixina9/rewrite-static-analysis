/*
 * Copyright 2022 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openrewrite.staticanalysis;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openrewrite.DocumentExample;
import org.openrewrite.Issue;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;
import static org.openrewrite.test.SourceSpecs.other;

@SuppressWarnings("JavadocDeclaration")
class RemoveEmptyJavaDocParametersTest implements RewriteTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new RemoveEmptyJavaDocParameters());
    }

    @DocumentExample
    @Test
    void emptyParam() {
        rewriteRun(
          //language=java
          java(
            """
              class Test {
                  /**
                   * @param arg0
                   */
                  void method(int arg0) {
                  }
              }
              """,
            """
              class Test {
                  void method(int arg0) {
                  }
              }
              """
          )
        );
    }

    @Test
    void emptyReturn() {
        rewriteRun(
          //language=java
          java(
            """
              class Test {
                  /**
                   * @return
                   */
                  int method() {
                  }
              }
              """,
            """
              class Test {              
                  int method() {
                  }
              }
              """
          )
        );
    }

    @Test
    void emptyThrows() {
        rewriteRun(
          //language=java
          java(
            """
              class Test {
                  /**
                   * @throws
                   */
                  void method() throws IllegalStateException {
                  }
              }
              """,
            """
              class Test {              
                  void method() throws IllegalStateException {
                  }
              }
              """
          )
        );
    }

    @Test
    void removeEmptyParams() {
        rewriteRun(
          //language=java
          java(
            """
              class Test {
                  /**
                   * @param arg0 description1
                   *   @param arg1
                   * @param arg2 description3
                   */
                  void method(int arg0, int arg1, int arg2) {
                  }
              }
              """,
            """
              class Test {
                  /**
                   * @param arg0 description1
                   * @param arg2 description3
                   */
                  void method(int arg0, int arg1, int arg2) {
                  }
              }
              """
          )
        );
    }

    @Test
    void multipleEmptyLines() {
        rewriteRun(
          //language=java
          java(
            """
              class Test {
                  /**
                   * @param arg0
                   * 
                   * 
                   * 
                   * @param arg1 description
                   */
                  void method(int arg0, int arg1) {
                  }
              }
              """,
            """
              class Test {
                  /**
                   * @param arg1 description
                   */
                  void method(int arg0, int arg1) {
                  }
              }
              """
          )
        );
    }

    @Test
    void multipleEmptyLines2() {
        rewriteRun(
          //language=java
          java(
            """
              class Test {
                  /**
                   * @param arg0 description
                   * 
                   * 
                   * 
                   * @param arg1
                   */
                  void method(int arg0, int arg1) {
                  }
              }
              """,
            """
              class Test {
                  /**
                   * @param arg0 description
                   *
                   *
                   *
                   */
                  void method(int arg0, int arg1) {
                  }
              }
              """
          )
        );
    }

    @Nested
    class NoSpace {
        @Test
        void emptyParamNoSpace() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /**
                       *@param arg0
                       */
                      void method(int arg0) {
                      }
                  }
                  """,
                """
                  class Test {                  
                      void method(int arg0) {
                      }
                  }
                  """
              )
            );
        }

        @Test
        void emptyReturnNoSpace() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /**
                       *@return
                       */
                      int method() {
                      }
                  }
                  """,
                """
                  class Test {                  
                      int method() {
                      }
                  }
                  """
              )
            );
        }

        @Test
        void emptyThrowsNoSpace() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /**
                       *@throws
                       */
                      void method() throws IllegalStateException {
                      }
                  }
                  """,
                """
                  class Test {                  
                      void method() throws IllegalStateException {
                      }
                  }
                  """
              )
            );
        }

    }

    @Nested
    class SingleLine {
        @Test
        void singleLineParam() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /** @param arg0*/
                      void method(int arg0) {
                      }
                  }
                  """,
                """
                  class Test {                  
                      void method(int arg0) {
                      }
                  }
                  """
              )
            );
        }

        @Test
        void singleLineReturn() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /** @return*/
                      int method() {
                      }
                  }
                  """,
                """
                  class Test {                  
                      int method() {
                      }
                  }
                  """
              )
            );
        }

        @Test
        void singleLineThrows() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /** @throws*/
                      void method() throws IllegalStateException {
                      }
                  }
                  """,
                """
                  class Test {                  
                      void method() throws IllegalStateException {
                      }
                  }
                  """
              )
            );
        }

        @Test
        void singleLineParamNoSpace() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /**@param arg0*/
                      void method(int arg0) {
                      }
                  }
                  """,
                """
                  class Test {                  
                      void method(int arg0) {
                      }
                  }
                  """
              )
            );
        }

        @Test
        void singleLineReturnNoSpace() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /**@return*/
                      int method() {
                      }
                  }
                  """,
                """
                  class Test {                  
                      int method() {
                      }
                  }
                  """
              )
            );
        }

        @Test
        void singleLineThrowsNoSpace() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /**@throws*/
                      void method() throws IllegalStateException {
                      }
                  }
                  """,
                """
                  class Test {                  
                      void method() throws IllegalStateException {
                      }
                  }
                  """
              )
            );
        }
    }

    @Nested
    class FirstLine {
        @Test
        void firstLineParam() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /** @param arg0
                       */
                      void method(int arg0) {
                      }
                  }
                  """,
                """
                  class Test {                  
                      void method(int arg0) {
                      }
                  }
                  """
              )
            );
        }

        @Test
        void firstLineReturn() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /** @return
                       */
                      int method() {
                      }
                  }
                  """,
                """
                  class Test {                  
                      int method() {
                      }
                  }
                  """
              )
            );
        }

        @Test
        void firstLineThrows() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /** @throws
                       */
                      int method() throws IllegalStateException {
                      }
                  }
                  """,
                """
                  class Test {                  
                      int method() throws IllegalStateException {
                      }
                  }
                  """
              )
            );
        }

        @Test
        void firstLineParamNoSpace() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /**@param arg0
                       */
                      void method(int arg0) {
                      }
                  }
                  """,
                """
                  class Test {                  
                      void method(int arg0) {
                      }
                  }
                  """
              )
            );
        }

        @Test
        void firstLineReturnNoSpace() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /**@return
                       */
                      int method() {
                      }
                  }
                  """,
                """
                  class Test {                  
                      int method() {
                      }
                  }
                  """
              )
            );
        }

        @Test
        void firstLineThrowsNoSpace() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /**@throws
                       */
                      int method() throws IllegalStateException {
                      }
                  }
                  """,
                """
                  class Test {                  
                      int method() throws IllegalStateException {
                      }
                  }
                  """
              )
            );
        }
    }

    @Nested
    class EmptyJavaDoc {
        @Test
        void emptyJavaDoc() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /**
                       */
                      void method(int arg0) {
                      }
                  }
                  """,
                """
                  class Test {                  
                      void method(int arg0) {
                      }
                  }
                  """
              )
            );
        }

        @Test
        void emptyJavaDocSingleLine() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /** */
                      void method(int arg0) {
                      }
                  }
                  """,
                """
                  class Test {                  
                      void method(int arg0) {
                      }
                  }
                  """
              )
            );
        }

        @Test
        void emptyJavaDocSingleLineNoSpace() {
            rewriteRun(
              //language=java
              java(
                """
                  class Test {
                      /***/
                      void method(int arg0) {
                      }
                  }
                  """,
                """
                  class Test {                  
                      void method(int arg0) {
                      }
                  }
                  """
              )
            );
        }
    }


    @Test
    @Issue("https://github.com/openrewrite/rewrite/issues/3078")
    void visitingQuarkMustNotFail() {
        rewriteRun(
          other(
            """
              foo
              """
          )
        );
    }

    @Test
    void removeEmptyLinesWhenParam() {
        rewriteRun(
          //language=java
          java(
            """
              class Test {
                  /**
                  * Test not removing
                  *
                  * Successive newlines
                  *
                  *
                  * @param width
                  *
                  *@param height
                  *
                  *
                  */
                  void method(int width, int height) throws IllegalStateException {
                  }
              }
              """,
            """
              class Test {
                  /**
                  * Test not removing
                  *
                  * Successive newlines
                  *
                  *
                  */
                  void method(int width, int height) throws IllegalStateException {
                  }
              }
              """
          )
        );
    }

    @Test
    void removeEmptyLinesWhenThrows() {
        rewriteRun(
          //language=java
          java(
            """
              class Test {
                  /**
                  * Test not removing
                  *
                  * successive newlines
                  *
                  *
                  * @throws
                  *
                  *
                  */
                  void method() throws IllegalStateException {
                  }
              }
              """,
            """
              class Test {
                  /**
                  * Test not removing
                  *
                  * successive newlines
                  *
                  *
                  */
                  void method() throws IllegalStateException {
                  }
              }
              """
          )
        );
    }

    @Test
    void removeEmptyLinesWhenReturn() {
        rewriteRun(
          //language=java
          java(
            """
              class Test {
                  /**
                  * Test not removing
                  *
                  * successive newlines
                  *
                  *
                  * @return
                  *
                  *
                  */
                  void method() throws IllegalStateException {
                  }
              }
              """,
            """
              class Test {
                  /**
                  * Test not removing
                  *
                  * successive newlines
                  *
                  *
                  */
                  void method() throws IllegalStateException {
                  }
              }
              """
          )
        );
    }

    @Test
    @Issue("https://github.com/openrewrite/rewrite-static-analysis/issues/98")
    void issueTest() {
        rewriteRun(
          //language=java
          java(
            """
              class Test {
                  /**
                  * Renders this page and returns an image representation of itself.
                  *
                  * @param width
                  * @param height
                  *
                  */
                  void method(int width, int height) throws IllegalStateException {
                  }
              }
              """,
            """
              class Test {
                  /**
                  * Renders this page and returns an image representation of itself.
                  *
                  */
                  void method(int width, int height) throws IllegalStateException {
                  }
              }
              """
          )
        );
    }

    @Test
    void combinationTest() {
        rewriteRun(
          //language=java
          java(
            """
              class Test {
                  /**
                  * Renders only param1, return1 and IllegalStateException with description
                  *
                  *
                  * and skip rest.
                  *
                  * @param param1 param1 description
                  *
                  * param1 further description
                  *
                  *
                  *
                  *@param
                  *
                  * @return return1 description
                  * 
                  * return1 further description
                  *
                  *
                  *
                  *@return
                  *
                  *
                  * @throws IllegalStateException throws description
                  *
                  * further description
                  *
                  *
                  *
                  * @throws IllegalStateException
                  * 
                  *
                  *@throws IllegalStateException
                  *
                  *
                  * @throws
                  *
                  */
                  void method(int param1) throws IllegalStateException {
                  }
              }
              """,
            """
              class Test {
                  /**
                  * Renders only param1, return1 and IllegalStateException with description
                  *
                  *
                  * and skip rest.
                  *
                  * @param param1 param1 description
                  *
                  * param1 further description
                  *
                  *
                  *
                  * @return return1 description
                  * 
                  * return1 further description
                  *
                  *
                  *
                  * @throws IllegalStateException throws description
                  *
                  * further description
                  *
                  *
                  *
                  */
                  void method(int param1) throws IllegalStateException {
                  }
              }
              """
          )
        );
    }
}
