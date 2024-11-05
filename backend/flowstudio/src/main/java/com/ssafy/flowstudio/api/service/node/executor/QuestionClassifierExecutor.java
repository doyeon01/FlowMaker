package com.ssafy.flowstudio.api.service.node.executor;

import com.ssafy.flowstudio.api.service.node.RedisService;
import com.ssafy.flowstudio.domain.chat.entity.Chat;
import com.ssafy.flowstudio.domain.node.entity.Node;
import com.ssafy.flowstudio.domain.node.entity.NodeType;
import com.ssafy.flowstudio.domain.node.entity.QuestionClassifier;
import org.springframework.stereotype.Component;

@Component
public class QuestionClassifierExecutor extends NodeExecutor {

    public QuestionClassifierExecutor(RedisService redisService) {
        super(redisService);
    }

    // TODO : model 데이터 넣기 + param_list 작성
    // TODO : user 정보 필요 (API KEY)
    @Override
    public void execute(Node node, Chat chat) {
        QuestionClassifier questionClassifier = (QuestionClassifier) node;
        System.out.println("QuestionClassifierExecutor");
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.QUESTION_CLASSIFIER;
    }
}