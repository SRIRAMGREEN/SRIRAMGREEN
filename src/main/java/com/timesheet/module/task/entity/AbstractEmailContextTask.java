package com.timesheet.module.task.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class AbstractEmailContextTask {

    private String from;

    private String to;

    private String subject;

    private String email;

    private String attachment;

    private String fromDisplayName;

    private String emailLanguage;

    private String displayName;

    private String templateLocation;
    private Map<String, Object> context;

    public AbstractEmailContextTask() {
        this.context = new HashMap<>();
    }

    public Object put(String key, Object value) {
        return key == null ? null : this.context.put(key.intern(), value);
    }
}

