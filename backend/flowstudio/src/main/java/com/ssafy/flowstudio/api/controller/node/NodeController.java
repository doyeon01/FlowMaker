package com.ssafy.flowstudio.api.controller.node;

import com.ssafy.flowstudio.api.controller.node.request.NodeCreateRequest;
import com.ssafy.flowstudio.api.service.node.NodeService;
import com.ssafy.flowstudio.api.service.node.response.NodeCreateResponse;
import com.ssafy.flowstudio.common.annotation.CurrentUser;
import com.ssafy.flowstudio.common.payload.ApiResponse;
import com.ssafy.flowstudio.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class NodeController {

    private final NodeService nodeService;

    /**
     * 노드 생성
     * @param user
     * @param request
     * @return 
     */
    @PostMapping("/api/v1/chat-flows/nodes")
    public ApiResponse<NodeCreateResponse> createNode(
            @CurrentUser User user,
            @Valid @RequestBody NodeCreateRequest request
    ) {
        return ApiResponse.ok(nodeService.createNode(user, request.toServiceRequest()));
    }

    /**
     * 노드 삭제
     * @param user
     * @param nodeId
     * @return
     */
    @DeleteMapping("/api/v1/chat-flows/nodes/{nodeId}")
    public ApiResponse<Void> deleteNode(
            @CurrentUser User user,
            @PathVariable Long nodeId
    ) {
        nodeService.deleteNode(user, nodeId);

        return ApiResponse.ok();
    }

}
