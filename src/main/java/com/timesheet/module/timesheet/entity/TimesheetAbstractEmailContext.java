package com.timesheet.module.timesheet.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class TimesheetAbstractEmailContext  {

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

    public TimesheetAbstractEmailContext() {
        this.context = new HashMap<>();
    }

    public Object put(String key, Object value) {
        return key == null ? null : this.context.put(key.intern(), value);
    }
}

