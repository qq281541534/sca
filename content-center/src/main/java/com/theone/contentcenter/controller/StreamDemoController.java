package com.theone.contentcenter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stream")
@RequiredArgsConstructor
public class StreamDemoController {

    private final Source source;

    @GetMapping("/test")
    public String test() {
        this.source.output()
                .send(
                        MessageBuilder.withPayload(
                                "消息"
                        ).build()
                );
        return "success";
    }

}
