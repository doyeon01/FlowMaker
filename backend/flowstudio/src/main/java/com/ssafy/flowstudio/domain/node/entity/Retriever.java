package com.ssafy.flowstudio.domain.node.entity;

import com.ssafy.flowstudio.domain.document.entity.Document;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Retriever extends Node {

    @OneToOne(fetch = FetchType.LAZY)
    private Document document;

    @Builder
    private Retriever(Document document) {
        this.document = document;
    }

    public static Retriever create(Document document) {
        return Retriever.builder()
            .document(document)
            .build();
    }
}
