package com.ssafy.flowstudio.docs.chatflow;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.flowstudio.api.controller.chatflow.ChatFlowController;
import com.ssafy.flowstudio.api.controller.chatflow.request.ChatFlowRequest;
import com.ssafy.flowstudio.api.service.chatflow.ChatFlowService;
import com.ssafy.flowstudio.api.service.chatflow.request.ChatFlowServiceRequest;
import com.ssafy.flowstudio.api.service.chatflow.response.*;
import com.ssafy.flowstudio.api.service.node.response.AnswerResponse;
import com.ssafy.flowstudio.api.service.node.response.LlmResponse;
import com.ssafy.flowstudio.api.service.node.response.NodeResponse;
import com.ssafy.flowstudio.api.service.node.response.StartResponse;
import com.ssafy.flowstudio.api.service.user.response.UserResponse;
import com.ssafy.flowstudio.docs.RestDocsSupport;
import com.ssafy.flowstudio.domain.node.entity.NodeType;
import com.ssafy.flowstudio.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatFlowDocsTest extends RestDocsSupport {

    private ChatFlowService chatFlowService = mock(ChatFlowService.class);

    @Override
    protected Object initController() {
        return new ChatFlowController(chatFlowService);
    }

    @DisplayName("챗플로우 목록을 조회한다")
    @Test
    void getChatFlows() throws Exception {
        // given
        UserResponse author = UserResponse.builder()
                .id(1L)
                .username("username")
                .nickname("nickname")
                .profileImage("profileImage")
                .build();

        CategoryResponse category1 = CategoryResponse.builder()
                .categoryId(1L)
                .name("카테고리1")
                .build();

        CategoryResponse category2 = CategoryResponse.builder()
                .categoryId(2L)
                .name("카테고리2")
                .build();

        ChatFlowListResponse response = ChatFlowListResponse.builder()
                .chatFlowId(1L)
                .title("title")
                .description("description")
                .author(author)
                .thumbnail("1")
                .categories(List.of(category1, category2))
                .isPublic(false)
                .build();

        given(chatFlowService.getChatFlows(any(User.class)))
                .willReturn(List.of(response));

        // when
        ResultActions perform = mockMvc.perform(
                get("/api/v1/chat-flows")
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-chatflow-list",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("ChatFlow")
                                .summary("챗플로우 목록 조회")
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING)
                                                .description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("메시지"),
                                        fieldWithPath("data[].chatFlowId").type(JsonFieldType.NUMBER)
                                                .description("챗플로우 아이디"),
                                        fieldWithPath("data[].title").type(JsonFieldType.STRING)
                                                .description("챗플로우 제목"),
                                        fieldWithPath("data[].description").type(JsonFieldType.STRING)
                                                .description("챗플로우 설명"),
                                        fieldWithPath("data[].thumbnail").type(JsonFieldType.STRING)
                                                .description("챗플로우 썸네일"),
                                        fieldWithPath("data[].public").type(JsonFieldType.BOOLEAN)
                                                .description("챗플로우 공유 여부"),
                                        fieldWithPath("data[].author.id").type(JsonFieldType.NUMBER)
                                                .description("챗플로우 작성자 아이디"),
                                        fieldWithPath("data[].author.id").type(JsonFieldType.NUMBER)
                                                .description("챗플로우 작성자 아이디"),
                                        fieldWithPath("data[].author.username").type(JsonFieldType.STRING)
                                                .description("챗플로우 작성자 이메일"),
                                        fieldWithPath("data[].author.nickname").type(JsonFieldType.STRING)
                                                .description("챗플로우 작성자 닉네임"),
                                        fieldWithPath("data[].author.profileImage").type(JsonFieldType.STRING)
                                                .description("챗플로우 작성자 프로필이미지"),
                                        fieldWithPath("data[].categories[].categoryId").type(JsonFieldType.NUMBER)
                                                .description("카테고리 아이디"),
                                        fieldWithPath("data[].categories[].name").type(JsonFieldType.STRING)
                                                .description("카테고리 이름"))
                                .build())));

    }

    @DisplayName("챗플로우를 조회한다")
    @Test
    void getChatFlow() throws Exception {
        // given
        EdgeResponse edge1 = EdgeResponse.builder()
                .edgeId(1L)
                .sourceNodeId(1L)
                .targetNodeId(2L)
                .build();

        EdgeResponse edge2 = EdgeResponse.builder()
                .edgeId(1L)
                .sourceNodeId(2L)
                .targetNodeId(3L)
                .build();

        CoordinateResponse coordinate = CoordinateResponse.builder()
                .x(100)
                .y(100)
                .build();

        NodeResponse node1 = StartResponse.builder()
                .nodeId(1L)
                .name("Start")
                .type(NodeType.START)
                .coordinate(coordinate)
                .inputEdges(List.of(edge1))
                .outputEdges(List.of())
                .maxLength(10)
                .build();

        NodeResponse node2 = LlmResponse.builder()
                .nodeId(2L)
                .name("LLM")
                .type(NodeType.LLM)
                .promptSystem("promptSystem")
                .promptUser("promptUser")
                .coordinate(coordinate)
                .inputEdges(List.of(edge1))
                .outputEdges(List.of(edge2))
                .build();

        NodeResponse node3 = AnswerResponse.builder()
                .nodeId(3L)
                .name("Answer")
                .type(NodeType.ANSWER)
                .coordinate(coordinate)
                .outputEdges(List.of())
                .inputEdges(List.of(edge2))
                .outputMessage("outputMessage")
                .build();

        ChatFlowResponse response = ChatFlowResponse.builder()
                .chatFlowId(1L)
                .title("title")
                .nodes(List.of(node1, node2, node3))
                .build();

        given(chatFlowService.getChatFlow(any(User.class), any(Long.class)))
                .willReturn(response);

        // when
        ResultActions perform = mockMvc.perform(
                get("/api/v1/chat-flows/{chatFlowId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-chatflow-detail",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("ChatFlow")
                                .summary("챗플로우 상세 조회")
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING)
                                                .description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("메시지"),
                                        fieldWithPath("data.chatFlowId").type(JsonFieldType.NUMBER)
                                                .description("챗플로우 아이디"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING)
                                                .description("챗플로우 제목"),
                                        fieldWithPath("data.nodes[].nodeId").type(JsonFieldType.NUMBER)
                                                .description("노드 아이디"),
                                        fieldWithPath("data.nodes[].name").type(JsonFieldType.STRING)
                                                .description("노드 이름"),
                                        fieldWithPath("data.nodes[].type").type(JsonFieldType.STRING)
                                                .description("노드 타입"),
                                        fieldWithPath("data.nodes[].promptSystem").optional().type(JsonFieldType.STRING)
                                                .description("LLM노드 시스템프롬프트"),
                                        fieldWithPath("data.nodes[].promptUser").optional().type(JsonFieldType.STRING)
                                                .description("LLM노드 유저프롬프트"),
                                        fieldWithPath("data.nodes[].outputMessage").optional().type(JsonFieldType.STRING)
                                                .description("Answer노드 출력메시지"),
                                        fieldWithPath("data.nodes[].maxLength").optional().type(JsonFieldType.NUMBER)
                                                .description("Start 노드 입력 최대길이"),
                                        fieldWithPath("data.nodes[].coordinate.x").type(JsonFieldType.NUMBER)
                                                .description("노드 X좌표"),
                                        fieldWithPath("data.nodes[].coordinate.y").type(JsonFieldType.NUMBER)
                                                .description("노드 Y좌표"),
                                        fieldWithPath("data.nodes[].outputEdges[].edgeId").optional().type(JsonFieldType.NUMBER)
                                                .description("output 간선 아이디"),
                                        fieldWithPath("data.nodes[].outputEdges").optional().type(JsonFieldType.ARRAY)
                                                .description("output 간선"),
                                        fieldWithPath("data.nodes[].outputEdges[].sourceNodeId").optional().type(JsonFieldType.NUMBER)
                                                .description("output 간선 출발노드 아이디"),
                                        fieldWithPath("data.nodes[].outputEdges[].targetNodeId").optional().type(JsonFieldType.NUMBER)
                                                .description("output 간선 도착노드 아이디"),
                                        fieldWithPath("data.nodes[].inputEdges[].edgeId").optional().type(JsonFieldType.NUMBER)
                                                .description("input 간선 아이디"),
                                        fieldWithPath("data.nodes[].inputEdges[].sourceNodeId").optional().type(JsonFieldType.NUMBER)
                                                .description("input 간선 출발노드 아이디"),
                                        fieldWithPath("data.nodes[].inputEdges[].targetNodeId").optional().type(JsonFieldType.NUMBER)
                                                .description("input 간선 도착노드 아이디")
                                )
                                .build()
                        )
                ));

    }

    @DisplayName("챗플로우를 생성한다")
    @Test
    void createChatFlow() throws Exception {
        // given
        ChatFlowRequest request = ChatFlowRequest.builder()
                .title("title")
                .thumbnail("thumbnail")
                .description("description")
                .categoryIds(List.of(1L, 2L))
                .build();

        CoordinateResponse coordinate = CoordinateResponse.builder()
                .x(100)
                .y(100)
                .build();

        NodeResponse node1 = StartResponse.builder()
                .nodeId(1L)
                .name("Start")
                .type(NodeType.START)
                .coordinate(coordinate)
                .inputEdges(List.of())
                .outputEdges(List.of())
                .maxLength(10)
                .build();

        ChatFlowResponse response = ChatFlowResponse.builder()
                .chatFlowId(1L)
                .title("title")
                .nodes(List.of(node1))
                .build();

        given(chatFlowService.createChatFlow(any(User.class), any(ChatFlowServiceRequest.class)))
                .willReturn(response);

        // when
        ResultActions perform = mockMvc.perform(
                post("/api/v1/chat-flows")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("create-chatflow",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("ChatFlow")
                                .summary("챗플로우 생성")
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING)
                                                .description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("메시지"),
                                        fieldWithPath("data.chatFlowId").type(JsonFieldType.NUMBER)
                                                .description("챗플로우 아이디"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING)
                                                .description("챗플로우 제목"),
                                        fieldWithPath("data.nodes[].nodeId").type(JsonFieldType.NUMBER)
                                                .description("노드 아이디"),
                                        fieldWithPath("data.nodes[].name").type(JsonFieldType.STRING)
                                                .description("노드 이름"),
                                        fieldWithPath("data.nodes[].type").type(JsonFieldType.STRING)
                                                .description("노드 타입"),
                                        fieldWithPath("data.nodes[].maxLength").optional().type(JsonFieldType.NUMBER)
                                                .description("Start 노드 입력 최대길이"),
                                        fieldWithPath("data.nodes[].coordinate.x").type(JsonFieldType.NUMBER)
                                                .description("노드 X좌표"),
                                        fieldWithPath("data.nodes[].coordinate.y").type(JsonFieldType.NUMBER)
                                                .description("노드 Y좌표"),
                                        fieldWithPath("data.nodes[].inputEdges").optional().type(JsonFieldType.ARRAY)
                                                .description("input 간선 아이디"),
                                        fieldWithPath("data.nodes[].outputEdges").optional().type(JsonFieldType.ARRAY)
                                                .description("output 간선")
                                )
                                .build()
                        )
                ));
    }

    @DisplayName("챗플로우를 수정한다")
    @Test
    void updateChatFlow() throws Exception {
        // given
        ChatFlowRequest request = ChatFlowRequest.builder()
                .title("title")
                .thumbnail("thumbnail")
                .description("description")
                .categoryIds(List.of(1L, 2L))
                .build();

        ChatFlowUpdateResponse response = ChatFlowUpdateResponse.builder()
                .chatFlowId(1L)
                .title("title")
                .description("description")
                .thumbnail("thumbnail")
                .categories(List.of(CategoryResponse.builder()
                        .categoryId(1L)
                        .name("카테고리1")
                        .build(), CategoryResponse.builder()
                        .categoryId(2L)
                        .name("카테고리2")
                        .build()))
                .build();

        given(chatFlowService.updateChatFlow(any(User.class), any(Long.class), any(ChatFlowServiceRequest.class)))
                .willReturn(response);

        // when
        ResultActions perform = mockMvc.perform(
                patch("/api/v1/chat-flows/{chatFlowId}", 1L)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("update-chatflow",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("ChatFlow")
                                .summary("챗플로우 수정")
                                .pathParameters(
                                        parameterWithName("chatFlowId").description("챗플로우 아이디")
                                )
                                .requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING)
                                                .description("챗플로우 제목"),
                                        fieldWithPath("thumbnail").type(JsonFieldType.STRING)
                                                .description("챗플로우 썸네일"),
                                        fieldWithPath("description").type(JsonFieldType.STRING)
                                                .description("챗플로우 설명"),
                                        fieldWithPath("categoryIds").type(JsonFieldType.ARRAY)
                                                .description("카테고리 아이디 리스트")
                                )
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING)
                                                .description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("메시지"),
                                        fieldWithPath("data.chatFlowId").type(JsonFieldType.NUMBER)
                                                .description("챗플로우 아이디"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING)
                                                .description("챗플로우 제목"),
                                        fieldWithPath("data.description").type(JsonFieldType.STRING)
                                                .description("챗플로우 설명"),
                                        fieldWithPath("data.thumbnail").type(JsonFieldType.STRING)
                                                .description("챗플로우 썸네일"),
                                        fieldWithPath("data.categories[].categoryId").type(JsonFieldType.NUMBER)
                                                .description("카테고리 아이디"),
                                        fieldWithPath("data.categories[].name").type(JsonFieldType.STRING)
                                                .description("카테고리 이름")
                                )
                                .build())));
    }

    @DisplayName("챗플로우를 삭제한다")
    @Test
    void deleteChatFlow() throws Exception {
        // when
        ResultActions perform = mockMvc.perform(
                delete("/api/v1/chat-flows/{chatFlowId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("delete-chatflow",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("ChatFlow")
                                .summary("챗플로우 삭제")
                                .pathParameters(
                                        parameterWithName("chatFlowId").description("챗플로우 아이디")
                                )
                                .responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING)
                                                .description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.NULL)
                                                .description("data")
                                )
                                .build())));
    }

}
