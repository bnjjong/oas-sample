/*
 * MIT License
 *
 * Copyright (c) 2023-present, Henry<dogfootmaster@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.df.henry.oas.sample.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import capital.scalable.restdocs.section.SectionBuilder;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.df.henry.oas.sample.model.fixture.MemberFixture;
import io.df.henry.oas.sample.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.templates.TemplateFormats;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@AutoConfigureRestDocs
@ActiveProfiles({"test"})
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
class MemberApiTest {

  private MockMvc mvc;
  private ObjectMapper objectMapper;
  private final String baseUrl = "/member";
  @Mock
  private MemberService service;

  @BeforeEach
  public void setup(RestDocumentationContextProvider provider) {
    this.objectMapper = new ObjectMapper();
    RestDocumentationResultHandler restDocs =
        MockMvcRestDocumentationWrapper.document(
            "{class-name}/{method-name}",
            Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
            Preprocessors.preprocessResponse(
                ResponseModifyingPreprocessors.replaceBinaryContent(),
                ResponseModifyingPreprocessors.limitJsonArrayLength(this.objectMapper),
                Preprocessors.prettyPrint()),
            AutoDocumentation.sectionBuilder()
                .snippetNames(SectionBuilder.DEFAULT_SNIPPETS.stream().toList())
                .skipEmpty(true)
                .build());
    this.mvc = this.initMockMvc(new MemberApi(service), provider, restDocs);
  }

  private MockMvc initMockMvc(
      MemberApi controller,
      RestDocumentationContextProvider provider,
      RestDocumentationResultHandler restDocs) {
    return MockMvcBuilders.standaloneSetup(controller)
        .apply(
            MockMvcRestDocumentation.documentationConfiguration(provider)
                .uris()
                .withScheme("http")
                .withHost("localhost")
                .withPort(8080)
                .and()
                .snippets()
                .withEncoding("UTF-8")
                .withTemplateFormat(TemplateFormats.asciidoctor())
                .withDefaults(
                    CliDocumentation.curlRequest(),
                    HttpDocumentation.httpRequest(),
                    HttpDocumentation.httpResponse(),
                    AutoDocumentation.requestFields(),
                    AutoDocumentation.responseFields(),
                    AutoDocumentation.pathParameters(),
                    AutoDocumentation.requestParameters(),
                    AutoDocumentation.description(),
                    AutoDocumentation.methodAndPath(),
                    AutoDocumentation.section(),
                    AutoDocumentation.links(),
                    AutoDocumentation.embedded(),
                    AutoDocumentation.modelAttribute(
                        (new RequestMappingHandlerAdapter()).getArgumentResolvers())))
        .alwaysDo(JacksonResultHandlers.prepareJackson(this.objectMapper))
        .alwaysDo(restDocs)
        .build();
  }

  @Nested
  class FindById {
    @DisplayName("내 정보 조회")
    @Test
    void success() throws Exception {
      when(service.findById(any()))
          .thenReturn(MemberFixture.henryDto());

      // when then
      mvc.perform(get(baseUrl+"/1")
          .contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpectAll(status().isOk())
          .andDo(print());
    }
  }
  @Test
  void findById() {}

  @Test
  void save() {}

  @Test
  void modify() {}

  @Test
  void deleeteById() {}
}
