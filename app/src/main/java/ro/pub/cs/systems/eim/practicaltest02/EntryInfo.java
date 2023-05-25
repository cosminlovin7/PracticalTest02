package ro.pub.cs.systems.eim.practicaltest02;

import java.time.LocalDateTime;

public class EntryInfo {
    private String value;
    private LocalDateTime time;

    public EntryInfo(String value, LocalDateTime time) {
        this.value = value;
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
