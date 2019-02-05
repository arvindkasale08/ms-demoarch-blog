package com.msdemoarch.blog.kafka;

import com.msdemoarch.blog.kafka.service.PostSummaryPipeline;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    PostSummaryPipeline pipeline = applicationReadyEvent
        .getApplicationContext()
        .getBean(PostSummaryPipeline.class);
    pipeline.start();
  }
}
